package com.plnu.koorgame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	/*
	 * OnClick from play button starts new activity to play game
	 * @param the play button view that was clicked
	 */
	public void startGame(View view) {
		Intent intent = new Intent(this, GameBoard.class);
		startActivity(intent);
	}

}

