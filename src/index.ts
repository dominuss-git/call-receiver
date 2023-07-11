import { registerPlugin } from '@capacitor/core';

import type { CallReceiverPlugin } from './definitions';

const CallReceiver = registerPlugin<CallReceiverPlugin>('CallReceiver', {
  web: () => import('./web').then(m => new m.CallReceiverWeb()),
});

export * from './definitions';
export { CallReceiver };
