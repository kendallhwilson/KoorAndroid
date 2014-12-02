package com.plnu.koorgame;

import com.plnu.koorgame.BidFragment.onBidListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*
 * Fragment to let player choose trump
 */
public class ChooseTrumpDialogFragment extends DialogFragment {
	
	private onTrumpListener trumpCallback;

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.choose_trump_dialog_text);
		builder.setItems(new CharSequence[] {"Black", "Red", "Green", "Blue"},
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int color) {
	                	trumpCallback.trumpPass(color);
	                	
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
	                        	//blue trump
	                            break;
	                    }
	                }
	            });
        return builder.create();
    }
	
	public interface onTrumpListener {
		public void trumpPass(int color);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        try {
            trumpCallback = (onTrumpListener) activity;
        } 
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onTrumpPassedListener");
        }
    }
}
