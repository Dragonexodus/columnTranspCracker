package util;

import java.util.ArrayList;

/**
 * Created by celentano on 25.01.16.
 */
public class TranspositionListe extends ArrayList<ArrayList<Integer>> {

    public String getStringAt(int pos) {
        if (pos >= this.size()) {
            System.out.println("ERROR: TranspositionListe: getStringAt(): index is wrong !");
            return "";
        }
        String str = this.get(pos).toString();
        return str.replaceAll(("[\\[\\] ]"), "");
    }
}
