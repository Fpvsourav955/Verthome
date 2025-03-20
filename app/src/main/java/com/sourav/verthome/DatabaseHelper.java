package com.sourav.verthome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class DatabaseHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String DATABASE_NAME = "vert_home1.db";
    private static final int DATABASE_VERSION = 1;

   public static final String TABLE_NAME = "my_library";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
   public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_HOST = "host_id";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_HOST + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addDetail(String title, String address, int host_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_HOST, host_id);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Add Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);

        }

        return cursor;
    }

    boolean updateData(String row_id, String title, String address, String host_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_ADDRESS, address);


        try {
            int hostIdInt = Integer.parseInt(host_id);
            cv.put(COLUMN_HOST, hostIdInt);
        } catch (NumberFormatException e) {

            Toast.makeText(context, "Invalid host_id format", Toast.LENGTH_SHORT).show();
            return false;
        }


        int result = db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});
        if (result > 0) {

            Toast.makeText(context, "Successfully Updated.", Toast.LENGTH_SHORT).show();
            db.close();
            return true;
        } else {

            Toast.makeText(context, "Failed To Update.", Toast.LENGTH_SHORT).show();
            db.close();
            return false;
        }
    }
void deleteOneRow(String raw_id){
        SQLiteDatabase db= this.getWritableDatabase();
       long result= db.delete(TABLE_NAME,"id=?",new String[]{raw_id});
       if (result==-1){
           Toast.makeText(context, "Failed To Delete   ", Toast.LENGTH_SHORT).show();
       }
       else {
           Toast.makeText(context, "Delete Successfully.", Toast.LENGTH_SHORT).show();
       }
}


}
