import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Factors {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < 25; i++) {
            list.add(i);
        }

        Map<Integer, ArrayList<Integer>> map = getFactors(list);
        System.out.println(map);
    }

    public static Map<Integer, ArrayList<Integer>> getFactors(ArrayList<Integer> inputList) {
        Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

        for (Integer num : inputList) {
            ArrayList<Integer> tempList = new ArrayList<Integer>();
            for (Integer other : inputList) {
                if (num % other == 0) {
                    tempList.add(other);
                }
            }
            map.put(num, tempList);
        }
        return map;
    }
}
