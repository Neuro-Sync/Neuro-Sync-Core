package com.example.wrappercore.control.blink.events.controlSwitch;

import com.example.wrappercore.control.blink.events.IBlinkEventListener;

@Deprecated
public interface ISwitchEventListener extends IBlinkEventListener {

  void onSwitchEvent(SwitchEvent event);
}
