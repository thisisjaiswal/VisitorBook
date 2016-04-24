package com.lawazia.yourid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class YourIdAjaxRepository extends AjaxHelper implements YourIdRepository {

    public YourIdAjaxRepository(Context context) {

    }
    public int addYourInfo(YourInfo sheet){
       return 1;
    }

    public YourInfo getYourInfoByMobile(String mobile) {
         return new YourInfo();
    }
}
