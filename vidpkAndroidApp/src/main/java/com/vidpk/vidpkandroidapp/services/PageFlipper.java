package com.vidpk.vidpkandroidapp.services;

import android.content.Context;
import android.content.Intent;

import com.vidpk.vidpkandroidapp.model.VidpkObject;

public class PageFlipper {
	
	private VidpkObject parent;
	private Context context;
	private Class<?> nextClass;
	private int looking_for;
	
	public PageFlipper(Context context, Class<?> nextClass, int looking_for, VidpkObject parent)
	{
		this.context = context;
		this.nextClass = nextClass;
		this.looking_for = looking_for;
		this.parent = parent;
	}

	public void flipPage()
	{
		Intent intent = new Intent(context, nextClass);
		intent.putExtra("requesting", looking_for);
		intent.putExtra("parent", parent);
		context.startActivity(intent);
	}
}
