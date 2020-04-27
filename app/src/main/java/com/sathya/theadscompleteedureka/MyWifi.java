package com.sathya.theadscompleteedureka;

import android.util.Log;

public class MyWifi extends Thread {

    @Override
    public void run() {
        super.run();
        for(int ii = 0 ; ii<= 50 ;ii++){


            Log.d("tag"," in wifi  "+ii);

        }



    }
}
