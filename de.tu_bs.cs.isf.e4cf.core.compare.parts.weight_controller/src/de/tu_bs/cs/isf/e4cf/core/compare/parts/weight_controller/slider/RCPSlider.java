package de.tu_bs.cs.isf.e4cf.core.compare.parts.weight_controller.slider;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;

import de.tu_bs.cs.isf.e4cf.core.compare.interfaces.IWeighted;

public class RCPSlider {
	private Group _group;
	private Slider _slider;
	private Label _label;
	private IWeighted _module;
		
	public RCPSlider(Composite parent, IWeighted module,String name) {
		_group = new Group(parent, SWT.NONE);
		_module = module;
		_group.setText(name);
		_group.setLayout(new GridLayout(2,true));
		init();
		_group.pack();
	}
	
	private void init() {
		_slider = new Slider(_group, SWT.HORIZONTAL);
		_slider.setMinimum(0);
		_slider.setMaximum(110);
		_slider.setIncrement(1);
		_slider.setSelection((int) _module.getWeight());
		_label = new Label(_group, SWT.None);
		_label.setText(Float.toString(_module.getWeight()));

		_slider.addListener(SWT.Selection, e-> {
			_label.setText(Integer.toString(_slider.getSelection()));
			_module.setWeight(_slider.getSelection());
			_label.pack();
		});
	}
	
	public Slider getSlider() {
		return _slider;
	}
	
	public int getValue() {
		return (int) _module.getWeight();
	}
	public void removeSlider() {
		_group.dispose();
	}
	
	public void setValue(int value) {
		_slider.setSelection(value);
		_label.setText(Integer.toString(value));
		_module.setWeight(value);
	}
	
	public IWeighted getModule() {
		return _module;
	}
}
