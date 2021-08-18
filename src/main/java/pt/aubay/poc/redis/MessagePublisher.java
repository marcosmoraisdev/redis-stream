package pt.aubay.poc.redis;

public interface MessagePublisher {

    void publish(final String message);
}