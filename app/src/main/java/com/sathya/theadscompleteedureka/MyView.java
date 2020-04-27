package com.sathya.theadscompleteedureka;

import android.util.Log;

public class MyView extends Thread {

    @Override
    public void run() {
        super.run();
        for(int ii = 0 ; ii<= 50 ;ii++){


            Log.d("tag"," in View  "+ii);

             }



    }
}
