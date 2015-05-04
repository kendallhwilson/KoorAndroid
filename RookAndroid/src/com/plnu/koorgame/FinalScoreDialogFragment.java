package com.plnu.koorgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;

public class FinalScoreDialogFragment extends DialogFragment {
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String message = "Round Over!\n" + "Current winner is " + getArguments().getString("WINNERNAME")
				+ " with "  + getArguments().getInt("WINNERSCORE") + " points!";
		message += "\n Currently losing is " + getArguments().getString("LOSERNAME") + " with " + 
				getArguments().getInt("LOSERSCORE") + " points :( .";
		builder.setMessage(message);
		builder.setNeutralButton(R.string.ok, null);

        return builder.create();
    }
	
		
}
