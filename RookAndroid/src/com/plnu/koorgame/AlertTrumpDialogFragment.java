package com.plnu.koorgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/*
 * Alert the player of the winner of the bid and the trump color
 */
public class AlertTrumpDialogFragment extends DialogFragment {
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		int winnerNum = getArguments().getInt("WINNER");
		String color = getArguments().getString("TRUMPCOLOR");
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String message = "Player " + winnerNum + " won the bid. The trump color is "  + color + ".";
		builder.setMessage(message);
		builder.setNeutralButton(R.string.ok, null);
        return builder.create();
    }

}
