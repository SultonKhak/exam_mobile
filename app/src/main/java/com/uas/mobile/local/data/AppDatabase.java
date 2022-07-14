package com.uas.mobile.local.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.uas.mobile.local.data.dao.AddressDao;
import com.uas.mobile.local.data.entity.AddressBook;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AddressBook.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract AddressDao addressDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            "uas_mobile"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }

}
