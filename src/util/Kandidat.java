package util;

import java.util.ArrayList;
import java.util.Arrays;

public class Kandidat extends ArrayList<ZeichenListe> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int blockSize;
    private int firstMatchRow;
    private int firstMatchCol;
    private int foundCount;

    enum TYPE {
        CASE_1,
        CASE_2
    }

    private TYPE type;
    TranspositionListe permutationsListe;

    public Kandidat(TYPE type) {
        this.foundCount = 0;
        this.type = type;
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

    private int getPermutations() {
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

    public TranspositionListe getPermutationList() {
        ArrayList<Integer> permutation;

        if (permutationsListe == null) {
            permutationsListe = new TranspositionListe(getPermutations());

            int[][] temp = new int[getPermutations()][blockSize]; //TODO: this.size()

            for (int i = 0; i < this.size(); i++) {                                             // Anzahl der Tupel-Paare   //TODO: this.size()
                for (int j = 0; j < getPermutations() / factorial(this.get(i).size()); j++) {   //
                    if (this.get(i).size() > 1)
                        for (int k = 0; k < factorial(this.get(i).size()); k++) {
                            temp[k * (j + 1)][i] = getPermutationAtPos(k, getCharRaletivePos(i), i);
                        }
                    else
                        temp[j][i] = this.get(i).get(0).getPositionCol();
                }
            }
            for (int i = 0; i < getPermutations(); i++) {
                permutation = new ArrayList<>(blockSize);         //TODO: this.size()
                for (int j = 0; j < blockSize; j++)               //TODO: this.size()
                    permutation.add(temp[i][j]);
                permutationsListe.add(permutation);
            }
            if (type == TYPE.CASE_1)
                permutationsListe.removeWrongPermutations();
        }
        return this.permutationsListe;
    }

    private int getCharRaletivePos(int pos) {
        int count = 0;
        for (int i = 0; i < pos; i++)
            if (this.get(i).get(0).getC() == this.get(pos).get(0).getC())
                count++;
        return count;
    }

    public void printPermutationList() {
        if (permutationsListe == null)
            getPermutationList();
        for (int i = 0; i < permutationsListe.size(); i++)
            System.out.println("Per" + i + ": " + permutationsListe.get(i));
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
        this.blockSize = blockLength;
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

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
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

    private int[][] generatePermutations(ArrayList<Integer> list) {
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

    private void swap(int[] is, int k, int l) {
        int tmp_k = is[k];
        int tmp_l = is[l];
        is[k] = tmp_l;
        is[l] = tmp_k;
    }
}
