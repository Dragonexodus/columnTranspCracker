package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Kandidat extends ArrayList<ZeichenListe> {

    private int blockLength;
    private int firstMatchRow;
    private int firstMatchCol;
    private int foundCount;

    public Kandidat() {
        this.foundCount = 0;
    }

    public static int factorial(int n) {
        return n <= 1 ? 1 : n * factorial(n - 1);
    }

    private boolean isCharExistBefor(char c, int pos) {
        for (int i = 0; i < pos; i++)
            if (this.get(i).get(0).getC() == c)
                return true;
        return false;
    }

    public int getPermutations() {
        int permutation = 1;

        for (int i = 0; i < this.size(); i++)
            if (this.get(i).size() > 1 && !isCharExistBefor(this.get(i).get(0).getC(), i))
                permutation *= factorial(this.get(i).size());
        return permutation;
    }

    private int getPermutationAtPos(int row, int col, int element) {
        int[][] temp = generatePermutations(this.get(element).getPosList());
        int value = 0;
        value = temp[row][col];
        return value;
    }

    public ArrayList<ArrayList<Integer>> getPermutationList() {
        ArrayList<Integer> permutation;
        ArrayList<ArrayList<Integer>> permutations = new ArrayList<ArrayList<Integer>>(getPermutations());
        int[][] temp = new int[getPermutations()][blockLength];

        for (int i = 0; i < blockLength; i++) {
            for (int j = 0; j < getPermutations() / this.get(i).size(); j++) {
                if (this.get(i).size() > 1)
                    for (int k = 0; k < factorial(this.get(i).size()); k++) {
                        temp[k * (j + 1)][i] = getPermutationAtPos(k, getCharRaletivePos(i), i);
                    }
                else
                    temp[j][i] = this.get(i).get(0).getPositionCol();
            }
        }

        //TODO: Test
        for (int n = 0; n < getPermutations(); n++) {
            for (int o = 0; o < blockLength; o++)
                System.out.print(temp[n][o]);
            System.out.println();
        }

        return permutations;
    }

    private int getCharRaletivePos(int pos) {
        int count = 0;
        for (int i = 0; i < pos; i++)
            if (this.get(i).get(0).getC() == this.get(pos).get(0).getC())
                count++;
        return count;
    }

    public void printPermutationList(ArrayList<ArrayList<Integer>> list) {
        for (int i = 0; i < list.size(); i++)
            System.out.print("Per" + i + ": " + list.get(i));
    }

    public void print() {
        for (int i = 0; i < this.size(); i++) {
            System.out.print("char: " + this.get(i).get(0).getC() + ", pos: ");
            for (int j = 0; j < this.get(i).size(); j++) {
                System.out.print(this.get(i).get(j).getPositionCol() + ", ");
            }
            System.out.println();
        }
    }

    public void setFirstMatch(int blockLength, int firstMatchRow, int firstMatchCol) {
        this.blockLength = blockLength;
        this.firstMatchRow = firstMatchRow;
        this.firstMatchCol = firstMatchCol;
    }

    public char getLastChar() {
        if (this.get(this.size() - 1).size() > 0)
            return this.get(this.size() - 1).get(0).getC();
        else {
            System.out.println("ERROR: Kandidat: ZeichenListe ist leer !");
            return 0;
        }
    }

    public void appendSave(ZeichenListe liste) {
        if (this.size() > 0)
            if (this.getLastChar() == liste.getChar())
                this.set(this.size() - 1, liste);
            else
                this.add(liste);
        else
            this.add(liste);
    }

    public int getBlockLength() {
        return blockLength;
    }

    public void setBlockLength(int blockLength) {
        this.blockLength = blockLength;
    }

    public int getFirstMatchRow() {
        return firstMatchRow;
    }

    public void setFirstMatchRow(int firstMatchRow) {
        this.firstMatchRow = firstMatchRow;
    }

    public int getFirstMatchCol() {
        return firstMatchCol;
    }

    public void setFirstMatchCol(int firstMatchCol) {
        this.firstMatchCol = firstMatchCol;
    }

    public int getFoundCount() {
        return foundCount;
    }

    public void setFoundCount(int foundCount) {
        this.foundCount = foundCount;
    }

    public static int[][] generatePermutations(ArrayList<Integer> list) {
        int n = list.size();
        int[][] a = new int[factorial(n)][n];
        for (int i = 0; i < n; i++)
            a[0][i] = list.get(i);
        for (int i = 1; i < a.length; i++) {
            a[i] = Arrays.copyOf(a[i - 1], n);
            int k, l;
            for (k = n - 2; a[i][k] >= a[i][k + 1]; k--) ;
            for (l = n - 1; a[i][k] >= a[i][l]; l--) ;
            swap(a[i], k, l);
            for (int j = 1; k + j < n - j; j++) swap(a[i], k + j, n - j);
        }
        return a;
    }

    private static void swap(int[] is, int k, int l) {
        int tmp_k = is[k];
        int tmp_l = is[l];
        is[k] = tmp_l;
        is[l] = tmp_k;
    }
}
