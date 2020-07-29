package de.tu_bs.cs.isf.e4cf.core.compare.parts.weight_controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.core.compare.interfaces.IWeighted;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.weight_controller.slider.RCPSlider;
import de.tu_bs.cs.isf.e4cf.core.compare.string_table.E4CompareEventTable;
import de.tu_bs.cs.isf.e4cf.core.compare.templates.AbstractAttribute;

@Singleton
@Creatable
public class WeightControlView {
	private List<RCPSlider> _sliders = new LinkedList<>();
	private Group group;
	private Button _checkBox;
	private String _unitName ="";
	private TreeItem currentWeights;

	@PostConstruct
	public void createPartControl(Composite parent) {
		group = new Group(parent, SWT.NONE);
		group.setText(_unitName);
		group.setLayout(new GridLayout(1, false));
		
		Composite layer = new Composite(group, SWT.None);
		GridLayout grid = new GridLayout(2, false);
		layer.setLayout(grid);
		
		_checkBox = new Button(layer, SWT.CHECK);
		Text text = new Text(layer, SWT.NONE);
		text.setText("By using this option the sliders normalizing the weights to 100%");
		text.setEnabled(false);
	}
	
	/**
	 * sum checker returns true if sum > 100 false else
	 */
	public boolean sumChecker() {
		if(sum() > 100) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Adds the values of all sliders in the _sliders List
	 */
	public int sum() {
		int sum = 0;
		for(RCPSlider slider: _sliders) {
			sum = sum + slider.getValue();
		}
		return sum;
	}
	
	/**
	 * This method allows to add slider into the container and adds listener to this slider to perform a sumcheck for the normalization.
	 */
	public void addSlider(RCPSlider slider) {
		_sliders.add(slider);
		slider.getSlider().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(_checkBox.getSelection()) {
					Slider selectedSlider = ((Slider)e.getSource());

					if(!sumChecker()) {
						while(sum() > 100) {
							for(RCPSlider s : _sliders) {
								if(!s.getSlider().equals(selectedSlider))
									if(s.getValue()>0) {
											s.setValue(s.getValue()-1);
									}		
							}
						}
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	/**
	 * This method removes a slider from list by given attribute.
	 */
	public void removeSliderByModule(AbstractAttribute module) {
		RCPSlider toRemove = null;
		for(RCPSlider slider : _sliders) {
			if(slider.getModule().equals(module)) {
				toRemove = slider;
			}
		}
		toRemove.removeSlider();
		_sliders.remove(toRemove);
	}
	
	/**
	 * This method removes all slider from slider List.
	 */
	public void removeAll() {
		for(RCPSlider slider : _sliders)
			slider.removeSlider();
		_sliders.clear();
	}
	
	public Group getGroup() {
		return group;
	}
	
	public List<RCPSlider> getSlider() {
		return _sliders;
	}

	
	@Inject
	@Optional
	public void refreshWeightsConfig(@UIEventTopic(E4CompareEventTable.REFRESH_WEIGHTS_CONFIG) Object o) {
		try {
			if(currentWeights != null && currentWeights.getItems() != null) {
				removeAll();
				for(TreeItem item : currentWeights.getItems() ) {
					if(item.getData() instanceof IWeighted && item.getChecked()) {
						addSlider(new RCPSlider(group,(IWeighted)item.getData(),item.getText()));
					}
				}
				group.layout();
			}	
		} catch (SWTException e) {
			//TODO: CHECK REASON FOR WIDGET DISPOSE EXCEPTION
		}
	}	
	
	@Inject
	@Optional
	public void setConfWeights(@UIEventTopic(E4CompareEventTable.OPEN_CONFIG_WEIGHTS) Object o) {
		if(o instanceof TreeItem) {
			currentWeights = (TreeItem)o;
			removeAll();
			for(TreeItem item : currentWeights.getItems() ) {
				if(item.getData() instanceof IWeighted && item.getChecked()) {
					addSlider(new RCPSlider(group,(IWeighted)item.getData(),item.getText()));
				}
			}
			group.layout();
		}
	}
	
	@Inject
	@Optional
	public void refreshWeightsResult(@UIEventTopic(E4CompareEventTable.REFRESH_WEIGHTS_RESULT) Object o) {
		try {
			if(currentWeights != null) {
				removeAll();
				for(TreeItem item : currentWeights.getItems() ) {
					if(item.getData() instanceof IWeighted) {
						addSlider(new RCPSlider(group,(IWeighted)item.getData(),item.getText()));
					}
				}
				group.layout();
			}
		} catch(SWTException e) {
			//TODO: CHECK REASON FOR WIDGET DISPOSE EXCEPTION
		}
	}	
	
	@Inject
	@Optional
	public void setResultWeights(@UIEventTopic(E4CompareEventTable.OPEN_RESULT_WEIGHTS) Object o) {
		if(o instanceof TreeItem) {
			currentWeights = (TreeItem)o;
			removeAll();
			for(TreeItem item : currentWeights.getItems() ) {
				if(item.getData() instanceof IWeighted) {
					addSlider(new RCPSlider(group,(IWeighted)item.getData(),item.getText()));
				}
			}
			group.layout();
		}
	}	
}
