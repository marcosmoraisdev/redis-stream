package pt.aubay.poc.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pt.aubay.poc.model.Processo;
import pt.aubay.poc.redis.PurchaseEventProducer;
import pt.aubay.poc.repository.ProcessoRepository;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProcessoService {

    private final PurchaseEventProducer publisher;
    private final ProcessoRepository processoRepository;

    public void sendProcesso(List<Processo> processos) {

        this.processoRepository.saveAll(processos);

        processos.forEach(processo -> {
            log.info("Processo enviado: {} ", processo);
            publisher.publish(processo.getId());
        });

    }

    public void receiveProcesso(String id) {
        this.processoRepository.findById(id)
                .ifPresent(processo -> log.info("Processo recebido: {}", processo));
    }
}
