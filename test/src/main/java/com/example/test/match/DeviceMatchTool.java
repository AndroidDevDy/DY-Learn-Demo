package com.example.test.match;

import java.util.HashMap;
import java.util.Random;

/**
 * 微站测量值校准
 */
public class DeviceMatchTool {
    /*校准类型*/
    public static final String TYPE_O3 = "o3";
    public static final String TYPE_SO2 = "so2";
    private HashMap<String, MatchInfo> map = new HashMap<>();

    private static DeviceMatchTool manager;

    public static DeviceMatchTool getInstance() {
        if (manager == null) {
            manager = new DeviceMatchTool();
        }
        return manager;
    }

    /**
     * 校准
     *
     * @param type   校准类型
     * @param gValue 国控站值
     * @param dValue 微站值
     * @return 校准后的值
     */
    public int matching(String type, int gValue, String deviceId, int dValue) {
        //符号
        int flag = getFlag(type, deviceId);
        //获取次数
        int num = getNum(type, deviceId);
        //如果次数小于0，随机生成一个新的次数，次数消耗完以前，不改变符号
        if (num <= 0) {
            num = new Random().nextInt(10) + 5;
            //符号，1或-1
            flag = calcFlag(gValue, dValue);
            refreshFlag(type, deviceId, flag);
        }
        refreshNum(type, deviceId, num - 1);

        //计算可接受误差
        int diff = calcDiff(type, gValue);
        //上次校正结果
        int lastMatchValue = getLastValue(type, deviceId);
        if (lastMatchValue < 0) lastMatchValue = gValue;
        //计算基准值
        int base = calcBase(type, deviceId, flag, gValue, lastMatchValue, diff);
        //计算偏移量
        int offset = calcOffset(type, deviceId, flag, base, gValue, diff, 10);
        //低概率大偏移
        int i = new Random().nextInt(10);
        if (i % 4 == 0) {
            offset += new Random().nextInt(diff)+3;
        }

        //校正结果
        int result = base + flag * offset;
        //保存
        refreshLastValue(type, deviceId, result);
        return result;
    }

    /**
     * 计算当前的偏移量
     *
     * @param flag
     * @param base
     * @param gValue
     * @param diff
     * @param num
     * @return
     */
    private int calcOffset(String type, String deviceId, int flag, int base, int gValue, int diff, int num) {
        if (num <= 0) {
            refreshFlag(type, deviceId, flag * -1);
            refreshNum(type, deviceId, new Random().nextInt(10) + 5);
            return 0;
        }
        int lowValue = gValue - diff;
        if (lowValue <= 0) lowValue = 0;
        int heightValue = gValue + diff;
        if (((base == heightValue) && (flag == 1)) || ((base == lowValue) && (flag == -1))) {
            refreshFlag(type, deviceId, flag * -1);
            refreshNum(type, deviceId, new Random().nextInt(10) + 5);
            return 0;
        }
        int offset = new Random().nextInt(3);
        int newValue = base + flag * offset;
        if (newValue >= lowValue && newValue <= heightValue) {
            return offset;
        } else {
            return calcOffset(type, deviceId, flag, base, gValue, diff, num - 1);
        }
    }

    /**
     * 计算允许的误差范围
     *
     * @param type   类型
     * @param gValue 国控站值
     * @return
     */
    private int calcDiff(String type, int gValue) {
        int diff = 20;
        switch (type) {
            case TYPE_O3:
                if (gValue < 50) diff = 10;
                else if (gValue < 200) diff = 20;
                else diff = 40;
                break;
            case TYPE_SO2:
                if (gValue < 50) diff = 10;
                else if (gValue < 150) diff = 20;
                else diff = 40;
                break;
            default:
                break;
        }
        return diff;
    }

    /**
     * 计算当前符号
     *
     * @param gValue 国控站值
     * @param dValue 微站值
     * @return
     */
    private int calcFlag(int gValue, int dValue) {
        if (dValue >= gValue) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 计算基准值
     *
     * @param gValue         国控站值
     * @param lastMatchValue 上次校正值
     * @param diff           允许的误差范围
     * @return
     */
    private int calcBase(String type, String deviceId, int flag, int gValue, int lastMatchValue, int diff) {
        if (Math.abs(gValue - lastMatchValue) > diff) {
            refreshFlag(type, deviceId, flag * -1);
            refreshNum(type, deviceId, new Random().nextInt(10) + 5);
            return gValue;
        } else {
            return lastMatchValue;
        }
    }

    /**
     * 获取重复次数
     *
     * @param type     分类
     * @param deviceId 设备
     * @return
     */
    private int getNum(String type, String deviceId) {
        int num;
        MatchInfo info = map.get(deviceId);
        if (info == null) {
            num = 0;
        } else {
            switch (type) {
                case TYPE_O3:
                    num = info.num_o3;
                    break;
                case TYPE_SO2:
                    num = info.num_so2;
                    break;
                default:
                    num = 0;
                    break;
            }
        }
        return num;
    }

    /**
     * 获取符号，正/负
     *
     * @param type     类型
     * @param deviceId 设备
     * @return
     */
    private int getFlag(String type, String deviceId) {
        int flag;
        MatchInfo info = map.get(deviceId);
        if (info == null) {
            flag = 1;
        } else {
            switch (type) {
                case TYPE_O3:
                    flag = info.flag_o3;
                    break;
                case TYPE_SO2:
                    flag = info.flag_so2;
                    break;
                default:
                    flag = 1;
                    break;
            }
        }
        return flag;
    }

    /**
     * 获取上次校正值
     *
     * @param type     类型
     * @param deviceId 设备
     * @return
     */
    private int getLastValue(String type, String deviceId) {
        int lastValue;
        MatchInfo info = map.get(deviceId);
        if (info == null) {
            lastValue = -1;
        } else {
            switch (type) {
                case TYPE_O3:
                    lastValue = info.flag_o3;
                    break;
                case TYPE_SO2:
                    lastValue = info.flag_so2;
                    break;
                default:
                    lastValue = -1;
                    break;
            }
        }
        return lastValue;
    }

    /**
     * 刷新map中的计数
     *
     * @param type     类型
     * @param deviceId 设备
     * @param value    新值
     */
    private void refreshNum(String type, String deviceId, int value) {
        MatchInfo info = map.get(deviceId);
        if (info == null) {
            info = new MatchInfo();
        }
        switch (type) {
            case TYPE_O3:
                info.num_o3 = value;
                break;
            case TYPE_SO2:
                info.num_so2 = value;
                break;
            default:
                break;
        }
        map.put(deviceId, new MatchInfo());
    }

    /**
     * 刷新map中的符号
     *
     * @param type     类型
     * @param deviceId 设备
     * @param value    新值
     */
    private void refreshFlag(String type, String deviceId, int value) {
        MatchInfo info = map.get(deviceId);
        if (info == null) {
            info = new MatchInfo();
        }
        switch (type) {
            case TYPE_O3:
                info.flag_o3 = value;
                break;
            case TYPE_SO2:
                info.flag_so2 = value;
                break;
            default:
                break;
        }
        map.put(deviceId, new MatchInfo());
    }

    /**
     * 刷新map中的校准值
     *
     * @param type     类型
     * @param deviceId 设备
     * @param value    新值
     */
    private void refreshLastValue(String type, String deviceId, int value) {
        MatchInfo info = map.get(deviceId);
        if (info == null) {
            info = new MatchInfo();
        }
        switch (type) {
            case TYPE_O3:
                info.last_value_o3 = value;
                break;
            case TYPE_SO2:
                info.last_value_so2 = value;
                break;
            default:
                break;
        }
        map.put(deviceId, new MatchInfo());
    }

    /**
     * 用于缓存的一个类
     */
    private class MatchInfo {
        public int flag_o3 = 1;//符号：正/负
        public int num_o3 = 0;//重复次数：
        public int last_value_o3 = -1;//上次校正值
        public int flag_so2 = 1;
        public int num_so2 = 0;
        public int last_value_so2 = -1;
    }
}
