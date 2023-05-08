package com.vidpk.vidpkandroidapp.services;

import java.util.ArrayList;

import android.content.Context;

import com.vidpk.vidpkandroidapp.model.VidpkObject;

public class VideoInfoSeeker extends GenericSeeker<VidpkObject> {
	private String search_vid;
	
	public VideoInfoSeeker(String vid)
	{
		this.search_vid = vid;
	}
	
	public ArrayList<VidpkObject> find(Context c) {
		return find(c, 0);
	}

	public ArrayList<VidpkObject> find(Context c, int subsequent) {
		return retrieveVideoList(c, subsequent);
	}

	private ArrayList<VidpkObject> retrieveVideoList(Context c, int subsequent) {
		String url = constructSearchUrl();
		String response = httpRetriever.retrieve(c, url);
		httpRetriever.retrieve(c, logVideoViewURL());
		//Log.d(getClass().getSimpleName(), response); //Will show exception if string is null
		return jsonParser.parseVideoInfoResponse(response);
	}

	public String retrieveUniqueURL() {
		return "/videos/" + this.search_vid + "?";
	}

	public String logVideoViewURL() {
		//This function is completely outside of the Seeker architecture
		//TODO: Find a way to integrate it better
		return "http://api.vidpk.com/videos/logview/" + this.search_vid + "?platform=mobile";
	}
}
