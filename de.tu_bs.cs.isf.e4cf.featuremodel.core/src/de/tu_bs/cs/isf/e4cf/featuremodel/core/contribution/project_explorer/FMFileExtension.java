package de.tu_bs.cs.isf.e4cf.featuremodel.core.contribution.project_explorer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDFileTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;

public class FMFileExtension implements IProjectExplorerExtension {

	@Override
	public Image getIcon(RCPImageService imageService) {
		return imageService.getImage(FDStringTable.BUNDLE_NAME, FDFileTable.FM_ICON_16);
	}

	@Override
	public void execute(ServiceContainer container) {
		container.partService.showPart(FDStringTable.BUNDLE_NAME);
		FileTreeElement target = container.rcpSelectionService.getCurrentSelectionFromExplorer();

		File file = new File(target.getAbsolutePath());

		FileInputStream fileInStream;
		try {
			fileInStream = new FileInputStream(file);
			ObjectInputStream objectInStream = new ObjectInputStream(fileInStream);
			FeatureDiagram loadedDiagram = (FeatureDiagram) objectInStream.readObject();
			container.eventBroker.send(FDEventTable.LOAD_FEATURE_DIAGRAM, loadedDiagram);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
