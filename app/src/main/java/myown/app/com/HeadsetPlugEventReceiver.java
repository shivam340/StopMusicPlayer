package myown.app.com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Receive event when headset state change. (plugged or unplugged).
 * <p/>
 * Created by shivam on 10/1/15.
 */
public class HeadsetPlugEventReceiver extends BroadcastReceiver {

    private static final String TAG = HeadsetPlugEventReceiver.class.getSimpleName();
    private static final String MUSIC_SERVICE = "com.android.music.musicservicecommand";
    private static final String COMMAND = "command";
    private static final String PAUSE = "pause";


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "Headset state changed detected.");

        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {

            int state = intent.getIntExtra("state", -1);

            switch (state) {
                case 0:
                    Log.d(TAG, "Headset is unplugged");
                    Toast.makeText(context, "Headset is unplugged", Toast.LENGTH_SHORT).show();

                    AudioManager audioManager = (AudioManager) context.getSystemService(Context
                            .AUDIO_SERVICE);

                    //trick1 send broadcast to all running music player to stop.
                    stopMusicPlayer(context, audioManager);

                    //trick2 programmatically mute the audio.
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);

                    break;
                case 1:
                    Log.d(TAG, "Headset is plugged");
                    Toast.makeText(context, "Headset is plugged", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Log.d(TAG, "I have no idea what the headset state is");
                    Toast.makeText(context, "I have no idea what the headset state is", Toast
                            .LENGTH_SHORT).show();
            }

        }

    }

    private void stopMusicPlayer(Context context, AudioManager audioManager) {

        if (audioManager.isMusicActive()) {

            Intent intent = new Intent(MUSIC_SERVICE);
            intent.putExtra(COMMAND, PAUSE);
            context.sendBroadcast(intent);
        }
    }

}
