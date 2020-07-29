package de.tu_bs.cs.isf.e4cf.featuremodel.core.theme.themes;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.theme.interfaces.IFeatureEditorTheme;

public class DarkTheme implements IFeatureEditorTheme {

	@Override
	public String getCSSLocation() {
		return "/css/dark_theme.css";
	}

	@Override
	public String getThemeName() {
		return "Dark Theme";
	}

}
