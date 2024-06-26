package com.example.wrappercore.control.blink.events.click;

import java.util.EventObject;

public class ClickEvent extends EventObject {

  public ClickEvent(Object source) {
    super(source);
  }

  public String toString() {
    return "ClickEvent";
  }
}
