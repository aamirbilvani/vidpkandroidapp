package com.vidpk.vidpkandroidapp.services;

import java.util.ArrayList;

import android.content.Context;

import com.vidpk.vidpkandroidapp.model.Publisher;
import com.vidpk.vidpkandroidapp.model.VidpkObject;
import com.vidpk.vidpkandroidapp.util.Globals;

public class ShowSeeker extends GenericSeeker<VidpkObject> {

	private int page = 1;
	private int seek_what;
	
	public ShowSeeker(int what)
	{
		this.seek_what = what;
	}
	
	public int getRequestType()
	{
		return seek_what;
	}
	
	public ArrayList<VidpkObject> find(Context c){
		return find(c, 0);
	}

	public ArrayList<VidpkObject> find(Context c, int subsequent){
		return retrieveShowList(c, subsequent);
	}

	private ArrayList<VidpkObject> retrieveShowList(Context c, int subsequent){
		page = subsequent + 1;
		String url = constructSearchUrl();
		String response = httpRetriever.retrieve(c, url);
		//Log.d(getClass().getSimpleName(), response); //Will show exception if string is null
		return jsonParser.parseShowResponse(response);
	}

	public String retrieveUniqueURL() {
		if(this.seek_what==Globals.TV_BY_RECENTLY_UPDATED)
			return "/c/tv/all/recent?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;
		else if(this.seek_what==Globals.TV_BY_POPULAR_MONTH)
			return "/c/tv/all/popular_month?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;
		else if(this.seek_what==Globals.TV_BY_POPULAR_DAY)
			return "/c/tv/all/popular_day?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;
		else if(this.seek_what==Globals.FILM_BY_ALL)
			return "/c/film/all/alphabetical?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;
		else if(this.seek_what==Globals.FILM_BY_POPULAR_MONTH)
			return "/c/film/all/popular_month?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;
		else if(this.seek_what==Globals.FILM_BY_POPULAR_DAY)
			return "/c/film/all/popular_day?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;
		else if(this.seek_what==Globals.SHOW_BY_PUBLISHER || this.seek_what==Globals.SHOW_BY_STATION)
			return "/stations/" + ((Publisher) parentObj).sid + "/shows/recent?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;
		else if(this.seek_what==Globals.INDIE_BY_RECENTLY_UPDATED)
			return "/c/indie/all/recent?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;
		else if(this.seek_what==Globals.INDIE_BY_POPULAR_MONTH)
			return "/c/indie/all/popular_month?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;
		else if(this.seek_what==Globals.INDIE_BY_POPULAR_DAY)
			return "/c/indie/all/popular_day?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;
		else if(this.seek_what==Globals.TELEFILM_BY_RECENTLY_UPDATED)
			return "/c/telefilm/all/recent?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;
		else if(this.seek_what==Globals.TELEFILM_BY_POPULAR_MONTH)
			return "/c/telefilm/all/popular_month?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;
		else if(this.seek_what==Globals.TELEFILM_BY_POPULAR_DAY)
			return "/c/telefilm/all/popular_day?offset=" + ((page-1)*Globals.PER_PAGE) + "&limit=" + Globals.PER_PAGE;

		return null;
	}

}
