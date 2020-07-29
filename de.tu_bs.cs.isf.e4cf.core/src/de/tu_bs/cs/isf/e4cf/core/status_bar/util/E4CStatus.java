package de.tu_bs.cs.isf.e4cf.core.status_bar.util;

public class E4CStatus {
	private String statusText;
	private int maximalStatus;
	private int currentStatus;
	
	public E4CStatus() {
		statusText = "";
		maximalStatus = 100;
		currentStatus = 0;
	}
	
	public E4CStatus(String statusText, int maximalStatus, int currentStatus) {
		setStatusText(statusText);
		setMaximalStatus(maximalStatus);
		setCurrentStatus(currentStatus);
	}

	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public int getMaximalStatus() {
		return maximalStatus;
	}
	public void setMaximalStatus(int maximalStatus) {
		this.maximalStatus = maximalStatus;
	}
	public int getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	
	
	
}
