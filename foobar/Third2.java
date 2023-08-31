package foobar;

import java.math.BigInteger;

public class Third2 {
    public static int solution(String x) {
        BigInteger n = new BigInteger(x);
        int c = 0;
        while(n.compareTo(BigInteger.ONE) == 1){
            if(n.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0) n = n.divide(BigInteger.TWO);
            else if (n.mod(BigInteger.valueOf(4)).compareTo(BigInteger.ONE) == 0 || n.compareTo(BigInteger.valueOf(3)) == 0)
                n = n.subtract(BigInteger.ONE);
            else n = n.add(BigInteger.ONE);
            c++;
        }

        return c;
    }
    public static void main(String[] args){
        int sol = solution("4");
        System.out.println(sol);
    }
}
