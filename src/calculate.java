import java.util.ArrayList;
import java.util.Comparator;

public class calculate {
    public static ArrayList<String[]> output = new ArrayList<>();

    public static double currentTime;
    public static double interval;
    public static double currentCapacity;
    public static ArrayList<fahrzeug> list;
    public static ArrayList<Double> capacity;
    public static int i = 0;

    /**
     * function to start the charging calculations
     * @param intervalValue how long the intervals are
     * @param fahrzeugList list of vehicles that need charging, with start time asc
     * @param capacityList list of capacities starting at 00:00 each entry is spaced by one interval
     */
    public static void startCharging(double intervalValue, ArrayList<fahrzeug> fahrzeugList, ArrayList<Double> capacityList, double start, double end) {
        list = fahrzeugList;
        capacity = capacityList;
        interval = intervalValue;
        currentTime = start;

        Comparator<fahrzeug> customComparator = (CustomComparator.compareEnd());
        list.sort(customComparator);
       // double endTime = list.get(list.size() - 1).endTime;
        System.out.println();
        System.out.println("     Starting calculation at " + fahrzeug.cT(currentTime)+ " with " + list.size() + " vehicles, variable charging capacity and interval " + fahrzeug.cT(interval));
        System.out.println();
        /* 1 End Zeit schauen, und die richtigen rauslöschen
            2 Startzeit schauen und anfangen zu calculaten
            edge case: was wenn end zeit + intervall schon zu spät wäre?
         */
        currentCapacity = capacity.get((int) ((currentTime % 24)*4));
        System.out.println((int) ((currentTime % 24)*4));
        addEntry(new String[]{fahrzeug.cT(start), fahrzeug.cT(start), String.valueOf(currentCapacity),"","","","","NONE"});
        while (!list.isEmpty() && currentTime <= end) {
            currentCapacity = capacity.get((int) (((currentTime) % 24)*4));
           // System.out.println((int) (((currentTime+interval)  % 24)*4));
            helpCharge();
            currentTime += interval;
           // System.out.println("increase to " + currentTime);
        }while (list.isEmpty() &&currentTime + interval <= end) {
            currentCapacity = capacity.get((int) (((currentTime)  % 24)*4));
           // System.out.println((int) (((currentTime+interval)  % 24)*4));
            addEntry(new String[]{fahrzeug.cT(currentTime),fahrzeug.cT(currentTime+interval), String.valueOf(currentCapacity),"","","","","NONE"});
            currentTime += interval;
           // System.out.println("increase to " + currentTime);
        }

    }

    /**
     *helper function that deletes if the end time is reached, calls charging of vehicle at the current time
     */
    public static void helpCharge() {
        //1 End Zeit schauen, und die richtigen rauslöschen
        for (fahrzeug v : list) {
            if (v.endTime <= currentTime) {
                v.printFail(currentTime, currentCapacity);
                if (isIllegal()) {
                    break;
                }
            }
        }

        //2 Startzeit schauen und anfangen zu calculaten
        //System.out.println("current element: " +  ((int) ((currentTime % 24) / interval)) +" current time " + currentTime + " current capacity " + currentCapacity);
    if(!list.isEmpty())
        help(currentCapacity, interval, list.size());
    else
        System.out.print("\nDone Calculating\n");
}

    /**
     * sees if a vehicle has already arrived in time and charges it, as long as it is not full
     * @param currentCapacity current charging capacity at the time
     * @param rest rest of time it is allowed to charge for(here one interval usually)
     * @param x x to see if we iterated through all vehicles
     */
    public static void help(double currentCapacity, double rest, int x) {
        i=0;
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
        } else if (x == 0){
                addEntry(new String[]{fahrzeug.cT(currentTime  +interval ),fahrzeug.cT(currentTime +  interval +interval ), String.valueOf(currentCapacity),"","","","","NONE"});
        }
    }

    /**
     * different variant of charging function, if the previous vehicle didn't charge for the whole time
     * @param currentCapacity current charging capacity at the time
     * @param rest rest of the time, here it is less than 1 intervall
     * @param x to see if we iterated through all vehicles
     * @param calledAgain will usually be true, to make sure this function is called instead of the other one
     */
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
            //System.out.println("No");
            help(currentCapacity, interval, --x, false);
        }else if (x == 0){
            addEntry(new String[]{fahrzeug.cT(currentTime + interval - rest),fahrzeug.cT(currentTime+ interval + interval  - rest), String.valueOf(currentCapacity),"","","","","NONE"});
         
        }
    }

    /**
     *checks if a deletion is allowed to be done and deletes object
     * @return true if deleted
     */
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

    /**
     *increases currently iterated object, goes to 0 if max is reached
     */
    public static void increaseI() {
        if (i == list.size() - 1) i = 0;
        else i++;
    }

    /**
     * writes the Titles of the columns
     */
    public static void writeNames(){
        output.add(new String[]{"Startzeit", "Endzeit","Ladeleistung in kW","Fahrzeug ID", "Fahrzeug Typ", "current charge in kWh", "charge remaining in kWh", "Status"});
    }
    public static void addEntry(String[] arr){
        //add method that writes entries of the right side of the output
        output.add(arr);
    }
    /**
     * @param f fahrzeug
     * @param t1 start time
     * @param t2 end time/ current time
     * @param c current capacity
     * @param ic incoming charge
     * @param status loading/success/fail
     */
    public static void addEntry(fahrzeug f, String t1,String t2, double c, double ic, String status){
        output.add(new String[]{t1,t2,Double.toString(fahrzeug.round(c)),Integer.toString(f.id),f.type,Double.toString(fahrzeug.round(ic)),Double.toString(fahrzeug.round(f.chargeRemaining)),status});
    }

    /**
     * returns the array list that contains the info for Excel
     * @return arrayList of String[] columns+rows
     */
    public static ArrayList<String[]> returnList (){
        return output;
    }
}
