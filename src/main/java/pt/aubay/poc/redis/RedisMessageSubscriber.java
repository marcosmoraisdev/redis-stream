package pt.aubay.poc.redis;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import pt.aubay.poc.service.ProcessoService;

@Service
@Slf4j
@AllArgsConstructor
public class RedisMessageSubscriber implements MessageListener {

    private final ProcessoService processoService;

    @Autowired
    private StringRedisTemplate template;

    public void onMessage(Message message, byte[] pattern) {

        this.processoService.receiveProcesso(message.toString());
    }
}