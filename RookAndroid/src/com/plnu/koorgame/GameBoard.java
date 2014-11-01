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
	
	public void startBidding(View v)
	{
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.fragment_container, bidFragment);
		fragmentTransaction.commit();
	}
	
	public void chooseTrumpColor(View v)
	{
		// Create new fragment and transaction
		ChooseTrumpDialogFragment chooseTrumpFragment = new ChooseTrumpDialogFragment();
		chooseTrumpFragment.show(getFragmentManager(), "trumpdialogtag");
		//FragmentTransaction transaction = getFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack
		///transaction.replace(R.id.fragment_container, chooseTrumpFragment);
		//transaction.addToBackStack(null);

		// Commit the transaction
		//transaction.commit();
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