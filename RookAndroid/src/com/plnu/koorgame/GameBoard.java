package com.plnu.koorgame;

import android.app.Activity;
import android.os.Bundle;

public class GameBoard extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_board);
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