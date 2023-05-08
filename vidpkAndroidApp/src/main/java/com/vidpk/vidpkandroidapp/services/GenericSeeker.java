package com.vidpk.vidpkandroidapp.services;

import java.util.ArrayList;
import com.vidpk.vidpkandroidapp.model.VidpkObject;

import android.content.Context;

public abstract class GenericSeeker<T> {

	protected static final String BASE_URL = "http://api.vidpk.com/";

	protected HttpRetriever httpRetriever = new HttpRetriever();
	protected MyJsonParser jsonParser = new MyJsonParser();
	protected VidpkObject parentObj;

	public abstract ArrayList<VidpkObject> find(Context c);

	public abstract ArrayList<VidpkObject> find(Context c, int subsequent);

	public abstract String retrieveUniqueURL();

	protected String constructSearchUrl() {
		StringBuffer sb = new StringBuffer();
		sb.append(BASE_URL);
		sb.append(retrieveUniqueURL());
		sb.append("&platform=mobile");
		return sb.toString();
	}

	public void setParent(VidpkObject parentObj)
	{
		this.parentObj=parentObj;
	}
}