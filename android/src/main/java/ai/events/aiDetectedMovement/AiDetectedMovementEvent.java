package ai.events.aiDetectedMovement;

import com.example.wrappercore.control.action.events.ActionEvent;

public class AiDetectedMovementEvent extends ActionEvent {


  private final long timestamp;

  public AiDetectedMovementEvent(Object source, int flag) {
    super(source, flag);
    this.timestamp = System.currentTimeMillis();
  }

  public String toString() {
    return "AI Movement AiDetectedMovementEvent{" +
        ", timestamp=" + timestamp +
        '}';
  }

}
