import java.util.ArrayList;
import java.util.Comparator;

public class calculate {
    public static double currentTime;
    public static  double interval;
    public static  ArrayList<fahrzeug> list;
    public static  fahrzeug f;
    public static  ArrayList<Double> capacity;
    /*public double cCap;
    public double tmp;
    public double rest;*/
    public static  int  i= 0;
    public static void startCharging(double intervalValue, ArrayList<fahrzeug> fahrzeugList, ArrayList<Double> capacityList){
        list = fahrzeugList;
        capacity = capacityList;
        interval = intervalValue;
        currentTime = list.get(0).startTime;

        Comparator<fahrzeug> customComparator = (CustomComparator.compareEnd());
        list.sort(customComparator);
        double endTime = list.get(list.size()-1).endTime;

        /* 1 End Zeit schauen, und die richtigen rauslöschen
            2 Startzeit schauen und anfangen zu calculaten
            edge case: was wenn end zeit + intervall schon zu spät wäre?
         */
        while(list.size()>0 && currentTime<=endTime){
            helpCharge();
            currentTime += interval;
        }

    }

    public static void helpCharge() {
        //1 End Zeit schauen, und die richtigen rauslöschen
        for (int j = 0; j < list.size(); j++) {
            fahrzeug v = list.get(j);
            if (v.endTime <= currentTime) {
                v.printStatus(currentTime);
                if (isIllegal()) break;
            }
        }
        //2 Startzeit schauen und anfangen zu calculaten
        double currentCapacity = capacity.get((int) ((currentTime % 24) * interval));
        help(currentCapacity,interval, list.size());
    }
    public static void help( double currentCapacity, double rest, int x){
        fahrzeug f = list.get(i);
        if(f.startTime<= currentTime){
        double tmp = list.get(i).updateCharge(currentCapacity, rest);
        if (tmp == -1) {
            rest = 0;
            f.printStatus(currentTime + rest);
        } else {
           if(isIllegal()) {
               return;
           }
            rest -= tmp;
            f.printStatus(currentTime - rest);
            if(x>0&& rest > 0) {
                help(currentCapacity, rest,x);
            }
        }
            return;
        }
        increaseI();
        if(x>0&& rest > 0)
            help(currentCapacity,interval,--x);
    }


    public static boolean isIllegal(){
        if (i == 0 && list.size() == 1) {
           return true;
        } else if (list.size() > 1 && i > 0) {
            list.remove(i);
            i--;
        }
        else {list.remove(i);}
        return false;
    }
    public static void increaseI(){
        if(i == list.size()- 1 ) i=0;
        else i++;
    }
}
