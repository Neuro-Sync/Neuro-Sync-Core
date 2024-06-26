package com.example.wrappercore.control.action.events.movement;


import com.example.wrappercore.control.action.events.IActionEventListener;
import com.example.wrappercore.control.action.events.movement.MovementEvent;
public interface IAppMovementListener extends IActionEventListener {

  void onMovementEvent(
      MovementEvent movementEvent);
}
