package pdtech.geobuzz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by parth on Sep 20, 2016.
 */

public class Splash extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activited
                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(Splash.this,MainActivity.class));
                    finish();
                }else {


                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i);
                    finish();

                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}