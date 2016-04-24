package com.lawazia.yourid;

import android.content.Context;

public class YourIdRepositoryFactory {
    private static DataSource source = DataSource.Phone ;

    private static YourIdRepository instance;
    public static YourIdRepository Instance(Context context){
        if(instance==null) {
            switch (source) {
                case Server:
                    instance = new YourIdAjaxRepository(context.getApplicationContext());
                    break;
                case Phone:
                    instance = new YourIdSQLiteRepository(context.getApplicationContext());
                    break;
            }
        }
        return instance;
    }
}
