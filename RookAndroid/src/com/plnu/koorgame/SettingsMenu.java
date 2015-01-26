package com.plnu.koorgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class SettingsMenu extends Activity implements OnItemSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		
		Spinner spinner = (Spinner) findViewById(R.id.pointsSpinner);
		spinner.setOnItemSelectedListener(this);
	}
	
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // parent.getItemAtPosition(pos)
    	//setFinalPoints(getItemAtPosition(pos)*100);
    }

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	public void blueBackgroundSelect(View view) {
		E.GAME_BOARD_COLOR = R.color.game_board_blue;
	}
	
	public void redBackgroundSelect(View view) {
		E.GAME_BOARD_COLOR = R.color.game_board_red;
	}
	
	public void greenBackgroundSelect(View view) {
		E.GAME_BOARD_COLOR = R.color.game_board_green;
	}
	
	public void purpleBackgroundSelect(View view) {
		E.GAME_BOARD_COLOR = R.color.game_board_purple;
	}
	
}
