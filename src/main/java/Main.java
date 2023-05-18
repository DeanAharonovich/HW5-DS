import java.util.Arrays;
import java.util.Random;
public class Main {
    //amount of checks each algorithm does (NUMITER = 100 means each algorithm go 100 times)
    private static final int NUMITER = 250;
    private static final int[] inputSizes = {500000}; //the size the algorithms sorts
    public static void main(String[] args) {
        Sort sorter = new Sort();
        Random random = new Random();
        System.out.println();
        System.out.println("NOTE: you might need to change the flags to see the exact checks you desire" +
                "\nIn the beginning of the main function\n");
        boolean flagRandom = true;
        boolean flagIncreasing = false;
        boolean flagDecreasing = false;
        boolean flagRadix = false;

        if(flagRandom){
        System.out.println("Results for random order array");
        for (int n : inputSizes) {
            double[][] durationList = new double[6][NUMITER];

            for (int k = 0; k < NUMITER; k++) {
                Integer[] array = new Integer[n];
                int[] radixArray = new int[n];
                Integer[] copy;
                int[] radixCopy;

                //generate random array with Integer [0,MAX_Integer]
                for (int i = 0; i < n; i++) {
                    array[i] = random.nextInt();
                    radixArray[i] = array[i].intValue();
                }

                for (int i = 0; i < 6; i++) {
                    copy = Arrays.copyOf(array, n);
                    radixCopy = Arrays.copyOf(radixArray, n);

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
                            sorter.radixSort(radixCopy,10);
                            break;
                        case 5: //Java's sort
                            Arrays.sort(copy);
                            break;
                    }
                    long endTime = System.currentTimeMillis();
                    //store the runtime of the current run to the current algo type
                    durationList[i][k] = ((double) endTime - startTime);
                }
            }

            // Prints average durations for each algorithm and input size
            System.out.println("Input Size: " + n);
            System.out.println("Algorithm\t|\tAverage Duration (ms)\t|\tStandard Deviation");
            for (int i = 0; i < 6; i++) {
                printResults(durationList[i], i);
            }
            System.out.println();
        }
        }

        if(flagIncreasing) {
            System.out.println("Results for Increasing order array");
            for (int n : inputSizes) {
                double[][] durationList = new double[6][NUMITER];

                for (int k = 0; k < NUMITER; k++) {
                    Integer[] array = new Integer[n];
                    Integer[] copy;

                    //generate random array with Integer [0,MAX_Integer]
                    for (int i = 0; i < n; i++) {
                        array[i] = random.nextInt(Integer.MAX_VALUE);
                    }
                    Arrays.sort(array);

                /* 1) clone the random Array each algo measure
                   2) use the i'th algorithem on the clone
                   3) save the result
                 */
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
                            case 4://this one gets different input since it works specificly with int[] type
                                sorter.radixSort(copyIntegerTOint(copy), 3);
                                break;
                            case 5://naive Sort
                                Arrays.sort(copy);
                                break;
                        }
                        long endTime = System.currentTimeMillis();
                        //store the runtime of the current run to the current algo type
                        durationList[i][k] = ((double) endTime - startTime);
                    }
                }

                // Prints average durations for each algorithm and input size
                System.out.println("Input Size: " + n);
                System.out.println("Algorithm\t|\tAverage Duration (ms)\t|\tStandard Deviation");
                for (int i = 0; i < 6; i++) {
                    printResults(durationList[i], i);
                }
                System.out.println();
            }
        }

        if(flagDecreasing) {
            System.out.println("Results for Decreasing order array");
            for (int n : inputSizes) {
                double[][] durationList = new double[6][NUMITER];

                for (int k = 0; k < NUMITER; k++) {
                    Integer[] array = new Integer[n];
                    Integer[] copy;
                    //generate random array with Integer [0,MAX_Integer]
                    for (int i = 0; i < n; i++) {
                        array[i] = random.nextInt(Integer.MAX_VALUE);
                    }
                    Arrays.sort(array);
                    reverseArray(array);

                /* 1) clone the random Array each algo measure
                   2) use the i'th algorithem on the clone
                   3) save the result
                 */
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
                            case 4://this one gets different input since it works specificly with int[] type
                                sorter.radixSort(copyIntegerTOint(copy), 3);
                                break;
                            case 5://naive Sort
                                Arrays.sort(copy);
                                break;
                        }
                        long endTime = System.currentTimeMillis();
                        //store the runtime of the current run to the current algo type
                        durationList[i][k] = ((double) endTime - startTime);
                    }
                }

                // Prints average durations for each algorithm and input size
                System.out.println("Input Size: " + n);
                System.out.println("Algorithm\t|\tAverage Duration (ms)\t|\tStandard Deviation");
                for (int i = 0; i < 6; i++) {
                    printResults(durationList[i], i);
                }
                System.out.println();
            }
        }
    }

    public static void printResults(double[] arr,int i){
        double averageDuration = calculateAverage(arr);
        double standardDeviation = calcStandardDeviation(arr);
        System.out.println(getAlgorithmName(i) + "\t\t" + averageDuration + "\t\t" + standardDeviation);
    }

    /**
     * Reverses the order of elements in an integer array.
     * @param array The array to be reversed.
     */
    public static void reverseArray(Integer[] array) {
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


    //regular copying function
    public static Integer[] copyIntegerArray(Integer[] a) {
        Integer[] copy = new Integer[a.length];
        for (int i = 0; i < a.length; i++) {
            copy[i] = a[i];
        }
        return copy;
    }

    /**
     *
     * @param a is the original array we randomed
     * @return a clone array of type intp[] compatible for radix-sort
     */
    public static int[] copyIntegerTOint(Integer[] a) {
        int[] copy = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            copy[i] = a[i];
        }
        return copy;
    }


    /**
     * Runs over all the results of the experemint of the given algo
     * @param array is subarray of a 2-dim array
     * @return Avg time to sort
     */
    private static double calculateAverage(double[] array) {
        double sum = 0;
        for (double value : array) {
            sum += value;
        }
        return sum / array.length;
    }

    /**
     * Calculates the standard deviation of a given array of measurements values.
     * @param array The input array of double values.
     * @return The standard deviation of the input array.
     */
    public static double calcStandardDeviation(double[] array) {
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
