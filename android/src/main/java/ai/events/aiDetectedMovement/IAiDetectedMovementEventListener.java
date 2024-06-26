package ai.events.aiDetectedMovement;

import java.util.EventListener;

public interface IAiDetectedMovementEventListener extends EventListener {

  void onAiDetectedMovementEvent(AiDetectedMovementEvent event);
}
