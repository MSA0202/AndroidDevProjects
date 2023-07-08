package com.example.lab1;

import java.util.Random;
public class Algorithms {

    public static void main(String[] args) {
        int size=10000;
        Timer myTimer=new Timer();
        int[] data = initialize(size);
        myTimer.start();
        int position = linearCount(data, 50);
        myTimer.stop();
        System.out.println("Search Time : " + myTimer.getTime());

        myTimer.start();
        bubbleSort(data);
        myTimer.stop();
        System.out.println("Sort Time : " + myTimer.getTime());
    }


    public static int[] bubbleSort(int[] arr){
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr.length-i-1; j++){
                if( arr[j] > arr[j+1]){
                    int hold = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = hold;
                }
            }
        }
        return arr;
    }

    public static int[] initialize(int numItems){
        int arr[];
        arr = new int[numItems];
        Random generator = new Random();
        for(int i=0; i<arr.length; ++i){
            int number = generator.nextInt(1000000);
            arr[i]=number;
        }
        return arr;
    }

    public static int linearCount(int ar[],int key){
        int count=0;
        for(int i=0; i<ar.length; ++i){
            if(ar[i]==key){
                count++;
            }
        }
        return count;
    }

}
