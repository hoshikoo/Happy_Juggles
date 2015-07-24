package happy_juggles.c4q.nyc.happy_juggles;

import android.content.Context;

import com.dexafree.materialList.cards.OnButtonPressListener;
import com.dexafree.materialList.cards.SimpleCard;

/**
 * Created by Hoshiko on 7/1/15.
 */
public class TodoCard extends SimpleCard {

    private OnButtonPressListener mListener;
    private String buttonText;

    public OnButtonPressListener getOnButtonPressedListener() {
        return mListener;
    }

    public void setOnButtonPressedListener(OnButtonPressListener mListener) {
        this.mListener = mListener;
    }


    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }




    public TodoCard(Context context) {
        super(context);
    }

    @Override
    public int getLayout(){
        return R.layout.todo_custom_layout;
    }

//    @Override
//    public void onButtonPressedListener(View view, Card card) {
//
//    }
}
