package risabhmishra.com.air;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    Context context = this;
    SharedPreferences sharedpreference;
long aqi_percent,co_percent;
    private FusedLocationProviderClient mFusedLocationClient;
    private RelativeLayout mLayout;
    private LinearLayout llayout;
    private SpeedView speedometer1,speedometer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FirebaseApp.initializeApp(this);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference().child("Pollution");


        setContentView(R.layout.activity_main);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        llayout = findViewById(R.id.llayout);

        check_permission();

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


myRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        String values[] = dataSnapshot.getValue(String.class).split(",");
        long aqi = (long)Float.parseFloat(values[0]);
        long co  = (long)Float.parseFloat(values[3]);
Log.d("aqi",Long.toString(aqi));



     aqi_percent = map(aqi,0,600,0,100);
     co_percent = map(co,0,80,0,100);

     Log.d("a%",Long.toString(aqi_percent));

     speedometer1.speedPercentTo((int)aqi_percent);
     speedometer2.speedPercentTo((int)co_percent);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

        speedometer1 = findViewById(R.id.speedView1);
        //speedometer1.speedTo(19);
        speedometer1.setLowSpeedPercent(20);
        speedometer1.setMediumSpeedPercent(60);
        speedometer1.setUnitTextSize(7);

        speedometer2 = findViewById(R.id.speedView2);
        //speedometer2.speedPercentTo(48);
        speedometer1.setLowSpeedPercent(10);
        speedometer1.setMediumSpeedPercent(45);
        speedometer1.setUnitTextSize(7);

        SpeedView speedometer3 = findViewById(R.id.speedView3);
        speedometer3.speedPercentTo(50);
        speedometer1.setLowSpeedPercent(20);
        speedometer1.setMediumSpeedPercent(60);
        speedometer1.setUnitTextSize(7);

        SpeedView speedometer4 = findViewById(R.id.speedView4);
        speedometer4.speedTo(80);
        speedometer1.setLowSpeedPercent(10);
        speedometer1.setMediumSpeedPercent(70);
        speedometer1.setUnitTextSize(7);
    }


    long map(long x, long in_min, long in_max, long out_min, long out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
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
