package de.tu_bs.cs.isf.e4cf.evaluation.dialog;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class GeneratorViewController {
	
	private GeneratorOptions options;
	
	public GeneratorOptions getOptions() {
		
		options = new GeneratorOptions();
	
		try {
			options.variants = Integer.parseInt(textNumberVariants.getText());
		} catch (NumberFormatException e) {
			System.out.println("WARN! There was an issue parsing the mutation count, falling back to default value.");
			options.variants = 2;
		}
		
		options.crossoverPercentage = (int)Math.round(sliderCrossoverVariants.getValue());
		options.modificationRatioPercentage = (int)Math.round(sliderModificationRatio.getValue());
		options.variantChangeDegree = (int)Math.round(sliderChangeDegree.getValue());
		
		options.isSyntaxSafe = checkSyntaxSafe.isSelected();
		
		return options;
	}
	
	public void init() {
	
		// restrict some text fields to numbers only
		restrictTextfieldToNumbers(textNumberVariants);
		
		// link sliders to text fields
		linkTextWithSlider(textCrossoverVariants, sliderCrossoverVariants);
		linkTextWithSlider(textModificationRatio, sliderModificationRatio);
		
		// change tick labels of variant change
		sliderChangeDegree.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n <= 1.0) return "Less";
                if (n >= 5.0) return "More";
                return "";
            }

            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "Less":
                        return 1d;
                    case "More":
                    	return 5d;
                    default:
                        return 3d;
                }
            }
        });
	}
	
	private void restrictTextfieldToNumbers(TextField field) {
		field.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	field.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
	}
	
	/** Links a text field to a slider, limits it's input to numbers and range according to the slider*/
	private void linkTextWithSlider(TextField field, Slider slider) {
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			  @Override
			  public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) 
			  {   
				String v = String.valueOf(newValue);
				double d = Double.parseDouble(v);
			    field.setText("" + Math.round(d));
			  }
		});
		
		field.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	String v = newValue;
		        if (!newValue.matches("\\d*")) {
		        	v = newValue.replaceAll("[^\\d]", "");
		        }
		        
		        if (!v.equals("")) {
			        double d = Math.min(Math.max(Double.parseDouble(v), slider.getMin()), slider.getMax()); 		
			        field.setText(""+Math.round(d));
			        slider.setValue(d);
		        }
		    }
		});
		
	}
	
	// ------ Generation Settings ------
	
	@FXML 
	public TextField textNumberVariants;
	
	@FXML
	public TextField textCrossoverVariants;
	@FXML
	public Slider sliderCrossoverVariants;
	
	@FXML
	public TextField textModificationRatio;
	@FXML
	public Slider sliderModificationRatio;
	
	@FXML
	public Slider sliderChangeDegree;
	
	@FXML
	public CheckBox checkSyntaxSafe;
	
}
