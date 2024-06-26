package ai;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ModelDownloader {

  private final ExecutorService executor = Executors.newSingleThreadExecutor();

  public Future<ByteBuffer> downloadFile(String fileUrl) {
    return executor.submit(() -> {
      ByteBuffer byteBuffer = null;

      try {
        URL url = new URL(fileUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();

        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
          byte[] buffer = new byte[1024];
          int bytesRead;
          InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

          byteBuffer = ByteBuffer.allocateDirect(urlConnection.getContentLength());

          while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteBuffer.put(buffer, 0, bytesRead);
          }

          inputStream.close();
        } else {
          Log.e("ModelDownloader", "Failed to download file: " + urlConnection.getResponseMessage());
        }

        urlConnection.disconnect();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return byteBuffer;
    });
  }
}
