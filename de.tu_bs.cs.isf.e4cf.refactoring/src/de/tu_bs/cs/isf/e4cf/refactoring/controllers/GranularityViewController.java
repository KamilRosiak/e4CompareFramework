package de.tu_bs.cs.isf.e4cf.refactoring.controllers;

import java.util.List;
import java.util.Set;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.Granularity;
import de.tu_bs.cs.isf.e4cf.refactoring.views.GranularityView;

@Singleton
@Creatable
public class GranularityViewController extends Controller<GranularityView> {

	@Override
	protected void initView() {
		view.getLayerTree().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				TreeItem item = (TreeItem) event.item;

				if (event.detail == SWT.CHECK) {

					Granularity layer = (Granularity) item.getData();
					layer.setRefactor(item.getChecked());

				}

			}
		});

	}

	public void showView(Set<Granularity> granularities) {
		createView(new GranularityView());
		view.showView(granularities);

	}

}
