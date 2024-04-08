import java.util.Comparator;

public class CustomComparator {
    /**
     * @return sorted by start time ascending
     */
    public static Comparator<fahrzeug> compareStart() {
        return new Comparator<fahrzeug>() {
            @Override
            public int compare(fahrzeug o1, fahrzeug o2) {
                return Double.compare(o1.startTime, o2.startTime);
            }
        };
    }

    /**
     * @return sorted by  ascending
     */
    public static Comparator<fahrzeug> compareEnd() {
        return new Comparator<fahrzeug>() {
            @Override
            public int compare(fahrzeug o1, fahrzeug o2) {
                return Double.compare(o1.endTime, o2.endTime);
            }
        };
    }
}