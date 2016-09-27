package pdtech.geobuzz.fragment;

/**
 * Created by parth on Sep 19, 2016.
 */
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pdtech.geobuzz.MainActivity;
import pdtech.geobuzz.R;

public class FriendFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.friend_activity,container,false);


        final FloatingActionButton fab = ((MainActivity) getActivity()).getFloatingActionButton();

        fab.hide();
        return rootView;
    }
}