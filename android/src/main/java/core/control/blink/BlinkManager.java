package com.example.wrappercore.control.blink;

import android.util.Log;
import com.example.wrappercore.control.blink.events.IBlinkEventListener;
import com.example.wrappercore.control.blink.events.click.ClickEvent;
import com.example.wrappercore.control.blink.events.click.ClickEventHandler;
import com.example.wrappercore.control.blink.events.click.IClickEventListener;
import com.example.wrappercore.control.blink.events.controlSwitch.ControlModeTypes;
import com.example.wrappercore.control.blink.events.controlSwitch.ISwitchEventListener;
import com.example.wrappercore.control.blink.events.controlSwitch.SwitchEvent;
import com.example.wrappercore.control.blink.events.controlSwitch.SwitchEventHandler;
import headset.events.nskAlgo.algoBlink.AlgoBlinkEvent;
import headset.events.nskAlgo.algoBlink.IAlgoBlinkEventListener;
import java.util.ArrayList;

public class BlinkManager implements IAlgoBlinkEventListener {

  private final SwitchEventHandler switchEventHandler = new SwitchEventHandler();
  private final ClickEventHandler clickEventHandler = new ClickEventHandler();
  private final ControlModeTypes lastControlModeType = ControlModeTypes.APP_CONTROL;
  private final int blinkDetectionThreshold;
  //  private final int blinksToSwitch;
  private final int blinksToClick;

  private ArrayList<AlgoBlinkEvent> blinksList = new ArrayList<>();
  private int blinksCounter = 0;

  public BlinkManager(int blinkSensitivityThreshold, int blinksToClick) {
    this.blinkDetectionThreshold = blinkSensitivityThreshold;
//    this.blinksToSwitch = blinksToSwitch;
    this.blinksToClick = blinksToClick;
//    initiateEventScheduler();
  }

  public void addListener(IBlinkEventListener listener) {
    if (listener instanceof IClickEventListener) {
      clickEventHandler.addListener((IClickEventListener) listener);
    } else if (listener instanceof ISwitchEventListener) {
      switchEventHandler.addListener((ISwitchEventListener) listener);
    } else {
      throw new IllegalArgumentException("Invalid Listener Type");
    }
  }

  public void removeListener(IBlinkEventListener listener) {
    if (listener instanceof IClickEventListener) {
      clickEventHandler.removeListener((IClickEventListener) listener);
    } else if (listener instanceof ISwitchEventListener) {
      switchEventHandler.removeListener((ISwitchEventListener) listener);
    } else {
      throw new IllegalArgumentException("Invalid Listener Type");
    }
  }

  @Override
  public void onBlink(AlgoBlinkEvent event) {
    //TODO: Discuss this logic
    if (event.getBlinkData().strength() > blinkDetectionThreshold) {
      countBlink();
      addBlinkToClickList(event);
    }
  }

  //FIXME: This method is not used in the current implementation
//  private void initiateEventScheduler() {
//    new Timer().scheduleAtFixedRate(new TimerTask() {
//      @Override
//      public void run() {
//        //FIXME: replace all conditions with equality but after process testing
//        if (blinksCounter == blinksToClick) {
//          fireClickEvent();
//        } else if (blinksCounter == blinksToSwitch) {
//          fireSwitchModeEvent();
//        } else {
//          blinksCounter = 0;
//        }
//      }
//    }, 0, 3000);
//  }

  private void addBlinkToClickList(AlgoBlinkEvent event) {
    this.blinksList.add(event);

    for (AlgoBlinkEvent blink : this.blinksList) {
      if (event.getBlinkTimeStamp() - blink.getBlinkTimeStamp() > blinksToClick * 1000) {
        this.blinksList.remove(blink);
      }
    }

    //FIXME: due to the current state of the app, we only need to check for click event
    //       but we need to discuss this with the team
    //if (this.blinksList.size() == blinksToClick) {
    if (this.blinksList.size() >= blinksToClick) {
      fireClickEvent();
      this.blinksList.clear();
    }
  }

  public ControlModeTypes getLastControlModeType() {
    return lastControlModeType;
  }

  private void fireClickEvent() {
    Log.i("Control Component", "Click Event Fired");
    clickEventHandler.fireEvent(new ClickEvent(this));
  }

  private void fireSwitchModeEvent() {
    ControlModeTypes controlModeType =
        lastControlModeType == ControlModeTypes.APP_CONTROL ? ControlModeTypes.WHEELCHAIR_CONTROL
            : ControlModeTypes.APP_CONTROL;
    switchEventHandler.fireEvent(new SwitchEvent(this, controlModeType));
  }

  private void countBlink() {
    blinksCounter++;
    Log.w("Control Component", "Blink Was Added count is:" + blinksCounter);
  }
}
