package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Filip on 2017-05-22.
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            WakefulReceiver wakefulReceiver = new WakefulReceiver();
            wakefulReceiver.setAlarm(context);
        }
    }
}
