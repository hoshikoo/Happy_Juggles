package happy_juggles.c4q.nyc.happy_juggles;

import android.content.Context;

import com.dexafree.materialList.cards.SimpleCard;

/**
 * Created by Hoshiko on 7/1/15.
 */
public class TodoCard extends SimpleCard {
    public TodoCard(Context context) {
        super(context);
    }

    @Override
    public int getLayout(){
        return R.layout.todo_custom_layout;
    }
}
