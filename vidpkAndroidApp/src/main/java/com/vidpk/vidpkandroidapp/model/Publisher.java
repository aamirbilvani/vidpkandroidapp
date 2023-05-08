package com.vidpk.vidpkandroidapp.model;

import com.vidpk.vidpkandroidapp.util.Globals;

public class Publisher extends VidpkObject{

	private static final long serialVersionUID = 1L;
	public String name;
	public String sid;
	
	public Publisher(String name, String sid)
	{
		this.sid = sid;
		this.name = Globals.cleanString(name);
	}
	
	public String toString()
	{
		return "Name: " + name + ", Station ID:" + sid; 
	}
	
	public String retrieveThumbnail()
	{
		return "http://vidpk.com/public/img/stations/" + sid + ".jpg";
	}
}
