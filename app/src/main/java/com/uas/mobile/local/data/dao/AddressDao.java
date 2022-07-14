package com.uas.mobile.local.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.uas.mobile.local.data.entity.AddressBook;

import java.util.List;

@Dao
public interface AddressDao {

    @Insert
    long postAddress(AddressBook addressBook);

    @Query("SELECT * FROM address_book")
    List<AddressBook> getAddressBook();

}
