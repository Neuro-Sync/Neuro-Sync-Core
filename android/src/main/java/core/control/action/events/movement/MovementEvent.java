package com.example.wrappercore.control.action.events.movement;

import com.example.wrappercore.control.action.events.ActionEvent;
import com.example.wrappercore.control.action.events.movement.MovementTypes;
import java.util.HashMap;
import java.util.Map;

public class MovementEvent extends ActionEvent {


  private static final Map<Integer, MovementTypes> INTEGER_MOVEMENT_TYPES_MAP = new HashMap<Integer, MovementTypes>();

  static {
    for (MovementTypes type : MovementTypes.values()) {
      INTEGER_MOVEMENT_TYPES_MAP.put(type.ordinal(), type);
    }
  }

  private final MovementTypes movementType;

  private final long timestamp;

  public MovementEvent(Object source, int flag) {
    super(source, flag);
    this.movementType = INTEGER_MOVEMENT_TYPES_MAP.get(flag);
    this.timestamp = System.currentTimeMillis();
  }

  public MovementTypes getMovementType() {
    return movementType;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String toString() {
    return super.toString() + "MovementEvent{" +
        "movementType=" + movementType +
        ", timestamp=" + timestamp +
        '}';
  }

}
