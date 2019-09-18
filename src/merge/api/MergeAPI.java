package merge.api;

/**
 * Mit einer Factory k&ouml;nnte man mehrere Implementierungen des Algorithmus
 * anbieten.
 * 
 * @author mgxa2d
 */
public interface MergeAPI {
    
    /**
     * 
     * Mergt die &uuml;bergebenen Intervalle entspr. der Aufgabenstellung.
     * 
     * @param intervallString Erwartet Intervalle der Form [1,2] [3,4] [5,6]
     * @return Die gemergte Ergebnismenge.
     */
    String merge(String intervallString);
}
