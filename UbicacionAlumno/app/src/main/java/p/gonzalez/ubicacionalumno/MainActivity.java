package p.gonzalez.ubicacionalumno;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {
    String latitud;
    String longitud;
    TextView tvLatitud;
    TextView tvLongitud;
    TextView tvLocation;
    Button btnUbicacion;

    Geocoder geocoder;
    List<Address> addresses;
    private FusedLocationProviderClient client;

    LocationManager lm;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        geocoder = new Geocoder(this, Locale.getDefault());
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      //  requestPermission();
       // client = LocationServices.getFusedLocationProviderClient(this);
        tvLatitud = (TextView) (findViewById(R.id.tv_latitud));
        tvLongitud = (TextView) (findViewById(R.id.tv_longitud));
        tvLocation=(TextView)(findViewById(R.id.tv_location));
        btnUbicacion = (Button) (findViewById(R.id.btn_ubicacion));
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                tvLatitud.setText(tvLatitud.getText().toString()+latitude);
                tvLongitud.setText(tvLongitud.getText().toString()+longitude);

                try {
                    addresses=geocoder.getFromLocation(latitude,longitude,1);
                    String address=addresses.get(0).getAddressLine(0);

                    tvLocation.setText(address);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }

}
