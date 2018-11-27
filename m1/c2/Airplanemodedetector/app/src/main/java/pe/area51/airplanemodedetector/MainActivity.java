package pe.area51.airplanemodedetector;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AirplaneModeBroadcastReceiver.OnAirplaneModeChangedBroadcastReceivedListener {

    private TextView textViewAirplaneModeStatus;
    private AirplaneModeBroadcastReceiver airplaneModeBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewAirplaneModeStatus = (TextView) findViewById(R.id.textview_airplanemode_status);
        airplaneModeBroadcastReceiver = new AirplaneModeBroadcastReceiver(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTextViewAirplaneModeStatus(isAirplaneModeOn());
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(airplaneModeBroadcastReceiver);
    }

    private void setTextViewAirplaneModeStatus(boolean isAirplaneModeOn) {
        if (isAirplaneModeOn) {
            textViewAirplaneModeStatus.setText(R.string.airplane_mode_on);
        } else {
            textViewAirplaneModeStatus.setText(R.string.airplane_mode_off);
        }
    }

    private boolean isAirplaneModeOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
        } else {
            return Settings.System.getInt(getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
        }
    }

    @Override
    public void onAirplaneModeChanged(boolean isAirplaneModeOn) {
        setTextViewAirplaneModeStatus(isAirplaneModeOn);
    }
}
