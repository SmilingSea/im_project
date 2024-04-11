package com.jiang.utils;

/**
 * @author SmilingSea
 * 2024/4/5
 */
public class SnowflakeIdGenerator {

    // 起始的时间戳，2020-01-01 00:00:00
    private static final long START_TIMESTAMP = 1577808000000L;

    // 每一部分占用的位数
    private static final long SEQUENCE_BIT = 12; // 序列号占用的位数
    private static final long MACHINE_BIT = 5;   // 机器标识占用的位数
    private static final long DATACENTER_BIT = 5; // 数据中心占用的位数

    // 每一部分的最大值
    private static final long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private static final long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private static final long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    // 每一部分向左的位移
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  // 数据中心
    private long machineId;     // 机器标识
    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L; // 上次时间戳

    public SnowflakeIdGenerator(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    public synchronized long generateId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (timestamp == lastTimestamp) {
            // 如果是同一时间生成的，则进行毫秒内序列
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // 毫秒内序列溢出
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // 时间戳部分 | 数据中心部分 | 机器标识部分 | 序列号部分
        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT)
                | (datacenterId << DATACENTER_LEFT)
                | (machineId << MACHINE_LEFT)
                | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    // 示例用法
    public static void main(String[] args) {

    }
}

