package com.plnu.koorgame;

import com.plnu.koorgame.ChooseTrumpDialogFragment.onTrumpListener;

import android.app.Activity;
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
	private View view;
	private int numDiscarded = 0;
	private int [] discardIds = {-1, -1, -1, -1, -1}; //testing purposes only
	private int numIds = 0;
	public ImageView handArray[] = new ImageView[15];
	private int playerHandValues[] = new int[15];
	private int playerHandNewValues[] = new int[15];
	private int discardedCards[] = new int[5];
	
	private onDiscardListener discardCallback;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.discard_layout, container, false);
		Button discardButton = (Button) view.findViewById(R.id.discard_button);
		discardButton.setOnClickListener(this);
		
		handArray[0] = (ImageView) view.findViewById(R.id.card1);
		handArray[1] = (ImageView) view.findViewById(R.id.card2);
		handArray[2] = (ImageView) view.findViewById(R.id.card3);
		handArray[3] = (ImageView) view.findViewById(R.id.card4);
		handArray[4] = (ImageView) view.findViewById(R.id.card5);
		handArray[5] = (ImageView) view.findViewById(R.id.card6);
		handArray[6] = (ImageView) view.findViewById(R.id.card7);
		handArray[7] = (ImageView) view.findViewById(R.id.card8);
		handArray[8] = (ImageView) view.findViewById(R.id.card9);
		handArray[9] = (ImageView) view.findViewById(R.id.card10);
		handArray[10] = (ImageView) view.findViewById(R.id.card11);
		handArray[11] = (ImageView) view.findViewById(R.id.card12);
		handArray[12] = (ImageView) view.findViewById(R.id.card13);
		handArray[13] = (ImageView) view.findViewById(R.id.card14);
		handArray[14] = (ImageView) view.findViewById(R.id.card15);
		
		displayPlayerCards();
		playerHandNewValues = getArguments().getIntArray("PlayerHandWithKitty");
		return view;
	}
	
    @Override
	public void onResume() {
        super.onResume();
       
    }
    
	@Override
	public void onPause() {
		super.onPause();
		
	}
	
    public void cardClick(View v) {
		ImageView image = (ImageView) v;
		
    	if (numDiscarded < 5 && (image.getAlpha() == 1)) { //If it has not been selected, then make it appear as selected and add to array.
    		numDiscarded++;
    		image.setAlpha((float)0.5);
    		discardIds[numIds] = image.getId();
    		
    		for(int i=0; i <15; i++){
    			if(handArray[i].getId() == image.getId()){
    				discardedCards[numIds] = playerHandNewValues[i];
    				playerHandNewValues[i] = 100;
    			}
    		}
    		
    		if(numIds < 4){
    			numIds++;
    			}
    		
		} else if(numDiscarded <= 5 && image.getAlpha() == 0.5){//Otherwise undo the select by changing the alpha back to one, remove the id from discardIds and decrement numDiscarded and numIds.
			numDiscarded--;
			image.setAlpha((float)1.0);
			discardIds[numIds] = -1;
			numIds--;
			
			for(int i=0; i <15; i++){
    			if(handArray[i].getId() == image.getId()){
    				discardedCards[numIds] = 100;
    				playerHandNewValues[i] = playerHandValues[i];
    			}
    		}
		}
    
    }

    /*
     * Called when user pushes Discard button
     */
	@Override
	public void onClick(View v) {
		if (numDiscarded == 5) {
			int[] finalPlayerHand = new int[10];
			int counter = 0;
			for(int i=0; i < 15; i++){
				if(playerHandNewValues[i] != 100){
					finalPlayerHand[counter] = playerHandNewValues[i];
					counter++;
				}
			}
			
			for (int i = 0; i < discardIds.length -  1; i++) {
				view.findViewById(discardIds[i]).setVisibility(View.INVISIBLE);
			}
			discardCallback.doneDiscarding(finalPlayerHand, discardedCards);
		}
		
		
	}
	
	public interface onDiscardListener {
		public void doneDiscarding(int[] playerHand, int[] playerDiscards);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        try {
            discardCallback = (onDiscardListener) activity;
        } 
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onDiscardListener");
        }
	}
	
	
	public void displayPlayerCards(){	
		//Card color notes: value \ 11 = color.
		//Card value notes: value % 11 = number.
		playerHandValues = getArguments().getIntArray("PlayerHandWithKitty");
		
		for(int i=0; i < 15; i++){
			String cardText = "@drawable/" + getCardText(playerHandValues[i]);
			int imageResource = getResources().getIdentifier(cardText, null, "com.plnu.koorgame");
			handArray[i].setImageResource(imageResource);
		}
	}
	
	public String getCardText(int cardNumber)
	{
		String returningCardText = "";
		String cardColors[] = {"red", "blue", "green", "black"};
		String cardNames[] = {"five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", 
				"thirteen", "fourteen", "one"};
		
		if(cardNumber == 44)
			return "zerorook";
		
		returningCardText = cardNames[cardNumber % 11];
		returningCardText = returningCardText + cardColors[cardNumber / 11];
		
		return returningCardText;
		
	}

}
