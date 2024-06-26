package com.example.wrappercore.control.action;

import ai.events.aiDetectedMovement.AiDetectedMovementEvent;
import ai.events.aiDetectedMovement.IAiDetectedMovementEventListener;
import com.example.wrappercore.control.action.events.IActionEventListener;
import com.example.wrappercore.control.action.events.movement.MovementEvent;
import com.example.wrappercore.control.action.events.movement.MovementEventHandler;
import com.example.wrappercore.control.blink.BlinkManager;

//TODO: ask more about how the ai will interact with this class
public class ActionManager implements
    IAiDetectedMovementEventListener {

  private final MovementEventHandler movementEventHandler = new MovementEventHandler();
  private final BlinkManager blinkManager;
  private MovementEvent lastMovementEvent;
  private int changeToStopInMillis = 0;

  public ActionManager(BlinkManager blinkManager, int changeToStopInMillis) {
    this.blinkManager = blinkManager;
    this.changeToStopInMillis = changeToStopInMillis;
//    initiateEventScheduler();
  }

//  private void initiateEventScheduler() {
//    new Timer().scheduleAtFixedRate(new TimerTask() {
//      @Override
//      public void run() {
//        if (lastMovementEvent != null) {
//          if (Timestamp.valueOf(lastMovementEvent.getTimestamp().toString()).getTime() + changeToStopInMillis
//              <= System.currentTimeMillis()
//              && lastMovementEvent.getMovementType() != MovementTypes.STOP) {
//            fireEvent(new ActionEvent(this, MovementTypes.STOP.ordinal()));
//          } else {
//            fireEvent(new ActionEvent(this, lastMovementEvent.getFlag()));
//          }
//        } else {
//          Log.w("Control component - Action", "LastMovement is Null");
//        }
//      }
//    }, 0, 1000);
//  }

  public void addListener(IActionEventListener listener) {
    movementEventHandler.addListener(listener);
  }

  public void removeListener(IActionEventListener listener) {
    movementEventHandler.removeListener(listener);
  }

  @Override
  public void onAiDetectedMovementEvent(AiDetectedMovementEvent aiDetectedMovementEvent) {
    //TODO: Make @AhmedMohsen Implement a listener for this in his interface
    this.movementEventHandler.fireAppMovementEvent(new MovementEvent(this, aiDetectedMovementEvent.getFlag()));
  }

//  private void fireEvent(ActionEvent event) {
//    lastMovementEvent = new MovementEvent(this, event.getFlag());
//    if (blinkManager.getLastControlModeType() == ControlModeTypes.WHEELCHAIR_CONTROL) {
//      movementEventHandler.fireWheelchairMovementEvent(lastMovementEvent);
//    } else if (blinkManager.getLastControlModeType() == ControlModeTypes.APP_CONTROL) {
//      movementEventHandler.fireAppMovementEvent(lastMovementEvent);
//    }
//  }

}
