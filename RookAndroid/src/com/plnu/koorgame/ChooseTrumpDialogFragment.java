package com.plnu.koorgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChooseTrumpDialogFragment extends DialogFragment {

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.choose_trump_dialog_text);
		builder.setItems(new CharSequence[] {"Black", "Red", "Green", "Yellow"},
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int color) {
	                    switch (color) {
	                        case 0:
	                        	//black trump
	                            break;
	                        case 1:
	                        	//red trump
	                            break;
	                        case 2:
	                        	//green trump
	                            break;
	                        case 3:
	                        	//yellow trump
	                            break;
	                    }
	                }
	            });
        return builder.create();
    }
}
