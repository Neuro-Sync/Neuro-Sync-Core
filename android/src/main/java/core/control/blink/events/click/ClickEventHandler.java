package com.example.wrappercore.control.blink.events.click;

import android.util.Log;
import java.util.ArrayList;

public class ClickEventHandler {

  private final ArrayList<IClickEventListener> listeners = new ArrayList<IClickEventListener>();

  public void addListener(IClickEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IClickEventListener listener) {
    listeners.remove(listener);
  }

  public void fireEvent(ClickEvent event) {
    Log.w("ClickEventHandler", "fireEvent: " + event.toString());
    for (IClickEventListener listener : listeners) {
      listener.onClickEvent(event);
    }
  }
}
