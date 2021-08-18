package pt.aubay.poc.redis;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@AllArgsConstructor
public class RedisMessagePublisher implements MessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;



    public void publish(final String message) {

        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}