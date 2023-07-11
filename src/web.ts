import { WebPlugin } from '@capacitor/core';

import type { CallReceiverPlugin } from './definitions';

export class CallReceiverWeb extends WebPlugin implements CallReceiverPlugin {
  checkPermissions(): Promise<any> {
    throw new Error('Method not implemented.');
  }
  requestPermissions(): Promise<any> {
    throw new Error('Method not implemented.');
  }
}
