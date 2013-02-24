package com.larrydru.flashbaq;

public class Card {
	private int id = -1;
	private String question = null;
	private String correct_answer = null;
	private String card_type = null;
	private int level = -1;
	private int game_id = -1;
	private int sender_id = -1;
	private int receiver_id = -1;
	private int deck_id = -1;
	private int in_play = -1;
	private boolean played = false;

	public boolean isPlayed() {
		return played;
	}
	public void setPlayed(boolean played) {
		this.played = played;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getCorrect_answer() {
		return correct_answer;
	}
	public void setCorrect_answer(String correct_answer) {
		this.correct_answer = correct_answer;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getGame_id() {
		return game_id;
	}
	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}
	public int getSender_id() {
		return sender_id;
	}
	public void setSender_id(int sender_id) {
		this.sender_id = sender_id;
	}
	public int getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(int receiver_id) {
		this.receiver_id = receiver_id;
	}
	public int getDeck_id() {
		return deck_id;
	}
	public void setDeck_id(int deck_id) {
		this.deck_id = deck_id;
	}
	public int getIn_play() {
		return in_play;
	}
	public void setIn_play(int in_play) {
		this.in_play = in_play;
	}
}