package com.etacar.plugins.callreceiver;

import android.Manifest;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;

@CapacitorPlugin(
    name = "CallReceiver",
    permissions = {
        @Permission(
            strings = { Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG },
            alias = "telephony"
        )
    }
)
public class CallReceiverPlugin extends Plugin {
    private CallReceiver implementation;
    String TELEPHONY_EVENT = "telephonyStatusChange";

    @Override
     public void load() {
        super.load();
        implementation = new CallReceiver(getContext());
        implementation.setStatusChangeListener(this::updateTelephonyStatus);
    }

    @Override
    protected void handleOnDestroy() {
        implementation.stopMonitoring(getActivity());
        implementation.setStatusChangeListener(null);
    }

    @Override
    protected void handleOnResume() {
        implementation.startMonitoring(getActivity());
    }

    @Override
    protected void handleOnPause() {
//        implementation.stopMonitoring(getActivity());
    }

    private void updateTelephonyStatus(JSObject object) {
        notifyListeners(TELEPHONY_EVENT, object);
    }
}