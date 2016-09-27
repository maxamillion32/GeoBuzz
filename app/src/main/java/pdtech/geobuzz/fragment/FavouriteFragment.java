package pdtech.geobuzz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pdtech.geobuzz.MainActivity;
import pdtech.geobuzz.R;

/**
 * Created by parth on Sep 19, 2016.
 */


    public class FavouriteFragment extends Fragment{

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView=inflater.inflate(R.layout.favourite_activity,container,false);


            final FloatingActionButton fab = ((MainActivity) getActivity()).getFloatingActionButton();

            //fab.hide();


            CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
            p.setAnchorId(View.NO_ID);
            fab.setLayoutParams(p);
            fab.setVisibility(View.GONE);

            return rootView;
        }
    }


