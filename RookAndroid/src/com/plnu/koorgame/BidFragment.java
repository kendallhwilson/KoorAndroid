package com.plnu.koorgame;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class BidFragment extends Fragment implements OnClickListener {
	public TextView currentHighBid;
	public TextView playerBid;
	private int bid = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.bid_layout, container, false);
		
		currentHighBid = (TextView) v.findViewById(R.id.current_bid_number_textview);
		playerBid = (TextView) v.findViewById(R.id.my_bid_number_textview);
		
		Button increaseFiveButton = (Button) v.findViewById(R.id.bid_add_five_button);
		increaseFiveButton.setOnClickListener(this);
		Button bidChosenButton = (Button) v.findViewById(R.id.bid_go_button);
		bidChosenButton.setOnClickListener(this);
		
        return v;
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
    			playerBid.setText(String.valueOf(bid));
    		}
            break;
        case R.id.bid_go_button:
        	//call method to send new bid
        	break;
        case R.id.pass_button:
        	//call method to pass on bidding
        	break;
        }
    }
	
	/*
	 * Method called to update high bid in GUI
	 * @param the high bid we are showing
	 */
	public void displayHighBid(int highBid){
		currentHighBid.setText(String.valueOf(highBid));
		playerBid.setText(String.valueOf(highBid));
		bid = highBid;
	}
}

