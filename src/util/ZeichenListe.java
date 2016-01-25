package util;

import java.util.ArrayList;

/**
 * Created by celentano on 23.01.16.
 */
public class ZeichenListe extends ArrayList<Zeichen> {

    public char getChar() {
        if (this.size() > 0)
            return this.get(0).getC();
        else
            System.out.println("ERROR: ZeichenListe: keine Elemente !");
        return 0;
    }

    public ArrayList<Integer> getPosList() {
        ArrayList<Integer> list = new ArrayList<Integer>(this.size());

        for (int i = 0; i < this.size(); i++)
            list.add(this.get(i).getPositionCol());
        return list;
    }
}
