import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Sorting {
    public static void main(String[] args) {
        File file1K = new File("1K_random_input.txt");
        File file10K = new File("10K_random_input.txt");
        File file100K = new File("100K_random_input.txt");

        for (int i = 1; i <= 4; i++) {
            sortSelection(i,file1K,1000,"random");
            sortSelection(i,file1K,1000,"increasing");
            sortSelection(i,file1K,1000,"decreasing");

            sortSelection(i,file10K,10000,"random");
            sortSelection(i,file10K,10000,"increasing");
            sortSelection(i,file10K,10000,"decreasing");

            sortSelection(i,file100K,100000,"random");
            sortSelection(i,file100K,100000,"increasing");
            sortSelection(i,file100K,100000,"decreasing");
        }

    }


    public static void sortSelection(int i,File file,int size,String dataType) {
        int[] arr = new int[size];
        readFileToArr(arr, file);
        if (dataType.equals("increasing")) {
            Arrays.sort(arr);
        }
        else if (dataType.equals("decreasing")) {
            decreasingQuickSort(arr, 0, arr.length - 1);
            //Arrayi azalan sırada sıralayabilmek için quickSortun azalan sırada sıralayan hali yazıldı.
        }

        String sortName = "";
        long startTime = System.nanoTime();
        switch (i) {
            case 1:
                insertionSort(arr);
                sortName = "insertionsort";
                break;
            case 2:
                mergeSort(arr, 0, arr.length - 1);
                sortName = "mergesort";
                break;
            case 3:
                heapSort(arr, arr.length);
                sortName = "heapsort";
                break;
            case 4:
                quickSort(arr, 0, arr.length - 1);
                sortName = "quicksort";
                break;
            default:
                break;
        }
        double runningTime = calculateRunningTime(startTime);

        String arraySize = "";
        if (size == 1000)
            arraySize = "1K";
        else if (size == 10000)
            arraySize = "10K";
        else if (size == 100000)
            arraySize = "100K";
        System.out.println(arraySize + " array of " + dataType + " integers has been sorted with " + sortName + " in " + runningTime + " ms");
        String fileName = sortName + "_" + arraySize + "_" + (dataType) + "_output.txt";
        writeArrToFile(arr,fileName);
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

    public static void writeArrToFile(int[] arr,String fileName) {
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

    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && (array[j] > key)) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }

        //Best Case : O(n)
        //Average Case : O(n^2)
        //Worst Case : O(n^2)
    }

    public static void mergeSort(int[] array, int p, int r) {
        if(p < r) {
            int q = (p + r) / 2;
            mergeSort(array, p, q);
            mergeSort(array, q + 1, r);
            merge(array, p, q, r);
        }
        //Best Case : O(nlogn)
        //Average Case : O(nlogn)
        //Worst Case : O(nlogn)
    }

    private static void merge(int[] array, int p, int q, int r) {
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
            if (leftArr[i] <= rightArr[j])  {//gönderilen boolean değerine göre artan veya azalan sırada sıralanır.
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

    public static void quickSort(int[] array, int p, int r) {
        if (p < r) {
            int q = quickSortPartition(array, p, r);
            quickSort(array, p, q - 1);
            quickSort(array, q + 1, r);
        }
        //Best Case: O(nlogn)
        //Average Case: O(nlogn)
        //Worst Case: O(n^2)
    }

    private static int quickSortPartition(int[] array, int p, int r) {
        int x = array[r];//son eleman pivot olarak seçilir.
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (array[j] <= x) {
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

    public static void heapSort(int[] array, int n) {
        for(int i = n/2 - 1; i >= 0; i--){
            heapify(array, i, n);
        }
        for(int i = n-1; i >= 0; i--){
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            heapify(array, 0, i);
        }

        //Best Case: O(nlogn)
        //Average Case: O(nlogn)
        //Worst Case: O(nlogn)
    }

    private static void heapify(int[] array, int i, int n) {
        int leftIndex = 2 * i + 1;//sol ve sağ çocuğun indexi belirlenir
        int rightIndex = 2 * i + 2;
        int largest = i;

        if(leftIndex < n && (array[leftIndex] > array[largest])){
            largest = leftIndex;
        }
        if(rightIndex < n && (array[rightIndex] > array[largest])) {
            largest = rightIndex;
        }
        if(largest != i){
            int temp = array[largest];
            array[largest] = array[i];
            array[i] = temp;
            heapify(array, largest, n);
        }
    }

    public static void decreasingQuickSort(int[] array, int p, int r) {
        if (p < r) {
            int q = decreasingQuickSortPartition(array, p, r);
            decreasingQuickSort(array, p, q - 1);
            decreasingQuickSort(array, q + 1, r);
        }
    }

    private static int decreasingQuickSortPartition(int[] array, int p, int r) {
        int x = array[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (array[j] >= x) {
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

}
