package com.vidpk.vidpkandroidapp.services;

import java.util.ArrayList;

import android.content.Context;

import com.vidpk.vidpkandroidapp.model.VidpkObject;
import com.vidpk.vidpkandroidapp.util.Globals;

public class PublisherSeeker extends GenericSeeker<VidpkObject> {

	private int seek_what;
	
	public PublisherSeeker(int what)
	{
		this.seek_what = what;
	}
	
	public int getRequestType()
	{
		return seek_what;
	}

	public ArrayList<VidpkObject> find(Context c){
		ArrayList<VidpkObject> stationList = retrieveStationList(c);
		return stationList;
	}

	public ArrayList<VidpkObject> find(Context c, int subsequent){
		return find(c);
	}

	private ArrayList<VidpkObject> retrieveStationList(Context c){
		String url = constructSearchUrl();
		String response = httpRetriever.retrieve(c, url);
		//Log.d(getClass().getSimpleName(), response); //Will show exception if string is null
		return jsonParser.parseStationResponse(response);
	}

	public String retrieveUniqueURL() {
		if(this.seek_what==Globals.TV)
			return "/stations/tv?";
		else if(this.seek_what==Globals.INDIE)
			return "/stations/indie?";

		return null;
	}
}