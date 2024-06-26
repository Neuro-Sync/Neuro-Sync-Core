package com.example.wrappercore.control.blink.events.controlSwitch;

import java.time.LocalDateTime;
import java.util.EventObject;

@Deprecated
public class SwitchEvent extends EventObject {

  private final ControlModeTypes controlModeType;

  private final LocalDateTime timestamp = LocalDateTime.now();

  public SwitchEvent(Object source, ControlModeTypes controlModeType) {
    super(source);
    this.controlModeType = controlModeType;
  }

  public ControlModeTypes getControlModeType() {
    return controlModeType;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public String toString() {
    return "SwitchEvent{" +
        "controlModeType=" + controlModeType +
        ", timestamp=" + timestamp +
        '}';
  }
}
