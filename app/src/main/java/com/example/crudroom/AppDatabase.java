package com.example.crudroom;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.crudroom.data.BarangDAO;
import com.example.crudroom.model.Barang;

@Database(entities = {Barang.class}, version = 1)public abstract class AppDatabase extends RoomDatabase {
    public abstract BarangDAO barangDAO();
}