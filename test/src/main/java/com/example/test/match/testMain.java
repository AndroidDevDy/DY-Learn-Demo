package com.example.test.match;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class testMain {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        int total = 200;
        int num = 0;
        int base = new Random().nextInt(250);
        int last = base;
        while (num < total) {
            int size = new Random().nextInt(10);
            int flag = new Random().nextInt(2) * 2 - 1;
            for (int i = 0; i < size; i++) {
                if (num >= 240) break;
                int diff = new Random().nextInt(10);
                last = last + flag * diff;
                if (last <= 0) last = 0;
                list.add(last);
                System.out.println(String.valueOf(last));
                num++;
            }
            list.add(null);
            list.add(null);
            list.add(null);
        }
        System.out.println("===================2");
        num = 0;
        last = base;
        while (num < list.size()) {
            int size = new Random().nextInt(10);
            int flag = new Random().nextInt(2) * 2 - 1;
            for (int i = 0; i < size; i++) {
                if (num >= 240) break;
                int diff = new Random().nextInt(10);
                last = last + flag * diff;
                if (last <= 0) last = 0;
                list2.add(last);
                System.out.println(String.valueOf(last));
                num++;
            }
        }
        System.out.println("===================3");
        for (int i = 0; i < list.size(); i++) {
            int item = RandomMatchingO3(list.get(i), list2.get(i));
            System.out.println(String.valueOf(item));
        }
        System.out.println("===================4");
        for (int i = 0; i < list.size(); i++) {
            int item = DeviceMatchTool.getInstance().matching(DeviceMatchTool.TYPE_O3, list.get(i), "ASD", list2.get(i));
            System.out.println(String.valueOf(item));
        }
    }

    /**
     * 拟合
     *
     * @param gValue 国控站数据
     * @param dValue 微站数据
     * @return 拟合后的数据
     */
    public static int RandomMatching(int gValue, int dValue) {
        int base = 40;
        if (gValue < 50) base = 10;
        else if (gValue < 150) base = 20;
        int r1 = new Random().nextInt(base);
        int r2 = new Random().nextInt(base);
        int r3 = new Random().nextInt(base);
        int flag = (dValue > gValue) ? 1 : -1;
        return Math.abs(gValue + flag * (r1 + r2 + r3) / 3);
    }

    public static int RandomMatchingO3(int gValue, int dValue) {
        int base = 40;
        if (gValue < 50) base = 10;
        else if (gValue < 140) base = 30;
        else if (gValue < 200) base = 20;
        int ran = getRandom(base);
        int flag = (dValue > gValue) ? 1 : -1;
        return Math.abs(gValue + flag * ran);
    }

    public static int getRandom(int base) {
        if (new Random().nextInt(100) > 50) {
            if (new Random().nextInt(100) > 60) {
                return new Random().nextInt((int) (base * 0.2)) + (int) (base * 0.8);
            }
            return new Random().nextInt((int) (base * 0.5)) + (int) (base * 0.5);
        }
        return new Random().nextInt(base);
    }
}
