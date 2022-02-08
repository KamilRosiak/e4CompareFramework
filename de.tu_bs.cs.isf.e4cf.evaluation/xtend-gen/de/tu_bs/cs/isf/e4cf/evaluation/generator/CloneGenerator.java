package de.tu_bs.cs.isf.e4cf.evaluation.generator;

import com.google.common.base.Objects;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.GsonExportService;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.DSValidator;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.evaluation.dialog.GeneratorOptions;
import de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@Singleton
@Creatable
@SuppressWarnings("all")
public class CloneGenerator {
  public static class Variant {
    public Tree tree;
    
    public Tree trackingTree;
    
    public int parentIndex;
    
    public int crossOverParentIndex = (-1);
    
    public int index;
    
    public boolean isCorrect = false;
    
    public Variant(final Tree t, final Tree trackingT, final int parentIndex, final int index) {
      this.tree = t;
      this.trackingTree = trackingT;
      this.parentIndex = parentIndex;
      this.index = index;
    }
    
    public Variant(final Tree t, final Tree trackingT, final int parentIndex, final int index, final boolean isCorrect) {
      this.tree = t;
      this.trackingTree = trackingT;
      this.parentIndex = parentIndex;
      this.index = index;
      this.isCorrect = isCorrect;
    }
  }
  
  @Inject
  private GsonExportService gsonExportService;
  
  @Inject
  private CloneLogger logger;
  
  @Inject
  private CloneHelper helper;
  
  @Inject
  private Taxonomy taxonomy;
  
  @Inject
  private ServiceContainer services;
  
  public void go(final Tree tree, final GeneratorOptions options) {
    this.go(tree, options, true, true);
  }
  
  public List<CloneGenerator.Variant> go(final Tree tree, final GeneratorOptions options, final boolean save, final boolean printLogs) {
    this.helper.setTrackingTree(tree);
    final boolean isOriginalSyntaxCorrect = DSValidator.checkSyntax(tree.getRoot());
    if (printLogs) {
      InputOutput.<String>println(("Original Syntax Correct: " + Boolean.valueOf(isOriginalSyntaxCorrect)));
    }
    Tree _trackingTree = this.helper.getTrackingTree();
    CloneGenerator.Variant _variant = new CloneGenerator.Variant(tree, _trackingTree, 0, 0, isOriginalSyntaxCorrect);
    ArrayList<CloneGenerator.Variant> variants = CollectionLiterals.<CloneGenerator.Variant>newArrayList(_variant);
    double _ceil = Math.ceil((options.variants * (options.crossoverPercentage / 100f)));
    int crossoverTargetNum = ((int) _ceil);
    if (printLogs) {
      InputOutput.<String>println(("Target Crossovers: " + Integer.valueOf(crossoverTargetNum)));
    }
    final long starttime = System.nanoTime();
    try {
      for (int pass = 1; (pass <= options.variants); pass++) {
        {
          if (printLogs) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("Starting Variant Pass #");
            _builder.append(pass);
            InputOutput.<String>println(_builder.toString());
          }
          boolean crossoverFallback = false;
          if (((pass > (options.variants / 2)) && (crossoverTargetNum > 0))) {
            boolean _doCrossover = this.doCrossover(variants);
            boolean _not = (!_doCrossover);
            crossoverFallback = _not;
            if ((!crossoverFallback)) {
              crossoverTargetNum--;
            }
          } else {
            crossoverFallback = true;
          }
          if (crossoverFallback) {
            final CloneGenerator.Variant predecessor = CloneHelper.<CloneGenerator.Variant>random(variants);
            this.logger.logVariant(predecessor.index, variants.size());
            final TreeImpl currentTree = this.helper.deepCopy(predecessor.tree);
            this.helper.setTrackingTree(predecessor.trackingTree);
            final double nodeToSourceFactor = 6.0;
            final int modToLineFactor = 10;
            int _size = IterableExtensions.size(currentTree.getRoot().depthFirstSearch());
            double _divide = (_size / (nodeToSourceFactor * modToLineFactor));
            double _ceil_1 = Math.ceil(_divide);
            final double numModifications = (_ceil_1 * options.variantChangeDegree);
            for (int mod = 1; (mod <= numModifications); mod++) {
              int _nextInt = new Random().nextInt(100);
              boolean _lessThan = (_nextInt < options.modificationRatioPercentage);
              if (_lessThan) {
                this.taxonomy.performType2Modification(currentTree, options.isSyntaxSafe);
              } else {
                this.taxonomy.performType3Modification(currentTree, options.isSyntaxSafe);
              }
            }
            final boolean isVariantSyntaxCorrect = DSValidator.checkSyntax(currentTree.getRoot());
            if (printLogs) {
              InputOutput.<String>println(("  Sanity Syntax Check: " + Boolean.valueOf(isVariantSyntaxCorrect)));
            }
            this.logger.logRaw((CloneST.SYNTAX_CORRECT_FLAG + Boolean.valueOf(isVariantSyntaxCorrect)));
            Tree _trackingTree_1 = this.helper.getTrackingTree();
            int _size_1 = variants.size();
            CloneGenerator.Variant _variant_1 = new CloneGenerator.Variant(currentTree, _trackingTree_1, predecessor.index, _size_1, isVariantSyntaxCorrect);
            variants.add(_variant_1);
          }
        }
      }
    } finally {
      if (save) {
        this.save(variants);
      }
      final long endtime = System.nanoTime();
      if (printLogs) {
        InputOutput.<String>println(("Runtime: " + Long.valueOf((endtime - starttime))));
      }
    }
    return variants;
  }
  
  /**
   * Performs a crossover on two variants of the given list.
   * @return false if the crossover could not be achieved.
   */
  private boolean doCrossover(final List<CloneGenerator.Variant> variants) {
    final Function1<CloneGenerator.Variant, Boolean> _function = (CloneGenerator.Variant v) -> {
      final Function1<Node, Boolean> _function_1 = (Node n) -> {
        NodeType _standardizedNodeType = n.getStandardizedNodeType();
        return Boolean.valueOf((_standardizedNodeType == NodeType.METHOD_DECLARATION));
      };
      return Boolean.valueOf(IterableExtensions.<Node>exists(v.tree.getRoot().breadthFirstSearch(), _function_1));
    };
    final CloneGenerator.Variant receiver = CloneHelper.<CloneGenerator.Variant>random(IterableExtensions.<CloneGenerator.Variant>filter(variants, _function));
    if ((receiver == null)) {
      return false;
    }
    final Function1<CloneGenerator.Variant, Boolean> _function_1 = (CloneGenerator.Variant v) -> {
      return Boolean.valueOf((((((v.index != 0) && IterableExtensions.<Node>exists(v.tree.getRoot().breadthFirstSearch(), ((Function1<Node, Boolean>) (Node n) -> {
        NodeType _standardizedNodeType = n.getStandardizedNodeType();
        return Boolean.valueOf((_standardizedNodeType == NodeType.METHOD_DECLARATION));
      }))) && 
        (!Objects.equal(v, receiver))) && (!this.logger.reconstructVariantTaxonomy(receiver.index).contains(Integer.valueOf(v.index)))) && 
        (!this.logger.reconstructVariantTaxonomy(v.index).contains(Integer.valueOf(receiver.index)))));
    };
    final CloneGenerator.Variant donor = CloneHelper.<CloneGenerator.Variant>random(IterableExtensions.<CloneGenerator.Variant>filter(variants, _function_1));
    if ((receiver == null)) {
      return false;
    }
    final CloneGenerator.Variant crossoverVariant = this.taxonomy.performCrossOver(receiver, donor);
    if ((crossoverVariant != null)) {
      final boolean isVariantSyntaxCorrect = DSValidator.checkSyntax(crossoverVariant.tree.getRoot());
      this.logger.logRaw((CloneST.SYNTAX_CORRECT_FLAG + Boolean.valueOf(isVariantSyntaxCorrect)));
      crossoverVariant.isCorrect = isVariantSyntaxCorrect;
      variants.add(crossoverVariant);
      return true;
    }
    return false;
  }
  
  /**
   * Saves tree strings to json file and log
   */
  private void save(final List<CloneGenerator.Variant> variants) {
    this.logger.projectFolderName = " 02 Trees";
    Path _xifexpression = null;
    Path _outPutDirBasedOnSelection = this.logger.getOutPutDirBasedOnSelection();
    boolean _tripleNotEquals = (_outPutDirBasedOnSelection != null);
    if (_tripleNotEquals) {
      _xifexpression = this.logger.getOutPutDirBasedOnSelection();
    } else {
      _xifexpression = this.logger.getOutputPath();
    }
    final Path selectedPath = _xifexpression;
    String subfolderName = "GeneratedVariants";
    int i = 1;
    while (Files.exists(selectedPath.resolve(subfolderName))) {
      {
        subfolderName = (("GeneratedVariants (" + Integer.valueOf(i)) + ")");
        i++;
      }
    }
    final Path outputDirectory = selectedPath.resolve(subfolderName);
    final String selectionFileName = this.services.rcpSelectionService.getCurrentSelectionFromExplorer().getFileName();
    for (final CloneGenerator.Variant variant : variants) {
      {
        final String variantSerialized = this.gsonExportService.exportTree(((TreeImpl) variant.tree));
        String infix = ("." + Integer.valueOf(variant.parentIndex));
        if ((variant.crossOverParentIndex != (-1))) {
          String _infix = infix;
          infix = (_infix + ("," + Integer.valueOf(variant.crossOverParentIndex)));
        }
        String _infix_1 = infix;
        infix = (_infix_1 + ("~" + Integer.valueOf(variant.index)));
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(selectionFileName);
        _builder.append(infix);
        _builder.append(".tree");
        final String fileName = _builder.toString();
        this.logger.write(outputDirectory, fileName, CollectionLiterals.<String>newArrayList(variantSerialized));
      }
    }
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(selectionFileName);
    _builder.append(".log");
    this.logger.outputLog(outputDirectory, _builder.toString());
    this.logger.resetLogs();
  }
}
