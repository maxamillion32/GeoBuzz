package pdtech.geobuzz.fragment;

/**
 * Created by parth on Sep 20, 2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pdtech.geobuzz.R;

public class NearbyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.nearby_activity,container,false);


        return rootView;
    }
}

