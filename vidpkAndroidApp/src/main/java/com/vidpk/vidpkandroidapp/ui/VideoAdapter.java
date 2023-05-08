package com.vidpk.vidpkandroidapp.ui;

import java.util.ArrayList;

import com.vidpk.vidpkandroidapp.R;
import com.vidpk.vidpkandroidapp.model.Video;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class VideoAdapter extends ArrayAdapter<Video> {
	private final Context context;
	private final ArrayList<Video> videos;

	public VideoAdapter(Context context, int textViewResourceId,
			ArrayList<Video> videos) {
		super(context, R.layout.video_row, videos);
		this.context = context;
		this.videos = videos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.video_row, parent, false);

		Video thisVideo = videos.get(position);

		Typeface tf1 = Typeface.createFromAsset(this.context.getAssets(),
	            "fonts/Raleway-SemiBold.otf");
		Typeface tf2 = Typeface.createFromAsset(this.context.getAssets(),
	            "fonts/Raleway-Regular.otf");

		TextView videoName = (TextView) rowView.findViewById(R.id.video_title);
		videoName.setText(thisVideo.title);
		videoName.setTypeface(tf1);

		TextView videoLastUpdated = (TextView) rowView
				.findViewById(R.id.video_last_updated);
		String lupt = thisVideo.last_updated;
		videoLastUpdated.setText(lupt);
		videoLastUpdated.setTypeface(tf2);
		
		return rowView;
	}
}