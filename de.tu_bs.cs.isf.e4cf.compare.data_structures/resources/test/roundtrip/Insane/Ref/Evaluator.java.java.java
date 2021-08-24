package de.tu_bs.cs.isf.e4cf.evaluation.evaluator;

import com.google.common.base.Objects;
import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.NodeResultElement;
import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.ResultElement;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonExportService;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.evaluation.dialog.EvaluatorOptions;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneLogger;
import de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class CloneEvaluator {

	/*
   * Data class that holds evaluation information
   */
	public static class Evaluation {

		public NodeComparison comp;

		public TreeImpl tree;

		public String name;

		public float precision;

		public float recall;

		public List<Comparison<Node>> truePositives;

		public List<Comparison<Node>> falsePositives;

		public List<Comparison<Node>> trueNegatives;

		public List<Comparison<Node>> falseNegatives;
	}

	@Inject
	private CloneLogger logger;

	@Inject
	private ServiceContainer services;

	@Inject
	private ReaderManager readerManager;

	@Inject
	private GsonExportService gsonExportService;

	@Inject
	private CloneHelper helper;

	private final String PROJECT_PATH = " 03 Metrics";

	private final SortingMatcher matcher = new SortingMatcher();

	private final MetricImpl metric = new MetricImpl("Metric");

	private final CompareEngineHierarchical engine = new CompareEngineHierarchical(this.matcher, this.metric);

	private Path outputDir;

	/*
   * Entry point of the evaluator
   * Evaluates the selected directory
   */
	public void evaluate(final EvaluatorOptions options) {
		try {
			final FileTreeElement directoryElement = this.services.rcpSelectionService.getCurrentSelectionFromExplorer();
			directoryElement.getChildren();
			Tree originalTree = null;
			final HashMap<Integer, Tree> variantTrees = CollectionLiterals.<Integer, Tree>newHashMap();
			final HashMap<Integer, Tree> allTrees = CollectionLiterals.<Integer, Tree>newHashMap();
			final ArrayList<String> allVariantNames = CollectionLiterals.<String>newArrayList();
			List<FileTreeElement> _children = directoryElement.getChildren();
			for (final FileTreeElement child : _children) {
				{
					final String name = child.getFileName();
					boolean _endsWith = name.endsWith("0~0.tree");
					if (_endsWith) {
						originalTree = this.readerManager.readFile(child);
						allTrees.put(Integer.valueOf(0), originalTree);
						allVariantNames.add(name);
					} else {
						boolean _endsWith_1 = name.endsWith(".tree");
						if (_endsWith_1) {
							int index = Integer.parseInt(ListExtensions.<String>reverse(((List<String>) Conversions.doWrapArray(name.split("[~.]")))).get(1));
							Tree tree = this.readerManager.readFile(child);
							variantTrees.put(Integer.valueOf(index), tree);
							allTrees.put(Integer.valueOf(index), tree);
							allVariantNames.add(name);
						} else {
							boolean _endsWith_2 = name.endsWith(".log");
							if (_endsWith_2) {
								this.logger.read(child.getAbsolutePath());
							}
						}
					}
				}
			}
			final ArrayList<String> evaluatorResults = CollectionLiterals.<String>newArrayList();
			this.logger.projectFolderName = this.PROJECT_PATH;
			Path _xifexpression = null;
			Path _outPutDirBasedOnSelection = this.logger.getOutPutDirBasedOnSelection();
			boolean _tripleNotEquals = (_outPutDirBasedOnSelection != null);
			if (_tripleNotEquals) {
				_xifexpression = this.logger.getOutPutDirBasedOnSelection();
			} else {
				_xifexpression = this.logger.getOutputPath().resolve("Evaluation");
			}
			this.outputDir = _xifexpression;
			boolean _exists = this.outputDir.toFile().exists();
			if (_exists) {
				final Predicate<Path> _function = (Path f) -> {
					return f.getFileName().endsWith(".tree");
				};
				final Consumer<Path> _function_1 = (Path p) -> {
					p.toFile().delete();
				};
				Files.walk(this.outputDir, 1).filter(_function).forEach(_function_1);
			} else {
				Files.createDirectories(this.outputDir);
			}
			if (options.doIntraEvaluation) {
			}
			if (options.doInterEvaluation) {
				this.intervariantSimilarityEvaluation(originalTree, variantTrees, evaluatorResults, options);
			}
			if (options.doTaxonomyEvaluation) {
				this.taxonomyEvaluation(allVariantNames, allTrees, evaluatorResults, options);
			}
			this.logger.write(this.outputDir, "CloneEvaluation.results", evaluatorResults);
			this.logger.resetLogs();
		} catch (Throwable _e) {
			throw Exceptions.sneakyThrow(_e);
		}
	}

	/*
   * Compares each variant with the original variant
   * And then evaluates the results based on the generator log
   */
	private void intervariantSimilarityEvaluation(final Tree originalTree, final Map<Integer, Tree> variantTrees, final List<String> evaluatorResults, final EvaluatorOptions options) {
		final ArrayList<CloneEvaluator.Evaluation> evaluations = CollectionLiterals.<CloneEvaluator.Evaluation>newArrayList();
		for (int i = 1; (i <= variantTrees.size()); i++) {
			{
				final Tree variantTree = variantTrees.get(Integer.valueOf(i));
				final List<String> logEntries = this.logger.getLogs().get(Integer.valueOf(i));
				final Function1<String, Boolean> _function = (String s) -> {
					return Boolean.valueOf(s.startsWith(CloneST.VARIANT));
				};
				final String variantName = IterableExtensions.<String>findFirst(ListExtensions.<String>reverse(logEntries), _function);
				final CloneEvaluator.Evaluation eval = new CloneEvaluator.Evaluation();
				eval.name = variantName;
				evaluations.add(eval);
				evaluatorResults.add(CloneST.LOG_SEPARATOR);
				evaluatorResults.add(variantName);
				int _size = IterableExtensions.size(originalTree.getRoot().depthFirstSearch());
				String _plus = ("Original tree nodes: " + Integer.valueOf(_size));
				evaluatorResults.add(_plus);
				int _size_1 = IterableExtensions.size(variantTree.getRoot().depthFirstSearch());
				String _plus_1 = ("Variant tree nodes: " + Integer.valueOf(_size_1));
				evaluatorResults.add(_plus_1);
				final NodeComparison comparison = this.doComparison(this.helper.deepCopy(originalTree).getRoot(), variantTree.getRoot());
				eval.comp = comparison;
				int _size_2 = this.getAllChildren(comparison).size();
				String _plus_2 = ("Comparison tree nodes: " + Integer.valueOf(_size_2));
				evaluatorResults.add(_plus_2);
				final Function1<Comparison<Node>, Boolean> _function_1 = (Comparison<Node> c) -> {
					float _resultSimilarity = c.getResultSimilarity();
					return Boolean.valueOf((_resultSimilarity == 1.0f));
				};
				final List<Comparison<Node>> detectedSimilarities = IterableExtensions.<Comparison<Node>>toList(IterableExtensions.<Comparison<Node>>filter(this.getAllChildren(comparison), _function_1));
				final List<Comparison<Node>> truePositives = detectedSimilarities;
				final ArrayList<Comparison<Node>> falsePositives = CollectionLiterals.<Comparison<Node>>newArrayList();
				final Function1<Comparison<Node>, Boolean> _function_2 = (Comparison<Node> c) -> {
					boolean _contains = detectedSimilarities.contains(c);
					return Boolean.valueOf((!_contains));
				};
				final List<Comparison<Node>> trueNegatives = IterableExtensions.<Comparison<Node>>toList(IterableExtensions.<Comparison<Node>>filter(this.getAllChildren(comparison), _function_2));
				final ArrayList<Comparison<Node>> falseNegatives = CollectionLiterals.<Comparison<Node>>newArrayList();
				final Function1<String, Boolean> _function_3 = (String l) -> {
					return Boolean.valueOf(l.startsWith(CloneST.ATOMIC));
				};
				Iterable<String> _filter = IterableExtensions.<String>filter(logEntries, _function_3);
				for (final String entry : _filter) {
					{
						Iterator<Comparison<Node>> iterator = truePositives.iterator();
						while (iterator.hasNext()) {
							{
								final Comparison<Node> positive = iterator.next();
								boolean _isNodeChanged = this.isNodeChanged(entry, positive);
								if (_isNodeChanged) {
									falsePositives.add(positive);
									iterator.remove();
								}
							}
						}
					}
				}
				final Iterator<Comparison<Node>> iterator = trueNegatives.iterator();
				while (iterator.hasNext()) {
					{
						final Comparison<Node> negative = iterator.next();
						boolean negativeWasChanged = false;
						final Function1<String, Boolean> _function_4 = (String l) -> {
							return Boolean.valueOf(l.startsWith(CloneST.ATOMIC));
						};
						Iterable<String> _filter_1 = IterableExtensions.<String>filter(logEntries, _function_4);
						for (final String entry_1 : _filter_1) {
							boolean _isNodeChanged = this.isNodeChanged(entry_1, negative);
							if (_isNodeChanged) {
								negativeWasChanged = true;
							}
						}
						if ((!negativeWasChanged)) {
							falseNegatives.add(negative);
							iterator.remove();
						}
					}
				}
				int _size_3 = truePositives.size();
				int _size_4 = truePositives.size();
				int _size_5 = falseNegatives.size();
				int _plus_3 = (_size_4 + _size_5);
				float _divide = (((float) _size_3) / _plus_3);
				final float recall = (_divide * 100);
				int _size_6 = truePositives.size();
				int _size_7 = falsePositives.size();
				int _plus_4 = (_size_6 + _size_7);
				String _plus_5 = ("Detected:" + Integer.valueOf(_plus_4));
				evaluatorResults.add(_plus_5);
				int _size_8 = truePositives.size();
				int _size_9 = falseNegatives.size();
				int _plus_6 = (_size_8 + _size_9);
				String _plus_7 = ("Detectable:" + Integer.valueOf(_plus_6));
				evaluatorResults.add(_plus_7);
				evaluatorResults.add((("Recall: " + Float.valueOf(recall)) + "%"));
				int _size_10 = truePositives.size();
				int _size_11 = truePositives.size();
				int _size_12 = falsePositives.size();
				int _plus_8 = (_size_11 + _size_12);
				float _divide_1 = (((float) _size_10) / _plus_8);
				final float precision = (_divide_1 * 100);
				evaluatorResults.add((("Precision: " + Float.valueOf(precision)) + "%"));
				int _size_13 = truePositives.size();
				String _plus_9 = ("Similarities that really did not change (TP): " + Integer.valueOf(_size_13));
				evaluatorResults.add(_plus_9);
				if (options.isLogVerbose) {
					this.logComparisions(evaluatorResults, truePositives);
				}
				int _size_14 = falsePositives.size();
				String _plus_10 = ("Similarities that actually changed(FP): " + Integer.valueOf(_size_14));
				evaluatorResults.add(_plus_10);
				this.logComparisions(evaluatorResults, falsePositives);
				int _size_15 = trueNegatives.size();
				String _plus_11 = ("Changes that really occurred (TN): " + Integer.valueOf(_size_15));
				evaluatorResults.add(_plus_11);
				if (options.isLogVerbose) {
					this.logComparisions(evaluatorResults, trueNegatives);
				}
				int _size_16 = falseNegatives.size();
				String _plus_12 = ("Changes that did not occur (FN): " + Integer.valueOf(_size_16));
				evaluatorResults.add(_plus_12);
				this.logComparisions(evaluatorResults, falseNegatives);
				final String vName = variantName.replace("New", "");
				int _length = vName.length();
				int _minus = (20 - _length);
				String _join = String.join("", Collections.<String>nCopies(_minus, " "));
				String _plus_13 = (vName + _join);
				String _plus_14 = (_plus_13 + "recall:");
				String _format = String.format("%10.5f", Float.valueOf(recall));
				String _plus_15 = (_plus_14 + _format);
				String _plus_16 = (_plus_15 + "% precision:");
				String _format_1 = String.format("%10.5f", Float.valueOf(precision));
				String _plus_17 = (_plus_16 + _format_1);
				String _plus_18 = (_plus_17 + "%");
				InputOutput.<String>println(_plus_18);
				eval.precision = precision;
				eval.recall = recall;
				eval.truePositives = truePositives;
				eval.falsePositives = falsePositives;
				eval.trueNegatives = trueNegatives;
				eval.falseNegatives = falseNegatives;
			}
		}
		this.buildEvaluationTrees(evaluations);
		for (final CloneEvaluator.Evaluation e : evaluations) {
			{
				final String serialized = this.gsonExportService.exportTree(e.tree);
				final String name = ListExtensions.<String>reverse(((List<String>) Conversions.doWrapArray(e.name.split(" ")))).get(0);
				this.logger.write(this.outputDir, (("Comparison." + name) + ".tree"), CollectionLiterals.<String>newArrayList(serialized));
			}
		}
	}

	/*
   * Evaluates the correctness of the taxonomy calculation based on generated variants taxonomy
   */
	private void taxonomyEvaluation(final List<String> allVariantNames, final Map<Integer, Tree> allTrees, final List<String> evaluatorResults, final EvaluatorOptions options) {
		InputOutput.<String>println("Calculating taxonomy...");
		final HashMap<Integer, Integer> variantToParentExpected = CollectionLiterals.<Integer, Integer>newHashMap();
		final Consumer<String> _function = (String v) -> {
			String[] _split = v.split("\\.");
			int _size = ((List<String>) Conversions.doWrapArray(v.split("\\."))).size();
			int _minus = (_size - 2);
			final String temp = _split[_minus];
			String _get = temp.split("~")[1];
			String _plus = ("" + _get);
			String _get_1 = temp.split("~")[0];
			String _plus_1 = ("" + _get_1);
			variantToParentExpected.put(Integer.valueOf(Integer.parseInt(_plus)), Integer.valueOf(Integer.parseInt(_plus_1)));
		};
		allVariantNames.forEach(_function);
		final HashMap<Object, Integer> variantToParentCalculated = this.calculateTaxonomy(allVariantNames, allTrees);
		int _size = allVariantNames.size();
		int _minus = (_size - 1);
		final Function1<Integer, Boolean> _function_1 = (Integer i) -> {
			Integer _get = variantToParentExpected.get(i);
			Integer _get_1 = variantToParentCalculated.get(i);
			return Boolean.valueOf(Objects.equal(_get, _get_1));
		};
		final Function1<Boolean, Boolean> _function_2 = (Boolean b) -> {
			return b;
		};
		int hits = IterableExtensions.size(IterableExtensions.<Boolean>filter(IterableExtensions.<Integer, Boolean>map(new IntegerRange(0, _minus), _function_1), _function_2));
		String _replace = variantToParentExpected.toString().replace("=", "->");
		String expected = ("Expected taxonomy:\t" + _replace);
		String _replace_1 = variantToParentCalculated.toString().replace("=", "->");
		String calculated = ("Calculated taxonomy:\t" + _replace_1);
		int _size_1 = allVariantNames.size();
		float _divide = (((float) hits) / _size_1);
		float _multiply = (_divide * 100);
		String _plus = ("Hitrate: " + Float.valueOf(_multiply));
		String hitrate = (_plus + "%");
		InputOutput.<String>println(expected);
		InputOutput.<String>println(calculated);
		InputOutput.<String>println(hitrate);
		evaluatorResults.add(CloneST.LOG_SEPARATOR);
		evaluatorResults.add(expected);
		evaluatorResults.add(calculated);
		evaluatorResults.add(hitrate);
	}

	/*
   * Simple algorithm to calculate taxonomy from variant trees
   */
	private HashMap<Object, Integer> calculateTaxonomy(final List<String> allVariantNames, final Map<Integer, Tree> allTrees) {
		final Integer[][] variantComparsions = new Integer[allVariantNames.size()][allVariantNames.size()] {};
		for (int i = 0; (i < allVariantNames.size()); i++) {
			for (int j = i; (j < allVariantNames.size()); j++) {
				if ((i != j)) {
					final NodeComparison comparison = this.doComparison(allTrees.get(Integer.valueOf(i)).getRoot(), allTrees.get(Integer.valueOf(j)).getRoot());
					final Function1<Comparison<Node>, Boolean> _function = (Comparison<Node> c) -> {
						float _resultSimilarity = c.getResultSimilarity();
						return Boolean.valueOf((_resultSimilarity == 1.0f));
					};
					final List<Comparison<Node>> detectedSimilarities = IterableExtensions.<Comparison<Node>>toList(IterableExtensions.<Comparison<Node>>filter(this.getAllChildren(comparison), _function));
					variantComparsions[i][j] = Integer.valueOf(detectedSimilarities.size());
					variantComparsions[j][i] = Integer.valueOf(detectedSimilarities.size());
				} else {
					variantComparsions[i][j] = Integer.valueOf(0);
				}
			}
		}
		final HashMap<Object, Integer> variantToParentCalculated = CollectionLiterals.<Object, Integer>newHashMap();
		for (int i = 0; (i < allVariantNames.size()); i++) {
			{
				final int _i = i;
				final Function1<Map.Entry<Object, Integer>, Boolean> _function = (Map.Entry<Object, Integer> e) -> {
					Integer _value = e.getValue();
					return Boolean.valueOf(Objects.equal(_value, Integer.valueOf(_i)));
				};
				final Function1<Map.Entry<Object, Integer>, Object> _function_1 = (Map.Entry<Object, Integer> e) -> {
					return e.getKey();
				};
				final Iterable<Object> predecessorsWithCurrentAsParent = IterableExtensions.<Map.Entry<Object, Integer>, Object>map(IterableExtensions.<Map.Entry<Object, Integer>>filter(variantToParentCalculated.entrySet(), _function), _function_1);
				int parent = 0;
				int offset = 0;
				List<Integer> similarities = ListExtensions.<Integer>reverse(IterableExtensions.<Integer>sort(((Iterable<Integer>) Conversions.doWrapArray(variantComparsions[i]))));
				do {
					{
						final Integer maxSimilarity = similarities.get(offset);
						parent = ((List<Integer>) Conversions.doWrapArray((variantComparsions[i]))).indexOf(maxSimilarity);
						offset++;
					}
				} while (IterableExtensions.contains(predecessorsWithCurrentAsParent, Integer.valueOf(parent)));
				variantToParentCalculated.put(Integer.valueOf(i), Integer.valueOf(parent));
			}
		}
		final Function1<Integer[], Integer> _function = (Integer[] e1) -> {
			final Function2<Integer, Integer, Integer> _function_1 = (Integer i1, Integer i2) -> {
				return Integer.valueOf(((i1).intValue() + (i2).intValue()));
			};
			return IterableExtensions.<Integer>reduce(((Iterable<? extends Integer>) Conversions.doWrapArray(e1)), _function_1);
		};
		final List<Integer> sumsOfVariantSimilarities = ListExtensions.<Integer[], Integer>map(((List<Integer[]>) Conversions.doWrapArray(variantComparsions)), _function);
		final Integer maxSimilarity = IterableExtensions.<Integer>max(sumsOfVariantSimilarities);
		final int root = sumsOfVariantSimilarities.indexOf(maxSimilarity);
		variantToParentCalculated.put(Integer.valueOf(root), Integer.valueOf(root));
		return variantToParentCalculated;
	}

	/*
   * Compares the left and right node with the comparison engine
   * @return comparison the resulting NodeComparison
   */
	private NodeComparison doComparison(final Node leftRoot, final Node rightRoot) {
		final NodeComparison comparison = this.engine.compare(leftRoot, rightRoot);
		comparison.updateSimilarity();
		this.matcher.<Object>calculateMatching(comparison);
		comparison.updateSimilarity();
		return comparison;
	}

	/*
   * Returns all children of a comparison
   */
	private ArrayList<Comparison<Node>> getAllChildren(final Comparison<Node> root) {
		ArrayList<Comparison<Node>> nodes = CollectionLiterals.<Comparison<Node>>newArrayList();
		this._getAllChildren(root, nodes);
		return nodes;
	}

	/*
   * Recursion to get all children of a comparison
   */
	private void _getAllChildren(final Comparison<Node> root, final List<Comparison<Node>> nodes) {
		final Consumer<Comparison<Node>> _function = (Comparison<Node> c) -> {
			nodes.add(c);
			this._getAllChildren(c, nodes);
		};
		root.getChildComparisons().forEach(_function);
	}

	/*
   * Checks if a node of a comparison actually changed in a log entry
   */
	private boolean isNodeChanged(final String logEntry, final Comparison<Node> comparison) {
		Node _xifexpression = null;
		Node _rightArtifact = comparison.getRightArtifact();
		boolean _tripleNotEquals = (_rightArtifact != null);
		if (_tripleNotEquals) {
			_xifexpression = comparison.getRightArtifact();
		} else {
			_xifexpression = comparison.getLeftArtifact();
		}
		final Node node = _xifexpression;
		String _get = logEntry.split(" ")[0];
		if (_get != null) {
			switch(_get) {
				case CloneST.COPY:
					final String clone = this.logger.readAttr(logEntry, CloneST.CLONE);
					String _string = node.getUUID().toString();
					return Objects.equal(_string, clone);
				case CloneST.MOVE:
				case CloneST.MOVEPOS:
					final String source = this.logger.readAttr(logEntry, CloneST.SOURCE);
					String _string_1 = node.getUUID().toString();
					return Objects.equal(_string_1, source);
				case CloneST.DELETE:
					final String target = this.logger.readAttr(logEntry, CloneST.TARGET);
					String _string_2 = node.getUUID().toString();
					return Objects.equal(_string_2, target);
				case CloneST.SETATTR:
					final String target_1 = this.logger.readAttr(logEntry, CloneST.TARGET);
					final String key = this.logger.readAttr(logEntry, CloneST.KEY);
					String _string_3 = node.getUUID().toString();
					boolean _equals = Objects.equal(_string_3, target_1);
					if (_equals) {
						final Function1<ResultElement<Node>, Boolean> _function = (ResultElement<Node> e) -> {
							return Boolean.valueOf((e instanceof NodeResultElement));
						};
						final Function1<ResultElement<Node>, Boolean> _function_1 = (ResultElement<Node> e) -> {
							return Boolean.valueOf(((NodeResultElement) e).getAttributeSimilarities().containsKey(key));
						};
						ResultElement<Node> resultWithAttribute = IterableExtensions.<ResultElement<Node>>findFirst(IterableExtensions.<ResultElement<Node>>filter(comparison.getResultElements(), _function), _function_1);
						if ((resultWithAttribute != null)) {
							Float _get_1 = ((NodeResultElement) resultWithAttribute).getAttributeSimilarities().get(key);
							return ((_get_1).floatValue() < 1.0);
						}
					}
					return false;
				default:
					{
						InputOutput.<String>println(("Unsupported atomic clone operation ignored: " + logEntry));
						return logEntry.contains(node.getUUID().toString());
					}
			}
		} else {
			{
				InputOutput.<String>println(("Unsupported atomic clone operation ignored: " + logEntry));
				return logEntry.contains(node.getUUID().toString());
			}
		}
	}

	/*
   * Append information for each node comparison to a log
   */
	private void logComparisions(final List<String> log, final List<Comparison<Node>> cNodes) {
		for (final Comparison<Node> c : cNodes) {
			{
				String _xifexpression = null;
				Node _leftArtifact = c.getLeftArtifact();
				boolean _tripleNotEquals = (_leftArtifact != null);
				if (_tripleNotEquals) {
					_xifexpression = c.getLeftArtifact().getUUID().toString();
				} else {
					_xifexpression = "";
				}
				final String leftUUID = _xifexpression;
				String _xifexpression_1 = null;
				Node _rightArtifact = c.getRightArtifact();
				boolean _tripleNotEquals_1 = (_rightArtifact != null);
				if (_tripleNotEquals_1) {
					_xifexpression_1 = c.getRightArtifact().getUUID().toString();
				} else {
					_xifexpression_1 = "";
				}
				final String rightUUID = _xifexpression_1;
				log.add((((("left:" + leftUUID) + " ") + "right:") + rightUUID));
			}
		}
	}

	/*
   * Return a tree containing all relevant information about variants and similarities
   */
	public void buildEvaluationTrees(final List<CloneEvaluator.Evaluation> evaluations) {
		for (final CloneEvaluator.Evaluation eval : evaluations) {
			{
				final TreeImpl tree = new TreeImpl("Evaluation Results");
				tree.setFileExtension("EVAL");
				eval.tree = tree;
				final Node variantCompRoot = new NodeImpl(NodeType.FILE, "Comparison");
				tree.setRoot(variantCompRoot);
				variantCompRoot.addChildWithParent(eval.comp.mergeArtifacts(false));
				StringValueImpl _stringValueImpl = new StringValueImpl(eval.name);
				variantCompRoot.addAttribute("Name", _stringValueImpl);
				StringValueImpl _stringValueImpl_1 = new StringValueImpl(("" + Float.valueOf(eval.precision)));
				variantCompRoot.addAttribute("Precision", _stringValueImpl_1);
				StringValueImpl _stringValueImpl_2 = new StringValueImpl(("" + Float.valueOf(eval.recall)));
				variantCompRoot.addAttribute("Recall", _stringValueImpl_2);
				this.addConfusionAttributes(variantCompRoot, "TP", eval.truePositives);
				this.addConfusionAttributes(variantCompRoot, "FP", eval.falsePositives);
				this.addConfusionAttributes(variantCompRoot, "TN", eval.trueNegatives);
				this.addConfusionAttributes(variantCompRoot, "FN", eval.falseNegatives);
			}
		}
	}

	/*
   * For a list of comparisons of a confusion class identified by a label,
   * find a corresponding Node in the result tree and add the label and similarity to the Node
   */
	private void addConfusionAttributes(final Node root, final String label, final List<Comparison<Node>> confusions) {
		final Iterable<Node> allChildren = root.depthFirstSearch();
		final Iterator<Comparison<Node>> iterator = confusions.iterator();
		while (iterator.hasNext()) {
			{
				final Comparison<Node> confusion = iterator.next();
				Node node = null;
				Node _leftArtifact = confusion.getLeftArtifact();
				boolean _tripleNotEquals = (_leftArtifact != null);
				if (_tripleNotEquals) {
					final Function1<Node, Boolean> _function = (Node n) -> {
						UUID _uUID = n.getUUID();
						UUID _uUID_1 = confusion.getLeftArtifact().getUUID();
						return Boolean.valueOf(Objects.equal(_uUID, _uUID_1));
					};
					node = IterableExtensions.<Node>findFirst(allChildren, _function);
				} else {
					final Function1<Node, Boolean> _function_1 = (Node n) -> {
						UUID _uUID = n.getUUID();
						UUID _uUID_1 = confusion.getRightArtifact().getUUID();
						return Boolean.valueOf(Objects.equal(_uUID, _uUID_1));
					};
					node = IterableExtensions.<Node>findLast(allChildren, _function_1);
				}
				if ((node != null)) {
					StringValueImpl _stringValueImpl = new StringValueImpl(label);
					node.addAttribute("Confusion", _stringValueImpl);
					float _resultSimilarity = confusion.getResultSimilarity();
					String _plus = ("" + Float.valueOf(_resultSimilarity));
					StringValueImpl _stringValueImpl_1 = new StringValueImpl(_plus);
					node.addAttribute("ResultSimilarity", _stringValueImpl_1);
					iterator.remove();
				}
			}
		}
	}
}
