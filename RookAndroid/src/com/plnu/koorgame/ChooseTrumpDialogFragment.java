package com.plnu.koorgame;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/*
 * Fragment to let player choose trump
 */
public class ChooseTrumpDialogFragment extends DialogFragment {

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.choose_trump_dialog_text);
		builder.setNeutralButton("OK", null);
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int color) {
	                }
	            };
        return builder.create();
    }
	
}
