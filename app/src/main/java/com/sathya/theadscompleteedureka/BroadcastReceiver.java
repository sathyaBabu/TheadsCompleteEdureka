package com.sathya.theadscompleteedureka;

import android.content.Context;
import android.content.Intent;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // context  AppCompatActivity
        // context will not point to the view....
        // this
       //  context = this;

    }
}
