package com.plnu.koorgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class FinalScoreDialogFragment extends DialogFragment {
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String message = "Hand complete! Score:\n" + "Your team: " + getArguments().getInt("YOURSCORE") + " points!";
		message += "\n Opponents: " + getArguments().getInt("COMPUTERSCORE") + " points.";
		builder.setMessage(message);
		builder.setNeutralButton(R.string.ok, null);
        return builder.create();
    }
}
