package com.plnu.koorgame;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/*
 * Fragment to show hand with kitty added and allow five discards
 */
public class DiscardFragment extends Fragment implements OnClickListener {
	private View v;
	private int numDiscarded = 0;
	private int [] discardIds = {-1, -1, -1, -1, -1};
	private int numIds = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.discard_layout, container, false);
		Button discardButton = (Button) v.findViewById(R.id.discard_button);
		discardButton.setOnClickListener(this);
		return v;
	}
	
    @Override
	public void onResume() {
        super.onResume();
       
    }
    
	@Override
	public void onPause() {
		super.onPause();
		
	}
	
	private void cardDisappear() {
		for (int i = 0; i < discardIds.length; i++) {
			v.findViewById(discardIds[i]).setVisibility(View.INVISIBLE);
		}
			
	}
	
    public void cardClick(View v) {
    	if (numDiscarded < 5) {
    		numDiscarded++;
    		ImageView image = (ImageView) v;
    		image.setAlpha((float)0.5);
    		discardIds[numIds] = image.getId();
    		numIds++;
		}
    }

	@Override
	public void onClick(View v) {
		if (numDiscarded == 5) {
			for (int i = 0; i < discardIds.length; i++) {
				v.findViewById(discardIds[i]).setVisibility(View.INVISIBLE);
			}
		}
		
	}

}