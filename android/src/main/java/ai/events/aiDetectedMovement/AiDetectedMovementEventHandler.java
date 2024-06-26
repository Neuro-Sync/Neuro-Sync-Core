package ai.events.aiDetectedMovement;

import java.util.ArrayList;

public class AiDetectedMovementEventHandler {

  private final ArrayList<IAiDetectedMovementEventListener> listeners = new ArrayList<IAiDetectedMovementEventListener>();

  public void addListener(IAiDetectedMovementEventListener listener) {
    if (!this.listeners.contains(listener)) {
      this.listeners.add(listener);
    }
  }

  public void removeListener(IAiDetectedMovementEventListener listener) {
    this.listeners.remove(listener);
  }

  public void fireEvent(AiDetectedMovementEvent event) {
    for (IAiDetectedMovementEventListener listener : listeners) {
      listener.onAiDetectedMovementEvent(event);
    }
  }

}
