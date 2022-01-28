package com.groot.base.web.generator;

import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Properties;

public class IdGenerator {

    private static final String WORKER_ID = "worker_id";

    /**
     * 十位的机器id 0~1023
     */
    private static final long workerId;

    /**
     * 十二位的序列号 0~4095
     */
    private static long sequence;

    private static final long MAX_WORK_ID = (1 << 10) - 1;

    private static final long MAX_SEQUENCE = (1 << 12) - 1;

    private static final long WORK_ID_SHIFT_LENGTH = 12L;

    private static final long TIMESTAMP_SHIFT_LENGTH = 22L;

    private static long lastTimestamp = -1L;

    static {
        String message = "\n生产环境：请配置环境变量" + WORKER_ID + "=xxx (xxx为0~1023之间的数字）\n" +
                "开发环境：请在Java启动命令中加：-D" + WORKER_ID + "=xxx(xxx为0~1023之间的数字）";
        Properties props = System.getProperties();
        String workId = props.getProperty(WORKER_ID);
        // 判断系统属性是否配置workerId
        if (!StringUtils.hasText(workId)) {
            // 判断系统环境变量是否有workerId
            Map<String, String> env = System.getenv();
            workId = env.get(WORKER_ID);
            if (!StringUtils.hasText(workId)) {
                throw new RuntimeException(message);
            } else {
                workerId = Long.parseLong(workId);
            }
        } else {
            workerId = Long.parseLong(workId);
        }
        // 校验workerId大小范围
        if (workerId < 0 || workerId > MAX_WORK_ID) {
            throw new RuntimeException(message);
        }
    }

    public synchronized static Long generateId() {
        long l = System.currentTimeMillis();
        if (l == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                while (l == lastTimestamp) {
                    l = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = l;
        return l << TIMESTAMP_SHIFT_LENGTH | workerId << WORK_ID_SHIFT_LENGTH | sequence;
    }

}
