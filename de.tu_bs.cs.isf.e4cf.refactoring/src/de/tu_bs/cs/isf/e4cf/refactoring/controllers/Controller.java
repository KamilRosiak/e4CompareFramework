package de.tu_bs.cs.isf.e4cf.refactoring.controllers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import de.tu_bs.cs.isf.e4cf.refactoring.views.View;

public abstract class Controller<T extends View> {

	protected T view;

	private boolean result;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	protected abstract void initView();

	public Controller(T view) {
		this.view = view;

		initView();

		view.getApplyButton().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				if (event.detail == 0) {
					result = true;
					view.closeView();

				}
			}
		});

		view.getAbortButton().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				if (event.detail == 0) {
					result = false;
					view.closeView();

				}
			}
		});

	}

}
