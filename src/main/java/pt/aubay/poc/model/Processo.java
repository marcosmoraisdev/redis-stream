package pt.aubay.poc.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("Processo")
public class Processo {

    @Id
    private String id;
    private Status status;
}
