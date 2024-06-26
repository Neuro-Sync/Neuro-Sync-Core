package com.example.wrappercore.control.action.events;

import java.util.EventObject;

public class ActionEvent extends EventObject {

  private final int flag;

  public ActionEvent(Object source, int flag) {
    super(source);
    this.flag = flag;
  }

  public int getFlag() {
    return flag;
  }

  public String toString() {
    return "ActionEvent{" +
        "flag=" + flag +
        '}';
  }
}
