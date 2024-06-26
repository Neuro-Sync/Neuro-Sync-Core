package com.example.wrappercore.control;

import com.example.wrappercore.control.action.ActionManager;
import com.example.wrappercore.control.action.events.IActionEventListener;
import com.example.wrappercore.control.blink.BlinkManager;
import com.example.wrappercore.control.blink.events.IBlinkEventListener;
import java.util.EventListener;

public class ControlManager {

  private final BlinkManager blinkManager;

  private final ActionManager actionManager;

  public ControlManager() {
    blinkManager = new BlinkManager(0, 3);
    actionManager = new ActionManager(blinkManager, 5000);
  }

  public void addListener(EventListener listener) {
    if (listener instanceof IActionEventListener) {
      actionManager.addListener((IActionEventListener) listener);
    } else if (listener instanceof IBlinkEventListener) {
      blinkManager.addListener((IBlinkEventListener) listener);
    } else {
      throw new IllegalArgumentException("Invalid Listener Type");
    }
  }

  public void removeListener(EventListener listener) {
    if (listener instanceof IActionEventListener) {
      actionManager.removeListener((IActionEventListener) listener);
    } else if (listener instanceof IBlinkEventListener) {
      blinkManager.removeListener((IBlinkEventListener) listener);
    } else {
      throw new IllegalArgumentException("Invalid Listener Type");
    }
  }

  public BlinkManager getBlinkManager() {
    return blinkManager;
  }

  public ActionManager getActionManager() {
    return actionManager;
  }
}
