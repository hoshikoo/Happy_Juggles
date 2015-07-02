//package happy_juggles.c4q.nyc.happy_juggles;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.widget.TextView;
//
//import com.dexafree.materialList.model.CardItemView;
//
///**
// * Created by Hoshiko on 7/1/15.
// */
//public class TodoCardItemView extends CardItemView<TodoCard> {
//
//    TextView mTitle;
//    TextView mDescription;
//
//    // Default constructors
//    public TodoCardItemView(Context context) {
//        super(context);
//    }
//
//    public TodoCardItemView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public TodoCardItemView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
//
//    @Override
//    public void build(TodoCard card) {
//        setTitle(card.getTitle());
//        setDescription(card.getDescription());
//    }
//
//    public void setTitle(String title){
//        mTitle = (TextView)findViewById(R.id.titleTextView);
//        mTitle.setText(title);
//    }
//
//    public void setDescription(String description){
//        mDescription = (TextView)findViewById(R.id.descriptionTextView);
//        mDescription.setText(description);
//    }
//
//}
