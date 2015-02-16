package com.plnu.koorgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/*
 * Alert the player of the winner of the bid and the trump color
 */
public class AlertTrumpDialogFragment extends DialogFragment {
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		int winnerNum = getArguments().getInt("WINNER");
		String color = getArguments().getString("TRUMPCOLOR");
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String message = "Player " + (winnerNum+1) + " won the bid. The trump color is "  + color + ".";
		builder.setMessage(message);
		builder.setNeutralButton(R.string.ok, null);
        return builder.create();
    }
	
	@Override public void onStart() {
		super.onStart();
		
		Window window = getDialog().getWindow();
		WindowManager.LayoutParams windowParams = window.getAttributes();
		windowParams.dimAmount = 0;
		windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		window.setAttributes(windowParams);
	}

}
