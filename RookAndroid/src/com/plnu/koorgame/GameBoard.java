package com.plnu.koorgame;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

public class GameBoard extends Activity {
	
	private BidFragment bidFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_board);
		
		bidFragment = new BidFragment();
	}
	
	public void startBidding(View v) {
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.fragment_container, bidFragment);
		fragmentTransaction.commit();
	}
	
	public void chooseTrumpColor(View v){
		// Create new fragment and transaction
		ChooseTrumpDialogFragment chooseTrumpFragment = new ChooseTrumpDialogFragment();
		chooseTrumpFragment.show(getFragmentManager(), "trumpdialogtag");
	}
    
    @Override
    protected void onResume() {
        super.onResume();
       
    }
    
	@Override
	protected void onPause() {
		super.onPause();
		
	}
		
}