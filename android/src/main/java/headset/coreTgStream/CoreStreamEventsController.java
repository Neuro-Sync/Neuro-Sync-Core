package headset.coreTgStream;

import android.util.Log;
import headset.events.stream.StreamEvent;
import headset.events.stream.streamAttention.IStreamAttentionEventListener;
import headset.events.stream.streamAttention.StreamAttentionEvent;
import headset.events.stream.streamAttention.StreamAttentionEventHandler;
import headset.events.stream.streamBandPower.IStreamBandPowerEventListener;
import headset.events.stream.streamBandPower.StreamBandPowerEvent;
import headset.events.stream.streamBandPower.StreamBandPowerHandler;
import headset.events.stream.streamMeditation.IStreamMeditationEventListener;
import headset.events.stream.streamMeditation.StreamMeditationEvent;
import headset.events.stream.streamMeditation.StreamMeditationEventHandler;
import headset.events.stream.streamRaw.IStreamRawDataEventListener;
import headset.events.stream.streamRaw.StreamRawDataEvent;
import headset.events.stream.streamRaw.StreamRawDataEventHandler;
import headset.events.stream.streamSignalQuality.IStreamSignalQualityEventListener;
import headset.events.stream.streamSignalQuality.StreamSignalQualityEvent;
import headset.events.stream.streamSignalQuality.StreamSignalQualityEventHandler;
import java.util.EventListener;


public class CoreStreamEventsController {

  private final StreamSignalQualityEventHandler streamSignalQualityEventHandler;
  private final StreamAttentionEventHandler streamAttentionEventHandler;
  private final StreamMeditationEventHandler streamMeditationEventHandler;
  private final StreamRawDataEventHandler streamRawDataEventHandler;
  private final StreamBandPowerHandler streamBandPowerHandler;


  CoreStreamEventsController() {
    this.streamSignalQualityEventHandler = new StreamSignalQualityEventHandler();
    this.streamAttentionEventHandler = new StreamAttentionEventHandler();
    this.streamMeditationEventHandler = new StreamMeditationEventHandler();
    this.streamRawDataEventHandler = new StreamRawDataEventHandler();
    this.streamBandPowerHandler = new StreamBandPowerHandler();
  }

  public void fireEvent(StreamEvent event) {
    boolean isEventFired = false;
    if (event instanceof StreamAttentionEvent) {
      this.streamAttentionEventHandler.fireEvent((StreamAttentionEvent) event);
      isEventFired = true;
    }
    if (event instanceof StreamMeditationEvent) {
      this.streamMeditationEventHandler.fireEvent((StreamMeditationEvent) event);
      isEventFired = true;
    }
    if (event instanceof StreamSignalQualityEvent) {
      this.streamSignalQualityEventHandler.fireEvent((StreamSignalQualityEvent) event);
      isEventFired = true;
    }
    if (event instanceof StreamBandPowerEvent) {
      this.streamBandPowerHandler.fireEvent((StreamBandPowerEvent) event);
      isEventFired = true;
    }
    if (event instanceof StreamRawDataEvent) {
      this.streamRawDataEventHandler.fireEvent((StreamRawDataEvent) event);
      isEventFired = true;
    }
    if (!isEventFired) {
      throw new IllegalArgumentException("Unknown event type: " + event.getClass().getName());
    }
  }

  public void addEventListener(EventListener listener) {
    boolean isListenerAdded = false;
    if (listener instanceof IStreamAttentionEventListener) {
      this.streamAttentionEventHandler.addListener((IStreamAttentionEventListener) listener);
      isListenerAdded = true;
    }
    if (listener instanceof IStreamMeditationEventListener) {
      this.streamMeditationEventHandler.addListener(
          (IStreamMeditationEventListener) listener);
      isListenerAdded = true;
    }
    if (listener instanceof IStreamSignalQualityEventListener) {
      this.streamSignalQualityEventHandler.addListener(
          (IStreamSignalQualityEventListener) listener);
      isListenerAdded = true;
    }
    if (listener instanceof IStreamBandPowerEventListener) {
      this.streamBandPowerHandler.addListener(
          (IStreamBandPowerEventListener) listener);
      isListenerAdded = true;
    }
    if (listener instanceof IStreamRawDataEventListener) {
      this.streamRawDataEventHandler.addListener(
          (IStreamRawDataEventListener) listener);
      isListenerAdded = true;
    }
    if (!isListenerAdded) {
      throw new IllegalArgumentException("Invalid Listener Type: " + listener.getClass().getName());
    }
  }


  public void removeEventListener(EventListener listener) {
    boolean isListenerRemoved = false;
    if (listener instanceof IStreamAttentionEventListener) {
      this.streamAttentionEventHandler.removeListener((IStreamAttentionEventListener) listener);
      isListenerRemoved = true;
    }
    if (listener instanceof IStreamMeditationEventListener) {
      this.streamMeditationEventHandler.removeListener(
          (IStreamMeditationEventListener) listener);
      isListenerRemoved = true;
    }
    if (listener instanceof IStreamSignalQualityEventListener) {
      this.streamSignalQualityEventHandler.removeListener(
          (IStreamSignalQualityEventListener) listener);
      isListenerRemoved = true;
    }
    if (listener instanceof IStreamBandPowerEventListener) {
      this.streamBandPowerHandler.removeListener(
          (IStreamBandPowerEventListener) listener);
      isListenerRemoved = true;
    }
    if (listener instanceof IStreamRawDataEventListener) {
      this.streamRawDataEventHandler.removeListener(
          (IStreamRawDataEventListener) listener);
      isListenerRemoved = true;
    }
    if (!isListenerRemoved) {
      throw new IllegalArgumentException("Invalid Listener Type: " + listener.getClass().getName());
    }
  }

  public boolean containsListener(EventListener listener) {
    if (listener instanceof IStreamAttentionEventListener) {
      return this.streamAttentionEventHandler.containsListener(
          (IStreamAttentionEventListener) listener);
    } else if (listener instanceof IStreamMeditationEventListener) {
      return this.streamMeditationEventHandler.containsListener(
          (IStreamMeditationEventListener) listener);
    } else if (listener instanceof IStreamSignalQualityEventListener) {
      return this.streamSignalQualityEventHandler.containsListener(
          (IStreamSignalQualityEventListener) listener);
    } else if (listener instanceof IStreamBandPowerEventListener) {
      return this.streamBandPowerHandler.containsListener(
          (IStreamBandPowerEventListener) listener);
    } else if (listener instanceof IStreamRawDataEventListener) {
      return this.streamRawDataEventHandler.containsListener(
          (IStreamRawDataEventListener) listener);
    } else {
      throw new IllegalArgumentException("Invalid Listener Type: " + listener.getClass().getName());
    }
  }
}
