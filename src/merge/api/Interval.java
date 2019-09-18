package merge.api;

/**
 *
 * Repr&auml;sentiert ein Intervall nach aussen.
 *
 * Aktuell nicht implementiert, aber man k&ouml;nnte eine weitere
 * Interfacemethode auf Intervall- statt auf Stringebene anbieten.
 *
 * @author mgxa2d
 */
public interface Interval {

    /**
     *
     * @return min Die untere Schranke des Intervalls.
     */
    int getMin();

    /**
     *
     * @return max, Die obere Schranke des Intervalls.
     */
    int getMax();
}
