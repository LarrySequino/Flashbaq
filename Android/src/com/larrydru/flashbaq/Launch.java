package com.larrydru.flashbaq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;

public class Launch extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		
		new CountDownTimer(300, 1) {
			
			public void onFinish() {
		        Intent intent = new Intent( getApplicationContext(), QuestionActivity.class );
		        startActivity( intent );
		        finish();
			}

			@Override
			public void onTick(long millisUntilFinished) {
				//do nothing
			}
		}.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launch, menu);
		return true;
	}

}
