package com.vidpk.vidpkandroidapp.ui;

import java.util.ArrayList;

import com.vidpk.vidpkandroidapp.R;
import com.vidpk.vidpkandroidapp.io.ImageCacher;
import com.vidpk.vidpkandroidapp.model.Show;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowAdapter extends ArrayAdapter<Show> {
	private final Context context;
	private final ArrayList<Show> shows;
	private ImageCacher image_cacher;

	public ShowAdapter(Context context, int textViewResourceId,
			ArrayList<Show> shows) {
		super(context, R.layout.show_row, shows);
		this.context = context;
		this.shows = shows;
		image_cacher = new ImageCacher(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.show_row, parent, false);

		Show thisShow = shows.get(position);

		Typeface tf1 = Typeface.createFromAsset(this.context.getAssets(),
	            "fonts/Raleway-SemiBold.otf");
		Typeface tf2 = Typeface.createFromAsset(this.context.getAssets(),
	            "fonts/Raleway-Regular.otf");

		TextView showName = (TextView) rowView.findViewById(R.id.show_name);
		showName.setText(thisShow.name);
		showName.setTypeface(tf1);

		TextView showLastUpdated = (TextView) rowView
				.findViewById(R.id.show_last_updated);
		showLastUpdated.setText(thisShow.last_updated);
		showLastUpdated.setTypeface(tf2);
		


		ImageView imageView = (ImageView) rowView.findViewById(R.id.show_thumb);
		String url = thisShow.retrieveThumbnail();

    	image_cacher.setImage(url, imageView);

		return rowView;
	}
}