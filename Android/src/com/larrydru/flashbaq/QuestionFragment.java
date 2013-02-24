package com.larrydru.flashbaq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.siteunseen.piecebook.restclient.RESTCallBackListener;

public class QuestionFragment extends Fragment implements OnClickListener, RESTCallBackListener {

	private Button[] answerButtons = new Button[3];
	private RelativeLayout questionRL;
	private TextView questionTV;
	private View v;
	private QuestionActivity parentQuestionActivity = null;
	private int myPlayerId = 1; // dru = 1
	private int currentGameId = 1;
	private int otherPlayersDeckId = 2;
	private int myDeckId = 1;
	
	// might wanna use a ActivityState enum to keep track of stuff
	
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
        
        // hack to get the start animation to show
        answerButtons[2].post(new Runnable() {

			@Override
			public void run() {
				slideAnswersOn();
			}
        });
        
        parentQuestionActivity = (QuestionActivity) this.getActivity();
        
        parentQuestionActivity.setRESTCallBackListener(this);
        
        parentQuestionActivity.downloadTheirDeck(currentGameId, otherPlayersDeckId);
        parentQuestionActivity.downloadMyNextCard(myPlayerId, currentGameId);
        
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
			//parentQuestionActivity.performCall(-1, 1, 1, 1);
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
		View view;
		
		public void SetView(View v)
		{
			view = v;
		}
		
		@Override
		public void onAnimationEnd(Animation animation) {
			float centerX = this.view.getWidth() / 2.0f;
			float centerY = this.view.getHeight() / 2.0f;
			
			questionTV.setText(QuestionActivity.CardToAnswer.getCorrect_answer());
			
			ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, centerX, centerY);
			anim.setDuration(200);
			anim.setFillAfter(true); // maybe false with words working properly?
			anim.setInterpolator(new AccelerateInterpolator());
			
			PostAnimationListener pal = new PostAnimationListener();
			pal.SetView(this.view);
			anim.setAnimationListener(pal);
			
			this.view.startAnimation(anim);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {}

		@Override
		public void onAnimationStart(Animation animation) {}
	}
	
	private class PostAnimationListener extends ReAnimationListener {
		
		@Override
		public void onAnimationEnd(Animation animation) {
			float halfScreen = v.getHeight() / 2.0f - this.view.getHeight() / 2.0f;
			
			TranslateAnimation anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, halfScreen);
			anim.setDuration(500);
			anim.setFillAfter(true); // maybe false with words working properly?
			
			PostPostAnimationListener ppal = new PostPostAnimationListener();
			ppal.SetView(this.view);
			anim.setAnimationListener(ppal);
			
			this.view.startAnimation(anim);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {}

		@Override
		public void onAnimationStart(Animation animation) {}	
	}
	
	private class PostPostAnimationListener extends ReAnimationListener {
		@Override
		public void onAnimationEnd(Animation animation) {
			float halfScreen = v.getHeight() / 2.0f -  this.view.getHeight() / 2.0f;
			
			TranslateAnimation anim = new TranslateAnimation(0.0f, 0.0f, halfScreen, v.getHeight());
			anim.setDuration(500);
			anim.setFillAfter(true); // maybe false with words working properly?
			anim.setStartOffset(1000);
			
			anim.setAnimationListener(new AnimationListener(){

				@Override
				public void onAnimationEnd(Animation animation) {
					parentQuestionActivity.switchToPlayCard();
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			this.view.startAnimation(anim);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {}

		@Override
		public void onAnimationStart(Animation animation) {}	
	}

	@Override
	public void onQueryComplete() {
		// do nothing if we can
	}

	@Override
	public void onQueryComplete(int id) {
		switch(id) {
		case QuestionActivity.NEXT_CARD_QUERY:
			this.questionTV.setText(QuestionActivity.CardToAnswer.getQuestion());
			UpdateScreenWithQuestionData();
			break;
		case QuestionActivity.THEIR_DECK_QUERY:
			for (int i = 0; i < answerButtons.length; i++)
			{
				int randomIndex = (int)(Math.random() * QuestionActivity.CardsInDeck.size() - 1);
				if (QuestionActivity.CardToAnswer != null && answerButtons[i].getText() == QuestionActivity.CardToAnswer.getCorrect_answer())
				{
					// do nothing
				}
				else
				{
					answerButtons[i].setText(QuestionActivity.CardsInDeck.get(randomIndex).getCorrect_answer());
				}
			}
			break;
		case QuestionActivity.GAME_OVER_QUERY:
			// ask to start game?
			break;
		}
	}

	private void UpdateScreenWithQuestionData() {
		int chooseMe = ((int)(Math.random() * 3.0f) % 3);
		answerButtons[chooseMe].setText(QuestionActivity.CardToAnswer.getCorrect_answer());
	}

	@Override
	public void onQueryFail() {
		// TODO Auto-generated method stub
		
	}
	
}
