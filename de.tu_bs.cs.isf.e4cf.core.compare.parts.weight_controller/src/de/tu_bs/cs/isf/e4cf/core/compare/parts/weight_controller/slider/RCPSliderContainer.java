package de.tu_bs.cs.isf.e4cf.core.compare.parts.weight_controller.slider;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;

import de.tu_bs.cs.isf.e4cf.core.compare.templates.AbstractAttribute;

/**
 * this class represents a container for slider that may be normalized by 100% 
 * @author {Kamil Rosiak}
 *
 */
public class RCPSliderContainer {
	private List<RCPSlider> _sliders = new LinkedList<>();
	private Group _group;
	private Button _checkBox;
	private String _unitName ="";

	
	public RCPSliderContainer(Composite parent,String unitName) {
		init(parent);
		_unitName = unitName;
	}
	/**
	 * initialiaze the slider container 
	 * @param parent
	 */
	public void init(Composite parent) {
		_group = new Group(parent, SWT.NONE);
		_group.setText(_unitName);
		FillLayout layout = new FillLayout(SWT.VERTICAL);
		_group.setLayout(layout);
		
		Composite layer = new Composite(_group, SWT.None);
		GridLayout grid = new GridLayout(2, false);
		layer.setLayout(grid);
		
		_checkBox = new Button(layer, SWT.CHECK);
		Text text = new Text(layer, SWT.NONE);
		text.setText("By using this option the sliders normalizing the weights to 100%");
		text.setEnabled(false);
	}
	/**
	 * sum checker returns true if sum > 100 false else
	 * @return
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
	 * @return 
	 */
	public int sum() {
		int sum = 0;
		for(RCPSlider slider: _sliders) {
			sum = sum + slider.getValue();
		}
		return sum;
	}
	
	/**
	 * This method allows to add some slider to the container and adds listener to perform a sumcheck and normalisation
	 * @param slider
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
	
	public void removeAll() {
		for(RCPSlider slider : _sliders)
			slider.removeSlider();
		_sliders.clear();
	}
	
	public Group getGroup() {
		return _group;
	}
	
	public List<RCPSlider> getSlider() {
		return _sliders;
	}
}
