package com.example.epochjscalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/** THIS IS NOT THE REAL CREATEENTRYSCREEN, JUST A PLACEHOLDER */
public class CreateEntryScreen extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
			//++++++++++++++++++++++++++++++++++++++++++++++
			// Get the message from the intent
			Intent intent = getIntent();
			String message = intent.getStringExtra(CalendarViewActivity.MESSAGE_DATE);
			//this.setMessageCurrent(message);
			//++++++++++++++++++++++++++++++++++++++++++++++
		
		
		TextView badView = new TextView(this);
		badView.setText("We did not implemnt the real CreateEntryScreen; this is just a placeholder! " + message);
		setContentView(badView);
	}
}
