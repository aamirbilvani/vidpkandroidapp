package com.vidpk.vidpkandroidapp.model;

public class HomepageButton {
	private String text;
	private int id;	

	public HomepageButton(String text, int id) {
		super();
		this.text = text;
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
