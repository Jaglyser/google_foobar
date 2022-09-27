package foobar;

import java.util.HashSet;
import java.util.Set;

public class first {
    private static int divide(String string) {
        for (int i = string.length(); i > 0; i--) {
            Set<String> parts = new HashSet<>();

            if (string.length() % i != 0) {
                continue;
            }

            int partitions = string.length() / i;

            int start = 0;
            int end = partitions;

            while (end <= string.length()) {
                String tempString = new String(string);
                parts.add(tempString.substring(start, end));

                if (parts.size() != 1) {
                    break;
                }

                start = end;
                end += partitions;
            }

            if(parts.size() != 1){
                continue;
            }

            return i;
        }
        return 0;
    }

    public static int solution(String x) {
        return divide(x);
    }

    public static void main(String[] args){
        System.out.println(divide("abccbaabccba"));
    }
}
