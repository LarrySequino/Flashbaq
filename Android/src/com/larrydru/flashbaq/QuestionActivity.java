package com.larrydru.flashbaq;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

public class QuestionActivity extends FragmentActivity {

	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from( this ).inflate( R.layout.activity_question, null );
		setContentView( view );
	}
}
