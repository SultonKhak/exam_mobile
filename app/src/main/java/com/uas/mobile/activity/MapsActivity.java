package com.uas.mobile.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.uas.mobile.R;
import com.uas.mobile.api.client.ApiClient;
import com.uas.mobile.api.ws.ApiEmployeeInterface;
import com.uas.mobile.local.data.AppDatabase;
import com.uas.mobile.local.data.dao.AddressDao;
import com.uas.mobile.local.data.entity.AddressBook;
import com.uas.mobile.model.ModelEmployeeResponse;
import com.uas.mobile.model.ModelUserResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ApiEmployeeInterface apiEmployeeInterface;
    public static String nim;
    public static String nama;
    private String urlImage;
    private String phoneNumber;
    private String email;

    private TextView txtName;
    private TextView txtCity;
    private TextView txtPhone;
    private TextView txtMemberSince;
    private TextView txtEmail;
    private TextView txtAddToAddressBook;

    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        txtName = findViewById(R.id.txtName);
        txtCity = findViewById(R.id.txtCity);
        txtPhone = findViewById(R.id.txtPhone);
        txtMemberSince = findViewById(R.id.txtMemberSince);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddToAddressBook = findViewById(R.id.txtAddToAddressBook);

        Intent intent = getIntent();
        int employeeId = intent.getIntExtra("employeeId", 0);
        nim = intent.getStringExtra("nim");
        nama = intent.getStringExtra("nama");
        urlImage = intent.getStringExtra("urlImage");

        String newUrl = ApiClient.BASE_URL + employeeId + "/";

        initApi();
        getEmployeeDetail(newUrl);

        txtAddToAddressBook.setOnClickListener(v -> insertAddressBook());

    }

    private void initApi() {
        apiEmployeeInterface = ApiClient.getClient().create(ApiEmployeeInterface.class);
    }

    private void getEmployeeDetail(final String url) {
        Call<ModelUserResponse> call = apiEmployeeInterface.getDetailEmployee(url, nim, nama);
        call.enqueue(new Callback<ModelUserResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ModelUserResponse> call, @NonNull Response<ModelUserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200 && response.body() != null) {
                        txtName.setText(response.body().getModelEmployeeResponses().get(0).getName().getTitle() + " " +
                                response.body().getModelEmployeeResponses().get(0).getName().getFirst() + " " +
                                response.body().getModelEmployeeResponses().get(0).getName().getLast());
                        txtCity.setText("City : " + response.body().getModelEmployeeResponses().get(0).getLocation().getCity() + ", " +
                                response.body().getModelEmployeeResponses().get(0).getLocation().getCountry());
                        phoneNumber = response.body().getModelEmployeeResponses().get(0).getPhone();
                        txtPhone.setText("Phone : " + phoneNumber + " / " +
                                response.body().getModelEmployeeResponses().get(0).getCell());
                        String date = response.body().getModelEmployeeResponses().get(0).getRegistered().getDate();
                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                        Date dates = null;
                        try {
                            dates = simpleDateFormat.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (dates != null) {
                            @SuppressLint("SimpleDateFormat")
                            DateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
                            String newDate = dateFormat.format(dates);
                            txtMemberSince.setText("Member since : " + newDate);
                        }
                        email = response.body().getModelEmployeeResponses().get(0).getEmail();
                        txtEmail.setText("Email : " + email);
                        latitude = Double.parseDouble(response.body().getModelEmployeeResponses().get(0).getLocation().getCoordinates().getLatitude());
                        longitude = Double.parseDouble(response.body().getModelEmployeeResponses().get(0).getLocation().getCoordinates().getLongitude());

                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        if (mapFragment != null) {
                            mapFragment.getMapAsync(MapsActivity.this);
                        }

                    } else {
                        Toast.makeText(getBaseContext(), "Data is Empty !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Failed to Load Data ! \n Try Again !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelUserResponse> call, @NonNull Throwable t) {
                Toast.makeText(getBaseContext(), "Failed to Load Data ! \n Try Again !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title(txtName.getText().toString()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void insertAddressBook() {
        ImageView imageView = new ImageView(getBaseContext());
        Picasso.get()
                .load(urlImage)
                .into(imageView);

        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        imageView.setVisibility(View.INVISIBLE);

        AddressBook addressBook = new AddressBook();
        addressBook.setName(txtName.getText().toString());
        addressBook.setCity(txtCity.getText().toString());
        addressBook.setBitmap(bitmap);
        addressBook.setPhoneNumber(phoneNumber);
        addressBook.setEmail(email);
        AppDatabase.databaseWriteExecutor.execute(() -> {
            final AppDatabase localDb = AppDatabase.getDatabase(getBaseContext());
            final AddressDao addressDao = localDb.addressDao();
            final int localId = (int) addressDao.postAddress(addressBook);
            if (localId != 0) {
                Intent intent = new Intent(getBaseContext(), AddressBookActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getBaseContext(), "Input Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

}