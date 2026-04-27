import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TariffData {

     private static String tariffs[][];

     /**
      * Parses the file for tariff data.
      * 
      * @param fileName The file to be parsed.
      * 
      * @return A 2DArray containing the parsed data.
      */
     private static String[][] loadData(String fileName) {

          ArrayList<String[]> loadedData = new ArrayList<>();
          try {

               File tariffFile = new File(fileName);
               Scanner tariffFileScanner = new Scanner(tariffFile);

               // Prevent the title row from appearing in the data
               tariffFileScanner.nextLine();

               // Fill the ArrayList with the values in the file
               while (tariffFileScanner.hasNext()) {
                    String line = tariffFileScanner.nextLine();
                    String[] lineArray = line.split(",");
                    ArrayList<String> lineArrayList = new ArrayList<>(Arrays.asList(lineArray));
                    loadedData.add(lineArrayList);
               }

               tariffFileScanner.close();

          } catch (Exception e) {
               System.out.println("[TariffData.loadData] An error occured: " + e.getMessage());
          }
     }

     /**
      * Updates the tariff data within the file.
      * 
      * @param fileName The file to be updated.
      * @param apiUrl   The API endpoint to be used to retrieve the new data.
      */
     private static void updateData(String fileName, String apiUrl) {
          System.out.println("[TariffData.updateData] Not implemented yet");
          tariffs = new String[1][1];
     }

     /**
      * Retrieve the tariff data related to a country.
      * 
      * @param countryCode The country's country code (e.g. DE, GB, JP).
      * 
      * @return The data related to the country code, or an empty array if not found.
      */
     public static String[] getData(String countryCode) {
          System.out.println("[TariffData.getData] Not implemented yet, demo data will be returned");
          return new String[1];
     }

     /**
      * Retrieve the data at a specific index.
      * 
      * @param index  The vertical index of the data.
      * @param index2 The horizontal index of the data.
      * 
      * @return The data located at the index.
      */
     public static String getData(int index, int index2) {
          System.out.println("[TariffData.getData] Not implemented yet, demo data will be returned");
          return "0.1";
     }

     /**
      * Retrieve the full tariff data.
      * 
      * @return The 2DArray containing all tariff data.
      */
     public static String[][] getFullData() {
          System.out.println("[TariffData.getFullData] Not implemented yet, demo data will be returned");
          return new String[1][1];
     }

     /**
      * This main method is used for testing only and should be removed in
      * production.
      */
     public static void main(String[] args) {
          System.out.println(
                    "[TariffData.main] This main method is used for testing only and should be removed in production.");
          updateData("tariff.csv", "https://api.com");
          loadData("tariff.csv");
     }
}
