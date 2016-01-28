package util;

import java.util.ArrayList;

/**
 * Created by celentano on 25.01.16.
 */
public class TranspositionListe extends ArrayList<ArrayList<Integer>> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TranspositionListe(int length) {
        super(length);
    }

    public String getStringAt(int pos) {
        if (pos >= this.size()) {
            System.out.println("ERROR: TranspositionListe: getStringAt(): index is wrong !");
            return "";
        }
        String str = this.get(pos).toString();
        return str.replaceAll(("[\\[\\] ]"), "");
    }

    public void removeWrongPermutations() {
        for (int i = 0; i < this.size(); i++) {
            boolean flag = false;

            for (int j = 0; j < this.get(i).size(); j++) {
                if (flag)
                    break;
                for (int k = j + 1; k < this.get(i).size(); k++) {
                    if (this.get(i).get(j) == this.get(i).get(k))
                        flag = true;
                }
            }
            if (flag) {
                this.remove(i);
                i--;
            }
        }
    }
}
