package com.plnu.koorgame;

import com.plnu.koorgame.R.drawable;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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
	public ImageView card1;
	public ImageView card2;
	public ImageView card3;
	public ImageView card4;
	public ImageView card5;
	public ImageView card6;
	public ImageView card7;
	public ImageView card8;
	public ImageView card9;
	public ImageView card10;
	private int bid = 100;
	
	private onBidListener bidCallback;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LayoutInflater lf = getActivity().getLayoutInflater();  
		View v = lf.inflate(R.layout.bid_layout, container, false);
		
		player1Bid = (TextView) v.findViewById(R.id.player1status_num);
		player2Bid = (TextView) v.findViewById(R.id.player2status_number);
		player3Bid = (TextView) v.findViewById(R.id.player3status_num);
		myBid = (TextView) v.findViewById(R.id.my_bid_number_textview);
		card1 = (ImageView) v.findViewById(R.id.card1);
		card2 = (ImageView) v.findViewById(R.id.card2);
		card3 = (ImageView) v.findViewById(R.id.card3);
		card4 = (ImageView) v.findViewById(R.id.card4);
		card5 = (ImageView) v.findViewById(R.id.card5);
		card6 = (ImageView) v.findViewById(R.id.card6);
		card7 = (ImageView) v.findViewById(R.id.card7);
		card8 = (ImageView) v.findViewById(R.id.card8);
		card9 = (ImageView) v.findViewById(R.id.card9);
		card10 = (ImageView) v.findViewById(R.id.card10);
		
		Button increaseFiveButton = (Button) v.findViewById(R.id.bid_add_five_button);
		increaseFiveButton.setOnClickListener(this);
		Button bidChosenButton = (Button) v.findViewById(R.id.bid_go_button);
		bidChosenButton.setOnClickListener(this);

		displayPlayerBid(1, getArguments().getInt("PLAYER1Bid"));
		displayPlayerBid(2, getArguments().getInt("PLAYER2Bid"));
		displayPlayerBid(3, getArguments().getInt("PLAYER3Bid"));
		
		displayPlayerCards(1,getArguments().getInt("card1"));
		displayPlayerCards(2,getArguments().getInt("card2"));
		displayPlayerCards(3,getArguments().getInt("card3"));
		displayPlayerCards(4,getArguments().getInt("card4"));
		displayPlayerCards(5,getArguments().getInt("card5"));
		displayPlayerCards(6,getArguments().getInt("card6"));
		displayPlayerCards(7,getArguments().getInt("card7"));
		displayPlayerCards(8,getArguments().getInt("card8"));
		displayPlayerCards(9,getArguments().getInt("card9"));
		displayPlayerCards(10,getArguments().getInt("card10"));
		
		
		
        return v;
	}
	
	public interface onBidListener {
		public void onBidPass();
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
        case R.id.pass_button:
        	bidCallback.onBidPass();
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
				bid = newBid;
				myBid.setText(String.valueOf(newBid + 5));
			}
	}
	
	public void displayPlayerCards(int cardIndex, int cardValue){
		
		switch(cardIndex){
		case 1:
			switch(cardValue){
				case 0:
					card1.setImageResource(drawable.fivered);
					break;
				case 1:
					card1.setImageResource(drawable.sixred);
					break;
				case 2:
					card1.setImageResource(drawable.sevenred);
					break;
				case 3: 
					card1.setImageResource(drawable.eightred);
					break;
				case 4:
					card1.setImageResource(drawable.ninered);
					break;
				case 5:
					card1.setImageResource(drawable.tenred);
					break;
				case 6:
					card1.setImageResource(drawable.elevenred);
					break;
				case 7:
					card1.setImageResource(drawable.twelvered);
					break;
				case 8:
					card1.setImageResource(drawable.thirteenred);
					break;
				case 9: 
					card1.setImageResource(drawable.fourteenred);
					break;
				case 10: 
					card1.setImageResource(drawable.onered);
					break;
				case 11:
					card1.setImageResource(drawable.fiveblue);
					break;
				case 12:
					card1.setImageResource(drawable.sixblue);
					break;
				case 13:
					card1.setImageResource(drawable.sevenblue);
					break;
				case 14: 
					card1.setImageResource(drawable.eightblue);
					break;
				case 15:
					card1.setImageResource(drawable.nineblue);
					break;
				case 16:
					card1.setImageResource(drawable.tenblue);
					break;
				case 17:
					card1.setImageResource(drawable.elevenblue);
					break;
				case 18:
					card1.setImageResource(drawable.twelveblue);
					break;
				case 19:
					card1.setImageResource(drawable.thirteenblue);
					break;
				case 20: 
					card1.setImageResource(drawable.fourteenblue);
					break;
				case 21: 
					card1.setImageResource(drawable.oneblue);
					break;
				case 22:
					card1.setImageResource(drawable.fivegreen);
					break;
				case 23:
					card1.setImageResource(drawable.sixgreen);
					break;
				case 24:
					card1.setImageResource(drawable.sevengreen);
					break;
				case 25: 
					card1.setImageResource(drawable.eightgreen);
					break;
				case 26:
					card1.setImageResource(drawable.ninegreen);
					break;
				case 27:
					card1.setImageResource(drawable.tengreen);
					break;
				case 28:
					card1.setImageResource(drawable.elevengreen);
					break;
				case 29:
					card1.setImageResource(drawable.twelvegreen);
					break;
				case 30:
					card1.setImageResource(drawable.thirteengreen);
					break;
				case 31: 
					card1.setImageResource(drawable.fourteengreen);
					break;
				case 32: 
					card1.setImageResource(drawable.onegreen);
					break;
				case 33:
					card1.setImageResource(drawable.fiveblack);
					break;
				case 34:
					card1.setImageResource(drawable.sixblack);
					break;
				case 35:
					card1.setImageResource(drawable.sevenblack);
					break;
				case 36: 
					card1.setImageResource(drawable.eightblack);
					break;
				case 37:
					card1.setImageResource(drawable.nineblack);
					break;
				case 38:
					card1.setImageResource(drawable.tenblack);
					break;
				case 39:
					card1.setImageResource(drawable.elevenblack);
					break;
				case 40:
					card1.setImageResource(drawable.twelveblack);
					break;
				case 41:
					card1.setImageResource(drawable.thirteenblack);
					break;
				case 42: 
					card1.setImageResource(drawable.fourteenblack);
					break;
				case 43: 
					card1.setImageResource(drawable.oneblack);
					break;
				case 44: 
					card1.setImageResource(drawable.rook);
					break;

				
			}
		break;
		case 2:
			switch(cardValue){
			case 0:
				card2.setImageResource(drawable.fivered);
				break;
			case 1:
				card2.setImageResource(drawable.sixred);
				break;
			case 2:
				card2.setImageResource(drawable.sevenred);
				break;
			case 3: 
				card2.setImageResource(drawable.eightred);
				break;
			case 4:
				card2.setImageResource(drawable.ninered);
				break;
			case 5:
				card2.setImageResource(drawable.tenred);
				break;
			case 6:
				card2.setImageResource(drawable.elevenred);
				break;
			case 7:
				card2.setImageResource(drawable.twelvered);
				break;
			case 8:
				card2.setImageResource(drawable.thirteenred);
				break;
			case 9: 
				card2.setImageResource(drawable.fourteenred);
				break;
			case 10: 
				card2.setImageResource(drawable.onered);
				break;
			case 11:
				card2.setImageResource(drawable.fiveblue);
				break;
			case 12:
				card2.setImageResource(drawable.sixblue);
				break;
			case 13:
				card2.setImageResource(drawable.sevenblue);
				break;
			case 14: 
				card2.setImageResource(drawable.eightblue);
				break;
			case 15:
				card2.setImageResource(drawable.nineblue);
				break;
			case 16:
				card2.setImageResource(drawable.tenblue);
				break;
			case 17:
				card2.setImageResource(drawable.elevenblue);
				break;
			case 18:
				card2.setImageResource(drawable.twelveblue);
				break;
			case 19:
				card2.setImageResource(drawable.thirteenblue);
				break;
			case 20: 
				card2.setImageResource(drawable.fourteenblue);
				break;
			case 21: 
				card2.setImageResource(drawable.oneblue);
				break;
			case 22:
				card2.setImageResource(drawable.fivegreen);
				break;
			case 23:
				card2.setImageResource(drawable.sixgreen);
				break;
			case 24:
				card2.setImageResource(drawable.sevengreen);
				break;
			case 25: 
				card2.setImageResource(drawable.eightgreen);
				break;
			case 26:
				card2.setImageResource(drawable.ninegreen);
				break;
			case 27:
				card2.setImageResource(drawable.tengreen);
				break;
			case 28:
				card2.setImageResource(drawable.elevengreen);
				break;
			case 29:
				card2.setImageResource(drawable.twelvegreen);
				break;
			case 30:
				card2.setImageResource(drawable.thirteengreen);
				break;
			case 31: 
				card2.setImageResource(drawable.fourteengreen);
				break;
			case 32: 
				card2.setImageResource(drawable.onegreen);
				break;
			case 33:
				card2.setImageResource(drawable.fiveblack);
				break;
			case 34:
				card2.setImageResource(drawable.sixblack);
				break;
			case 35:
				card2.setImageResource(drawable.sevenblack);
				break;
			case 36: 
				card2.setImageResource(drawable.eightblack);
				break;
			case 37:
				card2.setImageResource(drawable.nineblack);
				break;
			case 38:
				card2.setImageResource(drawable.tenblack);
				break;
			case 39:
				card2.setImageResource(drawable.elevenblack);
				break;
			case 40:
				card2.setImageResource(drawable.twelveblack);
				break;
			case 41:
				card2.setImageResource(drawable.thirteenblack);
				break;
			case 42: 
				card2.setImageResource(drawable.fourteenblack);
				break;
			case 43: 
				card2.setImageResource(drawable.oneblack);
				break;
			case 44: 
				card2.setImageResource(drawable.rook);
				break;

			
		}
		break;
		case 3:
			switch(cardValue){
			case 0:
				card3.setImageResource(drawable.fivered);
				break;
			case 1:
				card3.setImageResource(drawable.sixred);
				break;
			case 2:
				card3.setImageResource(drawable.sevenred);
				break;
			case 3: 
				card3.setImageResource(drawable.eightred);
				break;
			case 4:
				card3.setImageResource(drawable.ninered);
				break;
			case 5:
				card3.setImageResource(drawable.tenred);
				break;
			case 6:
				card3.setImageResource(drawable.elevenred);
				break;
			case 7:
				card3.setImageResource(drawable.twelvered);
				break;
			case 8:
				card3.setImageResource(drawable.thirteenred);
				break;
			case 9: 
				card3.setImageResource(drawable.fourteenred);
				break;
			case 10: 
				card3.setImageResource(drawable.onered);
				break;
			case 11:
				card3.setImageResource(drawable.fiveblue);
				break;
			case 12:
				card3.setImageResource(drawable.sixblue);
				break;
			case 13:
				card3.setImageResource(drawable.sevenblue);
				break;
			case 14: 
				card3.setImageResource(drawable.eightblue);
				break;
			case 15:
				card3.setImageResource(drawable.nineblue);
				break;
			case 16:
				card3.setImageResource(drawable.tenblue);
				break;
			case 17:
				card3.setImageResource(drawable.elevenblue);
				break;
			case 18:
				card3.setImageResource(drawable.twelveblue);
				break;
			case 19:
				card3.setImageResource(drawable.thirteenblue);
				break;
			case 20: 
				card3.setImageResource(drawable.fourteenblue);
				break;
			case 21: 
				card3.setImageResource(drawable.oneblue);
				break;
			case 22:
				card3.setImageResource(drawable.fivegreen);
				break;
			case 23:
				card3.setImageResource(drawable.sixgreen);
				break;
			case 24:
				card3.setImageResource(drawable.sevengreen);
				break;
			case 25: 
				card3.setImageResource(drawable.eightgreen);
				break;
			case 26:
				card3.setImageResource(drawable.ninegreen);
				break;
			case 27:
				card3.setImageResource(drawable.tengreen);
				break;
			case 28:
				card3.setImageResource(drawable.elevengreen);
				break;
			case 29:
				card3.setImageResource(drawable.twelvegreen);
				break;
			case 30:
				card3.setImageResource(drawable.thirteengreen);
				break;
			case 31: 
				card3.setImageResource(drawable.fourteengreen);
				break;
			case 32: 
				card3.setImageResource(drawable.onegreen);
				break;
			case 33:
				card3.setImageResource(drawable.fiveblack);
				break;
			case 34:
				card3.setImageResource(drawable.sixblack);
				break;
			case 35:
				card3.setImageResource(drawable.sevenblack);
				break;
			case 36: 
				card3.setImageResource(drawable.eightblack);
				break;
			case 37:
				card3.setImageResource(drawable.nineblack);
				break;
			case 38:
				card3.setImageResource(drawable.tenblack);
				break;
			case 39:
				card3.setImageResource(drawable.elevenblack);
				break;
			case 40:
				card3.setImageResource(drawable.twelveblack);
				break;
			case 41:
				card3.setImageResource(drawable.thirteenblack);
				break;
			case 42: 
				card3.setImageResource(drawable.fourteenblack);
				break;
			case 43: 
				card3.setImageResource(drawable.oneblack);
				break;
			case 44: 
				card3.setImageResource(drawable.rook);
				break;

			
		}
		break;
		case 4:
			switch(cardValue){
			case 0:
				card4.setImageResource(drawable.fivered);
				break;
			case 1:
				card4.setImageResource(drawable.sixred);
				break;
			case 2:
				card4.setImageResource(drawable.sevenred);
				break;
			case 3: 
				card4.setImageResource(drawable.eightred);
				break;
			case 4:
				card4.setImageResource(drawable.ninered);
				break;
			case 5:
				card4.setImageResource(drawable.tenred);
				break;
			case 6:
				card4.setImageResource(drawable.elevenred);
				break;
			case 7:
				card4.setImageResource(drawable.twelvered);
				break;
			case 8:
				card4.setImageResource(drawable.thirteenred);
				break;
			case 9: 
				card4.setImageResource(drawable.fourteenred);
				break;
			case 10: 
				card4.setImageResource(drawable.onered);
				break;
			case 11:
				card4.setImageResource(drawable.fiveblue);
				break;
			case 12:
				card4.setImageResource(drawable.sixblue);
				break;
			case 13:
				card4.setImageResource(drawable.sevenblue);
				break;
			case 14: 
				card4.setImageResource(drawable.eightblue);
				break;
			case 15:
				card4.setImageResource(drawable.nineblue);
				break;
			case 16:
				card4.setImageResource(drawable.tenblue);
				break;
			case 17:
				card4.setImageResource(drawable.elevenblue);
				break;
			case 18:
				card4.setImageResource(drawable.twelveblue);
				break;
			case 19:
				card4.setImageResource(drawable.thirteenblue);
				break;
			case 20: 
				card4.setImageResource(drawable.fourteenblue);
				break;
			case 21: 
				card4.setImageResource(drawable.oneblue);
				break;
			case 22:
				card4.setImageResource(drawable.fivegreen);
				break;
			case 23:
				card4.setImageResource(drawable.sixgreen);
				break;
			case 24:
				card4.setImageResource(drawable.sevengreen);
				break;
			case 25: 
				card4.setImageResource(drawable.eightgreen);
				break;
			case 26:
				card4.setImageResource(drawable.ninegreen);
				break;
			case 27:
				card4.setImageResource(drawable.tengreen);
				break;
			case 28:
				card4.setImageResource(drawable.elevengreen);
				break;
			case 29:
				card4.setImageResource(drawable.twelvegreen);
				break;
			case 30:
				card4.setImageResource(drawable.thirteengreen);
				break;
			case 31: 
				card4.setImageResource(drawable.fourteengreen);
				break;
			case 32: 
				card4.setImageResource(drawable.onegreen);
				break;
			case 33:
				card4.setImageResource(drawable.fiveblack);
				break;
			case 34:
				card4.setImageResource(drawable.sixblack);
				break;
			case 35:
				card4.setImageResource(drawable.sevenblack);
				break;
			case 36: 
				card4.setImageResource(drawable.eightblack);
				break;
			case 37:
				card4.setImageResource(drawable.nineblack);
				break;
			case 38:
				card4.setImageResource(drawable.tenblack);
				break;
			case 39:
				card4.setImageResource(drawable.elevenblack);
				break;
			case 40:
				card4.setImageResource(drawable.twelveblack);
				break;
			case 41:
				card4.setImageResource(drawable.thirteenblack);
				break;
			case 42: 
				card4.setImageResource(drawable.fourteenblack);
				break;
			case 43: 
				card4.setImageResource(drawable.oneblack);
				break;
			case 44: 
				card4.setImageResource(drawable.rook);
				break;

			
		}
		break;
		case 5:
			switch(cardValue){
			case 0:
				card5.setImageResource(drawable.fivered);
				break;
			case 1:
				card5.setImageResource(drawable.sixred);
				break;
			case 2:
				card5.setImageResource(drawable.sevenred);
				break;
			case 3: 
				card5.setImageResource(drawable.eightred);
				break;
			case 4:
				card5.setImageResource(drawable.ninered);
				break;
			case 5:
				card5.setImageResource(drawable.tenred);
				break;
			case 6:
				card5.setImageResource(drawable.elevenred);
				break;
			case 7:
				card5.setImageResource(drawable.twelvered);
				break;
			case 8:
				card5.setImageResource(drawable.thirteenred);
				break;
			case 9: 
				card5.setImageResource(drawable.fourteenred);
				break;
			case 10: 
				card5.setImageResource(drawable.onered);
				break;
			case 11:
				card5.setImageResource(drawable.fiveblue);
				break;
			case 12:
				card5.setImageResource(drawable.sixblue);
				break;
			case 13:
				card5.setImageResource(drawable.sevenblue);
				break;
			case 14: 
				card5.setImageResource(drawable.eightblue);
				break;
			case 15:
				card5.setImageResource(drawable.nineblue);
				break;
			case 16:
				card5.setImageResource(drawable.tenblue);
				break;
			case 17:
				card5.setImageResource(drawable.elevenblue);
				break;
			case 18:
				card5.setImageResource(drawable.twelveblue);
				break;
			case 19:
				card5.setImageResource(drawable.thirteenblue);
				break;
			case 20: 
				card5.setImageResource(drawable.fourteenblue);
				break;
			case 21: 
				card5.setImageResource(drawable.oneblue);
				break;
			case 22:
				card5.setImageResource(drawable.fivegreen);
				break;
			case 23:
				card5.setImageResource(drawable.sixgreen);
				break;
			case 24:
				card5.setImageResource(drawable.sevengreen);
				break;
			case 25: 
				card5.setImageResource(drawable.eightgreen);
				break;
			case 26:
				card5.setImageResource(drawable.ninegreen);
				break;
			case 27:
				card5.setImageResource(drawable.tengreen);
				break;
			case 28:
				card5.setImageResource(drawable.elevengreen);
				break;
			case 29:
				card5.setImageResource(drawable.twelvegreen);
				break;
			case 30:
				card5.setImageResource(drawable.thirteengreen);
				break;
			case 31: 
				card5.setImageResource(drawable.fourteengreen);
				break;
			case 32: 
				card5.setImageResource(drawable.onegreen);
				break;
			case 33:
				card5.setImageResource(drawable.fiveblack);
				break;
			case 34:
				card5.setImageResource(drawable.sixblack);
				break;
			case 35:
				card5.setImageResource(drawable.sevenblack);
				break;
			case 36: 
				card5.setImageResource(drawable.eightblack);
				break;
			case 37:
				card5.setImageResource(drawable.nineblack);
				break;
			case 38:
				card5.setImageResource(drawable.tenblack);
				break;
			case 39:
				card5.setImageResource(drawable.elevenblack);
				break;
			case 40:
				card5.setImageResource(drawable.twelveblack);
				break;
			case 41:
				card5.setImageResource(drawable.thirteenblack);
				break;
			case 42: 
				card5.setImageResource(drawable.fourteenblack);
				break;
			case 43: 
				card5.setImageResource(drawable.oneblack);
				break;
			case 44: 
				card5.setImageResource(drawable.rook);
				break;

			
		}
		break;
		case 6:
			switch(cardValue){
			case 0:
				card6.setImageResource(drawable.fivered);
				break;
			case 1:
				card6.setImageResource(drawable.sixred);
				break;
			case 2:
				card6.setImageResource(drawable.sevenred);
				break;
			case 3: 
				card6.setImageResource(drawable.eightred);
				break;
			case 4:
				card6.setImageResource(drawable.ninered);
				break;
			case 5:
				card6.setImageResource(drawable.tenred);
				break;
			case 6:
				card6.setImageResource(drawable.elevenred);
				break;
			case 7:
				card6.setImageResource(drawable.twelvered);
				break;
			case 8:
				card6.setImageResource(drawable.thirteenred);
				break;
			case 9: 
				card6.setImageResource(drawable.fourteenred);
				break;
			case 10: 
				card6.setImageResource(drawable.onered);
				break;
			case 11:
				card6.setImageResource(drawable.fiveblue);
				break;
			case 12:
				card6.setImageResource(drawable.sixblue);
				break;
			case 13:
				card6.setImageResource(drawable.sevenblue);
				break;
			case 14: 
				card6.setImageResource(drawable.eightblue);
				break;
			case 15:
				card6.setImageResource(drawable.nineblue);
				break;
			case 16:
				card6.setImageResource(drawable.tenblue);
				break;
			case 17:
				card6.setImageResource(drawable.elevenblue);
				break;
			case 18:
				card6.setImageResource(drawable.twelveblue);
				break;
			case 19:
				card6.setImageResource(drawable.thirteenblue);
				break;
			case 20: 
				card6.setImageResource(drawable.fourteenblue);
				break;
			case 21: 
				card6.setImageResource(drawable.oneblue);
				break;
			case 22:
				card6.setImageResource(drawable.fivegreen);
				break;
			case 23:
				card6.setImageResource(drawable.sixgreen);
				break;
			case 24:
				card6.setImageResource(drawable.sevengreen);
				break;
			case 25: 
				card6.setImageResource(drawable.eightgreen);
				break;
			case 26:
				card6.setImageResource(drawable.ninegreen);
				break;
			case 27:
				card6.setImageResource(drawable.tengreen);
				break;
			case 28:
				card6.setImageResource(drawable.elevengreen);
				break;
			case 29:
				card6.setImageResource(drawable.twelvegreen);
				break;
			case 30:
				card6.setImageResource(drawable.thirteengreen);
				break;
			case 31: 
				card6.setImageResource(drawable.fourteengreen);
				break;
			case 32: 
				card6.setImageResource(drawable.onegreen);
				break;
			case 33:
				card6.setImageResource(drawable.fiveblack);
				break;
			case 34:
				card6.setImageResource(drawable.sixblack);
				break;
			case 35:
				card6.setImageResource(drawable.sevenblack);
				break;
			case 36: 
				card6.setImageResource(drawable.eightblack);
				break;
			case 37:
				card6.setImageResource(drawable.nineblack);
				break;
			case 38:
				card6.setImageResource(drawable.tenblack);
				break;
			case 39:
				card6.setImageResource(drawable.elevenblack);
				break;
			case 40:
				card6.setImageResource(drawable.twelveblack);
				break;
			case 41:
				card6.setImageResource(drawable.thirteenblack);
				break;
			case 42: 
				card6.setImageResource(drawable.fourteenblack);
				break;
			case 43: 
				card6.setImageResource(drawable.oneblack);
				break;
			case 44: 
				card6.setImageResource(drawable.rook);
				break;

			
		}
		break;
		case 7:
			switch(cardValue){
			case 0:
				card7.setImageResource(drawable.fivered);
				break;
			case 1:
				card7.setImageResource(drawable.sixred);
				break;
			case 2:
				card7.setImageResource(drawable.sevenred);
				break;
			case 3: 
				card7.setImageResource(drawable.eightred);
				break;
			case 4:
				card7.setImageResource(drawable.ninered);
				break;
			case 5:
				card7.setImageResource(drawable.tenred);
				break;
			case 6:
				card7.setImageResource(drawable.elevenred);
				break;
			case 7:
				card7.setImageResource(drawable.twelvered);
				break;
			case 8:
				card7.setImageResource(drawable.thirteenred);
				break;
			case 9: 
				card7.setImageResource(drawable.fourteenred);
				break;
			case 10: 
				card7.setImageResource(drawable.onered);
				break;
			case 11:
				card7.setImageResource(drawable.fiveblue);
				break;
			case 12:
				card7.setImageResource(drawable.sixblue);
				break;
			case 13:
				card7.setImageResource(drawable.sevenblue);
				break;
			case 14: 
				card7.setImageResource(drawable.eightblue);
				break;
			case 15:
				card7.setImageResource(drawable.nineblue);
				break;
			case 16:
				card7.setImageResource(drawable.tenblue);
				break;
			case 17:
				card7.setImageResource(drawable.elevenblue);
				break;
			case 18:
				card7.setImageResource(drawable.twelveblue);
				break;
			case 19:
				card7.setImageResource(drawable.thirteenblue);
				break;
			case 20: 
				card7.setImageResource(drawable.fourteenblue);
				break;
			case 21: 
				card7.setImageResource(drawable.oneblue);
				break;
			case 22:
				card7.setImageResource(drawable.fivegreen);
				break;
			case 23:
				card7.setImageResource(drawable.sixgreen);
				break;
			case 24:
				card7.setImageResource(drawable.sevengreen);
				break;
			case 25: 
				card7.setImageResource(drawable.eightgreen);
				break;
			case 26:
				card7.setImageResource(drawable.ninegreen);
				break;
			case 27:
				card7.setImageResource(drawable.tengreen);
				break;
			case 28:
				card7.setImageResource(drawable.elevengreen);
				break;
			case 29:
				card7.setImageResource(drawable.twelvegreen);
				break;
			case 30:
				card7.setImageResource(drawable.thirteengreen);
				break;
			case 31: 
				card7.setImageResource(drawable.fourteengreen);
				break;
			case 32: 
				card7.setImageResource(drawable.onegreen);
				break;
			case 33:
				card7.setImageResource(drawable.fiveblack);
				break;
			case 34:
				card7.setImageResource(drawable.sixblack);
				break;
			case 35:
				card7.setImageResource(drawable.sevenblack);
				break;
			case 36: 
				card7.setImageResource(drawable.eightblack);
				break;
			case 37:
				card7.setImageResource(drawable.nineblack);
				break;
			case 38:
				card7.setImageResource(drawable.tenblack);
				break;
			case 39:
				card7.setImageResource(drawable.elevenblack);
				break;
			case 40:
				card7.setImageResource(drawable.twelveblack);
				break;
			case 41:
				card7.setImageResource(drawable.thirteenblack);
				break;
			case 42: 
				card7.setImageResource(drawable.fourteenblack);
				break;
			case 43: 
				card7.setImageResource(drawable.oneblack);
				break;
			case 44: 
				card7.setImageResource(drawable.rook);
				break;

			
		}
		break;
		case 8:
			switch(cardValue){
			case 0:
				card8.setImageResource(drawable.fivered);
				break;
			case 1:
				card8.setImageResource(drawable.sixred);
				break;
			case 2:
				card8.setImageResource(drawable.sevenred);
				break;
			case 3: 
				card8.setImageResource(drawable.eightred);
				break;
			case 4:
				card8.setImageResource(drawable.ninered);
				break;
			case 5:
				card8.setImageResource(drawable.tenred);
				break;
			case 6:
				card8.setImageResource(drawable.elevenred);
				break;
			case 7:
				card8.setImageResource(drawable.twelvered);
				break;
			case 8:
				card8.setImageResource(drawable.thirteenred);
				break;
			case 9: 
				card8.setImageResource(drawable.fourteenred);
				break;
			case 10: 
				card8.setImageResource(drawable.onered);
				break;
			case 11:
				card8.setImageResource(drawable.fiveblue);
				break;
			case 12:
				card8.setImageResource(drawable.sixblue);
				break;
			case 13:
				card8.setImageResource(drawable.sevenblue);
				break;
			case 14: 
				card8.setImageResource(drawable.eightblue);
				break;
			case 15:
				card8.setImageResource(drawable.nineblue);
				break;
			case 16:
				card8.setImageResource(drawable.tenblue);
				break;
			case 17:
				card8.setImageResource(drawable.elevenblue);
				break;
			case 18:
				card8.setImageResource(drawable.twelveblue);
				break;
			case 19:
				card8.setImageResource(drawable.thirteenblue);
				break;
			case 20: 
				card8.setImageResource(drawable.fourteenblue);
				break;
			case 21: 
				card8.setImageResource(drawable.oneblue);
				break;
			case 22:
				card8.setImageResource(drawable.fivegreen);
				break;
			case 23:
				card8.setImageResource(drawable.sixgreen);
				break;
			case 24:
				card8.setImageResource(drawable.sevengreen);
				break;
			case 25: 
				card8.setImageResource(drawable.eightgreen);
				break;
			case 26:
				card8.setImageResource(drawable.ninegreen);
				break;
			case 27:
				card8.setImageResource(drawable.tengreen);
				break;
			case 28:
				card8.setImageResource(drawable.elevengreen);
				break;
			case 29:
				card8.setImageResource(drawable.twelvegreen);
				break;
			case 30:
				card8.setImageResource(drawable.thirteengreen);
				break;
			case 31: 
				card8.setImageResource(drawable.fourteengreen);
				break;
			case 32: 
				card8.setImageResource(drawable.onegreen);
				break;
			case 33:
				card8.setImageResource(drawable.fiveblack);
				break;
			case 34:
				card8.setImageResource(drawable.sixblack);
				break;
			case 35:
				card8.setImageResource(drawable.sevenblack);
				break;
			case 36: 
				card8.setImageResource(drawable.eightblack);
				break;
			case 37:
				card8.setImageResource(drawable.nineblack);
				break;
			case 38:
				card8.setImageResource(drawable.tenblack);
				break;
			case 39:
				card8.setImageResource(drawable.elevenblack);
				break;
			case 40:
				card8.setImageResource(drawable.twelveblack);
				break;
			case 41:
				card8.setImageResource(drawable.thirteenblack);
				break;
			case 42: 
				card8.setImageResource(drawable.fourteenblack);
				break;
			case 43: 
				card8.setImageResource(drawable.oneblack);
				break;
			case 44: 
				card8.setImageResource(drawable.rook);
				break;

			
		}
		break;
		case 9:
			switch(cardValue){
			case 0:
				card9.setImageResource(drawable.fivered);
				break;
			case 1:
				card9.setImageResource(drawable.sixred);
				break;
			case 2:
				card9.setImageResource(drawable.sevenred);
				break;
			case 3: 
				card9.setImageResource(drawable.eightred);
				break;
			case 4:
				card9.setImageResource(drawable.ninered);
				break;
			case 5:
				card9.setImageResource(drawable.tenred);
				break;
			case 6:
				card9.setImageResource(drawable.elevenred);
				break;
			case 7:
				card9.setImageResource(drawable.twelvered);
				break;
			case 8:
				card9.setImageResource(drawable.thirteenred);
				break;
			case 9: 
				card9.setImageResource(drawable.fourteenred);
				break;
			case 10: 
				card9.setImageResource(drawable.onered);
				break;
			case 11:
				card9.setImageResource(drawable.fiveblue);
				break;
			case 12:
				card9.setImageResource(drawable.sixblue);
				break;
			case 13:
				card9.setImageResource(drawable.sevenblue);
				break;
			case 14: 
				card9.setImageResource(drawable.eightblue);
				break;
			case 15:
				card9.setImageResource(drawable.nineblue);
				break;
			case 16:
				card9.setImageResource(drawable.tenblue);
				break;
			case 17:
				card9.setImageResource(drawable.elevenblue);
				break;
			case 18:
				card9.setImageResource(drawable.twelveblue);
				break;
			case 19:
				card9.setImageResource(drawable.thirteenblue);
				break;
			case 20: 
				card9.setImageResource(drawable.fourteenblue);
				break;
			case 21: 
				card9.setImageResource(drawable.oneblue);
				break;
			case 22:
				card9.setImageResource(drawable.fivegreen);
				break;
			case 23:
				card9.setImageResource(drawable.sixgreen);
				break;
			case 24:
				card9.setImageResource(drawable.sevengreen);
				break;
			case 25: 
				card9.setImageResource(drawable.eightgreen);
				break;
			case 26:
				card9.setImageResource(drawable.ninegreen);
				break;
			case 27:
				card9.setImageResource(drawable.tengreen);
				break;
			case 28:
				card9.setImageResource(drawable.elevengreen);
				break;
			case 29:
				card9.setImageResource(drawable.twelvegreen);
				break;
			case 30:
				card9.setImageResource(drawable.thirteengreen);
				break;
			case 31: 
				card9.setImageResource(drawable.fourteengreen);
				break;
			case 32: 
				card9.setImageResource(drawable.onegreen);
				break;
			case 33:
				card9.setImageResource(drawable.fiveblack);
				break;
			case 34:
				card9.setImageResource(drawable.sixblack);
				break;
			case 35:
				card9.setImageResource(drawable.sevenblack);
				break;
			case 36: 
				card9.setImageResource(drawable.eightblack);
				break;
			case 37:
				card9.setImageResource(drawable.nineblack);
				break;
			case 38:
				card9.setImageResource(drawable.tenblack);
				break;
			case 39:
				card9.setImageResource(drawable.elevenblack);
				break;
			case 40:
				card9.setImageResource(drawable.twelveblack);
				break;
			case 41:
				card9.setImageResource(drawable.thirteenblack);
				break;
			case 42: 
				card9.setImageResource(drawable.fourteenblack);
				break;
			case 43: 
				card9.setImageResource(drawable.oneblack);
				break;
			case 44: 
				card9.setImageResource(drawable.rook);
				break;

			
		}
		break;
		case 10:
			switch(cardValue){
			case 0:
				card10.setImageResource(drawable.fivered);
				break;
			case 1:
				card10.setImageResource(drawable.sixred);
				break;
			case 2:
				card10.setImageResource(drawable.sevenred);
				break;
			case 3: 
				card10.setImageResource(drawable.eightred);
				break;
			case 4:
				card10.setImageResource(drawable.ninered);
				break;
			case 5:
				card10.setImageResource(drawable.tenred);
				break;
			case 6:
				card10.setImageResource(drawable.elevenred);
				break;
			case 7:
				card10.setImageResource(drawable.twelvered);
				break;
			case 8:
				card10.setImageResource(drawable.thirteenred);
				break;
			case 9: 
				card10.setImageResource(drawable.fourteenred);
				break;
			case 10: 
				card10.setImageResource(drawable.onered);
				break;
			case 11:
				card10.setImageResource(drawable.fiveblue);
				break;
			case 12:
				card10.setImageResource(drawable.sixblue);
				break;
			case 13:
				card10.setImageResource(drawable.sevenblue);
				break;
			case 14: 
				card10.setImageResource(drawable.eightblue);
				break;
			case 15:
				card10.setImageResource(drawable.nineblue);
				break;
			case 16:
				card10.setImageResource(drawable.tenblue);
				break;
			case 17:
				card10.setImageResource(drawable.elevenblue);
				break;
			case 18:
				card10.setImageResource(drawable.twelveblue);
				break;
			case 19:
				card10.setImageResource(drawable.thirteenblue);
				break;
			case 20: 
				card10.setImageResource(drawable.fourteenblue);
				break;
			case 21: 
				card10.setImageResource(drawable.oneblue);
				break;
			case 22:
				card10.setImageResource(drawable.fivegreen);
				break;
			case 23:
				card10.setImageResource(drawable.sixgreen);
				break;
			case 24:
				card10.setImageResource(drawable.sevengreen);
				break;
			case 25: 
				card10.setImageResource(drawable.eightgreen);
				break;
			case 26:
				card10.setImageResource(drawable.ninegreen);
				break;
			case 27:
				card10.setImageResource(drawable.tengreen);
				break;
			case 28:
				card10.setImageResource(drawable.elevengreen);
				break;
			case 29:
				card10.setImageResource(drawable.twelvegreen);
				break;
			case 30:
				card10.setImageResource(drawable.thirteengreen);
				break;
			case 31: 
				card10.setImageResource(drawable.fourteengreen);
				break;
			case 32: 
				card10.setImageResource(drawable.onegreen);
				break;
			case 33:
				card10.setImageResource(drawable.fiveblack);
				break;
			case 34:
				card10.setImageResource(drawable.sixblack);
				break;
			case 35:
				card10.setImageResource(drawable.sevenblack);
				break;
			case 36: 
				card10.setImageResource(drawable.eightblack);
				break;
			case 37:
				card10.setImageResource(drawable.nineblack);
				break;
			case 38:
				card10.setImageResource(drawable.tenblack);
				break;
			case 39:
				card10.setImageResource(drawable.elevenblack);
				break;
			case 40:
				card10.setImageResource(drawable.twelveblack);
				break;
			case 41:
				card10.setImageResource(drawable.thirteenblack);
				break;
			case 42: 
				card10.setImageResource(drawable.fourteenblack);
				break;
			case 43: 
				card10.setImageResource(drawable.oneblack);
				break;
			case 44: 
				card10.setImageResource(drawable.rook);
				break;

			
		}
		break;
		}
		
		
		
		
		
		
	}
}

