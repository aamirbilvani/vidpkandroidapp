package com.vidpk.vidpkandroidapp.services;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vidpk.vidpkandroidapp.model.Show;
import com.vidpk.vidpkandroidapp.model.Publisher;
import com.vidpk.vidpkandroidapp.model.Video;
import com.vidpk.vidpkandroidapp.model.VidpkObject;

public class MyJsonParser {

	public ArrayList<VidpkObject> parseStationResponse(String json) {
		ArrayList<VidpkObject> parsedList = new ArrayList<VidpkObject>();
		if(json==null)
			return parsedList;
		try {
			JSONObject o = new JSONObject(json);
			JSONArray jArray = o.getJSONArray("content"); 

			for (int i = 0; i < jArray.length(); i++) {
				JSONObject entry = jArray.getJSONObject(i);
				Publisher s = new Publisher((String) entry.get("name"),
						(String) entry.get("ID"));
				parsedList.add(s);
			}
		} catch (JSONException e) {
			System.out.println(e.toString());
			System.out.println(e.getStackTrace());
		}
		return parsedList;
	}

	public ArrayList<VidpkObject> parseShowResponse(String json) {
		ArrayList<VidpkObject> parsedList = new ArrayList<VidpkObject>();
		if(json==null)
			return parsedList;
		try {
			JSONObject o = new JSONObject(json);
			JSONArray jArray = o.getJSONArray("content"); 
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject entry = jArray.getJSONObject(i);
				Show s = new Show((String) entry.get("name"),
						(String) entry.get("ID"), (String) entry.get("secondline"));
				parsedList.add(s);
			}
		} catch (JSONException e) {
			System.out.println(e.toString());
			System.out.println(e.getStackTrace());
		}
		return parsedList;
	}

	public ArrayList<VidpkObject> parseVideoResponse(String json) {
		ArrayList<VidpkObject> parsedList = new ArrayList<VidpkObject>();
		if(json==null)
			return parsedList;
		try {
			JSONObject o = new JSONObject(json);
			JSONArray jArray = o.getJSONArray("content"); 
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject entry = jArray.getJSONObject(i);
				Video v = new Video((String) entry.get("name"),
						(String) entry.get("ID"), (String) entry.get("secondline"),
						"", "", "");
				parsedList.add(v);
			}
		} catch (JSONException e) {
			System.out.println(e.toString());
			System.out.println(e.getStackTrace());
		}
		return parsedList;
	}

	public ArrayList<VidpkObject> parseVideoInfoResponse(String json) {
		ArrayList<VidpkObject> parsedList = new ArrayList<VidpkObject>();
		if(json==null)
			return parsedList;
		try {
			JSONObject o = new JSONObject(json);
			JSONObject content = o.getJSONObject("content");
			JSONObject video_info = content.getJSONObject("video"); 

			Video v = new Video((String) video_info.get("title"),
						(String) video_info.get("VID"), "",
						(String) video_info.get("url"), (String) video_info.get("mp4_available"),
						(String) video_info.get("path"));
			parsedList.add(v);
		} catch (JSONException e) {
			System.out.println(e.toString());
			System.out.println(e.getStackTrace());
		}
		return parsedList;
	}
}