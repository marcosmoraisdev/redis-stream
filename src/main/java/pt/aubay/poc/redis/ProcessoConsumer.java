package pt.aubay.poc.redis;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;
import pt.aubay.poc.service.ProcessoService;

import java.net.InetAddress;

@Service
@AllArgsConstructor
@Slf4j
public class ProcessoConsumer implements StreamListener<String, ObjectRecord<String, String>> {

    private final ProcessoService processoService;

    @SneakyThrows
    @Override
    public void onMessage(ObjectRecord<String, String> record) {

        log.info(InetAddress.getLocalHost().getHostName() + " - consumed :" + record.getValue());

        this.processoService.receiveProcesso(record.getValue());
    }
}