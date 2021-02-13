package com.in.ka.vatika;

public class MailDataPojo {
	private String name;
	private String email;
	private String site;
	private String dated;
	
	private float mf;
	private float cm;
	
	private float cf;
	
	private float due;
	
	public float getCm() {
		return cm;
	}

	public void setCm(float cm) {
		this.cm = cm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDated() {
		return dated;
	}

	public void setDated(String dated) {
		this.dated = dated;
	}

	public float getMf() {
		return mf;
	}

	public void setMf(float mf) {
		this.mf = mf;
	}

	public float getCf() {
		return cf;
	}

	public void setCf(float cf) {
		this.cf = cf;
	}
	
	public float getDue() {
		return due;
	}

	public void setDue(float due) {
		this.due = due;
	}
	
	
}
