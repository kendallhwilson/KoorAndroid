package com.plnu.koorgame;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * Fragment to do all bidding
 */
public class BidFragment extends Fragment implements OnClickListener {
	
	public TextView player1Bid;
	public TextView player2Bid;
	public TextView player3Bid;
	public TextView myBid;
	public ImageView handArray[] = new ImageView[10];
	private int playerHandValues[] = new int[10];
	private int bid = 100;
	private int bid1;
	private int bid2;
	private int bid3;

	
	private onBidListener bidCallback;
	
	//Variables to go with timer
	private final int COUNTDOWN_SECOND = 1000;
	CountDownTimer textTimer1, textTimer2, textTimer3;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LayoutInflater lf = getActivity().getLayoutInflater();  
		View v = lf.inflate(R.layout.bid_layout, container, false);
		
		bid1=getArguments().getInt("PLAYER1Bid");
		bid2=getArguments().getInt("PLAYER2Bid");
		bid3=getArguments().getInt("PLAYER3Bid");
		
		
		player1Bid = (TextView) v.findViewById(R.id.player1status_num);
		player2Bid = (TextView) v.findViewById(R.id.player2status_number);
		player3Bid = (TextView) v.findViewById(R.id.player3status_num);
		myBid = (TextView) v.findViewById(R.id.my_bid_number_textview);
		
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
		
		Button increaseFiveButton = (Button) v.findViewById(R.id.bid_add_five_button);
		increaseFiveButton.setOnClickListener(this);
		Button bidChosenButton = (Button) v.findViewById(R.id.bid_go_button);
		bidChosenButton.setOnClickListener(this);

		//Timer from the GameFragment
		
		
		displayPlayerCards();
		
        return v;
	}
	
	public interface onBidListener {
		public void onBidPlayed(int bid);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        try {
            bidCallback = (onBidListener) activity;
        } 
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onBidPassedListener");
        }
    }
	
    @Override
	public void onResume() {
        super.onResume();
        textTimer1 = new CountDownTimer(1000, COUNTDOWN_SECOND) {
			public void onTick(long millisTillFinished) {
				//on tick
				System.out.print("in the timer!");
			}		
			public void onFinish() {
				displayPlayerBid(1, getArguments().getInt("PLAYER1Bid"));
			}
		};

		textTimer2 = new CountDownTimer(3000, COUNTDOWN_SECOND) {
			public void onTick(long millisTillFinished) {
				//on tick
				System.out.print("in the timer!");
			}		
			public void onFinish() {
				displayPlayerBid(2, getArguments().getInt("PLAYER2Bid"));

			}
		};
		
		textTimer3 = new CountDownTimer(5000, COUNTDOWN_SECOND) {
			public void onTick(long millisTillFinished) {
				//on tick
				System.out.print("in the timer!");
			}		
			public void onFinish() {
				displayPlayerBid(3, getArguments().getInt("PLAYER3Bid"));
			}
		};
		
		textTimer1.start();
		textTimer2.start();
		textTimer3.start();
    }
    
	@Override
	public void onPause() {
		super.onPause();
		
	}
	
	
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        case R.id.bid_add_five_button:
    		if (bid < 200) { //check bidding rules 
    			bid += 5;
    			myBid.setText(String.valueOf(bid));
    		}
            break;
        case R.id.bid_go_button:
        	bidCallback.onBidPlayed(bid);
        	break;
        }
    }
	
	/*
	 * Method called to update player bids in GUI
	 * @param the bid we are showing
	 */
	public void displayPlayerBid(int player, int newBid){
	
				if(newBid == -2){
					switch (player) {
					case 1:
						player1Bid.setText("Pass");
						break;
					case 2:
						player2Bid.setText("Pass");
						break;
					case 3:
						player3Bid.setText("Pass");
						break;
					}
				} else {
					switch (player) {
					case 1:
						player1Bid.setText(String.valueOf(newBid));
						break;
					case 2:
						player2Bid.setText(String.valueOf(newBid));
						break;
					case 3:
						player3Bid.setText(String.valueOf(newBid));
						break;
					}
				}
					if(newBid > 0){ //This will set the player's bid to a negative number if the last updated bid was negative. We just wont update the player's bid at that point.
//						bid = newBid;
//						myBid.setText(String.valueOf(newBid + 5));
						bid = newBid + 5;
						myBid.setText(String.valueOf(bid));
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
	
	public void DisplayBidsUsingTimers(int [] bids)
	{
		bid1=bids[0];
		bid2=bids[1];
		bid3=bids[2];
		textTimer1 = new CountDownTimer(1000, COUNTDOWN_SECOND) {
			public void onTick(long millisTillFinished) {
				//on tick
				System.out.print("in the timer!");
			}		
			public void onFinish() {
				displayPlayerBid(1, bid1);
			}
		};

		textTimer2 = new CountDownTimer(3000, COUNTDOWN_SECOND) {
			public void onTick(long millisTillFinished) {
				//on tick
				System.out.print("in the timer!");
			}		
			public void onFinish() {
				displayPlayerBid(2, bid2);

			}
		};
		
		textTimer3 = new CountDownTimer(5000, COUNTDOWN_SECOND) {
			public void onTick(long millisTillFinished) {
				//on tick
				System.out.print("in the timer!");
			}		
			public void onFinish() {
				displayPlayerBid(3, bid3);
			}
		};
		
		textTimer1.start();
		textTimer2.start();
		textTimer3.start();
	}
	
	 @Override
	    public void onDestroy() {
	      super.onDestroy();
	    }
	    
	 
}

