package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.templates;

import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;

public class KeyValueWizard extends Wizard {

	public static class NotFinishedException extends Exception {

		private static final String PRE_MESSAGE = "The Wizard has not yet been finished";
		private static final long serialVersionUID = -2535299273500772848L;

		public NotFinishedException(String message) {
			super(PRE_MESSAGE + ": " + message);
		}

		public NotFinishedException(Throwable throwable) {
			super(throwable);
		}

		public NotFinishedException(String message, Throwable throwable) {
			super(errorMessage(message), throwable);
		}

		private static String errorMessage(String message) {
			return PRE_MESSAGE + ": " + message;
		}
	}

	private Map<String, String> _keyValues;
	private KeyValuePage _page;

	public Map<String, String> getValueMap() {
		return _keyValues;
	}

	public KeyValueWizard(String wizardName, ImageDescriptor pageImageDescriptor, Map<String, String> keyValues) {
		_keyValues = keyValues;
		_page = new KeyValuePage(wizardName, wizardName, pageImageDescriptor, _keyValues);
		addPage(_page);

	}

	@Override
	public boolean canFinish() {
		return _page.isPageComplete();
	}

	@Override
	public boolean performFinish() {

		return true;
	}

	public Map<String, String> getValues() throws NotFinishedException {
		if (!canFinish()) {
			throw new NotFinishedException("The key-value map is not set.");
		}
		return _keyValues;
	}

}
