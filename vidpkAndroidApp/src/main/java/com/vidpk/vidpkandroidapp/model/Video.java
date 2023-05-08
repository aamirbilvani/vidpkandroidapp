package com.vidpk.vidpkandroidapp.model;

import com.vidpk.vidpkandroidapp.util.Globals;

public class Video extends VidpkObject{

	private static final long serialVersionUID = 1L;
	public String title;
	public String vid;
	public String last_updated;
	public String path;
	public boolean isMP4;
	public String file_name;

    public Video(String title, String vid, String last_updated, String file_name, String isMP4, String path)
	{
		this.title = Globals.cleanString(title);
		this.last_updated = last_updated;
		this.vid = vid;
		this.file_name = file_name;
		this.path = path;
		if(isMP4.equals("1"))
			this.isMP4 = true;
		else
			this.isMP4 = false;
	}

    public String retrieveThumbnail()
	{
		return "http://thumb.vidpk.com/2_" + vid + ".jpg";
	}

    public String getURL()
    {
    	return "http://vidpk.com/ajax/mob/mobView.php?vid=" + vid; 
    }

    public String getURI()
    {
    	return this.path;
    }
    
    public String toString()
    {
    	String ret = "";
    	ret += "\nTitle: " + title;
    	ret += "\nVid: " + vid;
    	ret += "\nLast Updated: " + last_updated;
    	ret += "\nisMP4: " + isMP4;
    	ret += "\nFile Name: " + file_name;
    	return ret;
    }
}