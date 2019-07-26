package top.codefuturesql.loginandregi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUtil {
    public static MyDatabaseHelper dbHelper;
    public static void createDatabase(){
        dbHelper.getWritableDatabase();
    }
    public static void insertData(String username,String password){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into account(username,password)values(?,?)",
                new String[]{username,password});
    }
    public static void deleteData(String username){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from account where username = ?",
                new String[]{username});
    }
    public static boolean isExist(String username){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select username from account where username = ?",
                new String[]{username});
        if (cursor.moveToNext()){
            cursor.close();
            return true;
        }
        else{
            cursor.close();
            return false;
        }

    }
}
