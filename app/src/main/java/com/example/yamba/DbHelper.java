package com.example.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();

    // Constructor
    public DbHelper(Context context) {
        super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
    }

    // Se llama para crear la tabla
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format(
                "create table %s (%s int primary key, %s text, %s text, %s int)",
                StatusContract.TABLE,
                StatusContract.Column.ID,
                StatusContract.Column.USER,
                StatusContract.Column.MESSAGE,
                StatusContract.Column.CREATED_AT);
        Log.d(TAG, "onCreate con SQL: " + sql);
        db.execSQL(sql);
    }

    // Se llama siempre que se tenga una nueva version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aqui irían las sentencias de tipo ALTER TABLE, lo hacemos mas sencillo...
        // Borramos la vieja base de datos...
        db.execSQL("drop table if exists " + StatusContract.TABLE);

        // ... y creamos una base de datos nueva
        onCreate(db);
        Log.d(TAG, "onUpgrade");
    }

}
