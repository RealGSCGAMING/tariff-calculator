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
     * Parses a JSON object and returns it as a JSONArray.
     * 
     * @param json The JSON object to be parsed as a String.
     * 
     * @return The parsed JSON as a JSONArray.
     */
    public static JSONArray parseJSON(String json) {
        JSONObject object = new JSONObject(json);
        return object.getJSONArray("");
    }

    /**
      * This main method is for testing only and should be removed in production.
      */
    public static void main(String[] args) {
        System.out.println(
                    "[TariffData.main] Note: This main method is for testing only and should be removed in production.");
    }
}
