package pt.aubay.poc.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PurchaseEventProducer implements MessagePublisher {

    @Value("${stream.key}")
    private String streamKey;

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Override
    public void publish(String message) {

        ObjectRecord<String, String> record = StreamRecords.newRecord()
                .ofObject(message)
                .withStreamKey(streamKey);

        this.redisTemplate
                .opsForStream()
                .add(record)
                .subscribe(System.out::println);
    }
}