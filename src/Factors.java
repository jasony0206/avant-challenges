import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Factors {
    public static void main(String[] args) {

    }

    public static void getFactors() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < 100000; i++) {
            list.add(i);
        }

        Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

        for (Integer num : list) {
            ArrayList<Integer> tempList = new ArrayList<Integer>();
            for (Integer other : list) {
                if (num % other == 0) {
                    tempList.add(other);
                }
            }
            map.put(num, tempList);
        }
    }
}
