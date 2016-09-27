package pdtech.geobuzz;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

import pdtech.geobuzz.fragment.FavouriteFragment;
import pdtech.geobuzz.fragment.FriendFragment;
import pdtech.geobuzz.fragment.NearbyFragment;
import pdtech.geobuzz.fragment.RecentFragment;
import pdtech.geobuzz.pojo.Reminder;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    public static final String TAG = "RecyclerViewDemo";
    // private FirebaseDatabase mRef;
    // private DatabaseReference remRef;
    private DatabaseReference mRef,remRef;
    String enteredName,enteredDescription;
    FloatingActionButton fabBtn;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView toolbarImg;
    int currentPage;
    int PLACE_PICKER_REQUEST = 1;
    LatLng ll;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setDrawer();
        initCollapsingToolbar();

        mRef = FirebaseDatabase.getInstance().getReference();
        remRef = mRef.child("reminder");
        toolbarImg = (ImageView) findViewById(R.id.backdrop);



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        fabBtn = (FloatingActionButton)findViewById(R.id.fab);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                createDialog();
            }
        });



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (currentPage){
                    case 0:
                        Glide.with(getApplicationContext()).load(R.drawable.appbg2).into(toolbarImg);
                        fabBtn.show();
                        break;
                    case 1:
                        fabBtn.hide();
                        Glide.with(getApplicationContext()).load(R.drawable.appbg3).into(toolbarImg);
                        break;
                    case 2:
                        fabBtn.hide();
                        Glide.with(getApplicationContext()).load(R.drawable.appbg).into(toolbarImg);
                        break;
                    case 3:
                        fabBtn.hide();
                        Glide.with(getApplicationContext()).load(R.drawable.appbg4).into(toolbarImg);
                        break;
                }
                /*if(currentPage == 1) {
                    fabBtn.hide();
                    Glide.with(getApplicationContext()).load(R.drawable.appbg).into(toolbarImg);
                }else if(){
                    Glide.with(getApplicationContext()).load(R.drawable.appbg2).into(toolbarImg);
                    fabBtn.show();
                }*/




            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;


            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });


        //createBottomBar();


    }

    private void createDialog() {
        Toast.makeText(this,"Active",Toast.LENGTH_SHORT).show();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText Name = (EditText) dialogView.findViewById(R.id.et_Name);
        final EditText Description = (EditText) dialogView.findViewById(R.id.et_Desc);


        dialogBuilder.setTitle("Add Reminder");
        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enteredName = Name.getText().toString();
                enteredDescription = Description.getText().toString();

                String bv = String.valueOf(ll);
                Reminder reminder = new Reminder(enteredName,bv);
                remRef.push().setValue(reminder, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.e(TAG, "Failed to write message", databaseError.toException());
                            Toast.makeText(MainActivity.this, "Error adding Reminder", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Reminder added successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //collapsingToolbar.setTitle(getString(R.string.app_name));
                    //isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }



    private void createBottomBar() {
       /* mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_favorites) {
                    RecentFragment recFrag = new RecentFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.myCoordinator,recFrag).commit();
                }else if(tabId == R.id.tab_recent){
                    FavouriteFragment favFrag = new FavouriteFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.myCoordinator,favFrag).commit();
                }else if(tabId == R.id.tab_nearby){
                    NearbyFragment nerFrag = new NearbyFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.myCoordinator,nerFrag).commit();
                }else if(tabId == R.id.tab_friend){
                    FriendFragment friFrag = new FriendFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.myCoordinator,friFrag).commit();
                }
            }
        });*/
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecentFragment(), "RECENT");
        adapter.addFragment(new FavouriteFragment(), "FAVOURITE");
        adapter.addFragment(new NearbyFragment(), "NEAR BY");
        adapter.addFragment(new FriendFragment(), "FRIEND");
        viewPager.setAdapter(adapter);
    }

    public FloatingActionButton getFloatingActionButton() {
        return fabBtn;
    }

    public void PickPlace(View view) {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();


        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);

                ll = place.getLatLng();
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setDrawer() {

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home);
        SecondaryDrawerItem item2 = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_settings);
        //create the drawer and remember the `Drawer` result object

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Parth Dani").withEmail("parth_dani@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                //.withTranslucentStatusBar(true)
                //.withActionBarDrawerToggle(false)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2
                        //new SecondaryDrawerItem().withName(R.string.drawer_item_settings)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return false;
                    }
                })
                .build();
        //           result.addItem(new DividerDrawerItem());
        result.addStickyFooterItem(new PrimaryDrawerItem().withName("StickyFooterItem"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



}

