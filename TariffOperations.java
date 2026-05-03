public class TariffOperations {

    // Enable for demo
    public static final boolean USE_IEEPA = true;

    /**
     * Retrieve the tariff rate on a country.
     * 
     * @param countryCode The ISO code of the country.
     * @param special     Whether to take into account special items. Examples
     *                    include auto parts, metals, etc.
     * 
     * @return The tariff rate on the country.
     */
    public static double getTariffRate(String countryCode, boolean special) {
        if (special) {
            return 0.5;
        } else {
            if (USE_IEEPA) {
                return Double.parseDouble(TariffData.getData(countryCode)[3]);
            } else {
                return Double.parseDouble(TariffData.getData(countryCode)[2]);
            }
        }
    }

    /**
     * Calculate the price of an item based on a set tariff rate.
     * 
     * @param price The original price of the item.
     * @param rate  The rate of tariffs on the item.
     * 
     * @return The price of the item with tariffs taken into account.
     */
    public static double getTariff(double price, double rate) {

        double tariff = price * (rate + 1);

        // Round to prevent extra decimals at end
        return Math.round(tariff * 100.0) / 100.0;
    }

    /**
     * Calculate the price of an item based on its country of origin.
     * 
     * @param price       The original price of the item.
     * @param countryCode The ISO code of the country.
     * @param special     Whether to take into account special items. Examples
     *                    include auto parts, metals, etc.
     * 
     * @return The price of the item with tariffs taken into account.
     */
    public static double getTariff(double price, String countryCode, boolean special) {
        double rate = getTariffRate(countryCode, special);
        return getTariff(price, rate);
    }
}