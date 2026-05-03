import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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
     * Parses the default currency file for currency data.
     */
    public static void loadData() {
        loadData(CURRENCY_FILE);
    }

    /**
     * Updates the currency data within the file.
     * 
     * @param fileName The file to be updated.
     */
    public static void updateData(String fileName) {
        String currencyApiKey = "";

        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("secrets.properties"));
            currencyApiKey = prop.getProperty("currencyapikey");
        } catch (Exception e) {
            System.out.println("Unable to access currency API key: " + e.getMessage());
        }

        String response = APICalls.get("https://v6.exchangerate-api.com/v6/" + currencyApiKey + "/latest/USD");
        String[][] currencies = APICalls.parseJSONObject(response, "conversion_rates");

        String updateDate = APICalls.parseJSONProperty(response, "time_last_update_utc").substring(5);

        try {
            Scanner sc = new Scanner(new File(fileName));
            String lastUpdateDate = sc.nextLine().split(",")[2];
            sc.close();
            if (!(lastUpdateDate.equals(updateDate))) {
                FileWriter fw = new FileWriter(fileName);
                fw.write("Currency Code,Conversion Rate," + updateDate + "\n");
                for (String[] i : currencies) {
                    fw.write(i[0] + "," + i[1] + "\n");
                }
                fw.close();
                System.out.println("Finished updating currency data.");
            } else {
                System.out.println("Currency data is already up-to-date.");
            }
        } catch (Exception e) {
            System.out.println("Unable to write to currency file: " + e.getMessage());
        }
    }

    /**
     * Updates the currency data within the default currency file.
     */
    public static void updateData() {
        updateData(CURRENCY_FILE);
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
    public static double convert(double price, String currencyCode) {
        return convert(price, getRate(currencyCode));
    }

    /**
     * Converts USD to local currencies based on a set rate.
     * 
     * @param price The price of an item in USD.
     * @param rate  The conversion rate of the local currency.
     * 
     * @return The price of the item in the local currency.
     */
    public static double reverseConvert(double price, double rate) {

        // Round to prevent extra decimals at end
        return Math.round((price * rate) * 100.0) / 100.0;
    }

    /**
     * Converts USD to local currencies.
     * 
     * @param price       The price of an item in USD.
     * @param countryCode The ISO code of the country.
     * 
     * @return The price of the item in the local currency.
     */
    public static double reverseConvert(double price, String currencyCode) {
        return reverseConvert(price, getRate(currencyCode));
    }

    /**
     * Retrieves the conversion rate of a country's local currency.
     * 
     * @param countryCode The country to retrieve the rate of.
     * 
     * @return The conversion rate of the country's local currency.
     */
    public static double getRate(String currencyCode) {
        for (String[] i : rates) {
            if (i[0].equals(currencyCode)) {
                return Double.valueOf(i[1]);
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
        updateData(CURRENCY_FILE);
        loadData();
        TariffData.loadData();
        System.out.println("$" + convert(10230, "JPY"));
        System.out.println("$" + TariffOperations.getTariff(convert(10230, "JPY"), "JP", false));
        System.out.println(
                "[CurrencyConversion.main] Note: This main method is for testing only and should be removed in production.");
    }
}
