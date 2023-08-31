package foobar;

public class Third1 {
    public static int solution(int[] l) {
        int[] c = new int[l.length];
        int count = 0;
        for(int i = 0; i < l.length; i++){
            for(int j = 0; j < i; j++){
                if(l[i] % l[j] == 0) {
                    c[i] = c[i] +1;
                    count += c[j];
                }
            }
        }
        return count;
    }
    public static void main(String[] args){
        int s = solution(new int[]{1, 2, 3, 4, 5, 6});
        System.out.println(s);
    }
}
