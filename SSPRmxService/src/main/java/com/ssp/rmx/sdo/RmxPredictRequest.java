package com.ssp.rmx.sdo;

public class RmxPredictRequest {

	private String category;
	
	private String subCategory;
	
	private String grade;
	
	private String[] headers;
	
	private double[][] requestValues;

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public double[][] getRequestValues() {
		return requestValues;
	}

	public void setRequestValues(double[][] requestValues) {
		this.requestValues = requestValues;
	}
	
}
