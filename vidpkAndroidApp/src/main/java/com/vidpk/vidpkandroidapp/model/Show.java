package com.vidpk.vidpkandroidapp.model;

import com.vidpk.vidpkandroidapp.util.Globals;

public class Show extends VidpkObject{

	private static final long serialVersionUID = 1L;
	public String name;
	public String last_updated;
	public String chid;

    public Show(String name, String chid, String last_updated)
	{
		this.name = Globals.cleanString(name);
		this.last_updated = last_updated;
		this.chid = chid;
	}

    public String retrieveThumbnail()
	{
		return "http://vidpk.com/public/img/shows/1_" + chid + ".jpg";
	}
}
