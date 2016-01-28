import java.util.*;

public class Factors {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < 25; i++) {
            list.add(i);
        }

        Map<Integer, ArrayList<Integer>> map = getFactorsDP(list);
        System.out.println(map);
    }

    // Naive implementation of getFactors method. It runs in O(N^2).
    public static Map<Integer, ArrayList<Integer>> getFactors(ArrayList<Integer> inputList) {
        Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

        for (Integer num : inputList) {
            ArrayList<Integer> curFactorsList = new ArrayList<Integer>();

            // For each of other numbers in the array,
            // if it is a factor, then add to the list of factors for this number.
            for (Integer other : inputList) {
                if ((num % other == 0) && (!num.equals(other))) {
                    curFactorsList.add(other);
                }
            }
            map.put(num, curFactorsList);
        }
        return map;
    }

    // Dynamic programming implementation of getFactors method.
    // Given an input of size 100000, it runs about 10 times faster than the naive approach.
    // General idea: This is a bottom-up DP algorithm. Consider the following example.
    // [2, 5, 10, 20]
    // When we are about to process 20, we already know the factors of 2, 5, and 10.
    // We can leverage memoization technique here to reduce time complexity.
    // More details below.
    public static Map<Integer, ArrayList<Integer>> getFactorsDP(ArrayList<Integer> inputList) {
        Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

        // First, sort the input list to reduce the traversal scope for each number:
        // When looking for factors of a given number, we only need to consider numbers to the left of it.
        Collections.sort(inputList);
        int length = inputList.size();

        for (int i = 0; i < length; i++) {
            int curNum = inputList.get(i);
            Set<Integer> curFactorsSet = new HashSet<Integer>();

            // 'marker' is a crucial part of this DP algorithm.
            // It is used in a similar fashion to how Quick Sort's partition function uses two counters.
            // The 'other' variable below acts as the left counter, and
            // 'marker' acts as the right counter.
            // When 'other' becomes greater than 'marker' is when we can stop.
            int marker = Integer.MAX_VALUE;

            // Only consider numbers to the left of curNum.
            for (int j = 0; j < i; j++) {
                int other = inputList.get(j);
                // 'other' greater than 'marker', finished work for this 'curNum'.
                if (other > marker) {
                    break;
                }

                if (curNum % other == 0) {
                    // Use pre-computed result: we already know factors of 'other'
                    Set<Integer> memoizedFactorSet1 = new HashSet<Integer>(map.get(other));
                    curFactorsSet.addAll(memoizedFactorSet1);
                    curFactorsSet.add(other);

                    ArrayList<Integer> memoizedFactorsList2 = map.get(curNum / other);
                    if (memoizedFactorsList2 != null) {
                        // Use pre-computed result: we already know factors of 'curNum / other'
                        // which is also a factor of curNum.
                        Set<Integer> memoizedFactorSet2 = new HashSet<Integer>(map.get(curNum / other));
                        curFactorsSet.addAll(memoizedFactorSet2);
                        curFactorsSet.add(curNum / other);
                    }

                    // 'marker' marks the smallest value of 'curNum / other' so far.
                    // When this number becomes smaller than 'other', we're done working for
                    // this particular 'curNum'
                    marker = curNum / other;
                }
            }

            ArrayList<Integer> curFactorsList = new ArrayList<Integer>();
            curFactorsList.addAll(curFactorsSet);
            map.put(curNum, curFactorsList);
        }
        return map;
    }
}
