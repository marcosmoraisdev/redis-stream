package pt.aubay.poc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.aubay.poc.model.Processo;

@Repository
public interface ProcessoRepository extends CrudRepository<Processo, String> {
}
