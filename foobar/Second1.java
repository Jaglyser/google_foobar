package foobar;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Second1 {
    public static String[] solution(String[] l) {
        if(l.length == 1){
            return l;
        }

        List<Version> versions = new ArrayList<>();

        for(int i = 0; i < l.length; i++){
            Version version = Version.parseVersion(l[i]);
            versions.add(version);
        }

        Collections.sort(versions);
        String[] v = new String[versions.size()];
        for(int n = 0; n < versions.size(); n++){
            v[n] = String.valueOf(versions.get(n));
        }
        return v;
    }

    public static void main(String[] args){
        String[] l = {"1.11", "2.0.0", "1.2", "2", "0.1", "1.2.1", "1.1.1", "2.0"};
        String[] w = solution(l);
        System.out.println(w.toString());
    }

}

class Version implements Comparable<Version>{
    private int major;
    private int minor;
    private int revision;

    public Version(int... formattedParts){
        this.major = formattedParts[0];
        this.minor = formattedParts.length > 1 ? formattedParts[1] : -1;
        this.revision = formattedParts.length > 2 ? formattedParts[2] : -1;
    }

    public static Version parseVersion(String version){
        String[] parts = version.trim().split("\\.");
        int[] formatedParts = new int[parts.length];

        for(int i = 0; i < parts.length; i++){
            int input = Integer.parseInt(parts[i]);
            formatedParts[i] = input;
        }
        return new Version(formatedParts);
    }

    @Override
    public int compareTo(Version that) {

        if (that == null) {
            return 1;
        }

        if (major != that.major) {
            return major - that.major;
        }

        if (minor != that.minor) {
            return minor - that.minor;
        }

        if (revision != that.revision) {
            return revision - that.revision;
        }

        return 0;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(major);
        if(minor != -1){
            stringBuilder.append(".");
            stringBuilder.append(minor);
        }

        if(revision != -1){
            stringBuilder.append(".");
            stringBuilder.append(revision);
        }
        return stringBuilder.toString();
    }
}