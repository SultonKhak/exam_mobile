package com.uas.mobile.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.uas.mobile.R;
import com.uas.mobile.adapter.AdapterEmployee;
import com.uas.mobile.api.client.ApiClient;
import com.uas.mobile.api.ws.ApiEmployeeInterface;
import com.uas.mobile.model.ModelEmployeeResponse;
import com.uas.mobile.model.ModelUserResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterEmployee.OnItemClickListener{

    private ApiEmployeeInterface apiEmployeeInterface;
    private RecyclerView recyclerViewEmployee;
    private TextView txtTitle;
    private List<ModelEmployeeResponse> listEmployee = new ArrayList<>();
    private AdapterEmployee adapterEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        recyclerViewEmployee = findViewById(R.id.recyclerViewEmployee);

        SearchView search = findViewById(R.id.search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        initApi();
        getAllEmployee();
    }

    private void initApi() {
        apiEmployeeInterface = ApiClient.getClient().create(ApiEmployeeInterface.class);
    }

    private void getAllEmployee() {
        Call<ModelUserResponse> call = apiEmployeeInterface.getAllData("2201842586", "Billy Keane");
        call.enqueue(new Callback<ModelUserResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ModelUserResponse> call, @NonNull Response<ModelUserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200 && response.body() != null) {
                        listEmployee = response.body().getModelEmployeeResponses();
                        recyclerViewEmployee.setHasFixedSize(true);
                        recyclerViewEmployee.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
                        adapterEmployee = new AdapterEmployee(getBaseContext(), listEmployee);
                        adapterEmployee.setOnItemClickListener(MainActivity.this);
                        recyclerViewEmployee.setAdapter(adapterEmployee);
                        recyclerViewEmployee.setNestedScrollingEnabled(true);
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
    public void onItemClick(int position) {
        Intent intent = new Intent(getBaseContext(), MapsActivity.class);
        intent.putExtra("employeeId", listEmployee.get(position).getEmployeeId());
        intent.putExtra("urlImage", listEmployee.get(position).getPicture().getLarge());
        startActivity(intent);
    }
}