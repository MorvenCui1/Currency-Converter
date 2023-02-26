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

import java.util.ArrayList;

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
          return 1;
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
          return 2;
      }
    } catch (Exception e) {
        return 3;
    }
    }
    
    // Declare arraylists of past currencies and amounts user entered
    public static ArrayList<String> currencies = new ArrayList<String>();
    public static ArrayList<Double> amounts = new ArrayList<Double>();
    
    // Method to add values to array lists
    public void add_value(String currency1, String currency2, double amount){
        // First check if currency is already in arraylist, then add currency if not for both from and to currencies
        int tracker = 0;
        for (int i = 0; i < currencies.size(); i++){
            if (currencies.get(i).equals(currency1)){
                tracker = 1;
            }
        }
        if (tracker == 0){
            currencies.add(currency1);
        }
        tracker = 0;
        for (int i = 0; i < currencies.size(); i++){
            if (currencies.get(i).equals(currency2)){
                tracker = 1;
            }
        }
        if (tracker == 0){
            currencies.add(currency2);
        }
        // Add amount to amount arraylist
        amounts.add(amount);
    }
    
    // Methods to sort amount arraylist
    public void heapSort(){
        double temp;

        for (int i = amounts.size() / 2 - 1; i >= 0; i--){
        heapify(amounts.size(), i);
        }

        for (int i = amounts.size() - 1; i > 0; i--){
            temp = amounts.get(0);
            amounts.set(0, amounts.get(i));
            amounts.set(i, temp);
            heapify(i , 0);
        }

        System.out.println("List of temperatures: " + amounts);
    }
    public void heapify(int n, int i){
        int MAX = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        double temp;

        if (left < n && amounts.get(left) > amounts.get(MAX)){
        MAX = left;
        }

        if (right < n && amounts.get(right) > amounts.get(MAX)){
            MAX = right;
        }

        if (MAX != i){
            temp = amounts.get(i);
            amounts.set(i, amounts.get(MAX));
            amounts.set(MAX, temp);
            heapify(n, MAX);
        }
    }
    
    // Search for amount value
    public int search_amount(double item){

        int first = 0;
        int last = amounts.size() - 1;

        while (first <= last){
            int mid = (first + last)/2;
      
            if (first > last){
                return 0;
            }
            else if (amounts.get(mid) < item){
                first = mid +  1;
            }
            else if (amounts.get(mid) == item){
                return 1;
            }
            else{
                last = mid - 1;
            }
        }
        return 0;
    }
    
    // Search for currency type
    public int search_currency(String currency){
        for (int i = 0; i < currencies.size(); i++){
            if (currencies.get(i).equals(currency)){
                return 1;
            }
        }
        return 0;
    }
    
    // Create display string for currencies
    public String create_string(){
        
        String return_string = "";
        for (int i = 0; i < currencies.size(); i++){
            return_string = return_string + currencies.get(i) + "\n";
        }
        return return_string;
    }
    
    // Create display string for amounts
    public String create_string1(){
        
        String return_string = "";
        for (int i = 0; i < amounts.size(); i++){
            return_string = return_string + amounts.get(i) + "\n";
        }
        return return_string;
    }
}
