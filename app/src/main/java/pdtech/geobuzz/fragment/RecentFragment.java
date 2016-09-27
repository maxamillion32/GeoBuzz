package pdtech.geobuzz.fragment;

/**
 * Created by parth on Sep 20, 2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pdtech.geobuzz.R;
import pdtech.geobuzz.pojo.Reminder;

public class RecentFragment extends Fragment {
    private FirebaseRecyclerAdapter<Reminder, ReminderHolder> mReminderAdapter;
    //private FirebaseDatabase mRef;
    //private DatabaseReference remRef;
    private DatabaseReference mRef,remRef;
    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.recent_activity,container,false);

        mRef = FirebaseDatabase.getInstance().getReference();
        remRef = mRef.child("reminder");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        attachRecyclerViewAdapter();
    }

    @Override
    public void onStop() {
        super.onStop();

        if(mReminderAdapter != null){
            mReminderAdapter.cleanup();
        }
    }
    private void attachRecyclerViewAdapter() {
        mReminderAdapter = new FirebaseRecyclerAdapter<Reminder, ReminderHolder>(
                Reminder.class,
                R.layout.reminder_recycler_row_view,
                ReminderHolder.class,
                remRef
        ) {
            @Override
            protected void populateViewHolder(ReminderHolder reminderHolder, Reminder reminder, int position) {
                reminderHolder.setReminderName(reminder.getReminderName());
                reminderHolder.setReminderLocation(reminder.getLatlng());
            }
        };
        recyclerView.setAdapter(mReminderAdapter);
    }

    public static class ReminderHolder extends RecyclerView.ViewHolder{
        View mView;

        public ReminderHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setReminderName(String remName){
            TextView field = (TextView) mView.findViewById(R.id.tv_shopName);
            field.setText(remName);
        }
        public void setReminderLocation(String remLoc){
            TextView field = (TextView) mView.findViewById(R.id.tv_shopDesc);
            field.setText(remLoc);
        }
    }
}

