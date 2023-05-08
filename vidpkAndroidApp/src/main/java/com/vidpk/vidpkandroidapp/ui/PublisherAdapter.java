package com.vidpk.vidpkandroidapp.ui;

import java.util.ArrayList;

import com.vidpk.vidpkandroidapp.R;
import com.vidpk.vidpkandroidapp.io.ImageCacher;
import com.vidpk.vidpkandroidapp.model.Publisher;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PublisherAdapter extends ArrayAdapter<Publisher> {
	private final Context context;
	private final ArrayList<Publisher> stations;

	private ImageCacher image_cacher;

	public PublisherAdapter(Context context, int textViewResourceId,
			ArrayList<Publisher> stations) {
		super(context, R.layout.station_row, stations);
		this.context = context;
		this.stations = stations;
		image_cacher = new ImageCacher(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.station_row, parent, false);

		Typeface tf = Typeface.createFromAsset(this.context.getAssets(),
				"fonts/Raleway-SemiBold.otf");

		TextView textView = (TextView) rowView.findViewById(R.id.station_name);
		Publisher thisStation = stations.get(position);
		textView.setText(thisStation.name);
		textView.setTypeface(tf);

		ImageView imageView = (ImageView) rowView
				.findViewById(R.id.station_thumb);
		String url = thisStation.retrieveThumbnail();

		image_cacher.setImage(url, imageView);
		return rowView;
	}

	 @Override
	    public int getCount() {
	        return stations.size();
	    }
}
