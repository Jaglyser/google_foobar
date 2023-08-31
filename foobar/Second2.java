package foobar;

import java.util.Arrays;

public class Second2 {
    public static int convert(int maX, int qX){
        int prev = -1;
        int curr = maX;
        int subtree = maX;

        if (curr == qX) return prev;

        prev = curr;

        while (subtree > 1){
            subtree = subtree >> 1;

            int left = curr - subtree - 1;
            int right = curr - 1;

            if(qX == right || qX == left) return prev;

            if (qX < left) curr = left;
            else curr = right;

            prev = curr;
        }
        return -1;

    }
    public static int[] solution(int h, int[] q) {
        int maX = (int) Math.pow(2, h) - 1;
        return Arrays.stream(q).map(qX -> convert(maX ,qX)).toArray();
    }
    public static void main(String[] args){
        int[] sol = solution(5, new int[]{19, 14, 28});
        System.out.println(Arrays.toString(sol));
    }
}
