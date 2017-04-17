package ipc.demo.merlin.ipc_demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

public class RemoteService extends Service {
    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    Binder binder=new SercviceAidlInterface.Stub(){
        @Override
        public String getString() throws RemoteException {
            return "success";
        }
    };
}
