package ai;

import ai.events.aiDetectedMovement.AiDetectedMovementEvent;
import ai.events.aiDetectedMovement.AiDetectedMovementEventHandler;
import ai.events.aiDetectedMovement.IAiDetectedMovementEventListener;
import android.util.Log;
import headset.events.stream.streamRaw.IStreamRawDataEventListener;
import headset.events.stream.streamRaw.StreamRawDataEvent;
import headset.events.stream.streamSignalQuality.IStreamSignalQualityEventListener;
import headset.events.stream.streamSignalQuality.StreamSignalQualityEvent;

public class ModelController implements IStreamRawDataEventListener,
    IStreamSignalQualityEventListener {

  private final Model model;
  private final AiDetectedMovementEventHandler aiDetectedMovementEventHandler = new AiDetectedMovementEventHandler();
  private int lastSignalQuality = 200;
  private int lastSignalQualityCounter = 3;

  public ModelController(String remoteModelUrl) {
    this.model = new Model(remoteModelUrl);
  }

  @Override
  public void onSignalQualityUpdate(StreamSignalQualityEvent event) {
    this.lastSignalQuality = event.getSignalQualityData().qualityLevel();
    if (lastSignalQuality == 0) {
      if (lastSignalQualityCounter > 0) {
        lastSignalQualityCounter--;
      }
    } else {
      lastSignalQualityCounter = 3;
    }
  }

  @Override
  public void onRawDataUpdate(StreamRawDataEvent event) {
    if (lastSignalQualityCounter == 0) {
      float[][][] input = new float[1][event.getRawData().rawData().length][1];
      for (int i = 0; i < event.getRawData().rawData().length; i++) {
        input[0][i][0] = event.getRawData().rawData()[i];
      }
      detectMovement(input);
    }
  }

  private void detectMovement(float[][][] input) {
    float[][] result = model.runInference(input);
    //FIXME: this is a dummy implementation
    //       the real implementation require the model to take the headset raw data as input and return some sort of a
    //       agreed upon flag {o: for incorrect detection of action, 1: for correct detection of action } that will be
    //       used to trigger the appropriate action
//    float[][] result = model.runInference(new float[][][]{{{1, 2}}});
    //TODO: Discuss this logic
    if (result[0][0] > 0.3) {
      Log.i("ModelController", "Movement Detected");
      aiDetectedMovementEventHandler.fireEvent(
          new AiDetectedMovementEvent(this, (int) Math.ceil(result[0][0])));
    }
  }

  public void addListener(IAiDetectedMovementEventListener listener) {
    aiDetectedMovementEventHandler.addListener(listener);
  }

  public void removeListener(IAiDetectedMovementEventListener listener) {
    aiDetectedMovementEventHandler.removeListener(listener);
  }
}
