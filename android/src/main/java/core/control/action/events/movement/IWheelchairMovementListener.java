package com.example.wrappercore.control.action.events.movement;

import com.example.wrappercore.control.action.events.IActionEventListener;

public interface IWheelchairMovementListener extends IActionEventListener {

  void onMovementEvent(MovementEvent movementEvent);
}
