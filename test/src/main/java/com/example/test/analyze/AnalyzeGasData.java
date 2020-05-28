package com.example.test.analyze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AnalyzeGasData {
    public static void main(String[] args) {
        Map<String, List<Double>> gasDataAll = getAllGasData();
        for (Map.Entry<String, List<Double>> item : gasDataAll.entrySet()) {
            print(item.getValue());
        }

        List<Double> averageList = getAverageList(gasDataAll);
        print(averageList);

        for (Map.Entry<String, List<Double>> item : gasDataAll.entrySet()) {
            String result = getMaxPoint(item.getValue(), averageList, getLimit());
            System.out.println(item.getKey() + ":" + result);
        }
    }

    //污染源分析
    private static String getMaxPoint(List<Double> data, List<Double> averageList, double limit) {
        String result = "";
        double a, b, c;
        for (int i = 1; i < data.size() - 1; i++) {
            a = data.get(i - 1);
            b = data.get(i);
            c = data.get(i + 1);
            if (a > limit && b > limit && c > limit) {
                //空气质量差
                result = "空气质量差";
                double ag, bg, cg, p;
                ag = averageList.get(i - 1);
                bg = averageList.get(i);
                cg = averageList.get(i + 1);
                p = getP();
                if (a > ag * p && b > bg * p && c > cg * p) {
                    //判定为污染源
                    result = "空气质量差，且附近有污染源";
                }
            }
        }
        return result;
    }

    //阀值
    private static double getLimit() {
        return 50;
    }

    //污染源判断系数
    private static double getP() {
        return 1.2;
    }

    //计算平均
    private static List<Double> getAverageList(Map<String, List<Double>> gasDataAll) {
        List<List<Double>> lists = new ArrayList<>();
        for (String s : gasDataAll.keySet()) {
            List<Double> data = gasDataAll.get(s);
            for (int i = 0; i < data.size(); i++) {
                if (lists.size() < i + 1) {
                    lists.add(i, new ArrayList<Double>());
                }
                lists.get(i).add(data.get(i));
            }
        }
        List<Double> averageList = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            averageList.add(getAverage(lists.get(i)));
        }
        return averageList;
    }

    //平均算法
    private static Double getAverage(List<Double> data) {
        double average = 0;
        int num = 0;
        for (Double datum : data) {
            if (datum >= 0) {
                average += datum;
                num++;
            }
        }
        if (num == 0) num = 1;
        average /= num;
        return average;
    }

    //生成一组测试数据
    private static Map<String, List<Double>> getAllGasData() {
        Map<String, List<Double>> map = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            map.put("station_" + i, createGasData());
        }
        return map;
    }

    //生成测试数据
    private static List<Double> createGasData() {
        List<Double> data = new ArrayList<>();
        data.add((double) new Random().nextInt(50));
        for (int i = 1; i < 24; i++) {
            int flag = (new Random().nextInt(10) % 2) * 2 - 1;
            double value = new Random().nextInt(50);
            double result = data.get(i - 1) + flag * value;
            data.add(i, result < 0 ? 0 : result);
        }
        return data;
    }

    //打印
    private static void print(List<Double> data) {
        System.out.println("\n");
        for (double item : data) {
            System.out.printf("%.1f\t", item);
        }
        System.out.println("\n");
    }
}


