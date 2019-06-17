package irwanprasetyo52gmail.com;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;
import android.widget.Toast;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;//membuat import android agar dalam penggabungan keduanya yaitu lokasi dan sensor dapat digabungkan jadi satu


public class MainActivity extends AppCompatActivity implements LocationListener, SensorEventListener{//kita implements location dan sensornya
    private TextView latituteField;
    private TextView longitudeField;//dengan latitude dan longtitude dengan menggunakan textview
    private LocationManager locationManager;
    private String provider;
    private ImageView image;
    private float currentDegree = 0f;
    private SensorManager mSensorManager;
    TextView tvHeading;//dengan private membuat location manager,provider image view untuk menampilkan gambar, float currentdegree dan sensor  anager

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//kita atur dengan akan menampilkan layout dari activity main.

        latituteField = (TextView) findViewById(R.id.TextView01);
        longitudeField = (TextView) findViewById(R.id.TextView02);//dengan latitude dan longtitude akan ditampilkan pada textview1 dan textview2 berdasarkan id nya
        locationManager = (LocationManager)//location manager akan menampilkan location manager
                getSystemService(Context.LOCATION_SERVICE);//dan sistem akan menservice location

        image = (ImageView) findViewById(R.id.imageViewCompass);//gambar akan ditampilkan berdasarkan id pada imageViewCompass
        tvHeading = (TextView) findViewById(R.id.tvHeading);//dan akan menampilkan pada tv heading berdasarkan id nya
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//pada sensor akan mengatir sensor pada sistem

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);//jika lokasi tidak sama dengan null maka akan menampilkan provider has been selected
        } else {
            latituteField.setText("Location not available");
            longitudeField.setText("Location not available");//jika tidak maka akan menampilkan location not avaliable
        }
    }

    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);     }


        protected void onPause() {
            super.onPause();
            locationManager.removeUpdates(this);//pada void onpause pada locasi manager akan remove update
mSensorManager.unregisterListener((SensorEventListener) this); }

    public void onSensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]);//pada float degree dengan math event dengan values 0

        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");
        RotateAnimation ra = new RotateAnimation(
                currentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,                 0.5f);//dengan tv heading float dengan degree maka akan merotate animation dengan pivot 0.5f

        ra.setDuration(210);         ra.setFillAfter(true); //dengan  durasi 210 dan set fiil after true

        image.startAnimation(ra);         currentDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
        public void onLocationChanged(Location location) {
            double lat = (location.getLatitude());
            double lng = (location.getLongitude());
            latituteField.setText(String.valueOf(lat));
            longitudeField.setText(String.valueOf(lng));     }//pada void lokasi lat akan mengambil data latitude, begitu juga dengan lng mengambil pada longtitude

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {     }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(this, "Enabled new provider " + provider,                 Toast.LENGTH_SHORT).show();
        }
        @Override     public void onProviderDisabled(String provider) {
            Toast.makeText(this, "Disabled provider " + provider, Toast.LENGTH_SHORT).show();
        }//pada void enable akan membuat enable pada rovider, sebaliknya pada disable
    }


