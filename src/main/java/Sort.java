import java.lang.reflect.Array;

public class Sort<T extends Comparable<T>> {
    private int threshold = 3; // A threshold to determine at which point to activate the naive sort algo


    /** Lecture quick sort's algorithm.
     * Sort's the array in an increasing order
     * @param array = the array to be sorted */
    public void quickSortClass(T[] array){
        quickSort(array, 0, array.length - 1, "Class");
    }

    /** QuickSort method for recursive calls.
     * @param array - The array to be sorted.
     * @param p = The starting index of the subarray to be sorted.
     * @param r = The ending index of the subarray to be sorted.
     * @param type = A string indicating the type of partition to be performed.
     *             "Class" for the partition from the lecture
     *             "Recitation" for the partition from the recitation .*/
    private void quickSort(T[] array, int p, int r, String type){
        int pivotIndex;
        if (r - p > threshold - 1){
            if (type.equals("Class")) {
                pivotIndex = partitionClass(array, p, r);
            }
            else {
                pivotIndex = partitionRecitation(array, p, r);
            }
            if (pivotIndex > p){
                quickSort(array, p, pivotIndex - 1, type);
            }
            if (pivotIndex < r){
                quickSort(array, pivotIndex, r, type);
            }
        }
        else {
            bubbleSort(array,p,r);
        }
    }

    /** Partition as taught in the lecture.
     * @param array = The array to find the pivot from.
     * @param p = The starting index of the subarray to find the pivot from.
     * @param r = The ending index of the subarray to find the pivot from.
     * @return = pivot's index. */
    private int partitionClass(T[] array, int p, int r){
        T pivot = array[r];
        int rightIndex = r, leftIndex = p - 1;
        T temp; // for swapping

        while (true){
            do {
                rightIndex --;
            } while (rightIndex >= p && array[rightIndex].compareTo(pivot) > 0);

            do {
                leftIndex ++;
            } while (leftIndex <= r && array[leftIndex].compareTo(pivot) < 0);

            if (leftIndex < rightIndex){ // found 2 elements to swap
                temp = array[leftIndex]; // Swap them
                array[leftIndex] = array[rightIndex];
                array[rightIndex] = temp;
            }

            else { // leftIndex and RightIndex crossed
                // swap pivot with R+1
                temp = array[r];
                array[r] = array[rightIndex+1];
                array[rightIndex+1] = temp;
                return rightIndex+1;
            }
        }
    }

    /** Naive sorting algorithm - Bubble sort.
     * Sorts the array in O(n^2).
     * @param array = The array to find the pivot from.
     * @param start = The starting index of the subarray to find the pivot from.
     * @param end = The ending index of the subarray to find the pivot from. */
    public void bubbleSort(T[] array, int start, int end) {
        for (int i = start; i <= end; i++) {
            for (int j = start; j < end; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    swap(array, j, j + 1);
                }
            }
        }
    }

    /** Recitation quick sort's algorithm.
     * Sort's the array in an increasing order
     * @param array = the array to be sorted */
    public void quickSortRecitation(T[] array){
        quickSort(array, 0, array.length - 1, "Recitation");
    }

    /** Partition as taught in the Recitation.
     * @param array = The array to find the pivot from.
     * @param p = The starting index of the subarray to find the pivot from.
     * @param r = The ending index of the subarray to find the pivot from.
     * @return = pivot's index. */
    private int partitionRecitation(T[] array, int p, int r){
        T pivot = array[r];
        int left = p - 1;
        T temp;

        for (int right = p; right < r ; right++) {
            if (array[right].compareTo(pivot) <= 0) {
                left++;
                // swap array[left] <-> array[right]
                temp = array[left];
                array[left] = array[right];
                array[right] = temp;
            }
        }
        // swap array[left + 1] <-> pivot
        temp = array[left + 1];
        array[left + 1] = array[r];
        array[r] = temp;
        return left + 1;
    }

    /** The radixSort algorithm.
     * Sorts the array in ascending order based on their digits,
     * considering each digit position from the least significant digit to the most significant digit.
     * @param array = An array of integers to be sorted.
     * @param base = Representing the base value of the numbers in the array.
     *           For example, if base is 10, it indicates a decimal number system. */
    public static void radixSort(int[] array, int base){
        // Find the number with the max digits
        int maxNumber = 0;

        for (int i = 0; i < array.length; i++){
            if (array[i] > maxNumber){
                maxNumber = array[i];
            }
        }

        for (int digit = 1; digit != 0 && maxNumber / digit > 0; digit *= base) {
            countingSort(array, base, digit);
        }
    }

    /** The countingSort algorithm.
     * Used to sort an array of positive integers based on a specific digit position.
     * It is a stable sorting algorithm that counts the occurrences of each element
     * and determines their correct positions in the sorted output.
     * @param arrA = An array of integers to be sorted.
     * @param base = Representing the base value of the numbers in the array.
     * @param digit = The digit position based on which the sorting will be performed. */
    private static void countingSort(int[] arrA, int base, int digit){
        int[] arrB = new int[arrA.length];
        int[] arrC = new int[base]; // array for counting the number of appearance.

        // initialize array C with 0
        for (int i = 0; i < base; i++) {
            arrC[i] = 0;
        }

        // counting the number of appearance of each digit
        for (int j = 1; j < arrA.length; j++){
            arrC[(arrA[j] / digit) % base] ++;
        }

        // adding the number of appearance of the previous digit for stability
        for (int i = 1; i < base; i++){
            arrC[i] += arrC[i-1];
        }

        // sorts the array's elements in array B with the help of array C
        for (int j = arrA.length - 1; j >= 0; j--){
            arrB[arrC[(arrA[j] / digit) % base]] = arrA[j];
            arrC[(arrA[j] / digit) % base] --;
        }

        // copy array B to array A
        for (int i = 0; i < arrA.length; i++){
            arrA[i] = arrB[i];
        }
    }

    /** Merge sort algorithm - Recursive implementation.
     * Sorts the array in an increasing order.
     * @param array = The array to be sorted. */
    public void mergeSortRecursive(T[] array){
        mergeSortRecursive(array, 0, array.length-1);
    }

    /** Merge sort method for recursive calls.
     * @param array = The array to be sorted.
     * @param left = The starting index of the subarray to be sorted.
     * @param right = The ending index of the subarray to be sorted. */
    public void mergeSortRecursive(T[] array, int left, int right){
        if (left < right) {
            if (right - left + 1 <= threshold) {
                bubbleSort(array, left, right);
            } else {
                int mid = (left + right) / 2;
                mergeSortRecursive(array, left, mid);
                mergeSortRecursive(array, mid + 1, right);
                merge(array, left, mid, right);
            }
        }
    }

    /** The merge method is a helper function used in the merge sort
     *  algorithm to merge two sorted sub-arrays into a single sorted array.
     * @param array = An array of objects to be sorted.
     * @param left = The starting index of the first sub-array.
     * @param mid = The index marking the end of the first sub-array and the start of the second subarray.
     * @param right = The index marking the end of the second sub-array. */
    private void merge(T[] array, int left, int mid, int right) {
        //size of sub-arrays
        int n1 = mid - left + 1;
        int n2 = right - mid;
        T[] leftAr = (T[]) Array.newInstance(Comparable.class, n1);
        T[] rightAr = (T[]) Array.newInstance(Comparable.class, n2);

        for(int i=0;i<n1;i++){
            leftAr[i]=array[left+i];
        }

        for(int i=0;i<n2;i++){
            rightAr[i]=array[mid + 1 + i];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftAr[i].compareTo(rightAr[j]) <= 0) {
                array[k++] = leftAr[i++];
            } else {
                array[k++] = rightAr[j++];
            }
        }

        while (i < n1) {
            array[k++] = leftAr[i++];
        }

        while (j < n2) {
            array[k++] = rightAr[j++];
        }
    }

    /** Merge sort algorithm - Iterative implementation.
     * Sorts the array in an increasing order.
     * @param array = The array to be sorted. */
    public void mergeSortIterative(T[] array) {
        int n = array.length;
        if (n <= threshold){
            bubbleSort(array,0, array.length - 1); // Naive sorting algo
        }
        else {
            // Divide the array into sub-arrays of size 1, then merge them pairwise
            for (int size = 1; size < n; size *= 2) {
                for (int left = 0; left < n - size; left += 2 * size) {
                    int mid = left + size - 1;
                    int right = Math.min(left + 2 * size - 1, n - 1); //prevent out of bound at last iteration
                    merge(array, left, mid, right);
                }
            }
        }
    }

    /** Helper function for swapping two array's elements. */
    private void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /** Setter method for the threshold. */
    public void setNaiveSortThreshold(int threshold){
        this.threshold = threshold;
    }


}
