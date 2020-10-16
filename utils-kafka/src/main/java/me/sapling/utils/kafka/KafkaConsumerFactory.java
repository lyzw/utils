package me.sapling.utils.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static me.sapling.utils.kafka.KafkaPropertyNameConstants.*;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/14
 * @since 1.0
 */
@Slf4j
public class KafkaConsumerFactory {

    private static final String STRING_DESERIALIZER = StringDeserializer.class.getName();
    private static final String BYTE_ARRAY_DESERIALIZER = ByteArrayDeserializer.class.getName();

    public static <K, V> KafkaConsumer<K, V> createDefaultBytesConsumer(String servers,
                                                                        String groupId,
                                                                        Boolean autoCommit ) {

        return createDefaultConsumer(servers,groupId,autoCommit,
                BYTE_ARRAY_DESERIALIZER,
                BYTE_ARRAY_DESERIALIZER);
    }

    public static <K, V> KafkaConsumer<K, V> createDefaultStringConsumer(String servers,
                                                                         String groupId,
                                                                         Boolean autoCommit) {
        return createDefaultConsumer(servers,groupId,autoCommit,
                STRING_DESERIALIZER, STRING_DESERIALIZER);

    }


    public static <K, V> KafkaConsumer<K, V> createDefaultConsumer(String servers,
                                                                   String groupId,
                                                                   Boolean autoCommit,
                                                                   String keyDeserializer,
                                                                   String valueDeserializer) {

        Properties props = defaultConsumerProperties();
        props.put(PROP_BOOTSTRAP_SERVER, servers);
        props.put(PROP_GROUP_ID, groupId);
        props.put(PROP_ENABLE_AUTO_COMMIT, autoCommit);
        props.put(PROP_AUTO_COMMIT_INTERVAL_MS, "1000");
        props.put(PROP_SESSION_TIMEOUT_MS, "30000");
        props.put(PROP_MAX_POLL_RECORDS, "10");
        props.put("key.deserializer", keyDeserializer);
        props.put("value.deserializer", valueDeserializer);
        props.put("auto.offset.reset", "latest");
        return new KafkaConsumer<K, V>(props);
    }

    public static <K, V> KafkaConsumer<K, V> createDefaultConsumer(Properties props) {

        if (!props.containsKey(PROP_BOOTSTRAP_SERVER)){
            throw new IllegalArgumentException(PROP_BOOTSTRAP_SERVER + "is required!");
        }
        if (!props.containsKey(PROP_GROUP_ID)){
            throw new IllegalArgumentException(PROP_GROUP_ID + "is required!");
        }
        return new KafkaConsumer<>(props);
    }

    private static Properties defaultConsumerProperties(){
        Properties props = new Properties();
        props.put("enable.auto.commit", true);
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("max.poll.records", "10");
        props.put("key.deserializer", STRING_DESERIALIZER);
        props.put("value.deserializer", STRING_DESERIALIZER);
        props.put("auto.offset.reset", "latest");
        return props;
    }


    public static <K, V> KafkaConsumer<K, V> createCustomerConsumer(String servers,
                                                                    String groupId,
                                                                    Boolean autoCommit,
                                                                    String keyDeserializer,
                                                                    String valueDeserializer,
                                                                    Properties properties){
        Properties props = defaultConsumerProperties();
        props.putAll(properties);
        props.put(PROP_BOOTSTRAP_SERVER, servers);
        props.put(PROP_GROUP_ID, groupId);
        props.put(PROP_ENABLE_AUTO_COMMIT, autoCommit);
        props.put("key.deserializer", keyDeserializer);
        props.put("value.deserializer", valueDeserializer);
        return new KafkaConsumer<>(props);
    }

    public static void main(String[] args) {
        KafkaConsumer<String,String> consummer = KafkaConsumerFactory.createDefaultStringConsumer(
                "192.168.1.153:9092",
                "log-test222",true);
        consummer.subscribe(Collections.singletonList("msservice-log-saas2"));
        int count =0;
        try {
//            while (true) {
                ConsumerRecords<String, String> records = consummer.poll(Duration.ofSeconds(1));
                for (ConsumerRecord record : records) {
                    count++;
                    if (count % 1000 == 0) {
                        System.out.println(record.partition() + "---" + record.offset());
                    }
//                    if (record.toString().contains("dataproduct-framework-log")) {
                        System.out.println(record.value().toString());
//                    }
                }
//            }
        }catch (Exception e){
            consummer.close();
        }
    }
}
