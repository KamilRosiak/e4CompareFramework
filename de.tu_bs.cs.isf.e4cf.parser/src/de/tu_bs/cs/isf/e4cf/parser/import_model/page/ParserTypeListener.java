package de.tu_bs.cs.isf.e4cf.parser.import_model.page;

import java.util.Arrays;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

import de.tu_bs.cs.isf.e4cf.parser.base.IParserProcess;
import de.tu_bs.cs.isf.e4cf.parser.base.ParserType;

public class ParserTypeListener implements ModifyListener {
	
	private ImportModelParserPage page;
	
	public ParserTypeListener(ImportModelParserPage page) {
		this.page = page;
	}
	
	@Override
	public void modifyText(ModifyEvent event) {			
		try {
			ParserType selectedType = getParserType();
			try {
				page.parserProcessList = page.parserFactory.getAllParserApplicationsFor(selectedType);
			} catch (CoreException e) {
				e.printStackTrace();
			}

			page.parserProcessCombo.removeAll();
			page.parserDesc.setText("");
			page.parserOutputFormat.setText("");
			for (IParserProcess parserProcess : page.parserProcessList) {
				page.parserProcessCombo.add(parserProcess.getLabel());
				if (parserProcess.isDefault()) {
					page.parserProcessCombo.select(page.parserProcessCombo.getItemCount()-1);
					page.parserDesc.setText(parserProcess.getDescription());
					page.parserOutputFormat.setText(parserProcess.getOutputFileFormat());
					page.choosenParserApp = parserProcess;
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			page.updateWizard();
		}
		
	}

	private ParserType getParserType() {
		java.util.List<ParserType> types = Arrays.asList(ParserType.values());
		return types.stream()
			.filter(parserType -> parserType.getName().equals(page.parserTypeCombo.getText()))
			.findFirst()
			.get();
	}
}