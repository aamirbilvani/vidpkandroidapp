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
import com.vidpk.vidpkandroidapp.model.Video;
import com.vidpk.vidpkandroidapp.model.VidpkObject;
import com.vidpk.vidpkandroidapp.services.PageFlipper;
import com.vidpk.vidpkandroidapp.services.ServerRequestHandler;
import com.vidpk.vidpkandroidapp.ui.VideoAdapter;
import com.vidpk.vidpkandroidapp.util.Globals;

public class BrowseByVideos extends ListActivity  implements VidpkListActivity {

	private ArrayList<Video> videos = new ArrayList<Video>();
	private VideoAdapter videoAdapter;
	private Show show;
	private int requesting;
	private int currentFirstVisibleItem;

	private ServerRequestHandler srh = new ServerRequestHandler(
			BrowseByVideos.this, this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);

		show = (Show) getIntent().getSerializableExtra("parent");
		requesting = (int) getIntent().getSerializableExtra("requesting");

		videoAdapter = new VideoAdapter(this,
				android.R.layout.simple_list_item_1, videos);

		if(requesting==Globals.VIRAL)
			setTitle("Viral Videos");
		else
			setTitle("Videos for " + show.name);

		setListAdapter(videoAdapter);

		ListView thisList = (ListView) findViewById(android.R.id.list);
		thisList.setOnScrollListener(scrollListener);

		if(requesting==Globals.VIRAL)
			srh.performRequest(show, 0, Globals.VIRAL);
		else
			srh.performRequest(show, 0, Globals.VIDEO);
	}

	private OnScrollListener scrollListener = new OnScrollListener() {

		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if ((totalItemCount - Globals
					.getTotalInAView(findViewById(android.R.id.list))) == firstVisibleItem
					&& currentFirstVisibleItem != firstVisibleItem) {
				if(requesting==Globals.VIRAL)
				{
					srh.performRequest(
							show,
							((totalItemCount + Globals.PER_PAGE - 1) / Globals.PER_PAGE),
							Globals.VIRAL);
				}
				else {
					srh.performRequest(
							show,
							((totalItemCount + Globals.PER_PAGE - 1) / Globals.PER_PAGE),
							Globals.VIDEO); // rounding up the division so when we
					// reach the end of the list the last
					// batch is not returned again
				}
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

		Video video = videoAdapter.getItem(position);
		PageFlipper pf = new PageFlipper(this, VideoPlayer.class, Globals.VIDEO_INFO, video);
		pf.flipPage();
	}

	public void dataChanged(ArrayList<VidpkObject> newResults) {
		Iterator<VidpkObject> i = newResults.iterator();
		while (i.hasNext()) {
			Video thisVid = (Video) i.next();
			videos.add(thisVid);
		}
		videoAdapter.notifyDataSetChanged();
	}

	//@Override
	//public void onRestart(){
	//	super.onRestart();
	//	ServerRequestHandler sr = new ServerRequestHandler(BrowseByVideos.this, this, BrowseByVideos.class);
	//	sr.performRequest(show, 0, requestType);
	//}
}