package de.tu_bs.cs.isf.e4cf.featuremodel.core.theme.themes;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.theme.interfaces.IFeatureEditorTheme;

public class DefaultTheme implements IFeatureEditorTheme {
	public static final String DEFAULT_THEME = "/css/default_theme.css";
	public static final String DEFAULT_THEME_NAME = "Default Theme";
	
	@Override
	public String getCSSLocation() {
		return DEFAULT_THEME;
	}
	@Override
	public String getThemeName() {
		return DEFAULT_THEME_NAME;
	}
}
