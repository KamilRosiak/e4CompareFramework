package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.iec_611331.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.NodeBuilder;
import de.tu_bs.cs.isf.familymining.ppu_iec.code_gen.st.ExpressionToStringExporter;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Action;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Configuration;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.POU;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Resource;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Task;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Variable;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.languageelement.LanguageElement;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.sequentialfunctionchart.AbstractAction;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.sequentialfunctionchart.ComplexAction;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.sequentialfunctionchart.SimpleAction;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.sequentialfunctionchart.Step;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.sequentialfunctionchart.Transition;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.sequentialfunctionchart.impl.SequentialFunctionChartImpl;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.Assignment;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.Case;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.ConditionalBlock;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.ForLoop;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.FunctionCallStatement;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.If;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.Statement;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.UnboundedLoop;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.impl.StructuredTextImpl;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtextexpression.Expression;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtextexpression.Parameter;

/**
 * Builder for IEC61131-3 nodes
 */
public class IEC61131Util {
	public static ExpressionToStringExporter expressionExporter = new ExpressionToStringExporter();

	/**
	 * Create the node representation of a Configuration model (IEC61131-3)
	 * 
	 */
	public static Node createConfigurationRoot(Configuration config) {
		NodeImpl node = new NodeImpl(NodeType.CONFIGURATION);
		node.addAttribute(NodeBuilder.createAttribute("identifier", config.getIdentifier()));
		node.getChildren().add(createGlobalVarNode(config.getGlobalVariables()));
		node.getChildren().add(createRessourceNode(config.getResources()));
		return node;
	}

	private static Node createRessourceNode(EList<Resource> resources) {
		NodeImpl node = new NodeImpl(NodeType.RESOURCES);
		resources.forEach(ressource -> {
			node.getChildren().add(createResourceNode(ressource));
		});
		return node;
	}

	private static Node createResourceNode(Resource ressource) {
		NodeImpl node = new NodeImpl(NodeType.RESOURCE);
		node.addAttribute(NodeBuilder.createAttribute("name", ressource.getName()));
		node.getChildren().add(createGlobalVarNode(ressource.getGlobalVariables()));
		node.getChildren().add(createPOUNode(ressource.getPous()));
		node.getChildren().add(createTaskNode(ressource.getTasks()));
		return node;
	}

	private static Node createTaskNode(List<Task> tasks) {
		NodeImpl node = new NodeImpl(NodeType.TASKS);
		tasks.forEach(task -> {
			node.getChildren().add(createTaskNode(task));
		});

		return node;
	}

	private static Node createTaskNode(Task task) {
		NodeImpl node = new NodeImpl(NodeType.TASKS);
		return node;
	}

	private static Node createPOUNode(List<POU> pous) {
		NodeImpl node = new NodeImpl(NodeType.POUS);
		pous.forEach(pou -> {
			node.getChildren().add(createPOU(pou));
		});
		return node;
	}

	private static Node createPOU(POU pou) {
		NodeImpl node = new NodeImpl(NodeType.POU);
		node.addAttribute(NodeBuilder.createAttribute("name", pou.getIdentifier()));
		node.getChildren().add(createImplementationNode(pou.getImplementations()));
		node.getChildren().add(createActionNode(pou.getActions()));
		return node;
	}

	private static Node createActionNode(List<Action> actions) {
		NodeImpl node = new NodeImpl(NodeType.ACTIONS);
		actions.forEach(action -> {
			node.getChildren().add(createActionNode(action));
		});
		return node;
	}

	private static Node createActionNode(Action action) {
		NodeImpl node = new NodeImpl(NodeType.ACTION);
		node.addAttribute("name", action.getName());
		List<LanguageElement> impl = new ArrayList<LanguageElement>();
		impl.add(action.getImplementation());
		node.getChildren().add(createImplementationNode(impl));
		return node;
	}

	private static Node createImplementationNode(List<LanguageElement> languageElements) {
		NodeImpl node = new NodeImpl(NodeType.IMPLEMENTATION);
		languageElements.forEach(impl -> {
			node.getChildren().addAll(createImplementationNodes(impl));
		});
		return node;
	}

	private static List<Node> createImplementationNodes(LanguageElement impl) {
		List<Node> implNodes = new ArrayList<Node>();
		if (impl instanceof StructuredTextImpl) {
			implNodes.addAll(createStructuredTextImplNodes(((StructuredTextImpl) impl).getStatements()));
		}

		if (impl instanceof SequentialFunctionChartImpl) {
			implNodes.addAll(createSequentialFunctionChartNodes((SequentialFunctionChartImpl) impl));
		}

		return implNodes;
	}

	private static List<Node> createSequentialFunctionChartNodes(SequentialFunctionChartImpl impl) {
		List<Node> implNodes = new ArrayList<Node>();
		impl.getSteps().forEach(step -> {
			implNodes.add(createStepNode(step));
		});
		return implNodes;
	}

	private static Node createStepNode(Step step) {
		NodeImpl node = new NodeImpl(NodeType.STEP);
		node.addAttribute(NodeBuilder.createAttribute("name", step.getName()));
		node.addAttribute(NodeBuilder.createAttribute("level", step.getStepLevel()));
		node.addAttribute(NodeBuilder.createAttribute("isInitial", step.getInitialStep()));
		node.addAttribute(NodeBuilder.createAttribute("local_id", step.getLocal_ID()));
		// node.addAttribute(NodeBuilder.createAttribute("qualifier",
		// step.getQualifier()));
		node.getChildren().add(createActionNode(step.getActions()));
		node.getChildren().add(createIncomingTransitionNode(step.getIncomingTransitions()));
		node.getChildren().add(createOutgoingTransitionNode(step.getOutgoingTransitions()));
		return node;
	}

	private static Node createOutgoingTransitionNode(List<Transition> outgoingTransitions) {
		NodeImpl node = new NodeImpl(NodeType.OUTGOING_TRANSISTIONS);
		outgoingTransitions.forEach(transition -> {
			node.getChildren().add(createTransitionNode(transition, NodeType.OUTGOING_TRANSISTION));
		});
		return node;
	}

	private static Node createTransitionNode(Transition transition, NodeType type) {
		NodeImpl node = new NodeImpl(type);
		node.addAttribute("condition", transition.getCondition());
		if (!transition.getSourceStep().isEmpty())
			node.addAttribute("source_step", transition.getSourceStep().get(0).getName());
		if (!transition.getTargetStep().isEmpty())
			node.addAttribute("target_step", transition.getTargetStep().get(0).getName());
		return node;
	}

	private static Node createIncomingTransitionNode(List<Transition> incomingTransitions) {
		NodeImpl node = new NodeImpl(NodeType.INCOMING_TRANSISTIONS);
		incomingTransitions.forEach(transition -> {
			node.getChildren().add(createTransitionNode(transition, NodeType.INCOMING_TRANSISTION));
		});
		return node;
	}

	private static Node createActionNode(EList<AbstractAction> actions) {
		NodeImpl node = new NodeImpl(NodeType.ACTIONS);
		actions.forEach(action -> {
			node.getChildren().add(createActionNode(action));
		});
		return node;
	}

	private static Node createActionNode(AbstractAction action) {
		if (action instanceof SimpleAction) {
			return createVariableNode(((SimpleAction) action).getCondition());
		}
		if (action instanceof ComplexAction) {
			return createActionNode(((ComplexAction) action).getPouAction());
		}
		return null;
	}

	private static List<Node> createStructuredTextImplNodes(List<Statement> impl) {
		List<Node> implNodes = new ArrayList<Node>();
		impl.forEach(statement -> {
			implNodes.add(createStatementNode(statement));
		});
		return implNodes;
	}

	/**
	 * Checks which statement type to parse
	 * 
	 * @param statement arbitrary structured text statement
	 */
	private static Node createStatementNode(Statement statement) {
		if (statement instanceof If)
			return createIfNode((If) statement);
		if (statement instanceof UnboundedLoop)
			return createUnboundedLoopNode((UnboundedLoop) statement);
		if (statement instanceof UnboundedLoop)
			return createUnboundedLoopNode((UnboundedLoop) statement);
		if (statement instanceof ForLoop)
			return createForLoopNode((ForLoop) statement);
		if (statement instanceof Assignment)
			return createAssignmentNode((Assignment) statement);
		if (statement instanceof FunctionCallStatement)
			return createFunctionCallStatementNode((FunctionCallStatement) statement);
		if (statement instanceof Case)
			return createCaseNode((Case) statement);
		System.out.println("not handled:" + statement);
		return null;
	}

	private static Node createCaseNode(Case statement) {
		NodeImpl node = new NodeImpl(NodeType.CASE);
		return node;
	}

	private static Node createFunctionCallStatementNode(FunctionCallStatement statement) {
		NodeImpl node = new NodeImpl(NodeType.METHOD_CALL);
		node.addAttribute("symbol", statement.getFunctionCall().getSymbol());
		node.addAttribute("dataType", statement.getFunctionCall().getDataType().getName());
		node.addAttribute("expressionType", statement.getFunctionCall().getExpressionType().getName());
		node.getChildren().add(createParameterNode(statement.getFunctionCall().getParameters()));
		return node;
	}

	private static Node createParameterNode(List<Parameter> parameters) {
		NodeImpl node = new NodeImpl(NodeType.PARAMETERS);
		parameters.forEach(parameter -> {
			node.getChildren().add(createParamterNode(parameter));
		});
		return node;
	}

	private static NodeImpl createParamterNode(Parameter parameter) {
		NodeImpl paramNode = new NodeImpl(NodeType.PARAMETER);
		paramNode.addAttribute("name", parameter.getInput().getSymbol());
		paramNode.addAttribute("dataType", parameter.getInput().getDataType().getName());
		paramNode.addAttribute("expressionType", parameter.getInput().getExpressionType().getName());
		return paramNode;
	}

	private static Node createAssignmentNode(Assignment statement) {
		NodeImpl node = new NodeImpl(NodeType.ASSIGNMENT);
		node.addAttribute("dataType", statement.getLeft().getDataType().getName());
		node.addAttribute("name", statement.getLeft().getSymbol());
		node.addAttribute("expressionType", statement.getRight().getExpressionType().getName());
		node.addAttribute(getExpressionAttr(statement.getRight()));
		return node;
	}

	private static Attribute getExpressionAttr(Expression expression) {
		return NodeBuilder.createAttribute("value", expressionExporter.apply(expression));
	}

	private static Node createForLoopNode(ForLoop statement) {
		NodeImpl node = new NodeImpl(NodeType.FOR);
		node.addAttribute("initialValue", new StringValueImpl(String.valueOf(statement.getInitialValue())));
		node.addAttribute("counter", expressionExporter.apply(statement.getCounter()));
		node.addAttribute("increment", new StringValueImpl(String.valueOf(statement.getIncrement())));
		node.getChildren().addAll(createStructuredTextImplNodes(statement.getSubstatements()));
		return node;
	}

	private static Node createUnboundedLoopNode(UnboundedLoop statement) {
		NodeImpl node = new NodeImpl(NodeType.LOOP_WHILE);
		node.addAttribute("condition", expressionExporter.apply(statement.getCondition()));
		node.getChildren().addAll(createStructuredTextImplNodes(statement.getSubstatements()));
		return node;
	}

	private static Node createIfNode(If statement) {
		NodeImpl node = new NodeImpl(NodeType.IF);
		statement.getConditionalBlocks().forEach(conditionBlock -> {
			node.getChildren().add(createConditionBlock(conditionBlock));
		});
		return node;
	}

	private static Node createConditionBlock(ConditionalBlock conditionBlock) {
		NodeImpl node = new NodeImpl(NodeType.IF_BLOCK);
		node.addAttribute("condition", expressionExporter.apply(conditionBlock.getCondition()));
		node.getChildren().addAll(createStructuredTextImplNodes(conditionBlock.getSubstatements()));
		return node;
	}

	public static Node createGlobalVarNode(List<Variable> varList) {
		NodeImpl node = new NodeImpl(NodeType.GLOBAL_VARIABELS);
		varList.forEach(variable -> {
			node.getChildren().add(createVariableNode(variable));
		});
		return node;
	}

	private static Node createVariableNode(Variable variable) {
		NodeImpl node = new NodeImpl(NodeType.VARIABLE);
		node.addAttribute(NodeBuilder.createAttribute("name", variable.getName()));
		node.addAttribute(NodeBuilder.createAttribute("type", variable.getTypeName()));
		return node;
	}

}
