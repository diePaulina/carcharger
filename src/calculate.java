import java.util.ArrayList;
import java.util.Comparator;

public class calculate {
    public static double currentTime;
    public static double interval;
    public static double currentCapacity;
    public static ArrayList<fahrzeug> list;
    public static ArrayList<Double> capacity;
    public static int i = 0;

    public static void startCharging(double intervalValue, ArrayList<fahrzeug> fahrzeugList, ArrayList<Double> capacityList) {
        list = fahrzeugList;
        capacity = capacityList;
        interval = intervalValue;
        currentTime = list.get(0).startTime;

        Comparator<fahrzeug> customComparator = (CustomComparator.compareEnd());
        list.sort(customComparator);
        double endTime = list.get(list.size() - 1).endTime;
        System.out.println();
        System.out.println("     Starting calculation with " + list.size() + " vehicles, variable charging capacity and interval " + fahrzeug.cT(interval));
        System.out.println();
        /* 1 End Zeit schauen, und die richtigen rauslöschen
            2 Startzeit schauen und anfangen zu calculaten
            edge case: was wenn end zeit + intervall schon zu spät wäre?
         */

        while (list.size() > 0 && currentTime <= endTime) {
            currentCapacity = capacity.get((int) ((currentTime % 24) / interval));
            helpCharge();
            currentTime += interval;
           // System.out.println("increase to " + currentTime);
        }

    }

    public static void helpCharge() {
        //1 End Zeit schauen, und die richtigen rauslöschen
        for (int j = 0; j < list.size(); j++) {
            fahrzeug v = list.get(j);
            if (v.endTime <= currentTime) {
                v.printFail(currentTime);
                if (isIllegal()) {break;}
            }
        }

        //2 Startzeit schauen und anfangen zu calculaten
        //System.out.println("current element: " +  ((int) ((currentTime % 24) / interval)) +" current time " + currentTime + " current capacity " + currentCapacity);
    if(list.size()>0)
        help(currentCapacity, interval, list.size());
    else
        System.out.print("\nDone Calculating\n");
}

    public static void help(double currentCapacity, double rest, int x) {
        fahrzeug f = list.get(i);
        if (f.startTime <= currentTime) {
            //System.out.println("succses");
            double tmp = list.get(i).updateCharge(currentCapacity, currentTime,currentTime+rest);
            if (tmp == -1) {
               // System.out.println("case 1");

            } else {
                //System.out.println("case 2");
                if (isIllegal()) {
                   // System.out.println("illegal");
                    return;
                }
                rest -= tmp;
                x = list.size();
                if (rest > 0) {
                   // System.out.println("call again");
                    help(currentCapacity, rest, x, true);
                }
            }
            return;
        }
       // System.out.println("1");
        increaseI();
        if (x > 0 && rest > 0) {
           // System.out.println("No");
            help(currentCapacity, interval, --x, false);
        }
    }

    public static void help(double currentCapacity, double rest, int x, boolean calledAgain) {
        fahrzeug f = list.get(i);
        if (f.startTime <= currentTime) {
          //  System.out.println("succssssssssses");
            double tmp = list.get(i).updateCharge(currentCapacity, currentTime+ interval - rest,currentTime+interval);
            if (tmp == -1) {
                //System.out.println("case 3");
            } else {
               // System.out.println("case 4");
                if (isIllegal()) {
                    //System.out.println("illegal");
                    return;
                }
                rest -= tmp;
                x = list.size();
                if (rest > 0) {
                    //System.out.println("call again");
                    help(currentCapacity, rest, x, true);
                }
            }
            return;
        }
       // System.out.println("1");
        increaseI();
        if (x > 0 && rest > 0) {
            System.out.println("No");
            help(currentCapacity, interval, --x, false);
        }
    }

    public static boolean isIllegal() {

        if (i == 0 && list.size() == 1) {
            list.remove(i);
            return true;
        } else if (list.size() > 1 && i > 0) {
            list.remove(i);

            i--;
        } else {
            list.remove(i);
        }
        return false;
    }

    public static void increaseI() {
        if (i == list.size() - 1) i = 0;
        else i++;
    }
}
