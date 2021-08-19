package pt.aubay.poc.redis;

import io.lettuce.core.RedisBusyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;

@Configuration
@Slf4j
public class RedisStreamConfig {

    @Value("${stream.key}")
    private String streamKey;

    @Autowired
    private StreamListener<String, ObjectRecord<String, String>> streamListener;

    @Bean
    public Subscription subscription(RedisConnectionFactory redisConnectionFactory, RedisTemplate<String, String> redisTemplate)  {

        try {
            createConsumerGroup(redisTemplate);

            var options = StreamMessageListenerContainer
                    .StreamMessageListenerContainerOptions
                    .builder()
                    .pollTimeout(Duration.ofSeconds(1))
                    .targetType(String.class)
                    .build();

            var listenerContainer = StreamMessageListenerContainer
                    .create(redisConnectionFactory, options);

            var subscription = listenerContainer.receiveAutoAck(
                    Consumer.from(streamKey, InetAddress.getLocalHost().getHostName()),
                    StreamOffset.create(streamKey, ReadOffset.lastConsumed()),
                    streamListener);

            listenerContainer.start();

            return subscription;

        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public void createConsumerGroup(RedisTemplate<String, String> redisTemplate) {
        try {
            // ReadOffset.from("0-0") will start reading stream from the very beginning.  Otherwise,
            // it will pick up at the point in the stream where the new group was created.
            //redisTemplate.opsForStream().createGroup(key, ReadOffset.from("0-0"), group);
            log.info("createConsumerGroup chamado");
            redisTemplate.opsForStream().createGroup(streamKey, streamKey);
        } catch (RedisSystemException e) {
            var cause = e.getRootCause();
            if (cause != null && RedisBusyException.class.equals(cause.getClass())) {
                log.info("STREAM - Redis group already exists, skipping Redis group creation: {}", streamKey);
            }
        }
    }
}