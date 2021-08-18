package pt.aubay.poc.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pt.aubay.poc.model.Processo;
import pt.aubay.poc.redis.RedisMessagePublisher;
import pt.aubay.poc.repository.ProcessoRepository;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProcessoService {

    private final RedisMessagePublisher publisher;
    private final ProcessoRepository processoRepository;

    public void sendProcesso(List<Processo> processos) {

        this.processoRepository.saveAll(processos);

        for (Processo processo : processos) {
            log.info("Processo enviado: {} ", processo);
            publisher.publish(processo.getId());
        }

    }

    public void receiveProcesso(String id) {

        Processo processo = this.processoRepository.findById(id).orElseThrow();
        log.info("Processo recebido: {}", processo);

    }
}
