package com.vidpk.vidpkandroidapp;

import java.util.ArrayList;

import com.vidpk.vidpkandroidapp.model.VidpkObject;

public interface VidpkListActivity {

	public void dataChanged(ArrayList<VidpkObject> newResults);
}
