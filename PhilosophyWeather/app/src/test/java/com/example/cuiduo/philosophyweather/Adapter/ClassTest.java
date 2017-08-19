package com.example.cuiduo.philosophyweather.Adapter;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cuiduo on 2017/7/13.
 */
public class ClassTest {
    @Test
    public void twoSum() throws Exception {
        int[] Nums = {2,7,11,15};
        int target = 18;
        int[] output = Class.twoSum2(Nums, target);
        int[] expected = {1, 2};
        Assert.assertArrayEquals(expected, output);
    }

}