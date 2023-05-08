package com.vidpk.vidpkandroidapp.ui;

import java.util.ArrayList;

import com.vidpk.vidpkandroidapp.BrowseByPublishers;
import com.vidpk.vidpkandroidapp.BrowseByShows;
import com.vidpk.vidpkandroidapp.BrowseByVideos;
import com.vidpk.vidpkandroidapp.R;
import com.vidpk.vidpkandroidapp.model.HomepageButton;
import com.vidpk.vidpkandroidapp.services.PageFlipper;
import com.vidpk.vidpkandroidapp.util.Globals;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class HomepageExpandableListAdapter extends BaseExpandableListAdapter {

	public ArrayList<String> groupItem;
	public ArrayList<HomepageButton> tempChild;
	public ArrayList<Object> Childtem = new ArrayList<Object>();
	public LayoutInflater minflater;
	public Activity activity;
	private Typeface tf;
	private PageFlipper pageflip;
	private Context context;


	public HomepageExpandableListAdapter(ArrayList<String> grList,
			ArrayList<Object> childItem, Context c) {
		groupItem = grList;
		this.Childtem = childItem;
		this.context = c;
	}

	public void setInflater(LayoutInflater mInflater, Activity act) {
		this.minflater = mInflater;
		activity = act;
		tf = Typeface.createFromAsset(act.getAssets(),
				"fonts/Raleway-SemiBold.otf");
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		tempChild = (ArrayList<HomepageButton>) Childtem.get(groupPosition);
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.childrow, null);
		}

		TextView text = (TextView) convertView.findViewById(R.id.textView1);
		text.setTypeface(tf);
		HomepageButton hb = (HomepageButton)tempChild.get(childPosition);
		text.setText(hb.getText());
		View clickerView = convertView.findViewById(R.id.childLayout); //The inner LinearLayout
		clickerView.setTag(hb);
		
		clickerView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.playSoundEffect(SoundEffectConstants.CLICK);

				if (((HomepageButton)v.getTag()).getId() == Globals.HOMEPAGE_BTN_BrowseByStationTV) {
					pageflip = new PageFlipper(context, BrowseByPublishers.class, Globals.TV, null);
				} else if (((HomepageButton)v.getTag()).getId() == Globals.HOMEPAGE_BTN_RecentlyUpdatedTV) {
					pageflip = new PageFlipper(context, BrowseByShows.class, Globals.TV_BY_RECENTLY_UPDATED, null);
				} else if (((HomepageButton)v.getTag()).getId()  == Globals.HOMEPAGE_BTN_PopularMonthTV) {
					pageflip = new PageFlipper(context, BrowseByShows.class, Globals.TV_BY_POPULAR_MONTH, null);
				} else if (((HomepageButton)v.getTag()).getId()  == Globals.HOMEPAGE_BTN_PopularTodayTV) {
					pageflip = new PageFlipper(context, BrowseByShows.class, Globals.TV_BY_POPULAR_DAY, null);
				} else if (((HomepageButton)v.getTag()).getId()  == Globals.HOMEPAGE_BTN_AllFilm) {
					pageflip = new PageFlipper(context, BrowseByShows.class, Globals.FILM_BY_ALL, null);
				} else if (((HomepageButton)v.getTag()).getId()  == Globals.HOMEPAGE_BTN_PopularMonthFilm) {
					pageflip = new PageFlipper(context, BrowseByShows.class, Globals.FILM_BY_POPULAR_MONTH, null);
				} else if (((HomepageButton)v.getTag()).getId()  == Globals.HOMEPAGE_BTN_PopularTodayFilm) {
					pageflip = new PageFlipper(context, BrowseByShows.class, Globals.FILM_BY_POPULAR_DAY, null);
				} else if (((HomepageButton)v.getTag()).getId()  == Globals.HOMEPAGE_BTN_BrowseByPublisher) {
					pageflip = new PageFlipper(context, BrowseByPublishers.class, Globals.INDIE, null);
				} else if (((HomepageButton)v.getTag()).getId()  == Globals.HOMEPAGE_BTN_RecentlyUpdatedIndie) {
					pageflip = new PageFlipper(context, BrowseByShows.class, Globals.INDIE_BY_RECENTLY_UPDATED, null);
				} else if (((HomepageButton)v.getTag()).getId()  == Globals.HOMEPAGE_BTN_PopularMonthIndie) {
					pageflip = new PageFlipper(context, BrowseByShows.class, Globals.INDIE_BY_POPULAR_MONTH, null);
				} else if (((HomepageButton)v.getTag()).getId()  == Globals.HOMEPAGE_BTN_PopularTodayIndie) {
					pageflip = new PageFlipper(context, BrowseByShows.class, Globals.INDIE_BY_POPULAR_DAY, null);
				} else if (((HomepageButton)v.getTag()).getId() == Globals.HOMEPAGE_BTN_BrowseByStationTelefilm) {
					pageflip = new PageFlipper(context, BrowseByPublishers.class, Globals.TELEFILM, null);
				} else if (((HomepageButton)v.getTag()).getId() == Globals.HOMEPAGE_BTN_RecentlyUpdatedTelefilm) {
					pageflip = new PageFlipper(context, BrowseByShows.class, Globals.TELEFILM_BY_RECENTLY_UPDATED, null);
				} else if (((HomepageButton)v.getTag()).getId()  == Globals.HOMEPAGE_BTN_PopularMonthTelefilm) {
					pageflip = new PageFlipper(context, BrowseByShows.class, Globals.TELEFILM_BY_POPULAR_MONTH, null);
				} else if (((HomepageButton)v.getTag()).getId()  == Globals.HOMEPAGE_BTN_PopularTodayTelefilm) {
					pageflip = new PageFlipper(context, BrowseByShows.class, Globals.TELEFILM_BY_POPULAR_DAY, null);
				} else if (((HomepageButton)v.getTag()).getId()  == Globals.HOMEPAGE_BTN_Viral) {
					pageflip = new PageFlipper(context, BrowseByVideos.class, Globals.VIRAL, null);
				}

				pageflip.flipPage();
			}
		});
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return ((ArrayList<String>) Childtem.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return groupItem.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.grouprow, null);
		}
		((CheckedTextView) convertView).setText(groupItem.get(groupPosition));
		((CheckedTextView) convertView).setChecked(isExpanded);
		((CheckedTextView) convertView).setTypeface(tf);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}