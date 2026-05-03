import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Update datasets? (Y/N)");
        boolean input = false;
        System.out.println("N");
        // boolean input = sc.nextLine().toLowerCase().equals("y");

        double time = System.nanoTime();
        if (input) {
            System.out.println("Updating datasets, please wait...");
            setup(true);
        } else {
            setup(false);
        }

        System.out.println("Opening GUI...");
        new GUI();

        System.out.println("Loaded in " + ((System.nanoTime() - time) / 1e9) + " secs");

        sc.close();
    }

    /**
     * Loads data files into data classes.
     */
    public static void setup() {
        TariffData.loadData();
        CurrencyConversion.loadData();
    }

    /**
     * Loads data files into data classes.
     * 
     * @param update Whether to update the data files over the internet.
     */
    public static void setup(boolean update) {
        if (update) {
            // TariffData.updateData();
            CurrencyConversion.updateData();
        }
        setup();
    }

    /**
     * A simple PrintLn UI for the Tariff Calculator.
     */
    public static void runPrintedGui() {

        TariffData.loadData();
        CurrencyConversion.loadData();

        System.out.println("Welcome to the Tariff Calculator");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("What is the country code of the item's country of origin");
            String countryCode = sc.nextLine().toUpperCase();

            System.out.println("Is the price in USD or local currency (true/false)");
            boolean local = Boolean.parseBoolean(sc.nextLine());

            String currencyCode = "";

            if (local) {
                System.out.println("What is the country code of the item's local currency");
                currencyCode = sc.nextLine().toUpperCase();
            }

            System.out.println("What is the price of the item");
            double price = Double.parseDouble(sc.nextLine());

            System.out.println("Is the item a special item (metal, auto parts, etc) (true/false)");
            boolean special = Boolean.parseBoolean(sc.nextLine());

            System.out.println("Checking tariff...");
            double newPrice;

            try {
                newPrice = TariffOperations.getTariff((local ? CurrencyConversion.convert(price, currencyCode) : price),
                        countryCode, special);
            } catch (Exception e) {
                System.out.println("An error occurred. " + e.getMessage());
                newPrice = 0;
            }

            System.out.println("New price: " + newPrice);

            System.out.println("Press enter to continue or type q to quit");
            if (sc.nextLine().toLowerCase().equals("q")) {
                break;
            }
        }

        sc.close();

    }
}