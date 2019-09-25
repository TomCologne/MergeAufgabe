package merge;

import merge.impl.MergeAPIImpl;

/**
 *
 * Command line Interface fuer das Merge-Programm.
 * 
 * @author mgxa2d
 */
public class MergeCLI {

    /**
     * Startet das command line tool.
     * 
     * @param args Erwartet durch Leerzeichen getrennte Intervalle.
     */
    public static void main(String[] args) {
        String params = String.join(" ", args);
        if (!checkHelp(params)) {
            merge(params);
        }       
    }    
    
    /**
     * Zeigt bei Bedarf die Hilfe an.
     * 
     * @param params
     * @return true, wenn die Hilfe angezeigt wurde.
     */
    static boolean checkHelp(String params) {
        boolean help = params.trim().isEmpty() || params.toLowerCase().contains("hilfe") || params.toLowerCase().contains("help");
        if (help) {
            usage();
        }
        return help;
    }
    
    /**
     * Startet den Mergeprozess.
     * 
     * @param params Der eingegebene Parameterstring.
     */
    static void merge(String params) {
        System.out.println(new MergeAPIImpl().merge(params));  
    }
    
    /**
     * Zeigt die Hilfe an.
     * 
     */
    public static void usage() {
        System.out.println("Aufruf:");
        System.out.println("java -jar merge.jar( [<minInt>,<maxInt>])*");
    }
}
