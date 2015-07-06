package happy_juggles.c4q.nyc.happy_juggles_jj;

/**
 * HOSHIKO'S TODO CARD
 *
 * Created by Hoshiko on 7/1/15.
 */

import android.content.Context;

import com.dexafree.materialList.cards.SimpleCard;

public class TodoCard extends SimpleCard {
    public TodoCard(Context context) {
        super(context);
    }

    @Override
    public int getLayout(){
        return R.layout.todo_custom_layout;
    }
}
