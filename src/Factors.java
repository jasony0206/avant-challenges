import java.util.*;

public class Factors {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < 13; i++) {
            list.add(i);
        }

        Map<Integer, ArrayList<Integer>> map = getFactorsDP(list);
        System.out.println(map);
    }

    public static Map<Integer, ArrayList<Integer>> getFactors(ArrayList<Integer> inputList) {
        Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

        for (Integer num : inputList) {
            ArrayList<Integer> curFactorsList = new ArrayList<Integer>();
            for (Integer other : inputList) {
                if (num % other == 0) {
                    curFactorsList.add(other);
                }
            }
            map.put(num, curFactorsList);
        }
        return map;
    }

    public static Map<Integer, ArrayList<Integer>> getFactorsDP(ArrayList<Integer> inputList) {
        Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
        Collections.sort(inputList);
        int length = inputList.size();

        for (int i = 0; i < length; i++) {
            Set<Integer> curFactorsSet = new HashSet<Integer>();
            int curNum = inputList.get(i);

            for (int j = 0; j < i; j++) {
                int other = inputList.get(j);
                if (curNum % other == 0) {
                    curFactorsSet.add(other);
                }
            }

            ArrayList<Integer> curFactorsList = new ArrayList<Integer>();
            curFactorsList.addAll(curFactorsSet);
            map.put(curNum, curFactorsList);
        }
        return map;
    }
}
