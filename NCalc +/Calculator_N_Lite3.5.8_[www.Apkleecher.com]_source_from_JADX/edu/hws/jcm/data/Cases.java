package edu.hws.jcm.data;

public class Cases {
    private int caseCt;
    private int[] cases;

    public Cases() {
        this.cases = new int[1];
    }

    public void clear() {
        this.caseCt = 0;
    }

    public void addCase(int value) {
        if (this.caseCt == this.cases.length) {
            int[] temp = new int[(this.caseCt * 2)];
            System.arraycopy(this.cases, 0, temp, 0, this.caseCt);
            this.cases = temp;
        }
        int[] iArr = this.cases;
        int i = this.caseCt;
        this.caseCt = i + 1;
        iArr[i] = value;
    }

    public boolean equals(Cases c) {
        if (c.caseCt != this.caseCt) {
            return false;
        }
        for (int i = 0; i < this.caseCt; i++) {
            if (c.cases[i] != this.cases[i]) {
                return false;
            }
        }
        return true;
    }
}
