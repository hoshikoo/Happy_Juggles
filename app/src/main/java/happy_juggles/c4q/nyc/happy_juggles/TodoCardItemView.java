package happy_juggles.c4q.nyc.happy_juggles;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dexafree.materialList.cards.internal.BaseTextCardItemView;

/**
 * Created by Hoshiko on 7/1/15.
 */
public class TodoCardItemView extends BaseTextCardItemView<TodoCard> {

    TextView mTitle;
    TextView mDescription;
    Button addButton;

    // Default constructors
    public TodoCardItemView(Context context) {
        super(context);
    }

    public TodoCardItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TodoCardItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void build(final TodoCard todocard) {
        setTitle(todocard.getTitle());
        setDescription(todocard.getDescription());
        setButton();

        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (todocard.getOnButtonPressedListener() != null) {
                    todocard.getOnButtonPressedListener().onButtonPressedListener(addButton, todocard);
                }
            }
        });
    }

    public void setTitle(String title){
        mTitle = (TextView)findViewById(R.id.titleTextView);
        mTitle.setText(title);
    }

    public void setButton(){
       addButton = (Button)findViewById(R.id.add_button);
    }



    public void setDescription(String description){
        mDescription = (TextView)findViewById(R.id.descriptionTextView);
        mDescription.setText(description);
    }


//    @Override
//    public void onButtonPressedListener(View view, Card card) {
//
//        Toast.makeText(getContext(), "Let's edit the card!", Toast.LENGTH_SHORT).show();
//    }
}
