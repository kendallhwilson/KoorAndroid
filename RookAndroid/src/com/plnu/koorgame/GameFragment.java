package com.plnu.koorgame;
import com.plnu.gamecode.Card;
import com.plnu.koorgame.AlertTrickWinnerDialogFragment.onTrickListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.plnu.koorgame.DiscardFragment.onDiscardListener;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * Fragment for main game play
 */
public class GameFragment extends Fragment{
	private final int TEXT_TIME = 30000;
	private final int COUNTDOWN_SECOND = 1000;
	CountDownTimer textTimer;
	CountDownTimer textTimer1;
	
	private TextView trickTaker;
	private TextView trickTakerFill;
	private TextView highBid;
	private TextView team1Score;
	private TextView team2Score;
	
	private ImageView player1Card;
	private ImageView player2Card;
	private ImageView player3Card;
	private ImageView player4Card;
	
	public ImageView handArray[] = new ImageView[10];
	private int playerHandValues[] = new int[10];
	private int whoLed = 0;
	
	public AdView adView;
	public String adId = "ca-app-pub-8436145435379887/7134674053";
	
	private onGamePlayListener gamePlayCallback;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.game_layout, container, false);
		trickTaker = (TextView) v.findViewById(R.id.trick_taker_textview);
		highBid = (TextView) v.findViewById(R.id.highbid_textview);
		team1Score = (TextView) v.findViewById(R.id.team1_textview);
		team2Score = (TextView) v.findViewById(R.id.team2_textview);
		
		player1Card = (ImageView) v.findViewById(R.id.player1_card); //Card slots didn't line up with how it is ordered in the array.
		player2Card = (ImageView) v.findViewById(R.id.player2_card);
		player3Card = (ImageView) v.findViewById(R.id.player3_card);
		player4Card = (ImageView) v.findViewById(R.id.player4_card);
		
		
		handArray[0] = (ImageView) v.findViewById(R.id.card1);
		handArray[1] = (ImageView) v.findViewById(R.id.card2);
		handArray[2] = (ImageView) v.findViewById(R.id.card3);
		handArray[3] = (ImageView) v.findViewById(R.id.card4);
		handArray[4] = (ImageView) v.findViewById(R.id.card5);
		handArray[5] = (ImageView) v.findViewById(R.id.card6);
		handArray[6] = (ImageView) v.findViewById(R.id.card7);
		handArray[7] = (ImageView) v.findViewById(R.id.card8);
		handArray[8] = (ImageView) v.findViewById(R.id.card9);
		handArray[9] = (ImageView) v.findViewById(R.id.card10);
		
		adView = new AdView(getActivity());
	    adView.setAdSize(AdSize.BANNER);
	    adView.setAdUnitId(adId);
	    LinearLayout adLineView = (LinearLayout)v.findViewById(R.id.adLine);
	    adLineView.addView(adView);
		adView = (AdView)v.findViewById(R.id.adView);
        AdRequest.Builder adReq = new AdRequest.Builder();
        adReq.addTestDevice("74685C1D33AFBBEDF9DC3EF0EF6BA54E");
        AdRequest adRequest = adReq.build();
        adView.loadAd(adRequest);
		
		textTimer = new CountDownTimer(TEXT_TIME, COUNTDOWN_SECOND) {
			public void onTick(long millisTillFinished) {
				//on tick
			}
			
			public void onFinish() {
			}
		};
		
		gamePlayCallback.playerDidntLeadInitialize();
		updateBid();
		updateScores();
		displayPlayerCards();
		return v;
	}
	
	public void cardPlayed(View v) {
		int cardLocationInHand = -1;
		
		ImageView image = (ImageView) v;
		
		for(int i=0; i < 10; i++){
			if(handArray[i].getId() == image.getId()){
				cardLocationInHand = i;
			}
		}
		
		if(cardLocationInHand != -1){
			displayPlayerCard(3, playerHandValues[cardLocationInHand]);
			gamePlayCallback.onPlayerPlayed(cardLocationInHand);
		}
	}
	
	/*
	 * Displays the trick winner for timer amount
	 * @param the player number who won
	 */
	public void trickWon(int winner) {
		trickTakerFill.setText("Player " + String.valueOf(winner));
		trickTakerFill.setVisibility(View.VISIBLE);
		trickTaker.setVisibility(View.VISIBLE);
		textTimer.start();
	}
	
	
	/*
	 * 
	 */
	public void displayPlayerCard(int player, int card) {

		switch (player) {
		case 1:
			String cardText1 = "@drawable/" + getCardText(card);
			int imageResource1 = getResources().getIdentifier(cardText1, null, "com.plnu.koorgame");
			player1Card.setImageResource(imageResource1);
			break;
		case 2:
			String cardText2 = "@drawable/" + getCardText(card);
			int imageResource2 = getResources().getIdentifier(cardText2, null, "com.plnu.koorgame");
			player2Card.setImageResource(imageResource2);			
			break;
		case 3:
			String cardText3 = "@drawable/" + getCardText(card);
			int imageResource3 = getResources().getIdentifier(cardText3, null, "com.plnu.koorgame");
			player3Card.setImageResource(imageResource3);			
			break;
		case 4:
			String cardText4 = "@drawable/" + getCardText(card);
			int imageResource4 = getResources().getIdentifier(cardText4, null, "com.plnu.koorgame");
			player4Card.setImageResource(imageResource4);
			break;
		}
	}
	public interface onGamePlayListener {
		public void onPlayerPlayed(int indexToPlay);
		public void playerDidntLeadInitialize();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        try {
            gamePlayCallback = (onGamePlayListener) activity;
            
        } 
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onGamePlayListener");
        }
	}
	
	public void displayPlayerCards(){	
		//Card color notes: value \ 11 = color.
		//Card value notes: value % 11 = number.
		playerHandValues = getArguments().getIntArray("playerHandArray");
		
		for(int i=0; i < 10; i++){
			
			if(playerHandValues[i] != -1 && playerHandValues[i] != 100){
				
			String cardText = "@drawable/" + getCardText(playerHandValues[i]);
			int imageResource = getResources().getIdentifier(cardText, null, "com.plnu.koorgame");
			handArray[i].setImageResource(imageResource);
			}
		}
	}
	
	public void displayPlayerCards(int[] playerHand){
		playerHandValues = playerHand;
		
		for(int i=0; i < 10; i++){
			
			if(playerHandValues[i] != 100 && playerHandValues[i] != -1){
			
			String cardText = "@drawable/" + getCardText(playerHandValues[i]);
			int imageResource = getResources().getIdentifier(cardText, null, "com.plnu.koorgame");
			handArray[i].setImageResource(imageResource);
			}
			else {
				handArray[i].setVisibility(View.INVISIBLE);
			}
		}
	}
	
	public String getCardText(int cardNumber)
	{
		if(cardNumber != -1){
		String returningCardText = "";
		String cardColors[] = {"red", "blue", "green", "black"};
		String cardNames[] = {"five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", 
				"thirteen", "fourteen", "one"};
		
		if(cardNumber == 44)
			return "zerorook";
		
		returningCardText = cardNames[cardNumber % 11];
		returningCardText = returningCardText + cardColors[cardNumber / 11];
		
		return returningCardText;
		}
		return "rook";
	}
	
	public void setOpponentsCards(Card[] playedCards, int trickWinnerLocation){ //Needs to determine the starting location of where play began so the cards line up with the correct slots. Fix this.
		
		int cardLocation = trickWinnerLocation;
		for(int i=0; i < 4; i++){
			if(playedCards[i].getValue() != -1){
			// Are these the cards to be displayed on timer?? If so...
			// DisplayCardUsingTimer(cardLocation+1, playedCards[i].getValue());
			// Would replace below vvv
			displayPlayerCard(cardLocation+1, playedCards[i].getValue());
			cardLocation = (cardLocation + 1 ) % 4;
			}
		}
		
	}
	
	public void setWhoLed(int leader){
		whoLed = leader;
	}
	
	public void resetAllTrickCards(){
		int imageResource = getResources().getIdentifier("@drawable/blankcard", null, "com.plnu.koorgame");

		player1Card.setImageResource(imageResource);
		player2Card.setImageResource(imageResource);
		player3Card.setImageResource(imageResource);
		player4Card.setImageResource(imageResource);


	}
	

	//Retrieve new highBid value
	public void updateBid(){
		int newBid = getArguments().getInt("highBid");
		highBid.setText(String.valueOf(newBid));
	}
	
	public void updateScores(){
		int[] scores = getArguments().getIntArray("CurrentTeamScores");
		team1Score.setText(String.valueOf(scores[0]));
		team2Score.setText(String.valueOf(scores[1]));
	}
	
    @Override
    public void onPause() {
      adView.pause();
      super.onPause();
    }

    @Override
    public void onResume() {
      super.onResume();
      adView.resume();
 
    }

    @Override
    public void onDestroy() {
      adView.destroy();
      super.onDestroy();
    }
    
    
	public void DisplayCardUsingTimer(int player, int card)
	{
		final int p =player;
		final int c = card;
		textTimer1 = new CountDownTimer(1000, COUNTDOWN_SECOND) {
			public void onTick(long millisTillFinished) {
				//on tick
				System.out.print("in the timer!");
			}		
			public void onFinish() {
				displayPlayerCard(p, c);
			}
		};

		textTimer1.start();
	}
    
}
