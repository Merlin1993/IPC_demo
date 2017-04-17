package ipc.demo.merlin.ipc_demo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ipc.demo.merlin.ipc_demo.db.IpcDatabaseHelper;
import ipc.demo.merlin.ipc_demo.entity.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SercviceAidlInterface remoteService;
    private Button bind_btn;
    private Button save_btn;
    private IpcDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent =new Intent(this,RemoteService.class);
        bindService(intent,conn, Context.BIND_AUTO_CREATE);

        bind_btn=((Button)findViewById(R.id.bind_btn));
        save_btn=(Button)findViewById(R.id.save_btn);
        bind_btn.setOnClickListener(this);
        save_btn.setOnClickListener(this);

        databaseHelper=new IpcDatabaseHelper(this,null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_btn:
            try {
                bind_btn.setText(remoteService.getString());
            } catch (Exception ex) {
                bind_btn.setText("fail");
            }
            break;
            case R.id.save_btn:
                databaseHelper.startRead();
                if(databaseHelper.insertUser(new User("小明","男"))){
                    save_btn.setText("success");
                }else{
                    save_btn.setText("fail");
                }
                databaseHelper.endRead();
                break;
        };
    }

    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            remoteService=SercviceAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
