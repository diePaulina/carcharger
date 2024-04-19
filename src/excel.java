import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class excel {
    public static void main(String[] args) throws IOException {

        ArrayList<String[]> list = readExcel();
        ArrayList<fahrzeug> vehicles = new ArrayList<>();//List of vehicles
        ArrayList<Double> capacityList = new ArrayList<Double>();
        calculate.writeNames();

        /*set parameters for our function call*/
        double interval = 0.25;
        for (int i = 1; i < list.size(); i++) {
            String[] row = list.get(i);
            if( row.length >3) {
                vehicles.add(new fahrzeug(i, convertToDouble(row[4]), convertToDouble(row[5]), row[3], convertToDouble(row[6]), convertToDouble(row[7])));
            }
            if(row.length>1&& !Objects.equals(row[1], "")){
                capacityList.add(convertToDouble(row[1]));
            }else break;
        }
        System.out.println();
        //überprüfen, ob es auch 96 werte sind
        if( capacityList.size() != 96){
            System.out.print("List does not have 96 elements, current elements: " + capacityList.size());
        } else System.out.println("List has correct size");
        System.out.println();
        Comparator<fahrzeug> customComparator = (CustomComparator.compareStart());
        vehicles.sort(customComparator);
        calculate.startCharging(interval, vehicles, capacityList);
        ArrayList<String[]> output = calculate.returnList();
        for (String[] a:output
             ) {
            for (String s: a
                 ) {
                System.out.printf("%16s", s);
            }
            System.out.println();
        }
        writeArrayListToCSV(output, "src/output.csv");

    }

    public static ArrayList<String[]> readExcel() throws IOException {
        String file = "src/fahrzeuge.csv";
        BufferedReader reader = null;
        String line = "";
        ArrayList<String[]> output = new ArrayList<>();
        String[] row = new String[0];
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                row = line.split(";");
                for (String s : row) {
                    //System.out.printf("%16s", s);
                }
                //System.out.println();
                output.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        return output;
    }

    public static double convertToDouble(String str) {
        // Replace the comma with a dot to form a valid double string
        String doubleStr = str.replace(",", ".");

        try {
            // Parse the string to double
            double result = Double.parseDouble(doubleStr);
            return result;
        } catch (NumberFormatException e) {
            // Handle parsing errors
            System.err.println("Error parsing string to double: " + str);
            e.printStackTrace();
            return 0.0; // Or any default value you prefer
        }
    }
    public static void writeArrayListToCSV(ArrayList<String[]> data, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    if (isNumeric(row[i])) { // Check if the value is numeric
                        row[i] = row[i].replace(".", ","); // Replace dot with comma
                    }
                    line.append(row[i]);
                    if (i < row.length - 1) {
                        line.append(";");
                    }
                }
                writer.write(line.toString());
                writer.newLine();
            }
            System.out.println("CSV file written successfully.");
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }
    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  // Regex to check if the string is a number
    }
}
