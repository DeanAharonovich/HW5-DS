import java.util.Arrays;
import java.util.Random;
public class Main {
    /** Options for testing */
    private static int[] radixBases = { 2, (int)Math.pow(2,5), (int)Math.pow(2,10), (int)Math.pow(2,15),
            (int)Math.pow(2,20), (int)Math.pow(2,25), (int)Math.pow(2,30) };
    private static int[] radixRange = {(int)Math.pow(2,10), (int)Math.pow(2,20), (int)Math.pow(2,30)};
    private static String[] inputArray = {"Random", "Increasing", "Decreasing"};


    /** Configuration before each test */
    private static final int NUMITER = 300; // Number of iterations for each algo
    private static final int THRESHOLD = 8;
    private static final int[] INPUT_SIZES = {1000000}; // Size of input array to be sorted
    private static final String INPUT_ARRAY = inputArray[2]; // Input Array's order of values
    private static final int RANGE = Integer.MAX_VALUE; // Range of values in the input array
    private static final int RADIX_BASE = radixBases[3];


    /** Main function for checking the run time of each algorithm. */
    public static void main(String[] args) {
        Sort sorter = new Sort();
        sorter.setNaiveSortThreshold(THRESHOLD);

        for (int n : INPUT_SIZES) {
            double[][] durationList = new double[6][NUMITER];

            for (int k = 0; k < NUMITER; k++) {
                Integer[] array = new Integer[n];
                Integer[] copy;

                // initialize array's value according to the desirable order.
                initializeArrayByOrder(array, INPUT_ARRAY);

                for (int i = 0; i < 6; i++) {
                    copy = copyIntegerArray(array);

                    long startTime = System.currentTimeMillis();
                    switch (i) {
                        case 0:
                            sorter.quickSortClass(copy);
                            break;
                        case 1:
                            sorter.quickSortRecitation(copy);
                            break;
                        case 2:
                            sorter.mergeSortRecursive(copy);
                            break;
                        case 3:
                            sorter.mergeSortIterative(copy);
                            break;
                        case 4:
                            sorter.radixSort(copyIntegerToInt(copy), RADIX_BASE);
                            break;
                        case 5: // Java's sort
                            Arrays.sort(copy);
                            break;
                    }
                    long endTime = System.currentTimeMillis();

                    // stores the algorithm's runtime in the table
                    durationList[i][k] = ((double) endTime - startTime);
                }
            }

            // Prints average durations for each algorithm and input size
            System.out.println("Input Size: " + n);
            System.out.println("Input array: " + INPUT_ARRAY);
            System.out.println("Algorithm\t|\tAverage Duration (ms)\t|\tStandard Deviation");
            for (int i = 0; i < 6; i++) {
                printResults(durationList[i], i);
            }
            System.out.println();
        }
    }

    /** Helper function for initializing the input array.
     * @param array = the input array to initialize.
     * @param orderType = the order type for the input array. 3 possibilities:
     *                  "Random", "sorted increasing", or "sorted decreasing" .*/
    private static void initializeArrayByOrder(Integer[] array, String orderType) {
        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            // Generate random Integers to the array form 0 to upper-bound parameter.
            // For radix analyze - enter a value from radixRange array.
            array[i] = random.nextInt(RANGE) & Integer.MAX_VALUE;
        }

        if (orderType.equals(inputArray[0])){
            return;
        }

        if (orderType.equals(inputArray[1])){
            Arrays.sort(array);
        }

        if (orderType.equals(inputArray[2])){
            Arrays.sort(array);
            int left = 0;
            int right = array.length - 1;

            while (left < right) {
                // Swap elements at left and right indices
                int temp = array[left];
                array[left] = array[right];
                array[right] = temp;

                // Move left and right indices towards the center
                left++;
                right--;
            }
        }
    }

    /** Helper function for printing the run time results. */
    private static void printResults(double[] arr,int i){
        double averageDuration = calculateAverage(arr);
        double standardDeviation = calcStandardDeviation(arr);
        System.out.printf(getAlgorithmName(i) + "\t\t" + "%.2f" + "\t\t" + "%.2f%n", averageDuration, standardDeviation);
    }

    /** Helper function for creating a copy of an Integer array. */
    private static Integer[] copyIntegerArray(Integer[] a) {
        Integer[] copy = new Integer[a.length];
        for (int i = 0; i < a.length; i++) {
            copy[i] = a[i];
        }
        return copy;
    }

    /** Helper function for copying an Integer array to an int array.
     * @param a is the original array we generated.
     * @return a clone array of type int[] compatible for radix-sort. */
    private static int[] copyIntegerToInt(Integer[] a) {
        int[] copy = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            copy[i] = a[i];
        }
        return copy;
    }


    /** Runs over all the results of the experiment of the given algo and calculate their average.
     * @param array is subarray of a 2-dim array.
     * @return Avg running time of algorithm. */
    private static double calculateAverage(double[] array) {
        double sum = 0;
        for (double value : array) {
            sum += value;
        }
        return sum / array.length;
    }

    /** Calculates the standard deviation of a given array of measurements values.
     * @param array = The input array of double values.
     * @return The standard deviation the values. */
    private static double calcStandardDeviation(double[] array) {
        double avg = calculateAverage(array);

        //calculation according to the given formula in the hw5 doc.
        double squaredDiffSum = 0; //represents the Sigma
        for (double current : array) {
            double diff = avg - current; //represents the parentheses in each iteration
            squaredDiffSum += diff * diff;
        }
        double standardDeviation = Math.sqrt( squaredDiffSum / array.length );
        return standardDeviation;
    }

    /** Helper function for the algorithm name for printing the results. */
    private static String getAlgorithmName(int algorithm) {
        switch (algorithm) {
            case 0:
                return "Class Quick Sort    ";
            case 1:
                return "Recitation Quick Sort";
            case 2:
                return "Merge Sort Recursive";
            case 3:
                return "Iterative Merge Sort  ";
            case 4:
                return "Radix Sort          ";
            case 5:
                return "Arrays.sort         ";
            default:
                return "";
        }
    }
}
