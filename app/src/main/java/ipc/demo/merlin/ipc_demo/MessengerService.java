package ipc.demo.merlin.ipc_demo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class MessengerService extends Service {

    public Messenger messenger=new Messenger(new messengerHandler());

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public class messengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Messenger replyMessenger=msg.replyTo;

            Message message=Message.obtain(null,0);
            Bundle bundle=new Bundle();
            bundle.putString("reply","suceess");
            message.setData(bundle);

            try{
                replyMessenger.send(message);
            }catch (RemoteException ex){
                ex.printStackTrace();
            }
        }
    }

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return messenger.getBinder();
    }
}
