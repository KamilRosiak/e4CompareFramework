package de.tu_bs.cs.isf.e4cf.core.compare.remote.handler;
public class RemoteComparisonStatus {

	private String status;
	private int time;
	private String result;
	private String error;
	private String uuid;

	public RemoteComparisonStatus(String status, int time, String result, String error, String uuid) {
		super();
		this.status = status;
		this.time = time;
		this.result = result;
		this.error = error;
		this.uuid = uuid;
	}

	public String getState() {
		return status;
	}

	public void setState(String state) {
		this.status = state;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	@Override
	public String toString() {
		return "RemoteComparisonStatus [state=" + status + ", time=" + time + ", result=" + result + ", error=" + error
				+ ", uuid=" + uuid + "]";
	}

}