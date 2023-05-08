package com.vidpk.vidpkandroidapp;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ListView;

import com.vidpk.vidpkandroidapp.model.Publisher;
import com.vidpk.vidpkandroidapp.model.VidpkObject;
import com.vidpk.vidpkandroidapp.services.PageFlipper;
import com.vidpk.vidpkandroidapp.services.ServerRequestHandler;
import com.vidpk.vidpkandroidapp.ui.PublisherAdapter;
import com.vidpk.vidpkandroidapp.util.Globals;

public class BrowseByPublishers extends ListActivity implements VidpkListActivity{

	private ServerRequestHandler srh = new ServerRequestHandler(
			BrowseByPublishers.this, this);

	private ArrayList<Publisher> publishers = new ArrayList<Publisher>();
	private PublisherAdapter publisherAdapter;
	private int requesting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);

		publisherAdapter = new PublisherAdapter(this,
				android.R.layout.simple_list_item_1, publishers);
		setListAdapter(publisherAdapter);

		requesting = getIntent().getIntExtra("requesting", 0);

		if(requesting == Globals.TV)
			setTitle(getString(R.string.title_activity_browse_by_stations));
		else if(requesting == Globals.INDIE)
			setTitle(getString(R.string.title_activity_browse_by_publishers));
		
		srh.performRequest(null, 0, requesting);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.browse_by_shows, menu);
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		v.playSoundEffect(SoundEffectConstants.CLICK);

		Publisher station = publisherAdapter.getItem(position);

		PageFlipper pf;
		if(requesting == Globals.TV)
			pf = new PageFlipper(this, BrowseByShows.class, Globals.SHOW_BY_STATION, station);
		else if(requesting == Globals.INDIE)
			pf = new PageFlipper(this, BrowseByShows.class, Globals.SHOW_BY_PUBLISHER, station);
		else
			return;
		
		pf.flipPage();
	}

	public void dataChanged(ArrayList<VidpkObject> newResults) {
		Iterator<VidpkObject> i = newResults.iterator();
		while (i.hasNext()) {
			Publisher thisPub = (Publisher) i.next();
			publishers.add(thisPub);
			System.out.println(thisPub);
		}
		publisherAdapter.notifyDataSetChanged();
	}

	
	@Override
	public void onStart() {
		super.onStart();
	}

}