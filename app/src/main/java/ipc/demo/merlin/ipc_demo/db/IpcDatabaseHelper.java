package ipc.demo.merlin.ipc_demo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ipc.demo.merlin.ipc_demo.entity.User;

/**
 * Created by ShengNianzu on 2017/4/14.
 */

public class IpcDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    private static final String CREAT_USER="create table user ("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"sex text)";
    private static final String DB_NAME="UserStore.db";
    private boolean is_start=false;
    private static final int version=1;
    SQLiteDatabase db;


    public IpcDatabaseHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREAT_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean startRead(){
        db=this.getReadableDatabase();
        is_start=true;
        return true;
    }

    public boolean endRead(){
        db.close();
        is_start=false;
        return false;
    }

    public boolean insertUser(User user){
        if(is_start){
            ContentValues values=new ContentValues();
            values.put("name",user.getName());
            values.put("sex",user.getSex());
            if(db.insert("user",null,values)==0){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }
}
