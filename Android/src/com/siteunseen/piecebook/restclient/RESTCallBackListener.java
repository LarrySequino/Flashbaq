package com.siteunseen.piecebook.restclient;

public interface RESTCallBackListener {

	public void onQueryComplete();
	public void onQueryComplete(int id);
	public void onQueryFail();
	
}
