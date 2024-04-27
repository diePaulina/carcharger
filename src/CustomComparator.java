import java.util.Comparator;

public class CustomComparator {
    /**
     * @return sorted by start time ascending
     */
    public static Comparator<fahrzeug> compareStart() {
        return Comparator.comparingDouble(o -> o.startTime);
    }

    /**
     * @return sorted by  ascending
     */
    public static Comparator<fahrzeug> compareEnd() {
        return Comparator.comparingDouble(o -> o.endTime);
    }
}