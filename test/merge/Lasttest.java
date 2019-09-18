package merge;

import merge.impl.MergeAPIImpl;
import java.util.Random;
import org.junit.Test;

/**
 *
 * @author mgxa2d
 */
public class Lasttest {

    // Grenzen der Intervalle.
    static int min = -100,max=10000;
    @Test
    public void testLast() {
        int runs = 8;
        String[] input = new String[runs];
        for (int i = 1; i < runs;i++ ) {
            input[i] = generateInputString((int) Math.pow(10, i));
        }
        System.gc();
        // Warmlauf:
        merge(input[2]);
        for (int i = 1; i < runs;i++ ) {
            System.out.println(String.format("Elemente:%10s\tDauer:%10s ms",(int) Math.pow(10, i),merge(input[i])));
        }
        if (true) return;
    }

    
    long merge(String s) {
        //System.out.println(s);
        long start = System.currentTimeMillis();
        String result = new MergeAPIImpl().merge(s);
        
        return System.currentTimeMillis() - start;
    }
    
    private String generateInputString(int elements) {
        StringBuilder sb = new StringBuilder();
        for (int i =0; i < elements;i++) {
            int minVal = getInt();
            sb.append(getInterval(minVal, getInt(minVal,max+1)));
        }
        return sb.toString();
    }
    
    
    public String getInterval(int min,int max) {
        return String.format("[%d,%d] ",min,max);
    }
    
     private static int getInt() {
        return getInt(min,max);
    }
     
    private static int getInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
