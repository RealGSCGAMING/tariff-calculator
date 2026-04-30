import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class CurrencyConversion {

    private static String[][] rates;
    private static final String CURRENCY_FILE = "currency.csv";

    /**
     * Parses the file for currency data.
     * 
     * @param fileName The file to be parsed.
     */
    private static void loadData(String fileName) {
        ArrayList<String[]> loadedData = new ArrayList<>();
        try {

            File currencyFile = new File(fileName);
            Scanner currencyFileScanner = new Scanner(currencyFile);

            // Prevent the title row from appearing in the data
            currencyFileScanner.nextLine();

            // Fill the ArrayList with the values in the file
            while (currencyFileScanner.hasNext()) {
                String line = currencyFileScanner.nextLine();
                String[] lineArray = line.split(",");
                loadedData.add(lineArray);
            }

            currencyFileScanner.close();

            String[][] arrayConvert = loadedData.toArray(new String[0][0]);

            rates = arrayConvert;

        } catch (Exception e) {
            System.out.println("[CurrencyConversion.loadData] An error occured: " + e.getMessage());
        }
    }

    /**
     * Parses the file for currency data.
     */
    public static void loadData() {
        loadData(CURRENCY_FILE);
    }

    /**
     * TODO: Updates the currency data within the file.
     * 
     * @param fileName The file to be updated.
     * @param apiUrl   The API endpoint to be used to retrieve the new data.
     */
    public static void updateData(String fileName, String apiUrl) {
        String currencyApiKey = "";

        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("secrets.properties"));
            currencyApiKey = prop.getProperty("currencyapikey");
            System.out.println(currencyApiKey);
        } catch (Exception e) {
            System.out.println("Unable to access currency API key");
        }

        String response = APICalls.get("https://v6.exchangerate-api.com/v6/" + currencyApiKey + "/latest/USD");
        String[] currencies = APICalls.parseJSON(response, "conversion_rates");
    }

    /**
     * Converts local currencies to USD based on a set rate.
     * 
     * @param price The price of an item in its local currency.
     * @param rate  The conversion rate of the local currency.
     * 
     * @return The price of the item in USD.
     */
    public static double convert(double price, double rate) {

        // Round to prevent extra decimals at end
        return Math.round((price / rate) * 100.0) / 100.0;
    }

    /**
     * Converts local currencies to USD.
     * 
     * @param price       The price of an item in its local currency.
     * @param countryCode The ISO code of the country.
     * 
     * @return The price of the item in USD.
     */
    public static double convert(double price, String countryCode) {
        return convert(price, getRate(countryCode));
    }

    /**
     * Retrieves the conversion rate of a country's local currency.
     * 
     * @param countryCode The country to retrieve the rate of.
     * 
     * @return The conversion rate of the country's local currency.
     */
    public static double getRate(String countryCode) {
        for (String[] i : rates) {
            if (i[0].equals(countryCode)) {
                return Double.valueOf(i[2]);
            }
        }
        throw new ArrayIndexOutOfBoundsException(
                "[CurrencyConversion.getRate] Could not find the specified country code within the array.");
    }

    /**
     * Retrieve the full currency data.
     * 
     * @return The 2DArray containing all currency data.
     */
    public static String[][] getFullData() {
        return rates;
    }

    /**
     * This main method is for testing only and should be removed in production.
     */
    public static void main(String[] args) {
        loadData();
        TariffData.loadData();
        System.out.println("$" + convert(10230, "JP"));
        System.out.println("$" + TariffOperations.getTariff(convert(10230, "JP"), "JP", false));
    }
}
