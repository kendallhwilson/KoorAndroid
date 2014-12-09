package com.plnu.koorgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class AlertTrickWinnerDialogFragment extends DialogFragment {
	
	private onTrickListener trickCallback;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		String message = "";
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		if(getArguments().getString("WinnerName").equals("Player 4")){
		message = "You won the trick! You lead next.";
		} else {
		message = "Winner of last trick was: " + getArguments().getString("WinnerName") + " . " + getArguments().getString("WinnerName") + " leads next.";
		}
		builder.setMessage(message);
		builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int something) {
            	trickCallback.resetAllTrickCards();
            }
        });
        
		return builder.create();
		
	}

	
	public interface onTrickListener{
		public void resetAllTrickCards();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        try {
            trickCallback = (onTrickListener) activity;
        } 
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onTrickListener");
        }
    }
}
	
