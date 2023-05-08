package com.vidpk.vidpkandroidapp.services;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.vidpk.vidpkandroidapp.io.FlushedInputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HttpRetriever {
	private HttpURLConnection client;

	public HttpRetriever() {
	}

	public String retrieve(Context c, String url) {
		InputStream in = this.retrieveStream(url);
		try
		{
			return inputStreamToString(in);
		}
		catch (Exception e)
		{
			System.out.println(e);
			return null;
		}
		finally
		{
			client.disconnect();
		}
	}

	public Bitmap retrieveBitmap(String url) throws Exception {
		InputStream inputStream = null;
		try {
			inputStream = this.retrieveStream(url);
			final Bitmap bitmap = BitmapFactory
					.decodeStream(new FlushedInputStream(inputStream));
			return bitmap;
		} finally {
			client.disconnect();
		}
	}

	public InputStream retrieveStream(String url) {
		InputStream in = null;
		try {
			URL urlObj = new URL(url);
			client = (HttpURLConnection) urlObj.openConnection();
			in = new BufferedInputStream(client.getInputStream());
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return in;
	}

	public String inputStreamToString(InputStream stream) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");
	    String output = "";
	    int data = reader.read();
	    while(data != -1)
	    {
	    	char current = (char) data;
	    	data = reader.read();
	    	output += current;
	    }   
	    return output;
	}

}