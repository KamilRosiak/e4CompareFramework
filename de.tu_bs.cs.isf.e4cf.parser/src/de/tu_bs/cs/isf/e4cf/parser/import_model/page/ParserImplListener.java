package de.tu_bs.cs.isf.e4cf.parser.import_model.page;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

import de.tu_bs.cs.isf.e4cf.parser.base.IParserProcess;

public class ParserImplListener implements ModifyListener {

	private ImportModelParserPage page;

	public ParserImplListener(ImportModelParserPage page) {
		this.page = page;
	}
	
	@Override
	public void modifyText(ModifyEvent e) {
		for (IParserProcess parserApp : page.getParserApplicationList()) {
			if (parserApp.getLabel().equalsIgnoreCase(page.parserProcessCombo.getText())) {
				page.getParserDesc().setText(parserApp.getDescription());
				page.parserOutputFormat.setText(parserApp.getOutputFileFormat());
				
				page.getParserDesc().update();
				page.setChoosenParserApp(parserApp);
			}
		}
		page.updateWizard();
	}

}
