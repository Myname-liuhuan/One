package com.example.one;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class upDataFragmentViewService extends Service {
    public upDataFragmentViewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

//    @Override
//    public int onStartCommand(Intent intent,int flags,int startId){
//        super.onStartCommand(intent,flags,startId);
//
//    }
}
