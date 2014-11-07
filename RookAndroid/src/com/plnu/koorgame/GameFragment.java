package com.plnu.koorgame;
import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.game_layout, container, false);
		trickTaker = (TextView) v.findViewById(R.id.trick_taker_textview);
		trickTakerFill = (TextView) v.findViewById(R.id.trick_taker_fill);
		
		player1Card = (ImageView) v.findViewById(R.id.player1_card);
		player2Card = (ImageView) v.findViewById(R.id.player2_card);
		player3Card = (ImageView) v.findViewById(R.id.player3_card);
		player4Card = (ImageView) v.findViewById(R.id.player4_card);
		
		textTimer = new CountDownTimer(TEXT_TIME, COUNTDOWN_SECOND) {
			public void onTick(long millisTillFinished) {
				//on tick
			}
			
			public void onFinish() {
				trickTakerFill.setVisibility(View.INVISIBLE);
				trickTaker.setVisibility(View.INVISIBLE);
			}
		};
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
		switch (player) {
		case 1:
			//player1Card.setImageResource(R.id.CARD)
			break;
		case 2:
			//player2Card.setImageResource(R.id.CARD)
			break;
		case 3:
			//player3Card.setImageResource(R.id.CARD)
			break;
		case 4:
			//player4Card.setImageResource(R.id.CARD)
			break;
		}
		
	}

}
