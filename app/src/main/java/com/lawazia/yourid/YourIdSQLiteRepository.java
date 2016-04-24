package com.lawazia.yourid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class YourIdSQLiteRepository extends SQLiteOpenHelper implements YourIdRepository {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "YourDB";
    private static final String TABLE_YOUR_INFO = "yourInfo";



    public YourIdSQLiteRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_YOUR_INFO_TABLE = "CREATE TABLE "
                + TABLE_YOUR_INFO + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mobile TEXT, " +
                "pin TEXT, " +
                "name TEXT )";
        db.execSQL(CREATE_YOUR_INFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_YOUR_INFO);
        this.onCreate(db);
    }

    public int addYourInfo(YourInfo sheet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mobile", sheet.getMobile());
        values.put("pin", sheet.getPin());
        values.put("name", sheet.getName());
        int sheetId = (int) db.insert(TABLE_YOUR_INFO, null, values);
        db.close();
        return sheetId;
    }

    public YourInfo getYourInfoByMobile(String mobile) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_YOUR_INFO, new String[]{"id", "mobile", "pin", "name"},
                " mobile = ?", new String[]{ String.valueOf(mobile) },
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0)
            cursor.moveToFirst();
        else
            return null;

        YourInfo sheet = new YourInfo();
        sheet.setId(Integer.parseInt(cursor.getString(0)));
        sheet.setMobile(cursor.getString(1));
        sheet.setPin(cursor.getString(2));
        sheet.setName(cursor.getString(3));
        return sheet;
    }
}
