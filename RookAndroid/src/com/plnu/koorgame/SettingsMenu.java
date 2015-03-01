package com.plnu.koorgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

public class SettingsMenu extends Activity implements OnItemSelectedListener {

	Button blueButton;
	Button redButton;
	Button greenButton;
	Button purpleButton;
	
	Button lastButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		
		Spinner spinner = (Spinner) findViewById(R.id.pointsSpinner);
		spinner.setOnItemSelectedListener(this);
		
		blueButton = (Button) findViewById(R.id.backgroundButtonBlue);
		redButton = (Button) findViewById(R.id.backgroundButtonRed);
		greenButton = (Button) findViewById(R.id.backgroundButtonGreen);
		purpleButton = (Button) findViewById(R.id.backgroundButtonPurple);
		
		switch (E.GAME_BOARD_COLOR)
		{
		case R.color.game_board_blue:
			lastButton = blueButton;
			break;
		case R.color.game_board_red:
			lastButton = redButton;
			break;
			case R.color.game_board_green:
				lastButton = greenButton;
				break;
		case R.color.game_board_purple:
			lastButton = purpleButton;
			break;
		}
		
		lastButton.setText("X");

	}
	
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
    	E.WINNING_POINT_TOTAL = 500 - ((pos) * 100);
    	System.out.println("WINNINT POINT TOTAL IS: " + E.WINNING_POINT_TOTAL + "!!!!!!!!!");
    }

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	public void blueBackgroundSelect(View view) {
		E.GAME_BOARD_COLOR = R.color.game_board_blue;
		blueButton.setText("X");
		lastButton.setText("");
		lastButton = blueButton;
		
	}
	
	public void redBackgroundSelect(View view) {
		E.GAME_BOARD_COLOR = R.color.game_board_red;
		redButton.setText("X");
		lastButton.setText("");
		lastButton = redButton;
	}
	
	public void greenBackgroundSelect(View view) {
		E.GAME_BOARD_COLOR = R.color.game_board_green;
		greenButton.setText("X");
		lastButton.setText("");
		lastButton = greenButton;
	}
	
	public void purpleBackgroundSelect(View view) {
		E.GAME_BOARD_COLOR = R.color.game_board_purple;
		purpleButton.setText("X");
		lastButton.setText("");
		lastButton = purpleButton;
	}
	
}
