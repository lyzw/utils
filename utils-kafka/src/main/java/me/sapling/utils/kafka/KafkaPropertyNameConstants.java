package me.sapling.utils.kafka;

import java.util.*;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/8/31
 * @since 1.0
 */
public class KafkaPropertyNameConstants {

    /**
     * bootstrap.servers - 启动服务器
     */
    public static String PROP_BOOTSTRAP_SERVER = "bootstrap.servers";
    /**
     * group.id - 消费者组id
     */
    public static String PROP_GROUP_ID = "group.id";
    /**
     * enable.auto.commit - 是否自动提交（偏移量）
     */
    public static String PROP_ENABLE_AUTO_COMMIT = "enable.auto.commit";
    /**
     * auto.commit.interval.ms - 自动提交（偏移量）时间间隔
     */
    public static String PROP_AUTO_COMMIT_INTERVAL_MS = "auto.commit.interval.ms";
    /**
     * max.poll.records - 每次拉取最多获取的记录数（即每次拉取最多达到max.poll.records配置后马上返回）
     */
    public static String PROP_MAX_POLL_RECORDS = "max.poll.records";
    /**
     * session.timeout.ms - Session超时时间
     */
    public static String PROP_SESSION_TIMEOUT_MS = "session.timeout.ms";
    /**
     * auto.offset.reset - 自动offset重置方式（latest - 使用最新的，earliest ）
     */
    public static String PROP_AUTO_OFFSET_RESET = "session.timeout.ms";



}
