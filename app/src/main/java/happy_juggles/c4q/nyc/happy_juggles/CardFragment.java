package happy_juggles.c4q.nyc.happy_juggles;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import happy_juggles.c4q.nyc.happy_juggles_jj.R;


/**
 *
 * Added a Fragment Class so it can work on fragments. The rest I'm lost in
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link//CardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment {
    //created the variables
    private RecyclerView rv;


    //if main activity does "setContentView", then fragment does inflater to connect the xml to
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_main, container, false);

      rv = (RecyclerView) result.findViewById(R.id.frag_recyclerview);

        return rv;

    }
}