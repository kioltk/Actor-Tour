package im.actor.tour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import im.actor.tour.R;

/**
 * Created by Jesus Christ. Amen.
 */
public class TourFragment extends Fragment {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_tour, null);

        return rootView;
    }
}
