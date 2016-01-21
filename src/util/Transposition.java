package util;

import java.util.ArrayList;
import java.util.List;

public class Transposition {

    private final List<Integer> PERMUTATION;
    private final int BLOCK_LENGTH;

    public Transposition(int blockLength) {

        this.BLOCK_LENGTH = blockLength;

        this.PERMUTATION = new ArrayList<Integer>();
        for (int i = 0; i < this.BLOCK_LENGTH; i++) {
            this.PERMUTATION.add(i);
        }
    }

    public Transposition(String permutation) {
        if (permutation != null) {
            String s[];
            s = permutation.split(",");
            if (s.length > 1) {
                this.BLOCK_LENGTH = s.length;
                this.PERMUTATION = new ArrayList<Integer>(this.BLOCK_LENGTH);
                for (int i = 0; i < BLOCK_LENGTH; i++)
                    PERMUTATION.add(i, Integer.parseInt(s[i]));
                for (Integer it : PERMUTATION) {
//                System.out.println(it); // TODO: ich habe kommentiert, da gestört hat
                }
                return;
            }
        }
        BLOCK_LENGTH = 0;
        PERMUTATION = null;
    }

    public List<Integer> getTransposition() {
        return this.PERMUTATION;
    }

    public int getBlockLength() {
        return this.BLOCK_LENGTH;
    }


}
