package com.vidpk.vidpkandroidapp;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.vidpk.vidpkandroidapp.model.Show;
import com.vidpk.vidpkandroidapp.model.Publisher;
import com.vidpk.vidpkandroidapp.model.VidpkObject;
import com.vidpk.vidpkandroidapp.services.PageFlipper;
import com.vidpk.vidpkandroidapp.services.ServerRequestHandler;
import com.vidpk.vidpkandroidapp.ui.ShowAdapter;
import com.vidpk.vidpkandroidapp.util.Globals;

public class BrowseByShows extends ListActivity implements VidpkListActivity {

	private ArrayList<Show> shows = new ArrayList<Show>();
	private ShowAdapter showAdapter;
	private Publisher station;
	private int currentFirstVisibleItem;
	private int requesting;

	private ServerRequestHandler srh = new ServerRequestHandler(
			BrowseByShows.this, this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);

		station = (Publisher) getIntent().getSerializableExtra("parent");
		requesting = getIntent().getIntExtra("requesting", 0);
		showAdapter = new ShowAdapter(this,
				android.R.layout.simple_list_item_1, shows);

		if(requesting==Globals.TV_BY_RECENTLY_UPDATED)
			setTitle("TV: Recently Updated");
		else if(requesting==Globals.TV_BY_POPULAR_MONTH)
			setTitle("TV: Popular This Month");
		else if(requesting==Globals.TV_BY_POPULAR_DAY)
			setTitle("TV: Popular Today");
		else if(requesting==Globals.FILM_BY_ALL)
			setTitle("All Films");
		else if(requesting==Globals.FILM_BY_POPULAR_MONTH)
			setTitle("Film: Popular This Month");
		else if(requesting==Globals.FILM_BY_POPULAR_DAY)
			setTitle("Film: Popular Today");
		else if(requesting==Globals.INDIE_BY_RECENTLY_UPDATED)
			setTitle("Indie: Recently Updated");
		else if(requesting==Globals.INDIE_BY_POPULAR_MONTH)
			setTitle("Indie: Popular This Month");
		else if(requesting==Globals.INDIE_BY_POPULAR_DAY)
			setTitle("Indie: Popular Today");
		else if(requesting==Globals.SHOW_BY_PUBLISHER)
			setTitle("Collections for " + station.name);
		else if(requesting==Globals.SHOW_BY_STATION)
			setTitle("Shows for " + station.name);
		else if(requesting==Globals.TELEFILM_BY_RECENTLY_UPDATED)
			setTitle("Telefilms: Recently Updated");
		else if(requesting==Globals.TELEFILM_BY_POPULAR_MONTH)
			setTitle("Telefilms: Popular This Month");
		else if(requesting==Globals.TELEFILM_BY_POPULAR_DAY)
			setTitle("Telefilms: Popular Today");


		setListAdapter(showAdapter);
		ListView thisList = (ListView) findViewById(android.R.id.list);
		thisList.setOnScrollListener(scrollListener);
		srh.performRequest(station, 0, requesting);
	}

	private OnScrollListener scrollListener = new OnScrollListener() {

		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if ((totalItemCount - Globals
					.getTotalInAView(findViewById(android.R.id.list))) == firstVisibleItem
					&& currentFirstVisibleItem != firstVisibleItem) {
				srh.performRequest(
						station,
						((totalItemCount + Globals.PER_PAGE - 1) / Globals.PER_PAGE),
							requesting); // rounding up the division so when we
										// reach the end of the list the last
										// batch is not returned again
			}
			currentFirstVisibleItem = firstVisibleItem;
		}

		public void onScrollStateChanged(AbsListView view, int scrollState) {
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.browse_by_shows, menu);
		return true;
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		v.playSoundEffect(SoundEffectConstants.CLICK);

		Show show = showAdapter.getItem(position);
		PageFlipper pf = new PageFlipper(this, BrowseByVideos.class, Globals.VIDEO, show);
		pf.flipPage();
	}


	public void dataChanged(ArrayList<VidpkObject> newResults) {
		Iterator<VidpkObject> i = newResults.iterator();
		while (i.hasNext()) {
			Show thisSh = (Show) i.next();
			shows.add(thisSh);
		}
		showAdapter.notifyDataSetChanged();
	}
}