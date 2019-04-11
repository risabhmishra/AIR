package risabhmishra.com.air;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.anastr.speedviewlib.SpeedView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;



public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    Context context = this;
    TextView rl_co,rl_no,rl_su,rl_o3;
    SharedPreferences sharedpreference;

    private FusedLocationProviderClient mFusedLocationClient;
    private RelativeLayout mLayout;
    private LinearLayout llayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        llayout = findViewById(R.id.llayout);

        check_permission();
        update_data();

        llayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeTop() {
                startActivity(new Intent(MainActivity.this,WeatherForecast.class));
            }
            public void onSwipeRight() {
                startActivity(new Intent(MainActivity.this,WeatherForecast.class));
            }
            public void onSwipeLeft() {
                startActivity(new Intent(MainActivity.this,WeatherForecast.class));
            }
            public void onSwipeBottom() {
                startActivity(new Intent(MainActivity.this,WeatherForecast.class));
            }

        });




        SpeedView speedometer1 = findViewById(R.id.speedView1);
        speedometer1.speedTo(19);
        speedometer1.setLowSpeedPercent(10);
        speedometer1.setMediumSpeedPercent(50);
        speedometer1.setUnitTextSize(7);

        SpeedView speedometer2 = findViewById(R.id.speedView2);
        speedometer2.speedPercentTo(48);
        speedometer1.setLowSpeedPercent(20);
        speedometer1.setMediumSpeedPercent(50);
        speedometer1.setUnitTextSize(7);

        SpeedView speedometer3 = findViewById(R.id.speedView3);
        speedometer3.speedTo(250);
        speedometer1.setLowSpeedPercent(20);
        speedometer1.setMediumSpeedPercent(60);
        speedometer1.setUnitTextSize(7);

        SpeedView speedometer4 = findViewById(R.id.speedView4);
        speedometer4.speedTo(80);
        speedometer1.setLowSpeedPercent(10);
        speedometer1.setMediumSpeedPercent(70);
        speedometer1.setUnitTextSize(7);
    }


    private void update_data() {


    }

    private void check_permission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new AlertDialog.Builder(this)
                        .setTitle("Required Location Permission")
                        .setMessage("You have to give this permission to acess this feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                Double latittude = location.getLatitude();
                                Double longitude = location.getLongitude();

                                sharedpreference = getApplicationContext().getSharedPreferences("sf",0);

                                SharedPreferences.Editor editor = sharedpreference.edit();
                                editor.putString("lat",latittude.toString());
                                editor.putString("long",longitude.toString());
                                editor.apply();


                                //Toast.makeText(MainActivity.this,"Latitude = " + latittude + "\nLongitude = " + longitude,Toast.LENGTH_LONG).show();

                            }
                        }
                    });

        }
    }
}
