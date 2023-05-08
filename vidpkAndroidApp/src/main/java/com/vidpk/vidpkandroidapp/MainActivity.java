package com.vidpk.vidpkandroidapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.vidpk.vidpkandroidapp.model.HomepageButton;
import com.vidpk.vidpkandroidapp.ui.HomepageExpandableListAdapter;
import com.vidpk.vidpkandroidapp.util.Globals;

public class MainActivity extends Activity {

	Tracker mTracker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		AdView mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
		
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Raleway-SemiBold.otf");

		if (!Globals.isInternetConnectionAvailable(this)) {
			Globals.showExitAlertDialog(MainActivity.this, MainActivity.this,
					getString(R.string.no_internet_title),
					getString(R.string.no_internet_text));
		}

		setupSearchView(tf);

		
   		ExpandableListView expandbleLis = (ExpandableListView) findViewById(R.id.mainExpListView);
   		expandbleLis.setGroupIndicator(null);
   		expandbleLis.setClickable(true);
   		setGroupData();
   		setChildGroupData();
   		HomepageExpandableListAdapter mNewAdapter = new HomepageExpandableListAdapter(
   				groupItem, childItem, MainActivity.this);
   		mNewAdapter.setInflater(
   				(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
   				MainActivity.this);
   		expandbleLis.setAdapter(mNewAdapter);

		VidpkApplication application = (VidpkApplication) getApplication();
		mTracker = application.getDefaultTracker();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();
		mTracker.setScreenName("Homepage");
		mTracker.send(new HitBuilders.ScreenViewBuilder().build());
	}

	private void setupSearchView(Typeface tf) {
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView = (SearchView) findViewById(R.id.searchView);

		// Styling for search box
		int searchPlateId = searchView.getContext().getResources()
				.getIdentifier("android:id/search_plate", null, null);
		searchView.findViewById(searchPlateId).setBackgroundColor(
				getResources().getColor(R.color.search_box_background_color));
		int searchTextViewId = searchView.getContext().getResources()
				.getIdentifier("android:id/search_src_text", null, null);
		TextView searchTextView = (TextView) searchView
				.findViewById(searchTextViewId);
		searchTextView.setHintTextColor(Color.LTGRAY);
		searchTextView.setTextColor(Color.WHITE);
		searchTextView.setTypeface(tf);
		searchTextView.setTextSize(24);

		SearchableInfo searchableInfo = searchManager
				.getSearchableInfo(getComponentName());
		searchView.setSearchableInfo(searchableInfo);
	}

	public void setGroupData() {
		groupItem.add(getString(R.string.tv));
		groupItem.add(getString(R.string.film));
		groupItem.add(getString(R.string.telefilm));
		groupItem.add(getString(R.string.indie));
		groupItem.add(getString(R.string.viral));
	}

	ArrayList<String> groupItem = new ArrayList<String>();
	ArrayList<Object> childItem = new ArrayList<Object>();

	public void setChildGroupData() {
		/**
		 * Add Data For TV
		 */
		ArrayList<HomepageButton> child = new ArrayList<HomepageButton>();
		child.add(new HomepageButton(getString(R.string.btn_browse_by_station),
				Globals.HOMEPAGE_BTN_BrowseByStationTV));
		child.add(new HomepageButton(getString(R.string.btn_recently_updated),
				Globals.HOMEPAGE_BTN_RecentlyUpdatedTV));
		child.add(new HomepageButton(getString(R.string.btn_popular_month),
				Globals.HOMEPAGE_BTN_PopularMonthTV));
		child.add(new HomepageButton(getString(R.string.btn_popular_today),
				Globals.HOMEPAGE_BTN_PopularTodayTV));
		childItem.add(child);

		/**
		 * Add Data For FILM
		 */
		child = new ArrayList<HomepageButton>();
		child.add(new HomepageButton(getString(R.string.btn_all), Globals.HOMEPAGE_BTN_AllFilm));
		child.add(new HomepageButton(getString(R.string.btn_popular_month),
				Globals.HOMEPAGE_BTN_PopularMonthFilm));
		child.add(new HomepageButton(getString(R.string.btn_popular_today),
				Globals.HOMEPAGE_BTN_PopularTodayFilm));
		childItem.add(child);

		/**
		 * Add Data For TELEFILM
		 */
		child = new ArrayList<HomepageButton>();
		child.add(new HomepageButton(getString(R.string.btn_browse_by_station),
				Globals.HOMEPAGE_BTN_BrowseByStationTelefilm));
		child.add(new HomepageButton(getString(R.string.btn_recently_updated),
				Globals.HOMEPAGE_BTN_RecentlyUpdatedTelefilm));
		child.add(new HomepageButton(getString(R.string.btn_popular_month),
				Globals.HOMEPAGE_BTN_PopularMonthTelefilm));
		child.add(new HomepageButton(getString(R.string.btn_popular_today),
				Globals.HOMEPAGE_BTN_PopularTodayTelefilm));
		childItem.add(child);

		/**
		 * Add Data For INDIE
		 */
		child = new ArrayList<HomepageButton>();
		child.add(new HomepageButton(getString(R.string.btn_browse_by_publisher),
				Globals.HOMEPAGE_BTN_BrowseByPublisher));
		child.add(new HomepageButton(getString(R.string.btn_recently_updated),
				Globals.HOMEPAGE_BTN_RecentlyUpdatedIndie));
		child.add(new HomepageButton(getString(R.string.btn_popular_month),
				Globals.HOMEPAGE_BTN_PopularMonthIndie));
		child.add(new HomepageButton(getString(R.string.btn_popular_today),
				Globals.HOMEPAGE_BTN_PopularTodayIndie));
		childItem.add(child);

		/**
		 * Add Data For VIRAL
		 */
		child = new ArrayList<HomepageButton>();
		child.add(new HomepageButton(getString(R.string.btn_viral),
				Globals.HOMEPAGE_BTN_Viral));
		childItem.add(child);

	}

}