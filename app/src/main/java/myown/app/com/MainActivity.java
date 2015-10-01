package myown.app.com;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private HeadsetPlugEventReceiver mHeadsetPlugEventReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // after registering same thing in manifest it was not receiving event,
        // so added here and it works.
        IntentFilter receiverFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        mHeadsetPlugEventReceiver = new HeadsetPlugEventReceiver();
        registerReceiver(mHeadsetPlugEventReceiver, receiverFilter);


    }


    @Override
    protected void onPause() {
        super.onPause();

        if( mHeadsetPlugEventReceiver != null && mHeadsetPlugEventReceiver.isOrderedBroadcast()){
            unregisterReceiver(mHeadsetPlugEventReceiver);
        }

    }
}
