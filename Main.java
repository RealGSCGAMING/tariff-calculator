import java.util.Scanner;

public class Main {

    /**
     * A simple PrintLn UI for the Tariff Calculator.
     */
    public static void main(String[] args) {

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