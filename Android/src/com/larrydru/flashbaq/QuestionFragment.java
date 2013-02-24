package com.larrydru.flashbaq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuestionFragment extends Fragment implements OnClickListener {

	private Button[] answerButtons = new Button[3];
	private RelativeLayout questionRL;
	private TextView questionTV;
	private View v;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_question, container, false);
        
        answerButtons[0] = (Button) v.findViewById(R.id.answer_one);
        answerButtons[1] = (Button) v.findViewById(R.id.answer_two);
        answerButtons[2] = (Button) v.findViewById(R.id.answer_three);
        questionRL = (RelativeLayout) v.findViewById(R.id.questionRL);
        questionTV = (TextView) v.findViewById(R.id.questionTV);
        
        for (int i = 0; i < answerButtons.length; ++i)
        {
        	answerButtons[i].setOnClickListener(this);
        }
        
        
        // hack to get the start animation
        answerButtons[2].post(new Runnable() {

			@Override
			public void run() {
				slideAnswersOn();
			}
        });
        
        return v;
    }
	
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
		case R.id.answer_one:
			this.answerClicked(v);
			break;
		case R.id.answer_two:
			this.answerClicked(v);
			break;
		case R.id.answer_three:
			this.answerClicked(v);
			break;
		}
	}
	
	public void answerClicked(View v) {
		Log.d("Clicked", "Answer" + v.getId());
		slideAnswersOff();
		showAnswer();
	}
	
	private void showAnswer()
	{
		rotate180Z(questionRL);
		// switch text to the actual answer
	}
	
	private void slideAnswersOff()
	{
		for (int i = 0; i < answerButtons.length; ++i)
		{
			TranslateAnimation anim = new TranslateAnimation(0.0f, v.getWidth(), 0.0f, 0.0f);
			anim.setDuration(200);
			anim.setFillAfter(true);
			anim.setInterpolator(new AccelerateInterpolator());
			anim.setStartOffset(i * 200);
			answerButtons[i].startAnimation(anim);
		}
	}
	
	private void slideAnswersOn()
	{
		for (int i = 0; i < answerButtons.length; ++i)
		{
			TranslateAnimation anim = new TranslateAnimation(-v.getWidth(), 0.0f, 0.0f, 0.0f);
			anim.setDuration(300);
			anim.setFillBefore(true);
			anim.setInterpolator(new AccelerateInterpolator());
			anim.setStartOffset(750 + i * 200);
			answerButtons[i].startAnimation(anim);
		}
	}
	
	/* random test, dw about it */
	private void flipAway(View v)
	{
		AlphaAnimation aanim = new AlphaAnimation(0.0f, 1.0f);
		aanim.setDuration(300);
		aanim.setFillAfter(true);
		aanim.setInterpolator(new AccelerateInterpolator());
		
		ScaleAnimation anim = new ScaleAnimation(0.0f, 0.0f, 0.0f, 90.0f);
		anim.setDuration(300);
		anim.setFillAfter(true);
		anim.setInterpolator(new AccelerateInterpolator());
		
		AnimationSet aset = new AnimationSet(false);
		aset.addAnimation(aanim);
		aset.addAnimation(anim);
		
		v.startAnimation(aset);
	}
	
	private void rotate180Z(View v)
	{
		float centerX = v.getWidth() / 2.0f;
		float centerY = v.getHeight() / 2.0f;
		
		ScaleAnimation anim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, centerX, centerY);
		anim.setDuration(200);
		anim.setFillAfter(true); // maybe false with words working properly?
		anim.setInterpolator(new AccelerateInterpolator());
		anim.setStartOffset(600);
		
		ReAnimationListener ral = new ReAnimationListener();
		ral.SetView(v);
		anim.setAnimationListener(ral);
		
		v.startAnimation(anim);
	}
	
	private class ReAnimationListener implements AnimationListener {
		View v;
		
		public void SetView(View view)
		{
			v = view;
		}
		
		@Override
		public void onAnimationEnd(Animation animation) {
			float centerX = v.getWidth() / 2.0f;
			float centerY = v.getHeight() / 2.0f;
			
			ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, centerX, centerY);
			anim.setDuration(200);
			anim.setFillAfter(true); // maybe false with words working properly?
			anim.setInterpolator(new AccelerateInterpolator());
			v.startAnimation(anim);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {}

		@Override
		public void onAnimationStart(Animation animation) {}
		
	}
	
}
