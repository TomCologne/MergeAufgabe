package merge.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import merge.api.MergeAPI;

/**
 *
 * Diese Klasse setzt den Merge-Algorithmus konkret um.
 *
 * Die Grundidee:
 *
 * Input: Ein Intervallstring der Form: [1,5] [3,7] [5,6] [11,12]
 *
 * Output: Ueberlappende Intervalle gemergt. [1,7] [11,12]
 *
 * Die Eingabe wird geparst, in Objekte vom Typ IntervalImpl ueberfuehrt und in
 * einem TreeSet nach Min-Wert sortiert.
 *
 * Auf diese Weise muessen nur noch benachbarte Intervalle betrachtet werden und
 * die Laufzeit reduziert sich auf O(n) fuer diesen Schritt, ausserdem ist die
 * Ergebnismenge nie groesser als notwendig.
 *
 * Intervalle mit min &gt; max werden nicht gemergt und an den Anfang sortiert.
 *
 * @author mgxa2d
 */
public class MergeAPIImpl implements MergeAPI {

    // Eingabevalidierung kann fuer Lasttests oder große Eingabemengen abgestellt werden.
    private static final boolean VALIDATE_INPUT = false;
    // Begrenzung der Eingabevalidierung.
    private static final int VALIDATE_INPUT_MAX_LENGTH = 10000;
    public static final String PARSE_ERROR = "Parse-Fehler: Bitte überprüfen Sie die übergebenen Intervalle!";
    private static final String SEPARATOR = " ";
    private static final String REGX_INTERVALS = "(\\[\\d+,\\d+\\][ ]{0,1})*";

    private final List<IntervalImpl> resultList;

    /**
     * Konstruktor
     *
     */
    public MergeAPIImpl() {
        this.resultList = new ArrayList<>();
    }

    /**
     * Reduce ueberprueft, ob zwei benachbarte Intervalle der sortierten Liste
     * eine gemeinsame Schnittmenge besitzen. Ist dies der Fall wird a mit b
     * gemergt. Wenn nicht wird a in die Ergebnismenge uebernommen und mit b
     * weiter gemacht.
     *
     * Am Ende wird die Ergebnismenge als String zurueckgeliefert.
     *
     * Die Eingabevalidierung kann fuer Lasttests ueber einen Schalter abgestellt
     * werden.
     * 
     * @param intervallString Bspw. "[1,2] [3,4] [3,6]"
     * @return Die gemergten Intervalle "[1,2] [3,6]"
     */
    @Override
    public String merge(String intervallString) {
        if (isNullOrEmpty(intervallString)) {
            return "";
        }

        if (VALIDATE_INPUT && !matchesIntervals(intervallString)) {
            return PARSE_ERROR;
        }

        Optional<IntervalImpl> last = getSortedSet(intervallString)
                .stream()
                .reduce((a, b) -> a.intersects(b) ? a.merge(b) : appendResultList(a, b));

        resultList.add(last.get());
        return toString();
    }

    /**
     * Ueberprueft, ob die Eingabe der Spezifikation entspricht.
     *
     * Achtung: bei sehr großen Eingabemengen fliegt ggf. ein StackOverflow.
     * 
     * Aus diesem Grund wird die Eingabevalidierung aber einem Schwellwert
     * {@link MergeAPIImp#VALIDATE_INPUT_MAX_LENGTH} abgestellt.
     *
     * @param intervalString
     * @return
     */
    boolean matchesIntervals(String intervalString) {
        if (intervalString.length() > VALIDATE_INPUT_MAX_LENGTH) {
            return true;
        }
        Pattern pattern = Pattern.compile(REGX_INTERVALS);
        return pattern.matcher(intervalString).matches();
    }

    /**
     * Die Methode wird aufgerufen, wenn im Reduce-Teil zwei benachbarte
     * Intervalle keine gemeinsame Schnittmenge besitzen.
     *
     * In diesem Fall besitzt a durch die Sortierung auch keine weitere
     * Schnittmenge mehr mit einem beliebigen Folgeelement und kann somit in die
     * Ergebnismenge aufgenommen werden. b wird dann als neues Vergleichselement
     * zurueckgegeben.
     *
     * @param a {@link IntervalImpl} Linkes Intervall
     * @param b {@link IntervalImpl} Rechtes Intervall
     * @return b {@link IntervalImpl}
     */
    private IntervalImpl appendResultList(IntervalImpl a, IntervalImpl b) {
        resultList.add(a);
        return b;
    }

    /**
     * Der Intervallstring wird zerlegt, in einzelne Objekte vom Typ
     * IntervalImpl ueberfuehrt, und nach unteren Klammern aufsteigend sortiert.
     * Durch die Sortierung vereinfacht sich der Merge-Algorithmus.
     *
     * @param intervallString Intervalle in der Form "[1,2] [3,4] [5,6]"
     * @return {@link TreeSet#IntervalImpl}
     */
    TreeSet<IntervalImpl> getSortedSet(String intervallString) {
        if (isNullOrEmpty(intervallString)) {
            return new TreeSet<>();
        } else {
            Supplier<TreeSet<IntervalImpl>> supplier = () -> new TreeSet<>(getComparator());
            return Arrays
                    .asList(intervallString.split(SEPARATOR))
                    .stream()
                    .map(oneInterval -> new IntervalImpl(oneInterval))
                    .collect(Collectors.toCollection(supplier));
        }
    }

    /**
     * Sortiert aufsteigend nach den Min-Werten. Sind diese gleich, wird auch
     * noch nach Max-Werten sortiert. Dies ist notwendig, da ansonsten bei
     * gleichen Min-Werten Gleichheit entstehen, und somit das 2. Intervall aus
     * der Liste verschwinden wuerde.
     *
     * @return {@link Comparator#IntervalImpl}
     */
    private Comparator<IntervalImpl> getComparator() {
        return (a, b) -> {
            int d = a.getMin() - b.getMin();
            int ret = d != 0 ? d : a.getMax() - b.getMax();
            return a.isValid() == b.isValid() ? ret : a.isValid() ? 1 : -1;
        };
    }

    /**
     *
     * @param s
     * @return
     */
    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        return toString(resultList);
    }

    /**
     *
     * @param c {@link Collection}
     * @return
     */
    private String toString(Collection c) {
        StringBuilder sb = new StringBuilder();
        c.forEach(interval -> sb.append(interval.toString()).append(SEPARATOR));
        return sb.toString().trim();
    }
}
