package pt.aubay.poc.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.aubay.poc.model.Processo;
import pt.aubay.poc.service.ProcessoService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/processos")
public class ProcessoController {

    private final ProcessoService processoService;

    @PostMapping
    public void sendProcessos(@RequestBody List<Processo> processos) {
        this.processoService.sendProcesso(processos);
    }
}
