package com.vidpk.vidpkandroidapp.services;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

import com.vidpk.vidpkandroidapp.R;
import com.vidpk.vidpkandroidapp.VidpkListActivity;
import com.vidpk.vidpkandroidapp.model.Video;
import com.vidpk.vidpkandroidapp.model.SearchKeyword;
import com.vidpk.vidpkandroidapp.model.VidpkObject;
import com.vidpk.vidpkandroidapp.util.Globals;

public class ServerRequestHandler {
	private GenericSeeker<VidpkObject> seeker;
	private ProgressDialog progressDialog;
	private Context context;
	private Activity activity;
	private PerformRequest task;

	public ServerRequestHandler(Context context, Activity activity) {
		this.context = context;
		this.activity = activity;
	}

	public void performRequest(VidpkObject parent, int subsequent,
			int requesting) {
		Globals.higherPriorityRequestAvailable = true;
		if (requesting == Globals.TV || requesting == Globals.INDIE)
			seeker = new PublisherSeeker(requesting); //requesting should be TV or INDIE
		else if (requesting == Globals.VIRAL)
			seeker = new VideoSeeker(true);
		else if (requesting == Globals.VIDEO)
			seeker = new VideoSeeker(false);
		else if (requesting == Globals.VIDEO_INFO)
			seeker = new VideoInfoSeeker(((Video) parent).vid);
		else if (requesting == Globals.SEARCH)
			seeker = new SearchSeeker(((SearchKeyword) parent).keyword);
		else
			seeker = new ShowSeeker(requesting); //requesting should be one of Globals.TV_BY_XXX etc

		if(requesting == Globals.SEARCH)
			progressDialog = ProgressDialog.show(context, context.getString(R.string.please_wait),
					context.getString(R.string.searching), true, false);			
		else if(subsequent==0)
			progressDialog = ProgressDialog.show(context, context.getString(R.string.please_wait),
					context.getString(R.string.retrieving), true, false);
		else
			progressDialog = ProgressDialog.show(context, context.getString(R.string.please_wait),
					context.getString(R.string.retrieving_next_ten), true, false);
			
		seeker.setParent(parent);
		task = new PerformRequest();
		if (subsequent != 0)
			task.setSubsequent(subsequent);
		//task.execute();
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		progressDialog
				.setOnCancelListener(new CancelTaskOnCancelListener(task));
	}

	private class PerformRequest extends
			AsyncTask<String, Void, List<VidpkObject>> {

		private int subsequent = 0;

		public void setSubsequent(int s) {
			subsequent = s;
		}

		@Override
		protected List<VidpkObject> doInBackground(String... params) {
				return seeker.find(context, subsequent);
		}

		@Override
		protected void onPostExecute(final List<VidpkObject> result) {
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (!activity.isDestroyed() && progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();
						progressDialog = null;
					}
					if (result != null && !result.isEmpty()) {
						ArrayList<VidpkObject> resultAL = (ArrayList<VidpkObject>) result;
						VidpkListActivity a = (VidpkListActivity) activity;
						a.dataChanged(resultAL);
						Globals.higherPriorityRequestAvailable = false;
					}
					else if (result.isEmpty())
					{
						activity.findViewById(R.id.no_data).setVisibility(0); //visible
						//Dont show this. Could be no results returned
						//Globals.showToast(context, context.getString(R.string.no_internet_text_short));
					}
				}
			});
		}
	}

	private class CancelTaskOnCancelListener implements OnCancelListener {
		private AsyncTask<?, ?, ?> task;

		public CancelTaskOnCancelListener(AsyncTask<?, ?, ?> task) {
			this.task = task;
		}

		@Override
		public void onCancel(DialogInterface dialog) {
			if (task != null) {
				task.cancel(true);
				task = null;
			}
		}
	}
}