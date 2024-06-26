package com.example.wrappercore.control.action.events.movement;

import android.util.Log;
import com.example.wrappercore.control.action.events.IActionEventListener;

public class MovementEventHandler {

  private IAppMovementListener appMovementListener;
  private IWheelchairMovementListener wheelchairMovementListener;

  public void addListener(IActionEventListener listener) {
    if (listener instanceof IAppMovementListener) {
      appMovementListener = (IAppMovementListener) listener;
    } else if (listener instanceof IWheelchairMovementListener) {
      wheelchairMovementListener = (IWheelchairMovementListener) listener;
    }
  }

  public void removeListener(IActionEventListener listener) {
    if (listener instanceof IAppMovementListener) {
      appMovementListener = null;
    } else if (listener instanceof IWheelchairMovementListener) {
      wheelchairMovementListener = null;
    }
  }

  public void fireAppMovementEvent(MovementEvent movementEvent) {
    Log.w("MovementEventHandler", "fireAppMovementEvent: " + movementEvent.toString());
    if (appMovementListener != null) {
      appMovementListener.onMovementEvent(movementEvent);
    } else {
      throw new IllegalStateException("No listener found for MovementEvent in App.");
    }
  }

  public void fireWheelchairMovementEvent(MovementEvent movementEvent) {
    Log.w("MovementEventHandler", "fireWheelchairMovementEvent: " + movementEvent.toString());
    if (wheelchairMovementListener != null) {
      wheelchairMovementListener.onMovementEvent(movementEvent);
    } else {
      throw new IllegalStateException("No listener found for MovementEvent in Wheelchair.");
    }
  }


}
