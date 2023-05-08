package com.vidpk.vidpkandroidapp.services;

import java.util.ArrayList;

import android.content.Context;

import com.vidpk.vidpkandroidapp.model.VidpkObject;

public class SearchSeeker extends GenericSeeker<VidpkObject> {
	private String keyword;
	
	public SearchSeeker(String keyword)
	{
		this.keyword = keyword;
	}
	
	public ArrayList<VidpkObject> find(Context c){
		return retrieveSearchResults(c);
	}

	@Override
	public ArrayList<VidpkObject> find(Context c, int subsequent) {
		return find(c);
	}

	private ArrayList<VidpkObject> retrieveSearchResults(Context c){
		String url = constructSearchUrl();
		String response = httpRetriever.retrieve(c, url);
		//Log.d(getClass().getSimpleName(), response); //Will show exception if string is null
		return jsonParser.parseShowResponse(response);
	}

	public String retrieveUniqueURL() {
		return "/c/search?q=" + keyword + "&limit=50&exclude=person";
	}
}
