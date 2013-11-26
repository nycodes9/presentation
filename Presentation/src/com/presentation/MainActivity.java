package com.presentation;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public class MainActivity extends Activity {
	private GestureDetector mGestureDetector;
	private static final int SPEECH_REQUEST = 0;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		mGestureDetector = createGestureDetector(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection.
		switch (item.getItemId()) {
		case R.id.next:
		{
			Intent intent = new Intent(this, NextActivity.class);
			startActivity(intent);
			return true;
		}
		case R.id.voice_input:
		{
			displaySpeechRecognizer();
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void displaySpeechRecognizer() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		startActivityForResult(intent, SPEECH_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
			List<String> results = data.getStringArrayListExtra(
					RecognizerIntent.EXTRA_RESULTS);
			String spokenText = results.get(0);
			Intent intent = new Intent(this, VoiceTriggerActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString(getResources().getString(R.string.bundle_key_speech), spokenText);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private GestureDetector createGestureDetector(Context context) {
		GestureDetector gestureDetector = new GestureDetector(context);
		//Create a base listener for generic gestures
		gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
			@Override
			public boolean onGesture(Gesture gesture) {
				if (gesture == Gesture.TAP) {
					openOptionsMenu();
					return true;
				} else if (gesture == Gesture.TWO_TAP) {
					// do something on two finger tap
					return true;
				} else if (gesture == Gesture.SWIPE_RIGHT) {
					// do something on right (forward) swipe
					return true;
				} else if (gesture == Gesture.SWIPE_LEFT) {
					// do something on left (backwards) swipe
					return true;
				}
				return false;
			}
		});
		return gestureDetector;
	}

	/*
	 * Send generic motion events to the gesture detector
	 */
	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		if (mGestureDetector != null) {
			return mGestureDetector.onMotionEvent(event);
		}
		return false;
	}
}
