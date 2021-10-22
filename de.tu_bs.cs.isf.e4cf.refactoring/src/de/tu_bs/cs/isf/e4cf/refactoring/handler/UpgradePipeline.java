package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.ActionViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.SynchronizationViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.EditScriptGenerator;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Action;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Delete;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Insert;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Move;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSetAttribute;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSetNode;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Update;

@Singleton
@Creatable
public class UpgradePipeline {

	private EditScriptGenerator editScriptGenerator;

	private ActionViewController actionViewController;
	private SynchronizationViewController synchronizationViewController;

	@Inject
	public UpgradePipeline(EditScriptGenerator editScriptGenerator, ActionViewController actionViewController,
			SynchronizationViewController synchronizationViewController) {
		this.editScriptGenerator = editScriptGenerator;
		this.actionViewController = actionViewController;
		this.synchronizationViewController = synchronizationViewController;
	}

	public void pipe(CloneModel cloneModel, Tree tree) {

		List<ActionScope> actionScopes = editScriptGenerator.generateEditScript(cloneModel.getTree(), tree);

		actionViewController.showView(cloneModel, actionScopes);

		if (actionViewController.isResult()) {

			Map<ActionScope, List<ActionScope>> synchronizationScopes = new HashMap<ActionScope, List<ActionScope>>();

			for (ActionScope actionScope : actionScopes) {
				Action action = actionScope.getAction();

				synchronizationScopes.put(actionScope, new ArrayList<ActionScope>());

				if (actionScope.isApply()) {

					if (action instanceof Insert) {
						Insert insert = (Insert) action;

						Set<MultiSetNode> referenceNodes = cloneModel.collectReferenceNodes(insert.getY());

						for (MultiSetNode referenceNode : referenceNodes) {
							Insert referenceInsert = new Insert(insert.getX(), referenceNode.getNode(),
									insert.getPosition());

							synchronizationScopes.get(actionScope)
									.add(new ActionScope(referenceInsert, true, referenceNode));
						}

					} else if (action instanceof Delete) {
						Delete delete = (Delete) action;

						Set<MultiSetNode> referenceNodes = cloneModel.collectReferenceNodes(delete.getX());

						for (MultiSetNode referenceNode : referenceNodes) {
							Delete referenceDelete = new Delete(referenceNode.getNode());
							synchronizationScopes.get(actionScope)
									.add(new ActionScope(referenceDelete, true, referenceNode));
						}

					} else if (action instanceof Update) {
						Update update = (Update) action;

						Set<MultiSetNode> referenceNodes = cloneModel.collectReferenceNodes(update.getX());

						for (MultiSetNode referenceNode : referenceNodes) {
							Update referenceUpdate = new Update(referenceNode.getNode(), update.getY());
							synchronizationScopes.get(actionScope)
									.add(new ActionScope(referenceUpdate, true, referenceNode));
						}

					} else if (action instanceof Move) {

						Move move = (Move) action;

						Set<MultiSetNode> referenceNodes = cloneModel.collectReferenceNodes(move.getX());

						for (MultiSetNode referenceNode : referenceNodes) {
							Move referenceMove = new Move(referenceNode.getNode(), move.getY(), move.getPosition());
							synchronizationScopes.get(actionScope)
									.add(new ActionScope(referenceMove, true, referenceNode));
						}

					}

				}
			}

			synchronizationViewController.showView(synchronizationScopes, cloneModel);
			if (synchronizationViewController.isResult()) {
				for (ActionScope actionScope : actionScopes) {

					Action action = actionScope.getAction();

					if (actionScope.isApply()) {

						if (action instanceof Insert) {
							Insert insert = (Insert) action;

							cloneModel.addChild(insert.getY(), insert.getX(), insert.getPosition(),
									getSelectedReferenceNodes(actionScope, synchronizationScopes));

						} else if (action instanceof Delete) {
							Delete delete = (Delete) action;

							cloneModel.delete(delete.getX(),
									getSelectedReferenceNodes(actionScope, synchronizationScopes));
							delete.getX().getParent().getChildren().remove(delete.getX());

						} else if (action instanceof Update) {
							Update update = (Update) action;

							List<Attribute> attributes = new ArrayList<Attribute>(update.getX().getAttributes());
							for (Attribute attribute : attributes) {
								cloneModel.deleteAttribute(update.getX(), attribute, mapNodesToAttributes(
										getSelectedReferenceNodes(actionScope, synchronizationScopes), attribute));
							}
							for (Attribute attribute : update.getY().getAttributes()) {
								cloneModel.addAttribute(update.getX(), attribute,
										getSelectedReferenceNodes(actionScope, synchronizationScopes));
							}

						} else if (action instanceof Move) {
							Move move = (Move) action;
							cloneModel.move(move.getX(), move.getPosition(),
									getSelectedReferenceNodes(actionScope, synchronizationScopes));
						}

					}

				}
			}

		}

	}

	private Set<MultiSetNode> getSelectedReferenceNodes(ActionScope actionScope,
			Map<ActionScope, List<ActionScope>> synchronizationScopes) {

		Set<MultiSetNode> multiSetNodes = new HashSet<MultiSetNode>();

		for (ActionScope synchronizationScope : synchronizationScopes.get(actionScope)) {
			if (synchronizationScope.isApply()) {
				multiSetNodes.add(synchronizationScope.getMultiSetNode());
			}

		}

		return multiSetNodes;

	}

	private Set<MultiSetAttribute> mapNodesToAttributes(Set<MultiSetNode> multiSetNodes, Attribute attribute) {

		Set<MultiSetAttribute> multiSetAttributes = new HashSet<MultiSetAttribute>();

		for (MultiSetNode multiSetNode : multiSetNodes) {
			MultiSetAttribute multiSetAttribute = multiSetNode.getAttribute(attribute);

			if (multiSetAttribute != null) {
				multiSetAttributes.add(multiSetAttribute);
			}
		}

		return multiSetAttributes;

	}

}
