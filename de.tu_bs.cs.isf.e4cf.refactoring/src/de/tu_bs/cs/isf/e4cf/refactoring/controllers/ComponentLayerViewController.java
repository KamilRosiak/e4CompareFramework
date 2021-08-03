package de.tu_bs.cs.isf.e4cf.refactoring.controllers;

import java.util.List;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.views.ComponentLayerView;

@Singleton
@Creatable
public class ComponentLayerViewController extends Controller<ComponentLayerView> {

	public ComponentLayerViewController() {
		super(new ComponentLayerView());

	}

	@Override
	protected void initView() {
		view.getLayerTree().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				TreeItem item = (TreeItem) event.item;

				if (event.detail == SWT.CHECK) {

					ComponentLayer layer = (ComponentLayer) item.getData();
					layer.setRefactor(item.getChecked());

				}

			}
		});

	}

	public void showView(List<ComponentLayer> refactoringLayers) {
		view.showView(refactoringLayers);

	}

}
