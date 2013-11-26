package com.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class VoiceTriggerActivity extends Activity{
	private TextView mUserInput;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_voice_trigger);
		
		mUserInput = (TextView)findViewById(R.id.user_input);
		Bundle bundle = getIntent().getExtras();
		String bunlde_speech_key = getResources().getString(R.string.bundle_key_speech);
		if(bundle != null && bundle.getString(bunlde_speech_key) != null){
			String spokenText = bundle.getString(bunlde_speech_key);
			if(spokenText != null){
				mUserInput.setText(spokenText);
			}
		} else {
			mUserInput.setText("No Input");
		}
	}
}
