package com.larrydru.flashbaq;

import java.util.ArrayList;
import java.util.Collections;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.siteunseen.piecebook.restclient.RESTCallBackListener;
import com.siteunseen.piecebook.restclient.RESTLoader;

public class QuestionActivity extends FragmentActivity implements LoaderCallbacks<RESTLoader.RESTResponse> {
	private Uri cardUri = Uri.parse("http://ineedth.at:3002/cards.json");
	public RESTCallBackListener restCallBackListener = null;
    private final String TAG = "FLASHBAQ TO THE NINETIES";
	public static Card CardToAnswer = null;
	public static ArrayList<Card> CardsInDeck = new ArrayList<Card>();
	public static boolean IsGameOver = false;
	
    public static final int NEXT_CARD_QUERY = 1;
    public static final int THEIR_DECK_QUERY = 2;
    public static final int GAME_OVER_QUERY = 3;
    public static final int SEND_CARD_PLAY = 4;
    
    public enum GameState {
    	PLAYING_CARD,
    	ANSWERING_CARD
    }
    
    public void setRESTCallBackListener(RESTCallBackListener listener){
    	restCallBackListener = listener;
    }
    
    @Override
    public Loader<RESTLoader.RESTResponse> onCreateLoader(int id, Bundle params) {
    	Log.d(TAG, "RESTLoader start!");
    	
    	Uri uri2send = null;
    	
    	if (params.getString("uri") != null)
    	{
    		uri2send = Uri.parse(params.getString("uri"));
        	if (uri2send != null)
        		params.remove("uri");
    	}
    	
    	if (id == 5)
    		return new RESTLoader(this, RESTLoader.HTTPVerb.POST, uri2send, params);
    	else if (id == SEND_CARD_PLAY)
			return new RESTLoader(this, RESTLoader.HTTPVerb.PUT, uri2send, params);
    	else
    		return new RESTLoader(this, RESTLoader.HTTPVerb.GET, cardUri, params);
    }

    @Override
    public void onLoadFinished(Loader<RESTLoader.RESTResponse> loader, RESTLoader.RESTResponse data) {
        int    code = data.getCode();
        String json = data.getData();
        Gson gson = new Gson();
        
        // Check to see if we got an HTTP 200 code and have some data.
        if (code == 200 && !json.equals("")) {
            
            //parse JSON into piece objects
        	Log.d(TAG, "got JSON from the server");
        	Card[] cards = gson.fromJson(json, Card[].class);
    		
        	
        	ArrayList<Card> cardsList = new ArrayList<Card>();
    		/*
        	for(int i=0; i<cards.length; i++){
        		if( cards[i].getTitle() == null || cards[i].getTitle().equals("") )
        			cards[i].setTitle("Untitled");
        	}
    		*/
    		Collections.addAll(cardsList, cards);
    		
    		switch(loader.getId())
    		{
	    		case NEXT_CARD_QUERY:
	    			if (cardsList.size() > 0)
	    			{
	    				QuestionActivity.CardToAnswer = cardsList.get(0);
	    			}    			
	    			break;
	    		case THEIR_DECK_QUERY:
	    			QuestionActivity.CardsInDeck = cardsList;
	    			break;
	    		case GAME_OVER_QUERY:
	    			if (cardsList.size() > 0)
	    				QuestionActivity.IsGameOver = false;
	    			else
	    				QuestionActivity.IsGameOver = true;	    				
	    			break;
    		}
    		
    		//issue the call back to the UI thread
    		restCallBackListener.onQueryComplete(loader.getId());
        }
        else {
        	restCallBackListener.onQueryFail();
        }
    }


	public void switchToPlayCard()
	{
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		PlayCardFragment fragment = new PlayCardFragment();
		fragmentTransaction.replace(R.id.fragment_holder, fragment);
		fragmentTransaction.commit();
	}
	
    
    @Override
    public void onLoaderReset(Loader<RESTLoader.RESTResponse> loader) {
    }

    /*
     * Any of the params following can be null or -1.
     */
    public void downloadMyNextCard(int receiver_id, int game_id)
    {
    	Bundle params = new Bundle();
    	if (receiver_id > -1)
    		params.putString("receiver_id", receiver_id + "");
    	if (game_id > -1)
    		params.putString("game_id", game_id + "");
    	
    	params.putString("in_play", 1 + "");

    	getSupportLoaderManager().restartLoader(NEXT_CARD_QUERY, params, this);
    }
	
    public void uploadMyChoice(Card card)
    {
    	Bundle params = new Bundle();
    	// do other things
    }
    
    /*
     * Any of the params following can be null or -1.
     */
    public void downloadTheirDeck(int game_id, int deck_id)
    {
    	Bundle params = new Bundle();
    	if (game_id > -1)
    		params.putString("game_id", game_id + "");
    	if (deck_id > -1)
    		params.putString("deck_id", deck_id + "");

    	getSupportLoaderManager().restartLoader(THEIR_DECK_QUERY, params, this);
    }
	
    public void downloadIsGameOver(int game_id)
    {
    	Bundle params = new Bundle();
    	if (game_id > -1)
    		params.putString("game_id", game_id + "");
    	
    	params.putString("in_play", 1 + "");
    	
    	getSupportLoaderManager().restartLoader(GAME_OVER_QUERY, params, this);    	
    }
    
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from( this ).inflate( R.layout.activity_question, null );
		setContentView( view );
		

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		QuestionFragment fragment = new QuestionFragment();
		fragmentTransaction.add(R.id.fragment_holder, fragment);
		fragmentTransaction.commit();
	}
}
