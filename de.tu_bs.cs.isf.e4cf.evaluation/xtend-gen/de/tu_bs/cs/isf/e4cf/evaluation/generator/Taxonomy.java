/**
 * This Class performs edits on a given tree with provided constraints
 */
package de.tu_bs.cs.isf.e4cf.evaluation.generator;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneGenerator;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneLogger;
import de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@Creatable
@Singleton
@SuppressWarnings("all")
public class Taxonomy {
  @Inject
  private CloneHelper helper;
  
  @Inject
  private CloneLogger logger;
  
  /**
   * Static initializer only executed once
   * Reads in the clone repository
   */
  private static final HashMap<String, Tree> CLONE_REPOSITORY = new Function0<HashMap<String, Tree>>() {
    @Override
    public HashMap<String, Tree> apply() {
      HashMap<String, Tree> _xblockexpression = null;
      {
        HashMap<String, Tree> map = CollectionLiterals.<String, Tree>newHashMap();
        String path = "resources/clone_repository";
        String _path = Taxonomy.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String _plus = (_path + path);
        File f = new File(_plus);
        File[] _listFiles = f.listFiles();
        for (final File file : _listFiles) {
          boolean _isFile = file.isFile();
          if (_isFile) {
            String _string = f.toPath().resolve(file.getName()).toString();
            de.tu_bs.cs.isf.e4cf.core.file_structure.components.File input = new de.tu_bs.cs.isf.e4cf.core.file_structure.components.File(_string);
            Tree tree = new ReaderManager().readFile(input);
            map.put(file.getName(), tree);
          }
        }
        _xblockexpression = map;
      }
      return _xblockexpression;
    }
  }.apply();
  
  /**
   * @param container is the container of the declaration of the value to modify
   */
  public void refactorIdentifiers(final Node container, final String newValue) {
    this.logger.logRaw(CloneST.REFACTOR_IDENT);
    this.helper.refactor(container, newValue);
  }
  
  public void replaceIdentifier(final Node container, final String newName) {
    this.logger.logRaw(CloneST.REPLACE_IDENT);
    this.helper.setAttributeValue(container, "Name", newName);
  }
  
  public Object literalChange(final Node container, final String newValue) {
    return null;
  }
  
  public void typeChange(final Node container, final String newType) {
    this.logger.logRaw(CloneST.TYPE_CHANGE);
    this.helper.setAttributeValue(container, "Type", newType);
  }
  
  /**
   * Returns a random method of clone type II
   */
  public Method getType2Method(final boolean isSyntaxSafe) {
    if (isSyntaxSafe) {
      final Function1<Method, Boolean> _function = (Method m) -> {
        String _name = m.getName();
        return Boolean.valueOf(Objects.equal(_name, "refactorIdentifiers"));
      };
      Iterable<Method> _filter = IterableExtensions.<Method>filter(((Iterable<Method>)Conversions.doWrapArray(this.getClass().getMethods())), _function);
      final Function1<Method, Boolean> _function_1 = (Method m) -> {
        String _name = m.getName();
        return Boolean.valueOf(Objects.equal(_name, "literalChange"));
      };
      Iterable<Method> _filter_1 = IterableExtensions.<Method>filter(((Iterable<Method>)Conversions.doWrapArray(this.getClass().getMethods())), _function_1);
      return CloneHelper.<Method>random(Iterables.<Method>concat(Collections.<Iterable<Method>>unmodifiableList(CollectionLiterals.<Iterable<Method>>newArrayList(_filter, _filter_1))));
    } else {
      final Function1<Method, Boolean> _function_2 = (Method m) -> {
        String _name = m.getName();
        return Boolean.valueOf(Objects.equal(_name, "refactorIdentifiers"));
      };
      Iterable<Method> _filter_2 = IterableExtensions.<Method>filter(((Iterable<Method>)Conversions.doWrapArray(this.getClass().getMethods())), _function_2);
      final Function1<Method, Boolean> _function_3 = (Method m) -> {
        String _name = m.getName();
        return Boolean.valueOf(Objects.equal(_name, "replaceIdentifier"));
      };
      Iterable<Method> _filter_3 = IterableExtensions.<Method>filter(((Iterable<Method>)Conversions.doWrapArray(this.getClass().getMethods())), _function_3);
      final Function1<Method, Boolean> _function_4 = (Method m) -> {
        String _name = m.getName();
        return Boolean.valueOf(Objects.equal(_name, "literalChange"));
      };
      Iterable<Method> _filter_4 = IterableExtensions.<Method>filter(((Iterable<Method>)Conversions.doWrapArray(this.getClass().getMethods())), _function_4);
      final Function1<Method, Boolean> _function_5 = (Method m) -> {
        String _name = m.getName();
        return Boolean.valueOf(Objects.equal(_name, "typeChange"));
      };
      Iterable<Method> _filter_5 = IterableExtensions.<Method>filter(((Iterable<Method>)Conversions.doWrapArray(this.getClass().getMethods())), _function_5);
      return CloneHelper.<Method>random(Iterables.<Method>concat(Collections.<Iterable<Method>>unmodifiableList(CollectionLiterals.<Iterable<Method>>newArrayList(_filter_2, _filter_3, _filter_4, _filter_5))));
    }
  }
  
  public Object performType2Modification(final Tree tree, final boolean isSyntaxSafe) {
    Object _xblockexpression = null;
    {
      final Method m = this.getType2Method(isSyntaxSafe);
      Object _xifexpression = null;
      if (isSyntaxSafe) {
        _xifexpression = this.performType2ModificationSyntaxSafe(tree, m);
      } else {
        _xifexpression = this.performType2ModificationNotSyntaxSafe(tree, m);
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public Object performType2ModificationSyntaxSafe(final Tree tree, final Method m) {
    Object _xblockexpression = null;
    {
      final Random rng = new Random();
      Object _switchResult = null;
      String _name = m.getName();
      if (_name != null) {
        switch (_name) {
          case "refactorIdentifiers":
            Object _xblockexpression_1 = null;
            {
              final Function1<Node, Boolean> _function = (Node n) -> {
                return Boolean.valueOf(((Collections.<NodeType>unmodifiableList(CollectionLiterals.<NodeType>newArrayList(NodeType.ARGUMENT, NodeType.CLASS, NodeType.COMPILATION_UNIT, NodeType.METHOD_DECLARATION, NodeType.VARIABLE_DECLARATOR)).contains(n.getStandardizedNodeType()) && 
                  (!IterableExtensions.isNullOrEmpty(IterableExtensions.<Attribute>filter(n.getAttributes(), ((Function1<Attribute, Boolean>) (Attribute a) -> {
                    String _attributeKey = a.getAttributeKey();
                    return Boolean.valueOf(Objects.equal(_attributeKey, "Name"));
                  }))))) && 
                  (!Objects.equal(n.getParent().getStandardizedNodeType(), NodeType.TEMPLATE))));
              };
              final Node declaration = CloneHelper.<Node>random(IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function));
              int _nextInt = rng.nextInt(Integer.MAX_VALUE);
              String _plus = ("I" + Integer.valueOf(_nextInt));
              _xblockexpression_1 = this.tryInvoke(m, declaration, _plus);
            }
            _switchResult = _xblockexpression_1;
            break;
          case "replaceIdentifier":
            throw new UnsupportedOperationException("Not yet implemented «m.name»");
          case "literalChange":
            Object _xblockexpression_2 = null;
            {
              final Function1<Node, Boolean> _function = (Node n) -> {
                NodeType _standardizedNodeType = n.getStandardizedNodeType();
                return Boolean.valueOf(Objects.equal(_standardizedNodeType, NodeType.LITERAL));
              };
              final Node literal = CloneHelper.<Node>random(IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function));
              Object _xifexpression = null;
              if ((literal != null)) {
                Object _xblockexpression_3 = null;
                {
                  final String oldValue = this.helper.getAttributeValue(literal, "Value");
                  String newValue = "";
                  String _attributeValue = this.helper.getAttributeValue(literal, "Type");
                  if (_attributeValue != null) {
                    switch (_attributeValue) {
                      case "int":
                      case "long":
                        long l = Long.parseLong(oldValue.replaceAll("[Ll\"]", ""));
                        long _min = Math.min(Math.abs(l), Integer.MAX_VALUE);
                        int max = ((int) _min);
                        int _xifexpression_1 = (int) 0;
                        if ((max == 0)) {
                          _xifexpression_1 = max = Short.MAX_VALUE;
                        } else {
                          _xifexpression_1 = max = max;
                        }
                        max = _xifexpression_1;
                        String _newValue = newValue;
                        int _nextInt = rng.nextInt(max);
                        float _signum = Math.signum(l);
                        int _multiply = (_nextInt * ((int) _signum));
                        newValue = (_newValue + Integer.valueOf(_multiply));
                        break;
                      case "char":
                        String _newValue_1 = newValue;
                        int _nextInt_1 = rng.nextInt(Character.MAX_VALUE);
                        newValue = (_newValue_1 + Character.valueOf(((char) _nextInt_1)));
                        break;
                      case "double":
                      case "float":
                        String _newValue_2 = newValue;
                        double _nextDouble = rng.nextDouble();
                        double _signum_1 = Math.signum(Double.parseDouble(oldValue));
                        double _multiply_1 = (_nextDouble * ((int) _signum_1));
                        newValue = (_newValue_2 + Double.valueOf(_multiply_1));
                        break;
                      case "String":
                        StringConcatenation _builder = new StringConcatenation();
                        _builder.append("\"L");
                        int _nextInt_2 = new Random().nextInt(Integer.MAX_VALUE);
                        _builder.append(_nextInt_2);
                        _builder.append("\"");
                        newValue = _builder.toString();
                        break;
                      case "boolean":
                        String _xifexpression_2 = null;
                        boolean _equals = Objects.equal(oldValue, "true");
                        if (_equals) {
                          _xifexpression_2 = "false";
                        } else {
                          _xifexpression_2 = "true";
                        }
                        newValue = _xifexpression_2;
                        break;
                      case "null":
                        return null;
                    }
                  }
                  _xblockexpression_3 = this.tryInvoke(m, literal, newValue);
                }
                _xifexpression = _xblockexpression_3;
              }
              _xblockexpression_2 = _xifexpression;
            }
            _switchResult = _xblockexpression_2;
            break;
          case "typeChange":
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("Not yet implemented ");
            String _name_1 = m.getName();
            _builder.append(_name_1);
            throw new UnsupportedOperationException(_builder.toString());
          default:
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("Not yet implemented ");
            String _name_2 = m.getName();
            _builder_1.append(_name_2);
            throw new UnsupportedOperationException(_builder_1.toString());
        }
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("Not yet implemented ");
        String _name_2 = m.getName();
        _builder_1.append(_name_2);
        throw new UnsupportedOperationException(_builder_1.toString());
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public Object performType2ModificationNotSyntaxSafe(final Tree tree, final Method m) {
    Object _switchResult = null;
    String _name = m.getName();
    if (_name != null) {
      switch (_name) {
        case "refactorIdentifiers":
          Object _xblockexpression = null;
          {
            final Function1<Node, Boolean> _function = (Node n) -> {
              return Boolean.valueOf((Collections.<NodeType>unmodifiableList(CollectionLiterals.<NodeType>newArrayList(NodeType.ARGUMENT, NodeType.CLASS, NodeType.COMPILATION_UNIT, NodeType.METHOD_DECLARATION, NodeType.VARIABLE_DECLARATOR)).contains(n.getStandardizedNodeType()) && 
                (!IterableExtensions.isNullOrEmpty(IterableExtensions.<Attribute>filter(n.getAttributes(), ((Function1<Attribute, Boolean>) (Attribute a) -> {
                  String _attributeKey = a.getAttributeKey();
                  return Boolean.valueOf(Objects.equal(_attributeKey, "Name"));
                }))))));
            };
            final Node declaration = CloneHelper.<Node>random(IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function));
            int _nextInt = new Random().nextInt(Integer.MAX_VALUE);
            String _plus = ("I" + Integer.valueOf(_nextInt));
            _xblockexpression = this.tryInvoke(m, declaration, _plus);
          }
          _switchResult = _xblockexpression;
          break;
        case "replaceIdentifier":
          Object _xblockexpression_1 = null;
          {
            final Function1<Node, Boolean> _function = (Node n) -> {
              return Boolean.valueOf((Collections.<NodeType>unmodifiableList(CollectionLiterals.<NodeType>newArrayList(NodeType.ARGUMENT, NodeType.CLASS, NodeType.COMPILATION_UNIT, NodeType.EXPRESSION, NodeType.METHOD_CALL, NodeType.METHOD_DECLARATION, NodeType.VARIABLE_DECLARATOR)).contains(n.getStandardizedNodeType()) && 
                (!IterableExtensions.isNullOrEmpty(IterableExtensions.<Attribute>filter(n.getAttributes(), ((Function1<Attribute, Boolean>) (Attribute a) -> {
                  String _attributeKey = a.getAttributeKey();
                  return Boolean.valueOf(Objects.equal(_attributeKey, "Name"));
                }))))));
            };
            final Node ident = CloneHelper.<Node>random(IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function));
            int _nextInt = new Random().nextInt(Integer.MAX_VALUE);
            String _plus = ("N" + Integer.valueOf(_nextInt));
            _xblockexpression_1 = this.tryInvoke(m, ident, _plus);
          }
          _switchResult = _xblockexpression_1;
          break;
        case "literalChange":
          Object _xblockexpression_2 = null;
          {
            final Function1<Node, Boolean> _function = (Node n) -> {
              NodeType _standardizedNodeType = n.getStandardizedNodeType();
              return Boolean.valueOf(Objects.equal(_standardizedNodeType, NodeType.LITERAL));
            };
            final Node literal = CloneHelper.<Node>random(IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function));
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("\"L");
            int _nextInt = new Random().nextInt(Integer.MAX_VALUE);
            _builder.append(_nextInt);
            _builder.append("\"");
            _xblockexpression_2 = this.tryInvoke(m, literal, _builder.toString());
          }
          _switchResult = _xblockexpression_2;
          break;
        case "typeChange":
          Object _xblockexpression_3 = null;
          {
            final Function1<Node, Boolean> _function = (Node n) -> {
              return Boolean.valueOf((Collections.<NodeType>unmodifiableList(CollectionLiterals.<NodeType>newArrayList(NodeType.ARGUMENT, NodeType.VARIABLE_DECLARATOR)).contains(n.getStandardizedNodeType()) && 
                (!IterableExtensions.isNullOrEmpty(IterableExtensions.<Attribute>filter(n.getAttributes(), ((Function1<Attribute, Boolean>) (Attribute a) -> {
                  String _attributeKey = a.getAttributeKey();
                  return Boolean.valueOf(Objects.equal(_attributeKey, "Type"));
                }))))));
            };
            final Node declaration = CloneHelper.<Node>random(IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function));
            String _random = CloneHelper.<String>random(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("boolean", "int", "String", "float", "Object")));
            _xblockexpression_3 = this.tryInvoke(m, declaration, ((String) _random));
          }
          _switchResult = _xblockexpression_3;
          break;
        default:
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("Not yet implemented ");
          String _name_1 = m.getName();
          _builder.append(_name_1);
          throw new UnsupportedOperationException(_builder.toString());
      }
    } else {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Not yet implemented ");
      String _name_1 = m.getName();
      _builder.append(_name_1);
      throw new UnsupportedOperationException(_builder.toString());
    }
    return _switchResult;
  }
  
  private Object tryInvoke(final Method m, final Node n, final String value) {
    try {
      Object _xifexpression = null;
      if ((n != null)) {
        _xifexpression = m.invoke(this, n, value);
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Error with ");
        String _name = m.getName();
        _builder.append(_name);
        _builder.append(" modification: No Target Found");
        System.err.println(_builder);
      }
      return _xifexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Adds a given source Node composition to the target Node at specified index
   */
  public NodeImpl add(final Node source, final Node target) {
    NodeImpl _xblockexpression = null;
    {
      this.logger.logRaw(CloneST.TAX_ADD);
      _xblockexpression = this.helper.copyRecursively(source, target);
    }
    return _xblockexpression;
  }
  
  /**
   * Delete a sub tree
   */
  public boolean delete(final Node target) {
    boolean _xblockexpression = false;
    {
      this.logger.logRaw(CloneST.TAX_DELETE);
      _xblockexpression = this.helper.delete(target);
    }
    return _xblockexpression;
  }
  
  /**
   * Moves a source Node composition to a new target parent at specified index
   */
  public Node move(final Node source, final Node target, final int index) {
    Node _xblockexpression = null;
    {
      this.logger.logRaw(CloneST.TAX_MOVE);
      _xblockexpression = this.helper.move(source, target, index);
    }
    return _xblockexpression;
  }
  
  /**
   * Adds a given source Node composition from the repository to the target Node at specified index
   */
  public NodeImpl addFromRepository(final Node source, final Node target) {
    NodeImpl _xblockexpression = null;
    {
      this.logger.logRaw(CloneST.TAX_ADD_REPO);
      _xblockexpression = this.helper.copyRecursively(source, target);
    }
    return _xblockexpression;
  }
  
  /**
   * Returns a random method of clone type III
   */
  public Method getType3Method(final boolean isSyntaxSafe) {
    if (isSyntaxSafe) {
      final Function1<Method, Boolean> _function = (Method m) -> {
        String _name = m.getName();
        return Boolean.valueOf(Objects.equal(_name, "addFromRepository"));
      };
      Iterable<Method> _filter = IterableExtensions.<Method>filter(((Iterable<Method>)Conversions.doWrapArray(this.getClass().getMethods())), _function);
      final Function1<Method, Boolean> _function_1 = (Method m) -> {
        String _name = m.getName();
        return Boolean.valueOf(Objects.equal(_name, "delete"));
      };
      Iterable<Method> _filter_1 = IterableExtensions.<Method>filter(((Iterable<Method>)Conversions.doWrapArray(this.getClass().getMethods())), _function_1);
      return CloneHelper.<Method>random(Iterables.<Method>concat(Collections.<Iterable<Method>>unmodifiableList(CollectionLiterals.<Iterable<Method>>newArrayList(_filter, _filter_1))));
    } else {
      final Function1<Method, Boolean> _function_2 = (Method m) -> {
        String _name = m.getName();
        return Boolean.valueOf(Objects.equal(_name, "addFromRepository"));
      };
      Iterable<Method> _filter_2 = IterableExtensions.<Method>filter(((Iterable<Method>)Conversions.doWrapArray(this.getClass().getMethods())), _function_2);
      final Function1<Method, Boolean> _function_3 = (Method m) -> {
        String _name = m.getName();
        return Boolean.valueOf(Objects.equal(_name, "add"));
      };
      Iterable<Method> _filter_3 = IterableExtensions.<Method>filter(((Iterable<Method>)Conversions.doWrapArray(this.getClass().getMethods())), _function_3);
      final Function1<Method, Boolean> _function_4 = (Method m) -> {
        String _name = m.getName();
        return Boolean.valueOf(Objects.equal(_name, "delete"));
      };
      Iterable<Method> _filter_4 = IterableExtensions.<Method>filter(((Iterable<Method>)Conversions.doWrapArray(this.getClass().getMethods())), _function_4);
      final Function1<Method, Boolean> _function_5 = (Method m) -> {
        String _name = m.getName();
        return Boolean.valueOf(Objects.equal(_name, "move"));
      };
      Iterable<Method> _filter_5 = IterableExtensions.<Method>filter(((Iterable<Method>)Conversions.doWrapArray(this.getClass().getMethods())), _function_5);
      return CloneHelper.<Method>random(Iterables.<Method>concat(Collections.<Iterable<Method>>unmodifiableList(CollectionLiterals.<Iterable<Method>>newArrayList(_filter_2, _filter_3, _filter_4, _filter_5))));
    }
  }
  
  public void performType3Modification(final Tree tree, final boolean isSyntaxSafe) {
    final Method m = this.getType3Method(isSyntaxSafe);
    if (isSyntaxSafe) {
      this.performType3ModificationSyntaxSafe(tree, m);
    } else {
      this.performType3ModificationNotSyntaxSafe(tree, m);
    }
  }
  
  public void performType3ModificationSyntaxSafe(final Tree tree, final Method m) {
    String _name = m.getName();
    if (_name != null) {
      switch (_name) {
        case "add":
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("Not yet implemented ");
          String _name_1 = m.getName();
          _builder.append(_name_1);
          throw new UnsupportedOperationException(_builder.toString());
        case "move":
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("Not yet implemented ");
          String _name_2 = m.getName();
          _builder_1.append(_name_2);
          throw new UnsupportedOperationException(_builder_1.toString());
        case "delete":
          final Function1<Node, Boolean> _function = (Node n) -> {
            return Boolean.valueOf((Collections.<NodeType>unmodifiableList(CollectionLiterals.<NodeType>newArrayList(NodeType.ASSIGNMENT, NodeType.CONSTRUCTION, NodeType.FIELD_DECLARATION, NodeType.LOOP_COLLECTION_CONTROLLED, NodeType.LOOP_COUNT_CONTROLLED, NodeType.LOOP_DO, NodeType.LOOP_WHILE, NodeType.METHOD_CALL, NodeType.METHOD_DECLARATION, NodeType.SWITCH, NodeType.TRY, NodeType.VARIABLE_DECLARATION)).contains(n.getStandardizedNodeType()) && this.validateDeleteCandidate(tree, n)));
          };
          final Node target = CloneHelper.<Node>random(IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function));
          if ((target == null)) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("Error with ");
            String _name_3 = m.getName();
            _builder_2.append(_name_3);
            _builder_2.append(" modification: No target found");
            System.err.println(_builder_2);
            return;
          }
          this.delete(target);
          break;
        case "addFromRepository":
          this.addFromRepository(tree, m, true);
          break;
        default:
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append("Not yet implemented ");
          String _name_4 = m.getName();
          _builder_3.append(_name_4);
          throw new UnsupportedOperationException(_builder_3.toString());
      }
    } else {
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("Not yet implemented ");
      String _name_4 = m.getName();
      _builder_3.append(_name_4);
      throw new UnsupportedOperationException(_builder_3.toString());
    }
  }
  
  private boolean validateDeleteCandidate(final Tree tree, final Node target) {
    boolean isViable = false;
    NodeType _standardizedNodeType = target.getStandardizedNodeType();
    if (_standardizedNodeType != null) {
      switch (_standardizedNodeType) {
        case LITERAL:
          NodeType _standardizedNodeType_1 = target.getParent().getStandardizedNodeType();
          boolean _equals = Objects.equal(_standardizedNodeType_1, NodeType.VARIABLE_DECLARATOR);
          if (_equals) {
            isViable = true;
          }
          break;
        case VARIABLE_DECLARATION:
        case FIELD_DECLARATION:
          final Function1<Node, Boolean> _function = (Node n) -> {
            return Boolean.valueOf((Objects.equal(n.getStandardizedNodeType(), NodeType.EXPRESSION) && Objects.equal(this.helper.getAttributeValue(IterableExtensions.<Node>head(target.getChildren()), "Name"), this.helper.getAttributeValue(n, "Name"))));
          };
          Iterable<Node> references = IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function);
          boolean _isEmpty = IterableExtensions.isEmpty(references);
          if (_isEmpty) {
            isViable = true;
          }
          break;
        case METHOD_DECLARATION:
          boolean _isEmpty_1 = IterableExtensions.isEmpty(this.getMethodDeclarationCalls(tree, target));
          if (_isEmpty_1) {
            isViable = true;
          }
          break;
        case METHOD_CALL:
          break;
        case CONSTRUCTION:
          final Function1<Node, Boolean> _function_1 = (Node n) -> {
            return Boolean.valueOf((Objects.equal(n.getStandardizedNodeType(), NodeType.EXPRESSION) && Objects.equal(this.helper.getAttributeValue(target, "Name"), this.helper.getAttributeValue(n, "Type"))));
          };
          Iterable<Node> calls = IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function_1);
          boolean _isEmpty_2 = IterableExtensions.isEmpty(calls);
          if (_isEmpty_2) {
            isViable = true;
          }
          break;
        case ARGUMENT:
          isViable = false;
          break;
        case CLASS:
          isViable = false;
          break;
        default:
          isViable = true;
          break;
      }
    } else {
      isViable = true;
    }
    return isViable;
  }
  
  public void performType3ModificationNotSyntaxSafe(final Tree tree, final Method m) {
    String _name = m.getName();
    if (_name != null) {
      switch (_name) {
        case "add":
        case "move":
          final Function1<Node, Boolean> _function = (Node n) -> {
            return Boolean.valueOf(Collections.<NodeType>unmodifiableList(CollectionLiterals.<NodeType>newArrayList(NodeType.ASSIGNMENT, NodeType.CLASS, NodeType.CONSTRUCTION, NodeType.FIELD_DECLARATION, NodeType.IF, NodeType.LOOP_COLLECTION_CONTROLLED, NodeType.LOOP_COUNT_CONTROLLED, NodeType.LOOP_DO, NodeType.LOOP_WHILE, NodeType.METHOD_CALL, NodeType.METHOD_DECLARATION, NodeType.SWITCH, NodeType.TRY, NodeType.VARIABLE_DECLARATION)).contains(n.getStandardizedNodeType()));
          };
          final Node source = CloneHelper.<Node>random(IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function));
          if ((source == null)) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("Error with ");
            String _name_1 = m.getName();
            _builder.append(_name_1);
            _builder.append(" modification: No Source found");
            System.err.println(_builder);
            return;
          }
          Node target = null;
          NodeType _standardizedNodeType = source.getStandardizedNodeType();
          if (_standardizedNodeType != null) {
            switch (_standardizedNodeType) {
              case ASSIGNMENT:
              case IF:
              case LOOP_COLLECTION_CONTROLLED:
              case LOOP_COUNT_CONTROLLED:
              case LOOP_DO:
              case LOOP_WHILE:
              case METHOD_CALL:
              case SWITCH:
              case TRY:
              case VARIABLE_DECLARATION:
                final Function1<Node, Boolean> _function_1 = (Node n) -> {
                  return Boolean.valueOf(((Collections.<NodeType>unmodifiableList(CollectionLiterals.<NodeType>newArrayList(NodeType.BLOCK, NodeType.CASE)).contains(n.getStandardizedNodeType()) && (!Objects.equal(n.getUUID(), source.getUUID()))) && (!IterableExtensions.<Node>exists(source.depthFirstSearch(), ((Function1<Node, Boolean>) (Node c) -> {
                    UUID _uUID = c.getUUID();
                    UUID _uUID_1 = n.getUUID();
                    return Boolean.valueOf(Objects.equal(_uUID, _uUID_1));
                  })))));
                };
                target = CloneHelper.<Node>random(IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function_1));
                break;
              case CONSTRUCTION:
              case FIELD_DECLARATION:
              case METHOD_DECLARATION:
              case CLASS:
                final Function1<Node, Boolean> _function_2 = (Node n) -> {
                  return Boolean.valueOf(((Collections.<NodeType>unmodifiableList(CollectionLiterals.<NodeType>newArrayList(NodeType.COMPILATION_UNIT, NodeType.CLASS)).contains(n.getStandardizedNodeType()) && (!Objects.equal(n.getUUID(), source.getUUID()))) && (!IterableExtensions.<Node>exists(source.depthFirstSearch(), ((Function1<Node, Boolean>) (Node c) -> {
                    UUID _uUID = c.getUUID();
                    UUID _uUID_1 = n.getUUID();
                    return Boolean.valueOf(Objects.equal(_uUID, _uUID_1));
                  })))));
                };
                target = CloneHelper.<Node>random(IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function_2));
                break;
              default:
                {
                  StringConcatenation _builder_1 = new StringConcatenation();
                  _builder_1.append("Error with ");
                  String _name_2 = m.getName();
                  _builder_1.append(_name_2);
                  _builder_1.append(" modification: Target for source ");
                  NodeType _standardizedNodeType_1 = source.getStandardizedNodeType();
                  _builder_1.append(_standardizedNodeType_1);
                  _builder_1.append(" cannot be determined");
                  System.err.println(_builder_1);
                  return;
                }
            }
          } else {
            {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append("Error with ");
              String _name_2 = m.getName();
              _builder_1.append(_name_2);
              _builder_1.append(" modification: Target for source ");
              NodeType _standardizedNodeType_1 = source.getStandardizedNodeType();
              _builder_1.append(_standardizedNodeType_1);
              _builder_1.append(" cannot be determined");
              System.err.println(_builder_1);
              return;
            }
          }
          if ((target == null)) {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("Error with ");
            String _name_2 = m.getName();
            _builder_1.append(_name_2);
            _builder_1.append(" modification: No target found.");
            System.err.println(_builder_1);
            return;
          }
          String _name_3 = m.getName();
          boolean _equals = Objects.equal(_name_3, "add");
          if (_equals) {
            this.add(source, target);
          } else {
            int _xifexpression = (int) 0;
            boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(target.getChildren());
            if (_isNullOrEmpty) {
              _xifexpression = 0;
            } else {
              _xifexpression = new Random().nextInt(target.getChildren().size());
            }
            final int index = _xifexpression;
            this.move(source, target, index);
          }
          break;
        case "delete":
          final Function1<Node, Boolean> _function_3 = (Node n) -> {
            return Boolean.valueOf((Collections.<NodeType>unmodifiableList(CollectionLiterals.<NodeType>newArrayList(NodeType.ASSIGNMENT, NodeType.ARGUMENT, NodeType.CLASS, NodeType.CONSTRUCTION, NodeType.EXPRESSION, NodeType.FIELD_DECLARATION, NodeType.IF, NodeType.LITERAL, NodeType.LOOP_COLLECTION_CONTROLLED, NodeType.LOOP_COUNT_CONTROLLED, NodeType.LOOP_DO, NodeType.LOOP_WHILE, NodeType.METHOD_CALL, NodeType.METHOD_DECLARATION, NodeType.SWITCH, NodeType.TRY, NodeType.VARIABLE_DECLARATION)).contains(n.getStandardizedNodeType()) && ((!Objects.equal(n.getStandardizedNodeType(), NodeType.EXPRESSION)) || (!Objects.equal(n.getParent().getStandardizedNodeType(), NodeType.EXPRESSION)))));
          };
          final Node target_1 = CloneHelper.<Node>random(IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function_3));
          if ((target_1 == null)) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("Error with ");
            String _name_4 = m.getName();
            _builder_2.append(_name_4);
            _builder_2.append(" modification: No target found");
            System.err.println(_builder_2);
            return;
          }
          this.delete(target_1);
          break;
        case "addFromRepository":
          this.addFromRepository(tree, m, false);
          break;
        default:
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append("Not yet implemented ");
          String _name_5 = m.getName();
          _builder_3.append(_name_5);
          throw new UnsupportedOperationException(_builder_3.toString());
      }
    } else {
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("Not yet implemented ");
      String _name_5 = m.getName();
      _builder_3.append(_name_5);
      throw new UnsupportedOperationException(_builder_3.toString());
    }
  }
  
  public Iterable<Node> getMethodDeclarationCalls(final Tree tree, final Node methodDecl) {
    final Function1<Node, Boolean> _function = (Node n) -> {
      return Boolean.valueOf(((Objects.equal(n.getStandardizedNodeType(), NodeType.METHOD_CALL) && Objects.equal(this.helper.getAttributeValue(methodDecl, "Name"), this.helper.getAttributeValue(n, "Name"))) && (IterableExtensions.<Node>head(n.getChildren()).getChildren().size() == IterableExtensions.<Node>head(methodDecl.getChildren()).getChildren().size())));
    };
    return IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function);
  }
  
  /**
   * Choose a random code repository tree,
   * then choose a random method or class to be added to the current variant
   * This is syntax save as we assume that the methods and classes are side effect free
   */
  public void addFromRepository(final Tree tree, final Method m, final boolean isSyntaxSafe) {
    final Tree sourceRepoTree = CloneHelper.<Tree>random(Taxonomy.CLONE_REPOSITORY.values());
    final Function1<Node, Boolean> _function = (Node n) -> {
      return Boolean.valueOf(Collections.<NodeType>unmodifiableList(CollectionLiterals.<NodeType>newArrayList(NodeType.METHOD_DECLARATION, NodeType.CLASS)).contains(n.getStandardizedNodeType()));
    };
    final Node source = CloneHelper.<Node>random(IterableExtensions.<Node>filter(sourceRepoTree.getRoot().depthFirstSearch(), _function));
    if ((source == null)) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Error with ");
      String _name = m.getName();
      _builder.append(_name);
      _builder.append(" modification: No Source found");
      System.err.println(_builder);
      return;
    }
    Node target = null;
    NodeType _standardizedNodeType = source.getStandardizedNodeType();
    if (_standardizedNodeType != null) {
      switch (_standardizedNodeType) {
        case METHOD_DECLARATION:
        case CLASS:
          if (isSyntaxSafe) {
            final Function1<Node, Boolean> _function_1 = (Node n) -> {
              return Boolean.valueOf((Collections.<NodeType>unmodifiableList(CollectionLiterals.<NodeType>newArrayList(NodeType.COMPILATION_UNIT, NodeType.CLASS)).contains(n.getStandardizedNodeType()) && 
                (!IterableExtensions.<Node>exists(n.getChildren(), 
                  ((Function1<Node, Boolean>) (Node c) -> {
                    return Boolean.valueOf((Objects.equal(c.getStandardizedNodeType(), source.getStandardizedNodeType()) && 
                      Objects.equal(this.helper.getAttributeValue(c, "Name"), this.helper.getAttributeValue(source, "Name"))));
                  })))));
            };
            target = CloneHelper.<Node>random(IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function_1));
          } else {
            final Function1<Node, Boolean> _function_2 = (Node n) -> {
              return Boolean.valueOf(Collections.<NodeType>unmodifiableList(CollectionLiterals.<NodeType>newArrayList(NodeType.COMPILATION_UNIT, NodeType.CLASS)).contains(n.getStandardizedNodeType()));
            };
            target = CloneHelper.<Node>random(IterableExtensions.<Node>filter(tree.getRoot().depthFirstSearch(), _function_2));
          }
          break;
        default:
          {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("Error with ");
            String _name_1 = m.getName();
            _builder_1.append(_name_1);
            _builder_1.append(" modification: Target for source ");
            NodeType _standardizedNodeType_1 = source.getStandardizedNodeType();
            _builder_1.append(_standardizedNodeType_1);
            _builder_1.append(" cannot be determined");
            System.err.println(_builder_1);
            return;
          }
      }
    } else {
      {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("Error with ");
        String _name_1 = m.getName();
        _builder_1.append(_name_1);
        _builder_1.append(" modification: Target for source ");
        NodeType _standardizedNodeType_1 = source.getStandardizedNodeType();
        _builder_1.append(_standardizedNodeType_1);
        _builder_1.append(" cannot be determined");
        System.err.println(_builder_1);
        return;
      }
    }
    if ((target == null)) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("Error with ");
      String _name_1 = m.getName();
      _builder_1.append(_name_1);
      _builder_1.append(" modification: No target found.");
      System.err.println(_builder_1);
      return;
    }
    this.addFromRepository(source, target);
  }
  
  public List<String> getLogFromLastCommonAncestor(final int donorId, final int receiverId) {
    final List<String> log = this.logger.getLogs().get(Integer.valueOf(donorId));
    final ArrayList<Integer> donorHistory = CollectionLiterals.<Integer>newArrayList();
    this.logger.reconstructVariantTaxonomy(donorId, donorHistory);
    final ArrayList<Integer> receiverHistory = CollectionLiterals.<Integer>newArrayList();
    this.logger.reconstructVariantTaxonomy(receiverId, receiverHistory);
    final Function1<Integer, Boolean> _function = (Integer h) -> {
      return Boolean.valueOf(receiverHistory.contains(h));
    };
    final Integer commonAncestorId = IterableExtensions.<Integer>findFirst(donorHistory, _function);
    if ((commonAncestorId == null)) {
      return IterableExtensions.<String>toList(log);
    }
    int commonAncestorSize = this.logger.getLogs().get(commonAncestorId).size();
    final ArrayList<String> truncatedLog = CollectionLiterals.<String>newArrayList();
    for (int i = commonAncestorSize; (i < log.size()); i++) {
      truncatedLog.add(log.get(i));
    }
    return truncatedLog;
  }
  
  public boolean variantHasChangesInSubtree(final int donorId, final int receiverId, final Node subtree) {
    final Function1<Node, Boolean> _function = (Node n) -> {
      final Function1<String, Boolean> _function_1 = (String l) -> {
        return Boolean.valueOf(l.contains(n.getUUID().toString()));
      };
      return Boolean.valueOf(IterableExtensions.<String>exists(this.getLogFromLastCommonAncestor(donorId, receiverId), _function_1));
    };
    final boolean hasChanges = IterableExtensions.<Node>exists(subtree.depthFirstSearch(), _function);
    return hasChanges;
  }
  
  public CloneGenerator.Variant performCrossOver(final CloneGenerator.Variant receiver, final CloneGenerator.Variant donor) {
    final TreeImpl receiverTree = this.helper.deepCopy(receiver.tree);
    final TreeImpl donorTree = this.helper.deepCopy(donor.tree);
    this.helper.setTrackingTree(receiver.trackingTree);
    final Function1<Node, Boolean> _function = (Node n) -> {
      return Boolean.valueOf((Collections.<NodeType>unmodifiableList(CollectionLiterals.<NodeType>newArrayList(NodeType.METHOD_DECLARATION)).contains(n.getStandardizedNodeType()) && this.variantHasChangesInSubtree(donor.index, receiver.index, n)));
    };
    final Iterable<Node> donations = IterableExtensions.<Node>filter(donorTree.getRoot().depthFirstSearch(), _function);
    final Function1<Node, Boolean> _function_1 = (Node n) -> {
      return Boolean.valueOf((Collections.<NodeType>unmodifiableList(CollectionLiterals.<NodeType>newArrayList(NodeType.METHOD_DECLARATION)).contains(n.getStandardizedNodeType()) && IterableExtensions.contains(IterableExtensions.<Node, String>map(donations, ((Function1<Node, String>) (Node s) -> {
        return this.helper.getAttributeValue(s, "Name");
      })), this.helper.getAttributeValue(n, "Name"))));
    };
    final Iterable<Node> targets = IterableExtensions.<Node>filter(receiverTree.getRoot().depthFirstSearch(), _function_1);
    if ((targets == null)) {
      return null;
    }
    final Node target = CloneHelper.<Node>random(targets);
    if ((target == null)) {
      return null;
    }
    final Function1<Node, Boolean> _function_2 = (Node n) -> {
      return Boolean.valueOf(this.helper.getAttributeValue(n, "Name").equals(this.helper.getAttributeValue(target, "Name")));
    };
    final Node donation = IterableExtensions.<Node>findFirst(donations, _function_2);
    if ((donation == null)) {
      return null;
    }
    int _size = this.logger.getLogs().size();
    int _plus = (_size + 1);
    this.logger.logVariantCrossover(receiver.index, donor.index, _plus);
    this.logger.isActive = false;
    this.delete(target);
    this.helper.copyRecursively(donation, target.getParent(), true);
    this.logger.isActive = true;
    final Function1<Node, String> _function_3 = (Node n) -> {
      return n.getUUID().toString();
    };
    final List<String> targetUuids = IterableExtensions.<String>toList(IterableExtensions.<Node, String>map(target.depthFirstSearch(), _function_3));
    final Consumer<String> _function_4 = (String u) -> {
      this.logger.deleteLogsContainingString(u);
    };
    targetUuids.forEach(_function_4);
    final Function1<Node, String> _function_5 = (Node n) -> {
      return n.getUUID().toString();
    };
    final List<String> donationUuids = IterableExtensions.<String>toList(IterableExtensions.<Node, String>map(donation.depthFirstSearch(), _function_5));
    final ArrayList<String> entriesToAdd = CollectionLiterals.<String>newArrayList();
    final Consumer<String> _function_6 = (String e) -> {
      final Function1<String, Boolean> _function_7 = (String u) -> {
        return Boolean.valueOf(e.contains(u));
      };
      boolean _exists = IterableExtensions.<String>exists(donationUuids, _function_7);
      if (_exists) {
        entriesToAdd.add(e);
      }
    };
    this.getLogFromLastCommonAncestor(donor.index, receiver.index).forEach(_function_6);
    final Consumer<String> _function_7 = (String e) -> {
      this.logger.logRaw(e);
    };
    entriesToAdd.forEach(_function_7);
    Tree _trackingTree = this.helper.getTrackingTree();
    int _size_1 = this.logger.getLogs().size();
    final CloneGenerator.Variant crossoverVariant = new CloneGenerator.Variant(receiverTree, _trackingTree, receiver.index, _size_1);
    crossoverVariant.crossOverParentIndex = donor.index;
    return crossoverVariant;
  }
}
