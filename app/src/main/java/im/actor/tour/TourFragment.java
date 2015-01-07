package im.actor.tour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import im.actor.tour.R;

/**
 * Created by Jesus Christ. Amen.
 */
public class TourFragment extends Fragment {
    private static final String ARG_POSITION = "arg_pos";
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_tour, null);

        TextView titleView = (TextView) rootView.findViewById(R.id.title);
        TextView bodyView = (TextView) rootView.findViewById(R.id.body);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.image);


        Bundle args = getArguments();
        int position = args.getInt(ARG_POSITION);

        switch (position) {
            case 1:
                titleView.setText("Hudge Group Chats");
                bodyView.setText("Incredibly large group chats. More than 100 users in one group. Communicate with your team and make decisions together.");
                imageView.setImageResource(R.drawable.intro_groups);
                break;
            case 2:
                titleView.setText("Works everywhere");
                bodyView.setText("No matter where you are in the subway or on the mountain hills. Your message will always reach the right people.");
                imageView.setImageResource(R.drawable.intro_subway);
                break;
            case 3:
                titleView.setText("Secure & Private");
                bodyView.setText("Rest assured no one will read your messages. Share documents, files and photos without fear that they could fall into the wrong hands.");
                imageView.setImageResource(R.drawable.intro_secure);
                break;
            case 4:
                titleView.setText("Click login above ^^");
                bodyView.setText("Much like actor");
                //imageView.setImageResource(R.drawable.ic_launcher);
                break;
        }

        return rootView;
    }

    public static Fragment getInstance(int position) {
        Fragment fragment = new TourFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION,position);
        fragment.setArguments(args);
        return fragment;
    }
}
