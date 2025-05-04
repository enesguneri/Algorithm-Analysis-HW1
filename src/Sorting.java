import java.io.*;
import java.util.Scanner;

public class Sorting {
    public static void main(String[] args) {
        File file1K = new File("input_files/1K_random_input.txt");
        File file10K = new File("input_files/10K_random_input.txt");
        File file100K = new File("input_files/100K_random_input.txt");

        for (int i = 1; i <= 4; i++) {
            sortSelection(i,file1K,1000,true,true);//Reading numbers from file, and sorting array after creating random numbers txt.
            sortSelection(i,file1K,1000,false,false);
            sortSelection(i,file10K,10000,true,true);
            sortSelection(i,file10K,10000,false,false);
            sortSelection(i,file100K,100000,true,true);
            sortSelection(i,file100K,100000,false,false);
        }

    }

    public static void sortSelection(int i,File file,int size,boolean isIncreasing,boolean isRandom) {
        int[] arr = new int[size];

        if (isRandom) {
            String arraySize = "";
            if (size == 1000)
                arraySize = "1K";
            else if (size == 10000)
                arraySize = "10K";
            else if (size == 100000)
                arraySize = "100K";

            long startTime = System.nanoTime();
            readFileToArr(arr, file);
            System.out.println(arraySize + " array has been assigned from file in " + calculateRunningTime(startTime) + " ms");

            String sortName = "";
            switch (i) {
                case 1:
                    sortName = "insertionsort";
                    break;
                case 2:
                    sortName = "mergesort";
                    break;
                case 3:
                    sortName = "heapsort";
                    break;
                case 4:
                    sortName = "quicksort";
                    break;
                default:
                    break;

            }

            String fileName = sortName + "_" + arraySize + "_" + "random" + ".txt";
            writeArrToFile(arr, fileName);

        }
        else
            readFileToArr(arr, file);
        long startTime = System.nanoTime();
        String sortName = "";
        switch (i) {
            case 1:
                insertionSort(arr, isIncreasing);
                sortName = "insertionsort";
                break;
            case 2:
                mergeSort(arr, 0, arr.length - 1, isIncreasing);
                sortName = "mergesort";
                break;
            case 3:
                heapSort(arr, arr.length - 1, isIncreasing);
                sortName = "heapsort";
                break;
            case 4:
                quickSort(arr, 0, arr.length - 1, isIncreasing);
                sortName = "quicksort";
                break;
            default:
                break;

        }
        String arraySize = "";
        if (size == 1000)
            arraySize = "1K";
        else if (size == 10000)
            arraySize = "10K";
        else if (size == 100000)
            arraySize = "100K";
        System.out.println(arraySize + " array has been sorted in " + (isIncreasing ? "increasing" : "decreasing") + " order with " + sortName + " in " + calculateRunningTime(startTime) + " ms");

        String fileName = sortName + "_" + arraySize + "_" + (isIncreasing ? "increasing" : "decreasing") + ".txt";

        writeArrToFile(arr, fileName);
    }

    public static void readFileToArr(int[] arr, File file) {
        try {
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < arr.length; i++) {
                arr[i] = scanner.nextInt();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

    }

    public static void writeArrToFile(int[] arr, String fileName) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for(int num : arr) {
                bufferedWriter.write(String.valueOf(num));
                bufferedWriter.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error writing to file");
        }
    }

    public static double calculateRunningTime(long startTime) {
        long endTime = System.nanoTime();
        return (double) (endTime - startTime) /1000000.0;
    }

    public static void insertionSort(int[] array, boolean isIncreasing) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && (isIncreasing ? array[j] > key : array[j] < key)) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }

        //Best Case : O(n)
        //Average Case : O(n^2)
        //Worst Case : O(n^2)
    }

    public static void mergeSort(int[] array, int p, int r, boolean isIncreasing) {
        if(p < r) {
            int q = (p + r) / 2;
            mergeSort(array, p, q, isIncreasing);
            mergeSort(array, q + 1, r , isIncreasing);
            merge(array, p, q, r, isIncreasing);
        }
        //Best Case : O(nlogn)
        //Average Case : O(nlogn)
        //Worst Case : O(nlogn)
    }

    private static void merge(int[] array, int p, int q, int r, boolean isIncreasing) {
        int n1 = q - p + 1;
        int n2 = r - q;
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];
        for (int i = 0; i < n1; i++) {
            leftArr[i] = array[p + i];//array ikiye bölünür.
        }
        for (int j = 0; j < n2; j++) {
            rightArr[j] = array[q + 1 + j];
        }
        int i = 0, j = 0, k = p;
        while (i < n1 && j < n2) {
            if (isIncreasing ? leftArr[i] <= rightArr[j] : leftArr[i] >= rightArr[j])  {//gönderilen boolean değerine göre artan veya azalan sırada sıralanır.
                array[k++] = leftArr[i++];
            } else {
                array[k++] = rightArr[j++];
            }
        }
        while (i < n1) {//üstteki döngüden sonra soldaki veya sağdaki arrayde eleman kalmışsa son atamalar yapılır.
            array[k++] = leftArr[i++];
        }
        while (j < n2) {
            array[k++] = rightArr[j++];
        }
    }

    public static void quickSort(int[] array, int p, int r, boolean isIncreasing) {
        if (p < r) {
            int q = quickSortPartition(array, p, r, isIncreasing);
            quickSort(array, p, q - 1, isIncreasing);
            quickSort(array, q + 1, r, isIncreasing);
        }
        //Best Case: O(nlogn)
        //Average Case: O(nlogn)
        //Worst Case: O(n^2)
    }

    private static int quickSortPartition(int[] array, int p, int r, boolean isIncreasing) {
        int x = array[r];//son eleman pivot olarak seçilir.
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (isIncreasing ? array[j] <= x : array[j] >= x) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[r];
        array[r] = temp;
        return i + 1;
    }

    public static void heapSort(int[] array, int n, boolean isIncreasing) {
        for(int i = n/2 - 1; i >= 0; i--){
            heapify(array, i, n, isIncreasing);
        }
        for(int i = n - 1; i >= 0; i--){
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            heapify(array, 0, i, isIncreasing);
        }

        //Best Case: O(nlogn)
        //Average Case: O(nlogn)
        //Worst Case: O(nlogn)
    }

    private static void heapify(int[] array, int i, int n, boolean isIncreasing) {
        int leftIndex = 2 * i + 1;//sol ve sağ çocuğun indexi belirlenir
        int rightIndex = 2 * i + 2;
        int largest = i;

        if(leftIndex < n && (isIncreasing ? array[leftIndex] > array[largest] : array[leftIndex] < array[largest])){
            largest = leftIndex;
        }
        if(rightIndex < n && (isIncreasing ? array[rightIndex] > array[largest] : array[rightIndex] < array[largest])) {
            largest = rightIndex;
        }
        if(largest != i){
            int temp = array[largest];
            array[largest] = array[i];
            array[i] = temp;
            heapify(array, largest, n, isIncreasing);
        }
    }

}
