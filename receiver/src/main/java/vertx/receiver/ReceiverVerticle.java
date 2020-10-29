package vertx.receiver;

import static vertx.common.Constants.ADDRESS;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiverVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverVerticle.class);

    @Override
    public void start() {
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer(ADDRESS,
            receivedMessage -> LOGGER.info("Received message: {}", receivedMessage.body()));
        LOGGER.info("Receiver ready!");
    }
}
