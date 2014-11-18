package com.plnu.koorgame;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/*
 * Fragment to do all bidding
 */
public class BidFragment extends Fragment implements OnClickListener {
	
	public TextView player1Bid;
	public TextView player2Bid;
	public TextView player3Bid;
	public TextView myBid;
	private int bid = 0;
	
	private onBidListener bidCallback;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.bid_layout, container, false);
		
		player1Bid = (TextView) v.findViewById(R.id.player1status_textview);
		player2Bid = (TextView) v.findViewById(R.id.player2status_textview);
		player3Bid = (TextView) v.findViewById(R.id.player3status_textview);
		myBid = (TextView) v.findViewById(R.id.my_bid_number_textview);
		
		Button increaseFiveButton = (Button) v.findViewById(R.id.bid_add_five_button);
		increaseFiveButton.setOnClickListener(this);
		Button bidChosenButton = (Button) v.findViewById(R.id.bid_go_button);
		bidChosenButton.setOnClickListener(this);
		
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
			bid = newBid;
			myBid.setText(String.valueOf(newBid + 5));
	}
}

