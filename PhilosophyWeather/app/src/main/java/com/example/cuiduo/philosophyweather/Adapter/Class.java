package com.example.cuiduo.philosophyweather.Adapter;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.max;

/**
 * Created by cuiduo on 2017/7/13.
 */

public class Class {
    public static void main(String[] args) {
//        int[] nums1 = {1,2,6};
//        int[] nums2 = {3,4,8};
//        double q = findMedianSortedArrays(nums1,nums2);
//        System.out.printf(String.valueOf(q));

//        int re = hammingDistance(1,4);
//        System.out.printf(String.valueOf(re));

        try {

        }catch (Exception e){

        }finally {
           
        }
        SimpleDateFormat siple =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar time = Calendar.getInstance();
        time.add(Calendar.DATE,-1);
//        String a = "hi,hello world";
//        String arr[] = a.split("\\b");
//        for (int i = arr.length - 1; i >= 0; i--) {
//            System.out.print(arr[i]);
//        }
        System.out.println(time.getTime());
    }

    public int lengthOfLongestSubstring(String s) {
        int longest = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0, j = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                j = Math.max(j, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            longest = Math.max(longest, i - j + 1);
        }
        return longest;
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int a = nums1.length;
        int b = nums2.length;
        int[] result = new int[a + b];
        double c;
        System.arraycopy(nums1, 0, result, 0, a);
        System.arraycopy(nums2, 0, result, a, b);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length - 1 - i; j++) {
                if (result[j] > result[j + 1]) {
                    int tmp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = tmp;
                }
            }
        }
        if ((a + b) % 2 == 0) {
            System.out.printf(String.valueOf(result[(a + b) / 2]));
            System.out.printf(String.valueOf(result[(a + b) / 2 - 1]));
            c = (double) (result[(a + b) / 2] + result[(a + b) / 2 - 1]) / 2;
        } else {
            c = result[(a + b - 1) / 2];
        }
        return c;
    }


    public static double findMedianSortedArrays2(int[] A, int[] B) {
        int m = A.length, n = B.length;
        int[] C = new int[m + n];
        System.arraycopy(A, 0, C, 0, A.length);
        int l = (m + n + 1) / 2;
        int r = (m + n + 2) / 2;
        return (getkth(A, 0, B, 0, l) + getkth(A, 0, B, 0, r)) / 2.0;
    }

    public static double getkth(int[] A, int aStart, int[] B, int bStart, int k) {
        if (aStart > A.length - 1) return B[bStart + k - 1];
        if (bStart > B.length - 1) return A[aStart + k - 1];
        if (k == 1) return Math.min(A[aStart], B[bStart]);

        int aMid = Integer.MAX_VALUE, bMid = Integer.MAX_VALUE;
        if (aStart + k / 2 - 1 < A.length) aMid = A[aStart + k / 2 - 1];
        if (bStart + k / 2 - 1 < B.length) bMid = B[bStart + k / 2 - 1];

        if (aMid < bMid)
            return getkth(A, aStart + k / 2, B, bStart, k - k / 2);// Check: aRight + bLeft
        else
            return getkth(A, aStart, B, bStart + k / 2, k - k / 2);// Check: bRight + aLeft
    }

    public static int hammingDistance(int x, int y) {
        int distance = Integer.bitCount(x ^ y);
        return distance;
    }

    public static int bytesToInt(byte[] des, int offset) {
        int value;
        value = (int) (des[offset] & 0xff
                | ((des[offset + 1] & 0xff) << 8)
                | ((des[offset + 2] & 0xff) << 16)
                | (des[offset + 3] & 0xff) << 24);
        return value;
    }

    static class A {
        protected int value;
        public A(int v){
            setValue(v);
        }
        public void setValue(int value){
            this.value = value;
        }

        public int getValue() {
            try {
                value++;
                return value;
            }catch (Exception e){
                System.out.println(e.toString());
            }finally {
                this.setValue(value);
                System.out.println(value);
            }
            return value;
        }
    }

    static class B extends A{

        public B() {
            super(5);
            setValue(getValue() -3);
        }

        @Override
        public void setValue(int value) {
            super.setValue(2*value);
        }
    }

}
