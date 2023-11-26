package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.ComponentFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.GroupVariability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IComponentFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeatureConfiguration;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Variability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.util.Pair;

public class FXGraphicalFeatureContextMenu extends ContextMenu {
	private ServiceContainer services;
	private FXGraphicalFeature fxGraFeature;
	private ContextMenu contextMenu = this;

	public FXGraphicalFeatureContextMenu(ServiceContainer services, FXGraphicalFeature fxGraFeature) {
		this.services = services;
		this.fxGraFeature = fxGraFeature;
		if (fxGraFeature.getFeature() instanceof ComponentFeature) {
			createComponentControl();
			// } else if (fxGraFeature.getFeature() instanceof ConfigurationFeature) {
			// createConfigurationControl();
		} else {
			createControl();
		}
	}

	public void createControl() {
		this.getItems().add(setDescription());
		this.getItems().add(renameFeatureMenuItem());
		this.getItems().add(addFeatureMakeAbstractMenuItem());
		this.getItems().add(removeFeatureMenuItem());
		this.getItems().add(removeFeatureTrunkMenuItem());
		this.getItems().add(splitFeature());
		this.getItems().add(new SeparatorMenuItem());
		this.getItems().add(addFeatureBelowMenuItem());
		this.getItems().add(addFeatureAboveMenuItem());
		this.getItems().add(createComponentFeature());

		// feature variability
		this.getItems().add(new SeparatorMenuItem());
		ToggleGroup variabilityToggle = new ToggleGroup();
		RadioMenuItem defaultVar = setDefaultMenuItem();
		defaultVar.setToggleGroup(variabilityToggle);
		RadioMenuItem optionalVar = setOptionalMenuItem();
		optionalVar.setToggleGroup(variabilityToggle);
		RadioMenuItem mandatoryVar = setMandatoryMenuItem();
		mandatoryVar.setToggleGroup(variabilityToggle);
		this.getItems().add(defaultVar);
		this.getItems().add(optionalVar);
		this.getItems().add(mandatoryVar);

		// group variability
		this.getItems().add(new SeparatorMenuItem());
		ToggleGroup groupVar = new ToggleGroup();
		fxGraFeature.setGroupVariability_Default();
		RadioMenuItem defGroupVar = setGroupDefaultMenuItem();
		defGroupVar.setToggleGroup(groupVar);
		RadioMenuItem orGroupVar = setGroupOrMenuItem();
		orGroupVar.setToggleGroup(groupVar);
		RadioMenuItem altGroupVar = setGroupAlternativeMenuItem();
		altGroupVar.setToggleGroup(groupVar);
		this.getItems().add(defGroupVar);
		this.getItems().add(orGroupVar);
		this.getItems().add(altGroupVar);

		this.getItems().add(new SeparatorMenuItem());
		this.getItems().add(addFeatureMakeHiddenMenuItem());
		this.getItems().add(mergeSelectedFeatures());
		this.getItems().add(moveSelectedFeatures());

	}

	private void createComponentControl() {
		this.getItems().add(setDescription());
		this.getItems().add(renameFeatureMenuItem());
		this.getItems().add(removeFeatureMenuItem());
		this.getItems().add(new SeparatorMenuItem());
		this.getItems().add(loadComponentFeatureDiagram());
		this.getItems().add(showFeatureConfigurations());
		this.getItems().add(selectConfigurations());
		this.getItems().add(new SeparatorMenuItem());
	}

	private void createConfigurationControl() {
//		this.getItems().add(showComponentFeatureConfiguration());
		this.getItems().add(renameFeatureMenuItem());
		this.getItems().add(new SeparatorMenuItem());
		this.getItems().add(removeFeatureMenuItem());
	}

	private MenuItem selectConfigurations() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_SET_CONFIGURATION);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				hide();
				services.eventBroker.send(FDEventTable.SELECT_CONFIGURATION_EVENT, fxGraFeature);
				event.consume();
			}
		});
		return item;
	}

	private MenuItem showFeatureConfigurations() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_SHOW_CONFIGURATIONS);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				hide();
				FeatureDiagram diagram = ((ComponentFeature) fxGraFeature.getFeature()).getDiagram();
				services.partService.showPart(FDStringTable.FD_FEATURE_CONFIG_PART_NAME);
				services.eventBroker.send(FDEventTable.EVENT_SHOW_CONFIGURATION_VIEW, diagram);
				event.consume();
			}
		});
		return item;
	}

	private MenuItem showComponentFeatureConfiguration() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_SHOW_COMPONENT_CONFIGURATION);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				hide();
				IComponentFeature componentFeature = ((IComponentFeature) fxGraFeature.getParentFxFeature()
						.getFeature());
				IFeatureConfiguration config = componentFeature.getConfiguration();
				services.partService.showPart(FDStringTable.FD_FEATURE_CONFIG_PART_NAME);
				services.eventBroker.send(FDEventTable.EVENT_SHOW_CONFIGURATION_VIEW, config);
				event.consume();
			}
		});
		return item;
	}

	private MenuItem renameFeatureMenuItem() {
		MenuItem item = new MenuItem(FDStringTable.FD_DIALOG_MENU_RENAME_FEATURE);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				hide();
				fxGraFeature.featureNameLabel.showRenameFeatureDialog();
				event.consume();
			}
		});

		return item;
	}

	private MenuItem createComponentFeature() {
		try {
			MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_CREATE_COMPONENTFEATURE);
			item.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Pair<String, FXGraphicalFeature> pair = new Pair<String, FXGraphicalFeature>(
							FDStringTable.COMPONENTFEATURE, fxGraFeature);
					IFeature newComponent = new ComponentFeature();

					FXGraphicalFeature newFxGra = new FXGraphicalFeature(newComponent);
					newFxGra.getFeature().setComponent(true);
					newFxGra.getFeature().setAbstract(false);
					newFxGra.featureNameLabel.restyle();
					fxGraFeature.addChildFeature(newFxGra);
					contextMenu.hide();

					services.eventBroker.post(FDEventTable.ADD_COMPONENTFEATURE, pair);
					event.consume();
				}
			});
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private MenuItem loadComponentFeatureDiagram() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_LOAD_COMPONENTFEATUREDIAGRAM);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FeatureDiagram diagram = ((IComponentFeature) fxGraFeature.getFeature()).getDiagram();
				services.eventBroker.send(FDEventTable.LOAD_FEATURE_DIAGRAM, diagram);
				event.consume();
			}
		});
		return item;
	}

	private MenuItem addFeatureBelowMenuItem() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_ADD_FEATURE_BELOW);
		item.setOnAction(e -> {
			e.consume();
			fxGraFeature.addChildFeature(new FXGraphicalFeature());
		});

		return item;
	}

	private MenuItem addFeatureAboveMenuItem() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_ADD_FEATURE_ABOVE);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FXGraphicalFeature newFeature = new FXGraphicalFeature();
				newFeature.setAbstract(false);
				FXGraphicalFeature parent = fxGraFeature.getParentFxFeature();
				contextMenu.hide();
				if (parent == null) { // root feature
					newFeature.getChildFeatures().add(fxGraFeature);
					newFeature.getFeature().addChild(fxGraFeature.getFeature());
					fxGraFeature.getFeature().setParent(newFeature.getFeature());
					fxGraFeature.parentFeatureProperty.set(newFeature);
				} else { // standard feature
					parent.removeChildFeature(fxGraFeature);
					parent.addChildFeature(newFeature);
					newFeature.addChildFeature(fxGraFeature);
				}

				services.eventBroker.send(FDEventTable.ADD_FEATURE_ABOVE, fxGraFeature);
				event.consume();
			}
		});

		return item;
	}

	private MenuItem addFeatureMakeAbstractMenuItem() {
		CheckMenuItem item = new CheckMenuItem(FDStringTable.FX_FEATURE_CM_MAKE_ABSTRACT);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				fxGraFeature.setAbstract(item.isSelected());
				System.out.println("context:" + item.isSelected());
				contextMenu.hide();
				event.consume();
				services.eventBroker.send(FDEventTable.LOGGER_CHANGED_FEATURE_ABSTRACTION, fxGraFeature);
				services.eventBroker.send(FDEventTable.SET_FEATURE_ABSTRACT, fxGraFeature);
			}
		});
		// item.setSelected(fxGraFeature.getFeature().isAbstract());
		return item;
	}

	private MenuItem addFeatureMakeHiddenMenuItem() {

		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_CHANGE_SUBFEATURES_VISIBILITY);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				services.eventBroker.send(FDEventTable.CHANGE_SUBFEATURES_VISIBILITY, fxGraFeature);
				event.consume();
			}
		});

		return item;
	}

	private MenuItem removeFeatureMenuItem() {
		MenuItem item = new MenuItem(FDStringTable.FD_CONTEXT_MENU_REMOVE_FEATURE);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FXGraphicalFeature parent = fxGraFeature.getParentFxFeature();
				if (parent == null) { // root feature
					contextMenu.hide();
					RCPMessageProvider.informationMessage("Remove feature",
							"Can't remove the root feature '" + fxGraFeature.getFeature().getName() + "'.");
				} else { // standard feature
					List<FXGraphicalFeature> children = fxGraFeature.getChildFeatures();
					parent.removeChildFeature(fxGraFeature);
					children.forEach(parent::addChildFeature);
				}
				contextMenu.hide();
				services.eventBroker.send(FDEventTable.REMOVE_FEATURE, fxGraFeature);
				event.consume();
			}
		});
		return item;
	}

	private MenuItem removeFeatureTrunkMenuItem() {
		MenuItem item = new MenuItem(FDStringTable.FD_CONTEXT_MENU_REMOVE_FEATURE_TRUNK);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				contextMenu.hide();
				List<FXGraphicalFeature> toRemove = fxGraFeature.getChildFeatures();
				while (!toRemove.isEmpty()) {
					FXGraphicalFeature trunkFeature = toRemove.remove(0);
					FXGraphicalFeature trunkParent = trunkFeature.getParentFxFeature();
					trunkParent.removeChildFeature(trunkFeature);
					toRemove.addAll(trunkFeature.getChildFeatures());
				}
				event.consume();
			}
		});
		return item;
	}

	private RadioMenuItem setMandatoryMenuItem() {
		RadioMenuItem item = new RadioMenuItem(FDStringTable.FX_FEATURE_CM_MANDATORY);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (item.isSelected()) {
					fxGraFeature.setVariability(Variability.MANDATORY);
				}
				event.consume();
			}
		});
		item.setSelected(fxGraFeature.getFeature().getVariability().equals(Variability.MANDATORY));
		return item;
	}

	private RadioMenuItem setOptionalMenuItem() {
		RadioMenuItem item = new RadioMenuItem(FDStringTable.FX_FEATURE_CM_OPTIONAL);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (item.isSelected()) {
					fxGraFeature.setVariability(Variability.OPTIONAL);
				}
				event.consume();
			}
		});
		item.setSelected(fxGraFeature.getFeature().getVariability().equals(Variability.OPTIONAL));
		return item;
	}

	private RadioMenuItem setDefaultMenuItem() {
		RadioMenuItem item = new RadioMenuItem(FDStringTable.FX_FEATURE_CM_DEFAULT);
		item.setOnAction(event -> {
			if (item.isSelected()) {
				fxGraFeature.setVariability(Variability.DEFAULT);
			}
			event.consume();
		});
		item.setSelected(fxGraFeature.getFeature().getVariability().equals(Variability.DEFAULT));
		return item;
	}

	private RadioMenuItem setGroupAlternativeMenuItem() {
		RadioMenuItem item = new RadioMenuItem(FDStringTable.FX_FEATURE_CM_ALTERNATIVE);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (item.isSelected()) {
					fxGraFeature.setGroupVariability_ALTERNATIVE();
				}
				event.consume();
			}
		});
		item.setSelected(fxGraFeature.getFeature().getGroupVariability().equals(GroupVariability.ALTERNATIVE));
		return item;
	}

	private RadioMenuItem setGroupOrMenuItem() {
		RadioMenuItem item = new RadioMenuItem(FDStringTable.FX_FEATURE_CM_OR);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (item.isSelected()) {
					fxGraFeature.setGroupVariability_OR();
				}
				event.consume();
			}
		});
		item.setSelected(fxGraFeature.getFeature().getGroupVariability().equals(GroupVariability.OR));
		return item;
	}

	private RadioMenuItem setGroupDefaultMenuItem() {
		RadioMenuItem item = new RadioMenuItem(FDStringTable.FX_FEATURE_CM_GROUP_DEFAULT);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (item.isSelected()) {
					fxGraFeature.setGroupVariability_Default();
				}
				event.consume();
			}
		});
		item.setSelected(fxGraFeature.getFeature().getGroupVariability().equals(GroupVariability.DEFAULT));
		return item;
	}

	private MenuItem mergeSelectedFeatures() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_GROUP_SELECTED_FEATURES);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				services.eventBroker.send(FDEventTable.GROUP_SELECTED_FEATURES_IN_FEATURE, fxGraFeature);
				event.consume();
			}
		});
		return item;
	}

	private MenuItem moveSelectedFeatures() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_MOVE_SELECTED_FEATURES_HERE);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				services.eventBroker.send(FDEventTable.MOVE_SELECTED_FEATURES_UNDER_FEATURE, fxGraFeature);
				event.consume();
			}
		});
		return item;
	}

	private MenuItem splitFeature() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_SPLIT_FEATURE);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				services.eventBroker.send(FDEventTable.SPLIT_FEATURE, fxGraFeature);
				event.consume();
			}
		});
		return item;
	}

	private MenuItem setDescription() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_SET_DESCRIPTION);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				hide();
				String description = RCPMessageProvider.inputDialog("Set Feature Description",
						String.format("Describe Feature '%s'", fxGraFeature.getFeature().getName()),
						fxGraFeature.getFeature().getDescription());
				fxGraFeature.getFeature().setDescription(description);
				event.consume();
			}
		});
		return item;
	}
}
