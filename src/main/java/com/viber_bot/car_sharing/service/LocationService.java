package com.viber_bot.car_sharing.service;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class LocationService {

    public String getLocation (Object locationObject) {
        System.out.println(locationObject.toString());
        String lon = StringUtils.substringBetween(locationObject.toString(), "lon=", ",");
        String lat = StringUtils.substringBetween(locationObject.toString(),  "lat=","}");
        try

        {
            System.out.println("LAT " + lat);
            System.out.println("LON " + lon);
            String address = "";
            URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lon + "&sensor=true");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = url.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String result, line = bufferedReader.readLine();
            result = line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

            if (jsonObject.containsKey("results")) {
                JSONArray matches = (JSONArray) jsonObject.get("results");
                JSONObject data = (JSONObject) matches.get(0);
                address = (String) data.get("formatted_address");
            }

            System.out.println("ADDRESS " + address);
            return address;
        } catch (Exception e) {
            System.out.println("Error" + e);
            return null;
        }
    }
    public LocationService() {}
}