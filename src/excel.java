import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class excel {
    public static void main(String[] args) throws IOException {

        ArrayList<String[]> list = readExcel();
        System.out.println("output:");
        for (String[] row: list) {
            System.out.println(Arrays.toString(row));
        }

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
                    System.out.printf("%16s", s);
                }
                System.out.println();
                output.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        return output;
    }
    public static void writeExcel(ArrayList<String[]> list){
        String file = "src/fahrzeuge.csv";
        BufferedWriter writer = null;
        String line = "";
    }
}
