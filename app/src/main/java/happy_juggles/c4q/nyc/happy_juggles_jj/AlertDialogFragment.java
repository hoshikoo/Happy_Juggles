package happy_juggles.c4q.nyc.happy_juggles_jj;

/**
 * JANNEISY CARD
 *
 * Created by s3a on 6/29/15.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;


public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.error_title))
                .setMessage(context.getString(R.string.error_message))
                .setPositiveButton(context.getString(R.string.ok_button), null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
