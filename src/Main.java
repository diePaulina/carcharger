import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {

        ArrayList<fahrzeug> list = new ArrayList<>();//List of vehicles

        /* initialising vehicles: id, startTime, endTime, Type, rangeCurrent, rangeNeeded */
        list.add(new fahrzeug(1, 16.0, 6.75, "Transporter", 20.0, 200.0));
        list.add(new fahrzeug(2, 19.0, 9.25, "Transporter", 110.0, 263.0));
        list.add(new fahrzeug(3, 16.5, 6.5, "Kleinbus", 80.0, 263.0));
        list.add(new fahrzeug(4, 17.0, 7.0, "Kleinbus", 150.0, 200.0));
        list.add(new fahrzeug(5, 17.0, 8.0, "Kleinbus", 30.0, 285.0));
        list.add(new fahrzeug(6, 18.0, 8.5, "Kleinbus", 80.0, 200.0));
        list.add(new fahrzeug(7, 17.0, 7.5, "Auto", 50.0, 285.0));
        list.add(new fahrzeug(8, 17.0, 9.0, "Auto", 110.0, 200.0));

        /* sort list by start time (will be needed in calculations) */
        Comparator<fahrzeug> customComparator = (CustomComparator.compareStart());
        list.sort(customComparator);

        /*set parameters for our function call*/
        double interval = 0.25;
        double capacity = 20;

        //24 h 0 ist 0 Uhr, Ende 24 Uhr
        ArrayList<Double> capacityList = new ArrayList<Double>();
        //das ist nur um beispiel werte zu erzeugen
        //eigentlich musst du 96 mal capacityList.add(deinWert); eingeben und das wegmachen
        for (int i = 0; i < 96/2; i++) {
            capacityList.add(capacity);
            capacityList.add(capacity-5);
        }

        System.out.println();
        //überprüfen ob es auch 96 werte sind
        if( capacityList.size() != 96){
            System.out.print("List does not have 96 elements, current elements: " + capacityList.size());
        } else System.out.println("List has correct size");
        System.out.println();

        calculate.startCharging(interval, list, capacityList);
    }
}
