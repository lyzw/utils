package me.sapling.utils.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/14
 * @since 1.0
 */
public class KafkaProducerFactory {

    public static KafkaProducer<String,String> createProducer(String bootStrapServers) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootStrapServers);
        props.put("acks", "all");// acks=0 配置适用于实现非常高的吞吐量 , acks=all 这是最安全的模式
//发送到同一个partition的消息会被先存储在batch中，该参数指定一个batch可以使用的内存大小，单位是 byte。不一定需要等到batch被填满才能发送
        props.put("batch.size", 16384);// 默认16384=16KB
//生产者在发送消息前等待linger.ms，从而等待更多的消息加入到batch中。如果batch被填满或者linger.ms达到上限，就把batch中的消息发送出去
        props.put("linger.ms", 1);// 当linger.ms>0时，延时性会增加，但会提高吞吐量，因为会减少消息发送频率
// props.put("client.id", 1);//用于标识发送消息的客户端，通常用于日志和性能指标以及配额
        props.put("buffer.memory", 33554432);// 32MB
// Snappy压缩技术是Google开发的，它可以在提供较好的压缩比的同时，减少对CPU的使用率并保证好的性能，所以建议在同时考虑性能和带宽的情况下使用。
// Gzip压缩技术通常会使用更多的CPU和时间，但会产生更好的压缩比，所以建议在网络带宽更受限制的情况下使用
//        props.put("compression.type", "Gzip");// 默认不压缩，该参数可以设置成snappy、gzip或lz4对发送给broker的消息进行压缩
// 默认值为0，当设置为大于零的值，客户端会重新发送任何发送失败的消息。
// 注意，此重试与客户端收到错误时重新发送消息是没有区别的。
// 在配置max.in.flight.requests.per.connection不等于1的情况下，允许重试可能会改变消息的顺序
// 因为如果两个批次的消息被发送到同一个分区，第一批消息发送失败但第二批成功，而第一批消息会被重新发送，则第二批消息会先被写入。
        props.put("retries", 1);
        // 生产者在收到服务器响应之前可以发送的消息个数
        props.put("max.in.flight.requests.per.connection", 2);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<String, String>(props);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        KafkaProducer producer = createProducer("10.30.4.212:9092");
        for (int index = 0;index<1;index++){
            ProducerRecord record = new ProducerRecord("data-sync-v20","2","1");
            Future future = producer.send(record);
            System.out.println(future.get());
        }

    }
}
