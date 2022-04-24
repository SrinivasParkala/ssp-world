package com.ssp.rmx.sdo;

public class PythonPredictRequest {

	private String path;
	
	private String[] yArrayInput;
	
	private double[][] xArrayInput;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String[] getyArrayInput() {
		return yArrayInput;
	}

	public void setyArrayInput(String[] yArrayInput) {
		this.yArrayInput = yArrayInput;
	}

	public double[][] getxArrayInput() {
		return xArrayInput;
	}

	public void setxArrayInput(double[][] xArrayInput) {
		this.xArrayInput = xArrayInput;
	}
}
