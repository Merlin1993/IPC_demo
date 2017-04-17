package ipc.demo.merlin.ipc_demo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import ipc.demo.merlin.ipc_demo.db.IpcDatabaseHelper;

public class ExternalContentProvider extends ContentProvider {
    public static final int USER_DIR=0;
    public static final int USER_ITEM=1;

    public static final String AUTHORITY="ipc.demo.merlin.ipc_demo_provider";//地址，要与注册文件一致

    private static UriMatcher uriMatcher;

    private IpcDatabaseHelper databaseHelper;

    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"user",USER_DIR);//标志取所有的值
        uriMatcher.addURI(AUTHORITY,"user/*",USER_ITEM);//标识根据后面的值取值
    }

    public ExternalContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //照着写，不需要改动
    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                return "vnd.android.cursor.dir/vnd.ipc.demo.merlin.ipc_demo.user";
            case USER_ITEM:
                return "vnd.android.cursor.item/vnd.ipc.demo.merlin.ipc_demo.user";
        };
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        databaseHelper=new IpcDatabaseHelper(getContext(),null);
        return true;
    }

    //读取
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor=null;
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                cursor=db.query("user",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case USER_ITEM:
                String name=uri.getPathSegments().get(1);
                cursor=db.query("user",projection,"name=?",new String [] { name },null,null,sortOrder);
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
