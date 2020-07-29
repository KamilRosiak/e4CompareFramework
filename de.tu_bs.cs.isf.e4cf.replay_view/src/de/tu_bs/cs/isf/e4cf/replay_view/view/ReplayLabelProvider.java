package de.tu_bs.cs.isf.e4cf.replay_view.view;

import java.util.Optional;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import FeatureDiagramModificationSet.FeatureModelModificationSet;
import FeatureDiagramModificationSet.Modification;
import de.tu_bs.cs.isf.e4cf.replay_view.util.ModificationSetUtil;
import de.tu_bs.cs.isf.e4cf.replay_view.util.ReplayViewConstants;

public class ReplayLabelProvider implements ITableLabelProvider, IColorProvider{
	
	private static final Color REPLAY_VIEW_COLOR = new Color(Display.getDefault(), 250, 161, 78); 
	private Object focusedObject;
	
	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Modification) {
			Modification mod = (Modification) element;
			
			switch(columnIndex) {
				case ReplayViewConstants.SUBMIT_BUTTON_COLUMN: {
					return null;
				}
				case ReplayViewConstants.NAME_COLUMN: {
					return "Modification";
				}
				case ReplayViewConstants.TIMESTAMP_COLUMN: {
					return ModificationSetUtil.toDateString(mod.getTimeStamp());
				} 
				case ReplayViewConstants.MOD_TYPE_COLUMN: {
					return mod.getModificationType();
				} 
				case ReplayViewConstants.FEATURE_ID_COLUMN: {
					return Integer.toString(mod.getFeatureID());
				} 
				case ReplayViewConstants.DELTA_PROPERTY_COLUMN: {
					if (mod.getDelta() != null) {
						return mod.getDelta().getProperty().toString();					
					} else {
						return null;
					}
				} 
				case ReplayViewConstants.DELTA_PRE_VALUE_COLUMN: {
					if (mod.getDelta() != null) {
						return mod.getDelta().getValuePriorChange();
					} else {
						return null;
					}
				} 
				case ReplayViewConstants.DELTA_POST_VALUE_COLUMN: {
					if (mod.getDelta() != null) {
						return mod.getDelta().getValueAfterChange();
					} else {
						return null;
					}
				}
				default: {
					return null;
				}
			}	
		} else if (element instanceof FeatureModelModificationSet) {
			FeatureModelModificationSet modSet = (FeatureModelModificationSet) element;
			
			switch (columnIndex) {
				case ReplayViewConstants.SUBMIT_BUTTON_COLUMN: {
					return null;
				}
				case ReplayViewConstants.NAME_COLUMN: {
					return modSet.getAffectedFeatureModelName();
				}
				case ReplayViewConstants.TIMESTAMP_COLUMN: {
					if (modSet.getModifications().isEmpty()) {
						return null;
					}
					
					// compute the time period for the whole modification set
					Optional<Long> minTimestamp = modSet.getModifications().stream()
						.map(mod -> mod.getTimeStamp())
						.min((t1, t2) -> Long.compare(t1, t2));
					Optional<Long> maxTimestamp = modSet.getModifications().stream()
						.map(mod -> mod.getTimeStamp())
						.max((t1, t2) -> Long.compare(t1, t2));
					
					String minDateString = ModificationSetUtil.toDateString(minTimestamp.get());
					String maxDateString = ModificationSetUtil.toDateString(maxTimestamp.get());
					return minDateString+" - "+maxDateString;	
				}
				default: {
					return null;
				}
			}
		}
	
		return null;
	}

	@Override
	public Color getForeground(Object element) {
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		if (element == focusedObject) {
			return REPLAY_VIEW_COLOR;
		} else {
			return null;
		}
		
	}
	
	/**
	 * Sets the focused object. Its corresponding row will be colored. For no coloring set <em>null</em>.
	 * 
	 * @param obj an object in the {@link TreeViewer}
	 */
	public void setFocusedObject(Object obj) {
		this.focusedObject = obj;
	}
}
