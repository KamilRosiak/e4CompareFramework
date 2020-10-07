package de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class CompareEngineView implements Initializable{
	@FXML Button button_1; 
	@FXML Button button_2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		button_1.setOnAction(e-> {
			button_1.setText("läuft");
		});
		
	}
	
	public void test() {
		System.out.println("test");
	}
}
