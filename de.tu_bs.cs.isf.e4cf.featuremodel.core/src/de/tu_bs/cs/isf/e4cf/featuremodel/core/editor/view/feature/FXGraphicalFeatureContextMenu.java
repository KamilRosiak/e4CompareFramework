package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature;


import FeatureDiagram.ComponentFeature;
import FeatureDiagram.ConfigurationFeature;
import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Variability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import featureConfiguration.FeatureConfiguration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.util.Pair;

public class FXGraphicalFeatureContextMenu extends ContextMenu {
	private ServiceContainer services;
	private FXGraphicalFeature fxGraFeature;
	
	public FXGraphicalFeatureContextMenu(ServiceContainer services, FXGraphicalFeature fxGraFeature) {
		if (fxGraFeature.getFeature() instanceof ComponentFeature) {
			createComponentControl();
		} else if (fxGraFeature.getFeature() instanceof ConfigurationFeature) {
			createConfigurationControl();
		} else {
			createControl();
		}
		
		this.services = services;
		this.fxGraFeature = fxGraFeature;
	}
 	
	public void createControl() {
		this.getItems().add(addFeatureBelowMenuItem());
		this.getItems().add(addFeatureAboveMenuItem());
		this.getItems().add(createComponentFeature());
		this.getItems().add(removeFeatureMenuItem());
		this.getItems().add(removeFeatureTrunkMenuItem());
		this.getItems().add(splitFeature());
		this.getItems().add(new SeparatorMenuItem());
		this.getItems().add(setOptionMenuItem());
		this.getItems().add(setMandatoryMenuItem());
		this.getItems().add(new SeparatorMenuItem());
		this.getItems().add(setAlternativeMenuItem());
		this.getItems().add(setOrMenuItem());
		this.getItems().add(setAndMenuItem());
		this.getItems().add(new SeparatorMenuItem());
		this.getItems().add(addFeatureMakeAbstractMenuItem());
		this.getItems().add(addFeatureMakeHiddenMenuItem());
		this.getItems().add(mergeSelectedFeatures());
		this.getItems().add(moveSelectedFeatures());
		this.getItems().add(new SeparatorMenuItem());
		this.getItems().add(setDescription());
	}

	private void createComponentControl() {
		this.getItems().add(loadComponentFeatureDiagram());
		this.getItems().add(showFeatureConfigurations());
		this.getItems().add(selectConfigurations());
		this.getItems().add(removeFeatureMenuItem());		

		this.getItems().add(new SeparatorMenuItem());
		this.getItems().add(renameFeatureMenuItem());
		this.getItems().add(setDescription());
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
            	FeatureDiagramm diagram = ((ComponentFeature) fxGraFeature.getFeature()).getFeaturediagramm();
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
            	FeatureDiagramm diagram = ((ComponentFeature) fxGraFeature.getParentFxFeature().getFeature()).getFeaturediagramm();
            	FeatureConfiguration config = ((ConfigurationFeature) fxGraFeature.getFeature()).getConfigurationfeature();
            	services.partService.showPart(FDStringTable.FD_FEATURE_CONFIG_PART_NAME);
				services.eventBroker.send(FDEventTable.EVENT_SHOW_CONFIGURATION_VIEW, new Pair<>(diagram, config));
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
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_CREATE_COMPONENTFEATURE);
		item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Pair<String, FXGraphicalFeature> pair = new Pair<String, FXGraphicalFeature>(FDStringTable.COMPONENTFEATURE, fxGraFeature);
            	
            	services.eventBroker.send(FDEventTable.ADD_COMPONENTFEATURE, pair);	
            	event.consume();
            }
        });

		return item;
	}
	
	private MenuItem loadComponentFeatureDiagram() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_LOAD_COMPONENTFEATUREDIAGRAM);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
				hide();
				services.eventBroker.send(FDEventTable.LOAD_COMPONENTFEATUREDIAGRAM_EVENT, fxGraFeature);	
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
            	services.eventBroker.send(FDEventTable.ADD_FEATURE_ABOVE, fxGraFeature);	
            	event.consume();
            }
        });

		return item;
	}
	
	private MenuItem addFeatureMakeAbstractMenuItem() {
		
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_MAKE_ABSTRACT);
		item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	services.eventBroker.send(FDEventTable.LOGGER_CHANGED_FEATURE_ABSTRACTION, fxGraFeature);
            	services.eventBroker.send(FDEventTable.SET_FEATURE_ABSTRACT, fxGraFeature);	
            	event.consume();
            }
        });

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
            	services.eventBroker.send(FDEventTable.REMOVE_FEATURE_TRUNK, fxGraFeature);	
        		event.consume();
            }
        });	
		return item;
	}
	
	private MenuItem setMandatoryMenuItem() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_MANDATORY);
		item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	fxGraFeature.setVariability(Variability.MANDATORY);
        		event.consume();
            }
        });
		return item;
	}
	
	private MenuItem setOptionMenuItem() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_OPTIONAL);
		item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	fxGraFeature.setVariability(Variability.OPTIONAL);
        		event.consume();
            }
        });
		return item;
	}
	
	private MenuItem setAlternativeMenuItem() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_ALTERNATIVE);
		item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	fxGraFeature.setGroupVariability_ALTERNATIVE();
        		event.consume();
            }
        });
		return item;
	}
	
	private MenuItem setOrMenuItem() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_OR);
		item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	fxGraFeature.setGroupVariability_OR();
        		event.consume();
            }
        });
		return item;
	}
	
	private MenuItem setAndMenuItem() {
		MenuItem item = new MenuItem(FDStringTable.FX_FEATURE_CM_AND);
		item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	fxGraFeature.setGroupVariability_AND();
        		event.consume();
            }
        });
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
				services.eventBroker.send(FDEventTable.SET_DESCRIPTION, fxGraFeature);	
            	event.consume();
            }
        });
		return item;
	}
}
