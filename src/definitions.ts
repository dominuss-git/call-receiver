import type { PluginListenerHandle, PermissionState } from '@capacitor/core';

export interface CallReceiverPlugin {
//   echo(options: { value: string }): Promise<{ value: string }>;
    // getStatus(): Promise<TelephonyStatus>;

    addListener(
        eventName: 'telephonyStatusChange',
        listenerFunc: TelephonyStatusChangeListener,
      ): Promise<PluginListenerHandle> & PluginListenerHandle;

    removeAllListeners(): Promise<void>;

    checkPermissions(): Promise<{ telephony: PermissionState }>;

    requestPermissions(): Promise<{ telephony: PermissionState }>;
}

export type TelephonyStatusChangeListener = (status: TelephonyState) => void;

// export interface TelephonyStatus {
//   connectionStatus: TelephonyStatus;
// }

export interface TelephonyStateRinging {
  phoneNumber: string,
  state: TelephonyStatus.RINGING,
}

export interface TelephonyStateIdle {
  phoneNumber: string | null,
  state: TelephonyStatus.IDLE,
}

export type TelephonyState = TelephonyStateIdle | TelephonyStateRinging;

export enum TelephonyStatus {
  RINGING = 'ringing',
  IDLE = 'idle'
};