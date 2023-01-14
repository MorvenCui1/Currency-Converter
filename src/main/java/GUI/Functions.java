/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author morve
 */

public class Functions {
    
    public Functions(){
    }
    
    public double convert(double amount, String targetCurrency, String fromCurrency){
    // Creates URL String variable for link required to get exchange rate
    String apiUrl = "https://api.exchangerate-api.com/v4/latest/" + fromCurrency;
    try {
      // Connects to URL link
      URL url = new URL(apiUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          response.append(line);
        }
        reader.close();

        // Parse the JSON response
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.toString());
        if (jsonObject.containsKey("error")) {
          // There was an error in the API response
          String error = (String) jsonObject.get("error");
        } else {
          // The API response was successful
          JSONObject rates = (JSONObject) jsonObject.get("rates");
          double rate = (double) rates.get(targetCurrency);

          // Calculate and format the result
          DecimalFormat df = new DecimalFormat("#.00");
          double result = amount * rate;
          String formattedResult = df.format(result);
          
          return result;
        }
      } else {
          return 1;
      }
    } catch (Exception e) {
        return 2;
    }
    return 3;
    }
}
