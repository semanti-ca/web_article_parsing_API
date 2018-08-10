import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SemantiCaBinding {

    final String apiKey = "YOUR_API_KEY"; // put your API key here
    final String apiURL = "https://semanti-ca.cloud.tyk.io";

    public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
          result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
          result.append("=");
          result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
          result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
    }

    public JsonObject callSemantiCaAPI(String articleURL) throws IOException {

      Map<String, String> parameters = new HashMap<>();
      parameters.put("url", articleURL);

      URL url = new URL(apiURL + "/extract-web-article?" + getParamsString(parameters));
      HttpURLConnection semantiCa = (HttpURLConnection) url.openConnection();
      semantiCa.setRequestMethod("GET");
      semantiCa.setInstanceFollowRedirects(false);
      semantiCa.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      semantiCa.setRequestProperty("authorization", apiKey);
      semantiCa.connect();

      int status = semantiCa.getResponseCode();

      if (status == 200) {
          BufferedReader in = new BufferedReader(new InputStreamReader(semantiCa.getInputStream()));
          String inputLine;
          StringBuffer content = new StringBuffer();
          while ((inputLine = in.readLine()) != null) {
              content.append(inputLine);
          }
          in.close();

          semantiCa.disconnect();

          JsonParser jp = new JsonParser();
          JsonElement root = jp.parse(content.toString());
          JsonObject data = root.getAsJsonObject();

          return data;
      }
      return null;
    }

    public static void main(String[] args) throws IOException {
      SemantiCaBinding semantiCaBinding = new SemantiCaBinding();
      
      // the "data" variable will contain the extracted data as an instance of com.google.gson.JsonObject class 
      JsonObject data = semantiCaBinding.callSemantiCaAPI("URL_TO_SCRAPE"); // put the URL you want to scrape here
    }
}
