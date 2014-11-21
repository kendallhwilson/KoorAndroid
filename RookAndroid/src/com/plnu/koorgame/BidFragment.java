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
		int cardValueMod = 0;
		if(cardValue==44){
			switch(cardIndex){
			case 1:
				card1.setImageResource(drawable.rook);
			case 2:	
				card2.setImageResource(drawable.rook);
			case 3:
				card3.setImageResource(drawable.rook);
			case 4:
				card4.setImageResource(drawable.rook);
			case 5:
				card5.setImageResource(drawable.rook);
			case 6:
				card6.setImageResource(drawable.rook);
			case 7:
				card7.setImageResource(drawable.rook);
			case 8:
				card8.setImageResource(drawable.rook);
			case 9:
				card9.setImageResource(drawable.rook);
			case 10:
				card10.setImageResource(drawable.rook);
			
			}
		}
		
		
		
		
		
		
	}
}

