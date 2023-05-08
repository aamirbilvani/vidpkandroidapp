package com.vidpk.vidpkandroidapp.services;

import java.util.ArrayList;

import android.content.Context;

import com.vidpk.vidpkandroidapp.model.Show;
import com.vidpk.vidpkandroidapp.model.VidpkObject;
import com.vidpk.vidpkandroidapp.util.Globals;

public class VideoSeeker extends GenericSeeker<VidpkObject> {

	private int page = 1;
	private boolean is_viral = false;

	public VideoSeeker(boolean is_viral)
	{
		this.is_viral = is_viral;
	}

	public ArrayList<VidpkObject> find(Context c){
		return find(c, 0);
	}

	public ArrayList<VidpkObject> find(Context c, int subsequent){
		return retrieveVideoList(c, subsequent);
	}

	private ArrayList<VidpkObject> retrieveVideoList(Context c, int subsequent){
		page = subsequent + 1;
		String url = constructSearchUrl();
		String response = httpRetriever.retrieve(c, url);
		//Log.d(getClass().getSimpleName(), response); //Will show exception if string is null
		return jsonParser.parseVideoResponse(response);
	}

	public String retrieveUniqueURL() {
		if(is_viral)
			return "/videos/viral?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;
		else
			return "/c/" + ((Show) parentObj).chid + "/videos/recent?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;
	}
}
