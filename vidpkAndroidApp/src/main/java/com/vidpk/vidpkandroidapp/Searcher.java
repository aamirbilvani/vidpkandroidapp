package com.vidpk.vidpkandroidapp;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.vidpk.vidpkandroidapp.model.SearchKeyword;
import com.vidpk.vidpkandroidapp.model.Show;
import com.vidpk.vidpkandroidapp.model.VidpkObject;
import com.vidpk.vidpkandroidapp.services.PageFlipper;
import com.vidpk.vidpkandroidapp.services.ServerRequestHandler;
import com.vidpk.vidpkandroidapp.ui.ShowAdapter;
import com.vidpk.vidpkandroidapp.util.Globals;

public class Searcher extends ListActivity implements VidpkListActivity {

	private ArrayList<Show> shows = new ArrayList<Show>();
	private ShowAdapter searchAdapter;

	private ServerRequestHandler srh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);

		// Get the intent, verify the action and get the query
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			SearchKeyword parent = new SearchKeyword(query);
			srh = new ServerRequestHandler(Searcher.this, this);
			srh.performRequest(parent, 1, Globals.SEARCH);
			searchAdapter = new ShowAdapter(this,
					android.R.layout.simple_list_item_1, shows);
			setListAdapter(searchAdapter);
			
			setTitle("Search results for " + query);
		}
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		v.playSoundEffect(SoundEffectConstants.CLICK);

		Show show = searchAdapter.getItem(position);
		
		PageFlipper pf = new PageFlipper(this, BrowseByVideos.class, Globals.VIDEO, show);
		pf.flipPage();
	}

	public void dataChanged(ArrayList<VidpkObject> newResults) {
		Iterator<VidpkObject> i = newResults.iterator();
		while (i.hasNext()) {
			Show thisSh = (Show) i.next();
			shows.add(thisSh);
		}
		searchAdapter.notifyDataSetChanged();
	}

	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		
		int titleId = getResources().getIdentifier("action_bar_title", "id",
	            "android");
	    TextView yourTextView = (TextView) findViewById(titleId);
	    Typeface tf = Typeface.createFromAsset(this.getAssets(),
				"fonts/Raleway-SemiBold.otf");

	    yourTextView.setTypeface(tf);
	}
}