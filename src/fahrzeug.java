import java.text.DecimalFormat;
import java.text.NumberFormat;

public class fahrzeug {
    int id;
    double startTime;
    double endTime;
    String type;
    double rangeCurrent;
    double rangeNeeded;
    double verbrauch; //kWh
    double chargeRemaining;
    DecimalFormat numberFormat = new DecimalFormat("00,00");
    int priority;

    /**
     * @param id id of vehicle
     * @param startTime arrival time
     * @param endTime departure time
     * @param type type of vehicle to match fuel consumption
     * @param rangeCurrent range of vehicle at arrival time
     * @param rangeNeeded range it should have after leaving
     */
    public fahrzeug(int id, double startTime, double endTime, String type, double rangeCurrent, double rangeNeeded) {

        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime + 24.0;
        this.type = type;
        this.rangeCurrent = rangeCurrent;
        this.rangeNeeded = rangeNeeded;
        switch (type) {
            case "Kleinbus" -> {
                verbrauch = 26.1;
                this.type+="   ";
            }
            case "Transporter" -> verbrauch = 29.7;
            case "Auto" -> {
                verbrauch = 20.0;
                this.type+="       ";
            }
        }
        this.chargeRemaining = ((rangeNeeded - rangeCurrent) * verbrauch) / 100.0;
        System.out.println("new " + type + " "+ id+ " from " + cT(startTime) + " to " + cT(endTime) + " with needed Charge " + numberFormat.format(chargeRemaining) + "kWh");

    }

    /**
     * updates the charge based on charge capacity and duration, returns -1 if it is not fully charged
     * @param charge capacity
     * @param startTime duration
     * @param endTime duration
     * @return -1 if not fully charged, time if it charges full
     */
    double updateCharge(double charge, double startTime, double endTime) {
        double time = endTime-startTime;
        double incomingCharge;
        if (chargeRemaining - charge*time > 0) {
            incomingCharge =charge*time;
            chargeRemaining -= incomingCharge;
            System.out.println("      " +this.type + " " + this.id + " at " + cT(startTime) + " to "+cT(endTime) + " charging with current charge "+ numberFormat.format(incomingCharge)+" kWh, Charge remaining " + numberFormat.format(this.chargeRemaining) + " kWh " + " current capacity " +charge +" kW "+ "     ");
            return -1.0;
        } else {
            double temp = chargeRemaining/ (charge*time);
            incomingCharge = charge *(temp * time);
            chargeRemaining = 0;
            System.out.println("----- " + this.type + " " + this.id + " at "+ cT(startTime)  + " to "+cT(startTime+ (temp *time)) + " CHARGED, with current charge "+ numberFormat.format(incomingCharge)+ " kWh, Charge remaining "  + numberFormat.format(this.chargeRemaining)+" kWh "+ " current capacity " +charge +" kW " +"-----" );
            return temp* time ;
        }
    }
    public void printFail(double time){
        System.out.println("----- " +this.type + " " + this.id + " at " + cT(time) + " FAILED with charge remaining: " + numberFormat.format(this.chargeRemaining) + " kWh " +"                                                               " + "-----"     );
    }

    /**
     * @param time prints the status of the current vehicle at a certain time
     */
    public void printStatus(double time) {
        if (round(time) >= round(this.endTime)) {
            System.out.println(this.type + " " + this.id + " at " + cT(time) + " FAILED with charge remaining: " + numberFormat.format(this.chargeRemaining) + " kWh" );
        } else if (this.chargeRemaining == 0.00) {
            System.out.println(this.type + " " + this.id + " at " + cT(time) + " CHARGED, remaining " + numberFormat.format(this.chargeRemaining) + " kWh");
        }else System.out.println(this.type + " " + this.id + " at "+ cT(time)  + " charging with current charge remaining " + numberFormat.format(this.chargeRemaining)+" kWh");
    }

    /**
     * @param timeStart prints the status of the current vehicle at a certain time
     * @param timeEnd prints the status of the current vehicle at a certain time
     * @param charge prints the status of the current vehicle at a certain time
     */
    public void printStatus(double timeStart, double timeEnd, double charge) {
        if (round(timeEnd) >= round(this.endTime)) {
            System.out.println(this.type + " " + this.id + " at " + cT(timeEnd) + " FAILED with charge remaining: " + numberFormat.format(this.chargeRemaining) + " kWh" );
        } else if (this.chargeRemaining == 0.00) {
            System.out.println(this.type + " " + this.id + " from " + cT(timeStart)  + " to "+ cT(timeEnd) + " CHARGED, remaining " + numberFormat.format(this.chargeRemaining));
        }else System.out.println(this.type + " " + this.id + " from "+ cT(timeStart)  + " to "+ cT(timeEnd)  + " charging with current charge remaining " + numberFormat.format(this.chargeRemaining)+" kWh" + " current charge capacity: " + numberFormat.format(charge)  + " kWh" + " charging for " + numberFormat.format(((endTime-startTime)*charge/100)/0.25)+ " kWh");
    }
    /**
     * @param time time to convert
     * @return time in hour:minute format
     */
    public static String cT(double time) {
        DecimalFormat numberFormat = new DecimalFormat("00");
        time = time % 24;
        int hour = (int) time;
        double minute = (time - hour) * 60;
        int minuteInt = (int) minute;
        double second = (minute - minuteInt) * 60;
        int secondInt = (int) second;
        return numberFormat.format(hour) + ":" + numberFormat.format(minuteInt) + ":" + numberFormat.format(secondInt);
    }

    /**
     * @param n number to round
     * @return round to 2nd decimal space
     */
    public static double round(double n){
        int t = (int) (n * 100);
        return t/100.0;
    }

    /**
     * @param charge capacity
     * @return -1 if not fully charged, time if it charges full
     */
    double updateCharge(double charge) {

        if (chargeRemaining - charge > 0) {
            chargeRemaining -= charge;
            return -1.0;
        } else {
            double temp = chargeRemaining/ charge;
            chargeRemaining = 0;

            return temp ;
        }
    }

    void setPriority(int p) {
        priority = p;
    }

    void decreasePriority() {
        priority = -1;
    }

}
