package ai;

import android.content.res.AssetFileDescriptor;
import android.util.Log;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.tensorflow.lite.Interpreter;

public class Model {

  private final String TAG = "AIModel";
  public Interpreter tflite;

  public Model(String remoteModelUrl) {
    loadModel(remoteModelUrl);
    Log.i(TAG, "Model loaded successfully");
  }


  public void loadModel(String remoteModelUrl) {
    try {
//      MappedByteBuffer modelBuffer = loadModelFile(assetFileDescriptor);
      ByteBuffer modelBuffer = loadModelFileFromNetwork(remoteModelUrl);
      Log.i(TAG, "modelBuffer: " + modelBuffer);
      tflite = new Interpreter(modelBuffer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public float[][] runInference(float[][][] inputData) {
    float[][] outputData = new float[1][1];

    Log.w(TAG, "Input Tensor Dimensions:" +
        Arrays.toString(tflite.getInputTensor(0).shape()) + ", Output Tensor Dimensions:"
        + Arrays.toString(
        tflite.getOutputTensor(0).shape())
        + ", Input Tensor Data Type:" + tflite.getInputTensor(0).dataType()
        + ", Output Tensor Data Type:"
        + tflite.getOutputTensor(0).dataType());

    Log.i(TAG, "Running inference");
    tflite.run(inputData, outputData);
    Log.i(TAG, "Inference finished");
    Log.i(TAG, "Output Data:" + outputData[0][0]);
    return outputData;
  }

  private ByteBuffer loadModelFileFromNetwork(String remoteModelUrl) throws IOException {
    ModelDownloader fileDownloader = new ModelDownloader();
    Future<ByteBuffer> future = fileDownloader.downloadFile(remoteModelUrl);
    ByteBuffer buffer = null;
    try {
      buffer = future.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return buffer;
  }

  private MappedByteBuffer loadModelFile(AssetFileDescriptor assetFileDescriptor)
      throws IOException {

    FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
    FileChannel fileChannel = fileInputStream.getChannel();
    long startOffset = assetFileDescriptor.getStartOffset();
    long declaredLength = assetFileDescriptor.getDeclaredLength();
    MappedByteBuffer test = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset,
        declaredLength);
    Log.i(TAG, String.valueOf(test));
    return test;
  }

  private float[][][] changeInputDataTo(float[][][] arr, float num) {
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        for (int k = 0; k < arr[i][j].length; k++) {
          arr[i][j][k] = num;
        }
      }
    }
    return arr;
  }

}