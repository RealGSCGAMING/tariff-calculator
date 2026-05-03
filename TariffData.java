import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TariffData {

     private static final String TARIFF_FILE = "tariff.csv";
     private static String tariffs[][];

     /**
      * Parses the file for tariff data.
      * 
      * @param fileName The file to be parsed.
      */
     private static void loadData(String fileName) {

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
                    loadedData.add(lineArray);
               }

               tariffFileScanner.close();

               String[][] arrayConvert = loadedData.toArray(new String[0][0]);

               tariffs = arrayConvert;

          } catch (Exception e) {
               System.out.println("[TariffData.loadData] An error occured: " + e.getMessage());
          }
     }

     /**
      * Parses the default tariff file for tariff data.
      */
     public static void loadData() {
          loadData(TARIFF_FILE);
     }

     /**
      * TODO: Updates the tariff data within the file.
      * 
      * @param fileName The file to be updated.
      */
     public static void updateData(String fileName) {
          System.out.println("[TariffData.updateData] Not implemented yet");
          tariffs = new String[1][1];
     }

     /**
      * Updates the tariff data within the default tariff file.
      */
     public static void updateData() {
          updateData(TARIFF_FILE);
     }

     /**
      * Retrieve the tariff data related to a country.
      * 
      * @param countryCode The country's country code (e.g. DE, GB, JP).
      * 
      * @return The data related to the country code, or an empty array if not found.
      */
     public static String[] getData(String countryCode) {
          for (String[] i : tariffs) {
               if (i[0].equals(countryCode)) {
                    return i;
               }
          }
          throw new ArrayIndexOutOfBoundsException(
                    "[TariffData.getData] Could not find the specified country code within the array.");
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
          if (index > tariffs.length - 1 || index2 > tariffs[index].length - 1) {
               throw new IndexOutOfBoundsException(
                         "[TariffData.getData] The specified index is out of the array's range.");
          } else {
               return tariffs[index][index2];
          }
     }

     /**
      * Retrieve the full tariff data.
      * 
      * @return The 2DArray containing all tariff data.
      */
     public static String[][] getFullData() {
          return tariffs;
     }

     /**
      * Utility method to print a 2D array.
      * 
      * @param array The 2D array to be printed.
      */
     public static void printArray(String[][] array) {
          for (String[] i : array) {
               for (String j : i) {
                    System.out.print(j + "\t");
               }
               System.out.println();
          }
     }
}
