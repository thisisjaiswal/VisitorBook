package com.lawazia.visitorbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VisitorSQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "VisitorDB";
    private static final String TABLE_SHEETS = "sheets";
    private static final String TABLE_ENTRIES = "entries";

    private static VisitorSQLiteHelper visitorDB;
    public static VisitorSQLiteHelper VisitorDB(Context context){
        if(visitorDB==null)
            visitorDB = new VisitorSQLiteHelper(context.getApplicationContext());
        return visitorDB;
    }

    public VisitorSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SHEETS_TABLE = "CREATE TABLE "
                + TABLE_SHEETS + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "bookId INTEGER, " +
                "created DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "name TEXT )";
        db.execSQL(CREATE_SHEETS_TABLE);

        String CREATE_ENTRIES_TABLE = "CREATE TABLE "
                + TABLE_ENTRIES + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "sheetId INTEGER, " +
                "yourName TEXT, " +
                "yourMobile TEXT )";
        db.execSQL(CREATE_ENTRIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHEETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);
        this.onCreate(db);
    }

    public int addSheet(Sheet sheet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bookId", sheet.getBookId());
        values.put("name", sheet.getName());
        int sheetId = (int) db.insert(TABLE_SHEETS, null, values);
        db.close();
        return sheetId;
    }

    public Sheet getSheet(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SHEETS, new String[]{"id", "bookId", "created", "name"},
                " id = ?", new String[]{ String.valueOf(id) },
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        return createSheet(cursor);
    }

    public List<Sheet> getAllSheets(int bookId)  {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_SHEETS, new String[]{"id", "bookId", "created", "name"},
                " bookId = ?", new String[]{ String.valueOf(bookId) },
                null, null, null, null);

        List<Sheet> sheets = new ArrayList<Sheet>();
        if (cursor.moveToFirst()) {
            do {
                sheets.add(createSheet(cursor));
            } while (cursor.moveToNext());
        }

        return sheets;
    }

    private Sheet createSheet(Cursor cursor) {
        Sheet sheet = new Sheet();
        sheet.setId(Integer.parseInt(cursor.getString(0)));
        sheet.setBookId(Integer.parseInt(cursor.getString(1)));
        try {
            sheet.setCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(cursor.getString(2)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sheet.setName(cursor.getString(3));
        return sheet;
    }

    public void addEntry(Entry entry){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sheetId", entry.getSheetId());
        values.put("yourName", entry.getYourName());
        values.put("yourMobile", entry.getYourMobile());
        db.insert(TABLE_ENTRIES, null, values);
        db.close();
    }

    public Entry getEntry(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ENTRIES, new String[]{"id", "sheetId", "yourName", "yourMobile"},
                " id = ?", new String[]{ String.valueOf(id) },
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        return createEntry(cursor);
    }

    public boolean entryExists(int sheetId, String mobile){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ENTRIES, new String[]{"id", "sheetId", "yourName", "yourMobile"},
                " sheetId = ? AND yourMobile = ?", new String[]{ String.valueOf(sheetId), String.valueOf(mobile) },
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0)
            cursor.moveToFirst();
        else
            return false;

        return createEntry(cursor) != null;
    }

    public List<Entry> getAllEntries(int sheetId) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_ENTRIES, new String[]{"id", "sheetId", "yourName", "yourMobile"},
                " sheetId = ?", new String[]{ String.valueOf(sheetId) },
                null, null, null, null);

        List<Entry> entries = new ArrayList<Entry>();
        if (cursor.moveToFirst()) {
            do {
                entries.add(createEntry(cursor));
            } while (cursor.moveToNext());
        }

        return entries;
    }

    private Entry createEntry(Cursor cursor) {
        Entry entry = new Entry();
        entry.id = Integer.parseInt(cursor.getString(0));
        entry.sheetId = Integer.parseInt(cursor.getString(1));
        entry.yourName = cursor.getString(2);
        entry.yourMobile = cursor.getString(3);
        return entry;
    }
}
