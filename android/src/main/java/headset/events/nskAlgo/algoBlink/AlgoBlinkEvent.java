package headset.events.nskAlgo.algoBlink;

import headset.events.nskAlgo.NskAlgoEvent;

public class AlgoBlinkEvent extends NskAlgoEvent {

  private final long timeStamp;
  private final AlgoBlinkData blinkData;

  public AlgoBlinkEvent(Object source, AlgoBlinkData data) {
    super(source);
    this.blinkData = data;
    this.timeStamp = System.currentTimeMillis();
  }

  public AlgoBlinkData getBlinkData() {
    return this.blinkData;
  }

  public long getBlinkTimeStamp() {
    return this.timeStamp;
  }

  public String toString() {
    return super.toString() + "AlgoBlinkEvent { BlinkData: " + blinkData + "}";
  }

}
