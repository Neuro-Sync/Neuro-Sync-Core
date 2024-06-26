package com.example.wrappercore.control.blink.events.click;

import com.example.wrappercore.control.blink.events.IBlinkEventListener;

public interface IClickEventListener extends IBlinkEventListener {

  void onClickEvent(ClickEvent event);
}
