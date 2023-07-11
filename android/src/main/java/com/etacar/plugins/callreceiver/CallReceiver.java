package com.etacar.plugins.callreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import android.content.BroadcastReceiver;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import com.getcapacitor.JSObject;

class CallReceiver {
    interface TelephonyStatusChangeListener {
        void onTelephonyStatusChanged(JSObject object);
    }

    @Nullable
    private TelephonyStatusChangeListener statusChangeListener = null;
    private BroadcastReceiver receiver;
    private TelephonyManager telephonyManager;
    @Nullable
    private String lastPhoneNumber = null;

    public CallReceiver(Context context) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(@Nullable Context context, @Nullable Intent intent) {
                int state = telephonyManager.getCallState();
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    // TODO: check on devices with API started from 29
                    String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    lastPhoneNumber = phoneNumber;
                    statusChangeListener.onTelephonyStatusChanged(convertResponse(phoneNumber, "ringing"));
                } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                    statusChangeListener.onTelephonyStatusChanged(convertResponse(lastPhoneNumber, "idle"));
                }

            }
        };
    }

    private JSObject convertResponse (@Nullable String phoneNumber, String state) {
        JSObject object = new JSObject();
        object.put("phoneNumber", phoneNumber);
        object.put("state", state);

        return object;
    }

    public void setStatusChangeListener(@Nullable TelephonyStatusChangeListener listener) {
        this.statusChangeListener = listener;
    }

    public TelephonyStatusChangeListener getStatusChangeListener() {
        return statusChangeListener;
    }

    public void startMonitoring (@NonNull AppCompatActivity activity) {
        IntentFilter filter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        activity.registerReceiver(receiver, filter);
    }
    public void stopMonitoring(@NonNull AppCompatActivity activity) {
        activity.unregisterReceiver(receiver);
    }
}