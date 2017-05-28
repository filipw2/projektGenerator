package receiver;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.projekt.generator.MainActivity;
import com.example.projekt.generator.PreferencesFragment;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static com.example.projekt.generator.R.string.generator;

/**
 * Created by Filip on 2017-05-22.
 */

public class WakefulReceiver extends BroadcastReceiver {

    private AlarmManager mAlarmManager;
    private SharedPreferences sp;
    private Boolean enable;

    @Override
    public void onReceive(Context context, Intent intent) {
        setNotification(context);

            setAlarm(context);
    }

    public void setAlarm(Context context){
        sp  = PreferenceManager.getDefaultSharedPreferences(context);
        enable=true;

        try {
            enable=sp.getBoolean("pref_enable", true);
        }catch(Exception e){
            Log.e("pref",e.toString());
        }
        if(!enable)
            return;

        Date currentDate = new Date();
        sp  = PreferenceManager.getDefaultSharedPreferences(context);
        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);


        int days = 30;//

        try {
            days = Integer.parseInt(sp.getString("days","30"));

        }catch (Exception e){
            Log.e("pref",e.toString());
        }

        c.add(Calendar.DAY_OF_YEAR,days);

        // Define our intention of executing AlertReceiver
        Intent alertIntent = new Intent(context, WakefulReceiver.class);

        // Allows you to schedule for your application to do something at a later date
        // even if it is in he background or isn't active
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // set() schedules an alarm to trigger
        // Trigger for alertIntent to fire in 5 seconds
        // FLAG_UPDATE_CURRENT : Update the Intent if active
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                PendingIntent.getBroadcast(context, 1, alertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT));



        ComponentName receiver = new ComponentName(context, receiver.BootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
               PackageManager.DONT_KILL_APP);

    }



    public void cancelAlarm(Context context){
        Log.d("WakefulAlarmReceiver", "{cancelAlarm}");

        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, WakefulReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        mAlarmManager.cancel(alarmIntent);


        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }


    public void setNotification(Context context){
        CharSequence title = "Alarm";

        int icon = android.R.drawable.ic_dialog_alert;

        CharSequence text = "Pora zmienic haslo";


        PendingIntent notificIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setContentText(text);

        // Defines the Intent to fire when the notification is clicked
        mBuilder.setContentIntent(notificIntent);

        // Auto cancels the notification when clicked on in the task bar
        mBuilder.setAutoCancel(true);

        // Gets a NotificationManager which is used to notify the user of the background event
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Post the notification
        mNotificationManager.notify(1, mBuilder.build());
    }
}
