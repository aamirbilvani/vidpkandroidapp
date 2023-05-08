package com.vidpk.vidpkandroidapp.util;

import com.vidpk.vidpkandroidapp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

public class Globals {
	public static final int PER_PAGE = 10;

	public static final int VIDEO = 2;
	public static final int SHOW_BY_PUBLISHER = 3;
	public static final int SHOW_BY_STATION = 4;
	public static final int VIDEO_BY_PUBLISHER = 5;
	public static final int TV_BY_RECENTLY_UPDATED = 6;
	public static final int TV_BY_POPULAR_MONTH = 7;
	public static final int TV_BY_POPULAR_DAY = 8;
	public static final int FILM_BY_ALL = 9;
	public static final int FILM_BY_POPULAR_MONTH = 10;
	public static final int FILM_BY_POPULAR_DAY = 11;
	public static final int TELEFILM_BY_RECENTLY_UPDATED = 12;
	public static final int TELEFILM_BY_POPULAR_MONTH = 13;
	public static final int TELEFILM_BY_POPULAR_DAY = 14;
	public static final int VIDEO_INFO = 15;
	public static final int PUBLISHER = 16;
	public static final int INDIE_BY_RECENTLY_UPDATED = 17;
	public static final int INDIE_BY_POPULAR_MONTH = 18;
	public static final int INDIE_BY_POPULAR_DAY = 19;
	public static final int TV = 20;
	public static final int FILM = 21;
	public static final int INDIE = 22;
	public static final int SEARCH = 23;
	public static final int TELEFILM = 24;
	public static final int VIRAL = 25;

	
	
	public static final int HOMEPAGE_BTN_BrowseByStationTV = 1;
	public static final int HOMEPAGE_BTN_RecentlyUpdatedTV = 2;
	public static final int HOMEPAGE_BTN_PopularMonthTV = 3;
	public static final int HOMEPAGE_BTN_PopularTodayTV = 4;
	public static final int HOMEPAGE_BTN_AllFilm = 5;
	public static final int HOMEPAGE_BTN_PopularMonthFilm = 6;
	public static final int HOMEPAGE_BTN_PopularTodayFilm = 7;
	public static final int HOMEPAGE_BTN_BrowseByPublisher = 8;
	public static final int HOMEPAGE_BTN_RecentlyUpdatedIndie = 9;
	public static final int HOMEPAGE_BTN_PopularMonthIndie = 10;
	public static final int HOMEPAGE_BTN_PopularTodayIndie = 11;
	public static final int HOMEPAGE_BTN_BrowseByStationTelefilm = 12;
	public static final int HOMEPAGE_BTN_RecentlyUpdatedTelefilm = 13;
	public static final int HOMEPAGE_BTN_PopularMonthTelefilm = 14;
	public static final int HOMEPAGE_BTN_PopularTodayTelefilm = 15;
	public static final int HOMEPAGE_BTN_Viral = 16;

	
	public static boolean higherPriorityRequestAvailable = false;

	public static int getTotalInAView(View l) {
		int height_in_px = l.getHeight();
		float density = l.getResources().getDisplayMetrics().density;
		float height_in_dp = height_in_px / density;
		float show_row_height = l.getResources().getDimension(
				R.dimen.show_row_height)
				/ density;
		int total_in_a_view = (int) ((height_in_dp + show_row_height) / show_row_height); // Rounding
																							// up
		return total_in_a_view;
	}

	public static void showToast(Context c, CharSequence message) {
		Toast.makeText(c, message, Toast.LENGTH_LONG).show();
	}

	public static void showExitAlertDialog(final Activity activity,
			Context context, String title, String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setIcon(R.drawable.error);

		// Setting OK Button
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				activity.finish();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	public static boolean isInternetConnectionAvailable(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}
	
	public static String cleanString(String s)
	{
		s = s.replace("&amp;", "&");
		s = s.replace("&quot;", "\"");
		s = s.replace("&#039;", "'");
		return s;
	}
}
