package com.plnu.koorgame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	
	RelativeLayout background;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		background = (RelativeLayout) findViewById(R.id.main_background);
		background.setBackgroundColor(getResources().getColor(E.GAME_BOARD_COLOR));
	}
    
    @Override
    protected void onResume() {
    	background.setBackgroundColor(getResources().getColor(E.GAME_BOARD_COLOR));
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
	
	public void openHelpMenu(View view) {
		Intent intent = new Intent(this, HelpMenu.class);
		startActivity(intent);
	}

	public void openSettingsMenu(View view) {
		Intent intent = new Intent(this, SettingsMenu.class);
		startActivity(intent);
	}

}

