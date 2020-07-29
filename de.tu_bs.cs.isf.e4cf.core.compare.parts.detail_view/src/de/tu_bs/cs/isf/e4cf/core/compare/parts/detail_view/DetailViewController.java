package de.tu_bs.cs.isf.e4cf.core.compare.parts.detail_view;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.detail_view.interfaces.IDetailView;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.detail_view.util.DetailViewStringTable;
import de.tu_bs.cs.isf.e4cf.core.compare.string_table.E4CompareEventTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

@Creatable
public class DetailViewController {
	private Composite parent;
	private TabFolder detailViewFolder;
	
	private List<IDetailView> views;
	private ServiceContainer services;
	
	@PostConstruct
	public void createPartControl(Composite parent, ServiceContainer services) {
		this.parent = parent;	
		this.services = services;
		this.views = new ArrayList<>();
		this.detailViewFolder = constructTabFolder();
		addViews();
	}
	
	/**
	 * This method creates a TabFolder 
	 */
	private TabFolder constructTabFolder() {
		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		return tabFolder;
	}
	
	/**
	 * Constructs and adds the views that should be included into the tab folder.
	 * 
	 * @see IDetailView
	 */
	private void addViews() {
		//load all available views from extension point
		List<Object> attrExtensions = RCPContentProvider.getInstanceFromBundle(DetailViewStringTable.DETAIL_VIEW_EXTENSION_NAME,DetailViewStringTable.DETAIL_VIEW_ATTR);
		if(attrExtensions != null) {
			for(Object obj : attrExtensions) {
				if(obj instanceof IDetailView) {
					((IDetailView)obj).setController(this);
					views.add((IDetailView)obj);
				}
			}
		}
	}
	
	/**
	 * This method creates the detail view
	 */
	public void showDetails(Object container) {
		disposeViews();
		for (IDetailView view : views) {
			if (view.isSupportedContainerType(container.getClass())) {
				Composite tabRoot = createTabItem(view);
				view.createControl(tabRoot);
				view.showContainer(container);
			}
		}		
		parent.layout();
	}
	
	private void disposeViews() {
		views.forEach(view -> view.dispose());
		for (int i = detailViewFolder.getItemCount()-1; i >= 0; i--) {
			TabItem item = detailViewFolder.getItem(i);
			item.dispose();
		}		
		detailViewFolder.layout();
	}
	
	private Composite createTabItem(IDetailView detailView) {
		TabItem viewTab = new TabItem(detailViewFolder, SWT.NONE);
		viewTab.setText(detailView.getName());
		Composite tabRoot = new Composite(detailViewFolder, SWT.NONE);
		viewTab.setControl(tabRoot);
		return tabRoot;
	}
	
	public ServiceContainer getServices() {
		return services;
	}
	
	/**
	 * After compare process the compare engine send a RESULT_EVENT with a reference on the result object that calls this method.
	 * 
	 */
	@Inject
	@Optional
	public void loadContainer(@UIEventTopic(E4CompareEventTable.SHOW_DETAIL_EVENT) Object o) {
		services.partService.showPart(DetailViewStringTable.FAMILYMODE_DETAIL_VIEW_ID);
		showDetails(o);			
	}

}
