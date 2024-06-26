package com.example.wrappercore.control.blink.events.controlSwitch;

import android.util.Log;
import java.util.ArrayList;

@Deprecated
public class SwitchEventHandler {

  private final ArrayList<ISwitchEventListener> listeners = new ArrayList<ISwitchEventListener>();

  public void addListener(ISwitchEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(ISwitchEventListener listener) {
    listeners.remove(listener);
  }

  public void fireEvent(SwitchEvent event) {
    Log.w("SwitchEventHandler", "fireEvent: " + event.toString());
    for (ISwitchEventListener listener : listeners) {
      listener.onSwitchEvent(event);
    }
  }


}
