import java.util.Comparator;

public class StringComparator implements Comparator<String> {

    public int compare(String s1, String s2) {
        String[] arr = s1.split("\\@");
        s1 = arr[0];
        arr = s2.split("\\@");
        s2 = arr[0];

        return s1.compareTo(s2);
    }
}
