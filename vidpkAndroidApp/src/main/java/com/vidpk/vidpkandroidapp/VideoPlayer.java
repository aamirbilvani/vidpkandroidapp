package com.vidpk.vidpkandroidapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.util.Log;

import com.vidpk.vidpkandroidapp.model.Video;
import com.vidpk.vidpkandroidapp.model.VidpkObject;
import com.vidpk.vidpkandroidapp.services.ServerRequestHandler;
import com.vidpk.vidpkandroidapp.util.Globals;
import com.vidpk.vidpkandroidapp.util.SystemUiHider;

import android.content.res.Configuration;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.net.Uri;
import android.view.ViewGroup.LayoutParams;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.MediaPlayer;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class VideoPlayer extends Activity implements SurfaceHolder.Callback, IVLCVout.Callback, LibVLC.HardwareAccelerationError, VidpkListActivity {

	private Video				video;

	// Display Surface
	public final static String TAG = "Abbas";

	public final static String LOCATION = "com.compdigitec.libvlcandroidsample.VideoActivity.location";

	private String mFilePath;

	// media player
	private LibVLC libvlc;
	private MediaPlayer mMediaPlayer = null;
	private int mVideoWidth;
	private int mVideoHeight;
	private final static int VideoSizeChanged = -1;

	// Display Surface
	private LinearLayout vlcContainer;
	private SurfaceView         mSurface;
	private SurfaceHolder       holder;

	// Overlay / Controls

	private FrameLayout vlcOverlay;
	private ImageView vlcButtonPlayPause;
	private Handler             handlerOverlay;
	private Runnable            runnableOverlay;
	private Handler handlerSeekbar;
	private Runnable            runnableSeekbar;
	private SeekBar vlcSeekbar;
	private TextView            vlcDuration;
	private TextView overlayTitle;

	private static ProgressDialog progressDialog;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mSurface = (SurfaceView) findViewById(R.id.vlc_surface);
		holder = mSurface.getHolder();
		holder.addCallback(this);


		// SETUP THE UI
		setContentView(R.layout.activity_video_player);

		// VLC
		vlcContainer = (LinearLayout) findViewById(R.id.vlc_container);
		mSurface = (SurfaceView) findViewById(R.id.vlc_surface);


		// OVERLAY / CONTROLS
		vlcOverlay = (FrameLayout) findViewById(R.id.vlc_overlay);
		vlcButtonPlayPause = (ImageView) findViewById(R.id.vlc_button_play_pause);
		vlcSeekbar = (SeekBar) findViewById(R.id.vlc_seekbar);
		vlcDuration = (TextView) findViewById(R.id.vlc_duration);

		overlayTitle = (TextView) findViewById(R.id.vlc_overlay_title);

		video = (Video) getIntent().getSerializableExtra("parent");
		ServerRequestHandler srh = new ServerRequestHandler(VideoPlayer.this, this);
		srh.performRequest(video, 0, Globals.VIDEO_INFO);
	}


	private void showOverlay() {
		vlcOverlay.setVisibility(View.VISIBLE);
	}

	private void hideOverlay() {
		vlcOverlay.setVisibility(View.GONE);
	}

	private void setupControls() {
		getActionBar().hide();
		// PLAY PAUSE
		vlcButtonPlayPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mMediaPlayer.isPlaying()) {
					mMediaPlayer.pause();
					vlcButtonPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_play_over_video));
				} else {
					mMediaPlayer.play();
					vlcButtonPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_pause_over_video));
				}
			}
		});

		// SEEKBAR
		handlerSeekbar = new Handler();
		runnableSeekbar = new Runnable() {
			@Override
			public void run() {
				if (libvlc != null) {
					long curTime = mMediaPlayer.getTime();
					long totalTime = (long) (curTime / mMediaPlayer.getPosition());
					int minutes = (int) (curTime / (60 * 1000));
					int seconds = (int) ((curTime / 1000) % 60);
					int endMinutes = (int) (totalTime / (60 * 1000));
					int endSeconds = (int) ((totalTime / 1000) % 60);
					String duration = String.format("%02d:%02d / %02d:%02d", minutes, seconds, endMinutes, endSeconds);
					vlcSeekbar.setProgress((int) (mMediaPlayer.getPosition() * 100));
					vlcDuration.setText(duration);
				}
				handlerSeekbar.postDelayed(runnableSeekbar, 1000);
			}
		};

		runnableSeekbar.run();
		vlcSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				Log.v("NEW POS", "pos is : " + i);
				//if (i != 0)
				//    libvlc.setPosition(((float) i / 100.0f));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		// OVERLAY
		handlerOverlay = new Handler();
		runnableOverlay = new Runnable() {
			@Override
			public void run() {
				vlcOverlay.setVisibility(View.GONE);
				//toggleFullscreen(true);
			}
		};
		final long timeToDisappear = 3000;
		handlerOverlay.postDelayed(runnableOverlay, timeToDisappear);
		vlcContainer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				vlcOverlay.setVisibility(View.VISIBLE);

				handlerOverlay.removeCallbacks(runnableOverlay);
				handlerOverlay.postDelayed(runnableOverlay, timeToDisappear);
			}
		});
	}

	public void surfaceCreated(SurfaceHolder holder) {
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int format,
							   int width, int height) {
		//if (libvlc != null)
		//	surfaceholder.attachSurface(surfaceholder.getSurface(), this);
	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setSize(mVideoWidth, mVideoHeight);
	}

	@Override
	protected void onResume() {
		super.onResume();
		createPlayer();
	}

	@Override
	protected void onPause() {
		super.onPause();
		releasePlayer();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		releasePlayer();
	}

	/*************
	 * Surface
	 *************/
	private void setSize(int width, int height) {
		mVideoWidth = width;
		mVideoHeight = height;
		if (mVideoWidth * mVideoHeight <= 1)
			return;

		if(holder == null || mSurface == null)
			return;

		// get screen size
		int w = getWindow().getDecorView().getWidth();
		int h = getWindow().getDecorView().getHeight();

		// getWindow().getDecorView() doesn't always take orientation into
		// account, we have to correct the values
		boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
		if (w > h && isPortrait || w < h && !isPortrait) {
			int i = w;
			w = h;
			h = i;
		}

		float videoAR = (float) mVideoWidth / (float) mVideoHeight;
		float screenAR = (float) w / (float) h;

		if (screenAR < videoAR)
			h = (int) (w / videoAR);
		else
			w = (int) (h * videoAR);

		// force surface buffer size
		holder.setFixedSize(mVideoWidth, mVideoHeight);

		// set display size
		LayoutParams lp = mSurface.getLayoutParams();
		lp.width = w;
		lp.height = h;
		mSurface.setLayoutParams(lp);
		mSurface.invalidate();
	}

	/*************
	 * Player
	 *************/

	private void createPlayer() {
		releasePlayer();
		//try {
			// Create LibVLC
			// TODO: make this more robust, and sync with audio demo
			ArrayList<String> options = new ArrayList<String>();
			//options.add("--subsdec-encoding <encoding>");
			options.add("--aout=opensles");
			options.add("--audio-time-stretch"); // time stretching
			options.add("-vvv"); // verbosity
			libvlc = new LibVLC(options);
			libvlc.setOnHardwareAccelerationError(this);
			//holder.setKeepScreenOn(true);

			// Create media player
			mMediaPlayer = new MediaPlayer(libvlc);
			mMediaPlayer.setEventListener(mPlayerListener);

			// Set up video output
			final IVLCVout vout = mMediaPlayer.getVLCVout();
			vout.setVideoView(mSurface);
			//vout.setSubtitlesView(mSurfaceSubtitles);
			vout.addCallback(this);
			vout.attachViews();

			overlayTitle.setText(video.title);
			Uri mediaURI = Uri.parse(video.getURI());
			Media m = new Media(libvlc, mediaURI);
			mMediaPlayer.setMedia(m);
			mMediaPlayer.play();
		//} catch (Exception e) {
		//	Toast.makeText(this, "Error creating player!", Toast.LENGTH_LONG).show();
		//}
	}

	// TODO: handle this cleaner
	private void releasePlayer() {
		if (libvlc == null)
			return;
		mMediaPlayer.stop();
		final IVLCVout vout = mMediaPlayer.getVLCVout();
		vout.removeCallback(this);
		vout.detachViews();
		holder = null;
		libvlc.release();
		libvlc = null;

		mVideoWidth = 0;
		mVideoHeight = 0;
	}

	/*************
	 * Events
	 *************/

	private MediaPlayer.EventListener mPlayerListener = new MyPlayerListener(this);

	@Override
	public void onNewLayout(IVLCVout vout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {
		if (width * height == 0)
			return;

		// store video size
		mVideoWidth = width;
		mVideoHeight = height;
		setSize(mVideoWidth, mVideoHeight);
	}

	@Override
	public void onSurfacesCreated(IVLCVout vout) {

	}

	@Override
	public void onSurfacesDestroyed(IVLCVout vout) {

	}

	private static class MyPlayerListener implements MediaPlayer.EventListener {
		private WeakReference<VideoPlayer> mOwner;

		public MyPlayerListener(VideoPlayer owner) {
			mOwner = new WeakReference<VideoPlayer>(owner);
		}

		@Override
		public void onEvent(MediaPlayer.Event event) {
			VideoPlayer player = mOwner.get();

			switch(event.type) {
				case MediaPlayer.Event.EndReached:
					Log.d(TAG, "MediaPlayerEndReached");
					player.releasePlayer();
					break;
				case MediaPlayer.Event.Playing:
				case MediaPlayer.Event.Paused:
				case MediaPlayer.Event.Stopped:
				default:
					break;
			}
		}
	}

	@Override
	public void eventHardwareAccelerationError() {
		// Handle errors with hardware acceleration
		Log.e(TAG, "Error with hardware acceleration");
		this.releasePlayer();
		Toast.makeText(this, "Error with hardware acceleration", Toast.LENGTH_LONG).show();
	}

	@Override
	public void dataChanged(ArrayList<VidpkObject> newResults) {

		video = (Video) newResults.get(0);

		progressDialog = ProgressDialog.show(this, "",
				getString(R.string.buffering), true);
		progressDialog.setCancelable(true);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		createPlayer();

		setTitle(video.title);
	}
}