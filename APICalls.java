import java.util.ArrayList;
import okhttp3.OkHttpClient;

import org.json.*;

public class APICalls {

    /**
     * Makes a HTTP GET request to the provided URL and returns the result as a
     * String.
     * Note: This method was written by AI.
     *
     * @param url The URL to make a request to.
     * 
     * @return The returned data from the request, or null if an error occurs.
     */
    public static String get(String url) {
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();
        try (okhttp3.Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected code " + response);
            }
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parses a JSON object for a value and returns it as a JSONArray.
     * 
     * @param json     The JSON object to be parsed as a String.
     * @param property The property in the JSON object to be found.
     * 
     * @return The parsed JSON as a 2DArray of keys and values.
     */
    public static String[][] parseJSONObject(String json, String property) {
        JSONObject object1 = new JSONObject(json);
        JSONObject object2 = object1.getJSONObject(property);
        JSONArray arr = object2.toJSONArray(new JSONArray(object2.names()));
        ArrayList<String> keyarrlist = new ArrayList<>();
        for (String key : object2.keySet()) {
            keyarrlist.add(key);
        }
        ArrayList<String> valuearrlist = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            valuearrlist.add(arr.get(i).toString());
        }
        String[][] result = new String[keyarrlist.size()][2];
        for (int i = 0; i < keyarrlist.size(); i++) {
            result[i][0] = keyarrlist.get(i);
            result[i][1] = valuearrlist.get(i);
        }
        return result;
    }

    /**
     * Parses a JSON object for a value and returns it as a String.
     * 
     * @param json     The JSON object to be parsed as a String.
     * @param property The property in the JSON object to be found.
     * 
     * @return The parsed property as a String.
     */
    public static String parseJSONProperty(String json, String property) {
        JSONObject object1 = new JSONObject(json);
        String value = object1.get(property).toString();
        return value;
    }

    /**
     * This main method is for testing only and should be removed in production.
     */
    public static void main(String[] args) {

        System.out.println(
                "[APICalls.main] Note: This main method is for testing only and should be removed in production.");
    }

    public static void printArray(String[] arr) {
        for (String i : arr) {
            System.out.println(i);
        }
    }
}
