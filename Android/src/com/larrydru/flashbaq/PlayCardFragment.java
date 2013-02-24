package com.larrydru.flashbaq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class PlayCardFragment extends Fragment implements OnClickListener {
	View v;
	
	Button[] cardButtons = new Button[4]; 
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_play_card, container, false);
        
        cardButtons[0] = (Button) v.findViewById(R.id.card_one);
        cardButtons[1] = (Button) v.findViewById(R.id.card_two);
        cardButtons[2] = (Button) v.findViewById(R.id.card_three);
        cardButtons[3] = (Button) v.findViewById(R.id.card_four);
        
        for (int i = 0; i < cardButtons.length; i++)
        {
        	cardButtons[i].setOnClickListener(this);
        }
        return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
		case R.id.card_one:
			this.cardSelected(0);
			break;
		case R.id.card_two:
			this.cardSelected(1);
			break;
		case R.id.card_three:
			this.cardSelected(2);
			break;
		case R.id.card_four:
			this.cardSelected(3);
			break;
		}
	}
	
	private void cardSelected(int i)
	{
		Log.d("NINETIES PARTY", "tapped.");
		
		//(QuestionActivity)(this.getActivity())
	}

}
