package com.vidpk.vidpkandroidapp.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import com.vidpk.vidpkandroidapp.services.HttpRetriever;
import com.vidpk.vidpkandroidapp.util.Globals;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageCacher {

	private Context context;
	private HttpRetriever httpRetriever;

	public ImageCacher(Context c) {
		context = c;
		httpRetriever = new HttpRetriever();
	}

	public void setImage(String url, ImageView imageView) {
		try {
			FileInputStream fis = context.openFileInput(url_to_filename(url));
			Bitmap bitmap = BitmapFactory.decodeStream(fis);
			fis.close();
			imageView.setImageBitmap(bitmap);
		} catch (Exception e) {
			new BitmapDownloaderTask(imageView).execute(url);
		}
	}

	private class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

		private String url;
		private final WeakReference<ImageView> imageViewReference;

		public BitmapDownloaderTask(ImageView imageView) {
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			url = params[0];
			if (Globals.higherPriorityRequestAvailable)
				return null;
			InputStream is = httpRetriever.retrieveStream(url);
			if (is == null)
				return null;
			return BitmapFactory.decodeStream(new FlushedInputStream(is));
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()) {
				bitmap = null;
			}

			if (bitmap == null)
				return;

			FileOutputStream fos;
			try {
				fos = context.openFileOutput(url_to_filename(url),
						Context.MODE_PRIVATE);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			} catch (FileNotFoundException e) {
			}

			if (imageViewReference != null) {
				ImageView imageView = imageViewReference.get();
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}
	}

	private String url_to_filename(String s) {
		// This works for now since only channel and station thumbs are cached,
		// and
		// channel thumbs are jpg while stations are png.
		String[] parts = s.split("/");
		return parts[parts.length - 1];
	}

}
