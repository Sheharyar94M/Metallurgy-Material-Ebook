package com.qsa.metallurgy_material_engineering_premium;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Booksdb.class},version = 1)
public abstract class Myappdatabas extends RoomDatabase {

    public abstract myDao myDao();
}
