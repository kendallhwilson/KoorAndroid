package com.plnu.koorgame;
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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * Fragment for main game play
 */
public class GameFragment extends Fragment {
	private final int TEXT_TIME = 30000;
	private final int COUNTDOWN_SECOND = 1000;
	CountDownTimer textTimer;
	
	private TextView trickTaker;
	private TextView trickTakerFill;
	
	private ImageView player1Card;
	private ImageView player2Card;
	private ImageView player3Card;
	private ImageView player4Card;
	
	public ImageView handArray[] = new ImageView[10];
	private int playerHandValues[] = new int[10];
	
	public AdView adView;
	public String adId = "ca-app-pub-8436145435379887/7134674053";
	
	private onGamePlayListener gamePlayCallback;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.game_layout, container, false);
		trickTaker = (TextView) v.findViewById(R.id.trick_taker_textview);
		trickTakerFill = (TextView) v.findViewById(R.id.trick_taker_fill);
		
		player1Card = (ImageView) v.findViewById(R.id.player1_card);
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
        //adReq.addTestDevice("74685C1D33AFBBEDF9DC3EF0EF6BA54E");
        AdRequest adRequest = adReq.build();
        adView.loadAd(adRequest);
		
		textTimer = new CountDownTimer(TEXT_TIME, COUNTDOWN_SECOND) {
			public void onTick(long millisTillFinished) {
				//on tick
			}
			
			public void onFinish() {
				trickTakerFill.setVisibility(View.INVISIBLE);
				trickTaker.setVisibility(View.INVISIBLE);
			}
		};
		
		displayPlayerCards();
		return v;
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
		String cardText;
		int imageResource;
		
		switch (player) {
		case 1:
			cardText = "@drawable/" + getCardText(card);
			imageResource = getResources().getIdentifier(cardText, null, "com.plnu.koorgame");
			player1Card.setImageResource(imageResource);
			break;
		case 2:
			cardText = "@drawable/" + getCardText(card);
			imageResource = getResources().getIdentifier(cardText, null, "com.plnu.koorgame");
			player2Card.setImageResource(imageResource);			
			break;
		case 3:
			cardText = "@drawable/" + getCardText(card);
			imageResource = getResources().getIdentifier(cardText, null, "com.plnu.koorgame");
			player3Card.setImageResource(imageResource);			
			break;
		case 4:
			cardText = "@drawable/" + getCardText(card);
			imageResource = getResources().getIdentifier(cardText, null, "com.plnu.koorgame");
			player4Card.setImageResource(imageResource);			
			break;
		}
	}
	public interface onGamePlayListener {
		public void cardPlayed(int card);
		public void onPlayerPlayed(int indexToPlay);
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
			String cardText = "@drawable/" + getCardText(playerHandValues[i]);
			int imageResource = getResources().getIdentifier(cardText, null, "com.plnu.koorgame");
			handArray[i].setImageResource(imageResource);
		}
	}
	
	public String getCardText(int cardNumber)
	{
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

}
