package com.benboer.boluo.factory.model.db;

import com.grosner.dbflow.annotation.Database;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "AppDatabase";

    public static final int VERSION = 1;
}
