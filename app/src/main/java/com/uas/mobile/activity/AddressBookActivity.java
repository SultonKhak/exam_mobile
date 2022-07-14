package com.uas.mobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.uas.mobile.R;
import com.uas.mobile.adapter.AdapterAddressBook;
import com.uas.mobile.local.data.AppDatabase;
import com.uas.mobile.local.data.dao.AddressDao;
import com.uas.mobile.local.data.entity.AddressBook;

import java.util.ArrayList;
import java.util.List;

public class AddressBookActivity extends AppCompatActivity {

    private List<AddressBook> listAddressBook = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);

        getAddressBook();

    }

    private void getAddressBook() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            final AppDatabase localDb = AppDatabase.getDatabase(getBaseContext());
            final AddressDao addressDao = localDb.addressDao();
            listAddressBook = addressDao.getAddressBook();

            RecyclerView recyclerViewAddressBook = findViewById(R.id.recyclerViewAddressBook);
            recyclerViewAddressBook.setHasFixedSize(true);
            recyclerViewAddressBook.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
            AdapterAddressBook adapterAddressBook = new AdapterAddressBook(getBaseContext(), listAddressBook);
            recyclerViewAddressBook.setAdapter(adapterAddressBook);
            recyclerViewAddressBook.setNestedScrollingEnabled(true);
        });
    }

}