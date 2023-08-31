package foobar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Third3 {
    public static int[] solution(int[][] m) {
        if (m.length == 1) return new int[]{1, 1};
        int[] denominators = Arrays.stream(m).mapToInt(el -> vectorSum(el)).toArray();
        Map<String, Fraction[][]> qAndr = separateQAndR(m, denominators);
        Fraction[][] q = qAndr.get("q");
        Fraction[][] iM = identityMatrix(qAndr.get("q"));
        Fraction[][] preN = substract(q, iM);
        Fraction[][] N = inverse(preN);
        Fraction[][] p = multiplyMatrix(N, qAndr.get("r"));
        int[] dens = getDens(p);
        int lcm = Math.abs(LcmOfArray(Arrays.stream(dens).filter(x -> x != 0).toArray(), 0));
        int[] format = formatAnswer(lcm, p[0], p[0].length);

        return format;
    }

    private static int[] formatAnswer(int lcm, Fraction[] p0, int length) {
        int[] format = new int[length + 1];
        for (int i = 0; i < length; i++) {
            if (p0[i].getDenominator() == 0) format[i] = 0;
            else {
                int factor = lcm / Math.abs(p0[i].getDenominator());
                format[i] = factor * Math.abs(p0[i].getNumerator());
            }
        }
        format[length] = lcm;
        return format;
    }

    private static int[] getDens(Fraction[][] p) {
        int[] dens = new int[p[0].length];
        for (int i = 0; i < p[0].length; i++) {
            dens[i] = p[0][i].getDenominator();
        }
        return dens;
    }

    private static Map<String, Fraction[][]> separateQAndR(int[][] m, int[] denominators) {
        int[] proto = Arrays.stream(denominators).filter(x -> x == 0).toArray();
        int qLength = Math.abs(denominators.length - proto.length);
        Fraction[][] q = new Fraction[qLength][qLength];
        Fraction[][] r = new Fraction[qLength][proto.length];

        int rCol = 0;
        int row = 0;
        int qCol = 0;

        Map<String, Fraction[][]> qAndR = new HashMap<>();
        for (int i = 0; i < m.length; i++) {
            if (denominators[i] == 0) {
                continue;
            }
            for (int j = 0; j < m[0].length; j++) {
                if (denominators[j] == 0) {
                    r[row][rCol] = new Fraction(m[i][j], denominators[i]);
                    rCol++;
                } else {
                    q[row][qCol] = new Fraction(m[i][j], denominators[i]);
                    qCol++;
                }
            }
            row++;
            qCol = 0;
            rCol = 0;
        }


        qAndR.put("q", q);
        qAndR.put("r", r);
        return qAndR;
    }

    private static Fraction[][] identityMatrix(Fraction[][] q) {
        Fraction[][] matrix = new Fraction[q.length][q.length];
        for (int i = 0; i < q.length; i++) {
            for (int j = 0; j < q.length; j++) {
                if (j == i) {
                    matrix[i][j] = new Fraction(1, 1);
                } else {
                    matrix[i][j] = new Fraction(0, 0);
                }
            }
        }
        return matrix;
    }

    private static Fraction[][] substract(Fraction[][] q, Fraction[][] iM) {
        for (int i = 0; i < q.length; i++) {
            for (int j = 0; j < q.length; j++) {
                q[i][j].subtract(iM[i][j]);
            }
        }
        return q;
    }


    public static Fraction[][] multiplyMatrix(Fraction[][] A, Fraction[][] B) {
        Fraction[][] out = new Fraction[A.length][B[0].length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                out[i][j] = new Fraction(0, 1);

                for (int k = 0; k < B.length; k++) {
                    out[i][j] = out[i][j].add(B[k][j].multiplyWith(A[i][k]));
                }
            }
        }

        return out;
    }

    private static int vectorSum(int[] v) {
        return Arrays.stream(v).sum();
    }

    static int __gcd(int a, int b) {
        return b == 0 ? a : __gcd(b, a % b);
    }

    public static int LcmOfArray(int[] arr, int idx) {
        if (idx == arr.length - 1) {
            return arr[idx];
        }

        int a = arr[idx];
        int b = LcmOfArray(arr, idx + 1);
        return (a * b / __gcd(a, b));
    }


    static Fraction[][] getCofactor(Fraction A[][], int p, int q, int n) {
        Fraction[][] temp = new Fraction[A.length][A.length];
        int i = 0, j = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp[i][j++] = A[row][col];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
        return temp;
    }

    static Fraction determinant(Fraction A[][], int n) {
        Fraction D = new Fraction(0, 0);

        if (n == 1) return A[0][0];

        int sign = 1;

        for (int f = 0; f < n; f++) {
            Fraction[][] temp = getCofactor(A, 0, f, n);
            Fraction D1 = new Fraction(A[0][f].getNumerator() * sign, A[0][f].getDenominator());
            D = D.add(D1.multiplyWith(determinant(temp, n - 1)));
            sign = -sign;
        }

        return D;
    }

    static Fraction[][] adjoint(Fraction A[][]) {
        Fraction[][] adj = new Fraction[A.length][A.length];
        if (A.length == 1) {
            adj[0][0] = new Fraction(1, 1);
            return adj;
        }

        int sign = 1;

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                Fraction[][] temp = getCofactor(A, i, j, A.length);

                sign = ((i + j) % 2 == 0) ? 1 : -1;

                adj[j][i] = (determinant(temp, A.length - 1)).multiplyWith(new Fraction(sign, 1));
            }
        }
        return adj;
    }

    static Fraction[][] inverse(Fraction A[][]) {
        Fraction[][] inverse = new Fraction[A.length][A.length];
        Fraction det = determinant(A, A.length);
        if (det.getDenominator() == 0) {
            return inverse;
        }

        Fraction[][] adj = adjoint(A);

        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A.length; j++)
                inverse[i][j] = adj[i][j].divideWith(det);

        return inverse;
    }
}

class Fraction {
    private int numerator;
    private int denominator;

    public Fraction(int numerator, int denominator) {
        if (numerator == 0) denominator = 0;
        this.numerator = numerator;
        this.denominator = denominator;
        reduce();
    }

    public Fraction multiplyWith(Fraction fraction) {
        return new Fraction(this.numerator * fraction.getNumerator(),
                this.denominator * fraction.getDenominator());
    }

    public Fraction divideWith(Fraction fraction) {
        int newNumerator = this.numerator * fraction.getDenominator();
        int newDenominator = this.denominator * fraction.getNumerator();
        return new Fraction(newNumerator, newDenominator);
    }

    public void subtract(Fraction fractionTwo) {
        if (fractionTwo.getDenominator() == 0) {
            return;
        }
        if (this.denominator == 0) {
            this.numerator -= fractionTwo.getNumerator();
            this.denominator = fractionTwo.getDenominator();
            return;
        }
        int newNumerator = (numerator * fractionTwo.denominator) -
                (fractionTwo.numerator * denominator);
        int newDenominator = denominator * fractionTwo.denominator;
        this.numerator = newNumerator;
        this.denominator = newDenominator;
    }

    public Fraction add(Fraction fractionTwo) {
        if (fractionTwo.getDenominator() == 0) {
            return new Fraction(this.numerator, this.denominator);
        }
        if (this.denominator == 0) {
            return new Fraction(fractionTwo.getNumerator(), fractionTwo.denominator);
        }
        int newNumerator = (numerator * fractionTwo.denominator) +
                (fractionTwo.numerator * denominator);
        int newDenominator = denominator * fractionTwo.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    public int calculateGCD(int numerator, int denominator) {
        if (numerator % denominator == 0) {
            return denominator;
        }
        return calculateGCD(denominator, numerator % denominator);
    }

    void reduce() {
        if (numerator == 0 || denominator == 0) return;
        int gcd = calculateGCD(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
    }


    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

}
