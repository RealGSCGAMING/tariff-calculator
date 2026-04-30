import java.util.ArrayList;

import org.json.*;

public class APICalls {

    /**
     * TODO: Makes a HTTP request to the provided URL and returns the result.
     * 
     * @param url The URL to make a request to.
     * 
     * @return The returned data from the request.
     */
    public static String get(String url) {
        return "";
    }

    /**
     * Parses a JSON object for a value and returns it as a JSONArray.
     * 
     * @param json     The JSON object to be parsed as a String.
     * @param property The property in the JSON object to be found.
     * 
     * @return The parsed JSON as an array.
     */
    public static String[] parseJSON(String json, String property) {
        JSONObject object1 = new JSONObject(json);
        JSONObject object2 = object1.getJSONObject(property);
        JSONArray arr = object2.toJSONArray(new JSONArray(object2.names()));
        System.out.println(arr);
        ArrayList<String> arrlist = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            arrlist.add(arr.getString(i));
        }
        return arrlist.toArray(new String[0]);
    }

    /**
     * This main method is for testing only and should be removed in production.
     */
    public static void main(String[] args) {

        // TODO: Test parseJSON and get using API from CurrencyConversion.java

        System.out.println(
                "[TariffData.main] Note: This main method is for testing only and should be removed in production.");
    }
}
