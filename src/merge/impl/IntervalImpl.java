/**
 *
 */
package merge.impl;

import merge.api.Interval;

/**
 * Repraesentiert ein Intervall.
 *
 * @author mgxa2d
 */
public final class IntervalImpl implements Interval {

    private static final String REGX_BRACKETS = "\\[|\\]";
    private static final String SEPARATOR = ",";

    private int min, max;

    /**
     *
     * @param min int, min &lt; max!
     * @param max int, max &gt; min!
     */
    public IntervalImpl(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Erwartet ein Intervall als Eingabe.
     *
     * Es findet keine &Uuml;berpr&uuml;fung mehr statt. Entspricht der String
     * interval nicht den Vorgaben wird eine NumberFormatException geworfen.
     *
     * @param interval Ein einzelnes Intervall der Form: '[1,2]'
     * @throws NumberFormatException wenn Intervall leer oder syntaktisch
     * falsch.
     */
    public IntervalImpl(String interval) {
        String[] a = interval.replaceAll(REGX_BRACKETS, "").trim().split(SEPARATOR);
        min = Integer.parseInt(a[0]);
        max = Integer.parseInt(a[1]);
    }

    /**
     * &Uuml;berpr&uuml;ft die Validit&auml;t des Intervalls.
     *
     * @return true, wenn g&uuml;ltig.
     */
    public boolean isValid() {
        return max - min >= 0;
    }

    /**
     * Zeigt eine &Uuml;berschneidung an.
     *
     * @param b Ein Intervall
     * @return true, wenn es eine gemeinsame Schnittmenge gibt, false, wenn
     * mind. ein Intervall invalide ist oder es keine gemeinsame Schnittmenge
     * gibt.
     */
    public boolean intersects(IntervalImpl b) {
        return intersects(b.min, b.max);
    }

    /**
     * Zeigt eine &Uuml;berschneidung an.
     *
     * @param min, untere Schranke
     * @param max, obere Schranke
     * @return true, wenn es eine gemeinsame Schnittmenge gibt, false, wenn
     * mind. ein Intervall invalide ist oder es keine gemeinsame Schnittmenge
     * gibt.
     */
    public boolean intersects(int min, int max) {
        return isValid() && min <= max && !(this.min > max || this.max < min);
    }

    /**
     *
     * Achtung: Es findet keine Ueberpruefung mehr statt!
     *
     * @param b Das zu mergende Intervall.
     * @return this
     */
    public IntervalImpl merge(IntervalImpl b) {
        merge(b.min, b.max);
        return this;
    }

    /**
     * Achtung: Es findet aus Performanzgruenden keine Ueberpruefung mehr statt!
     *
     * merge() sollte nur nach einem intersect() aufgerufen werden.
     *
     * @param min
     * @param max
     */
    public void merge(int min, int max) {
        if (min < this.min) {
            this.min = min;
        }
        if (max > this.max) {
            this.max = max;
        }
    }

    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        return String.format("[%d,%d]", getMin(), getMax());
    }

    /**
     *
     * @return min, Die untere Schranke.
     */
    public int getMin() {
        return min;
    }

    /**
     *
     * @return max, Die obere Schranke.
     */
    public int getMax() {
        return max;
    }
}
