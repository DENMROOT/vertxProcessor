package vertx.sender;

import static vertx.common.ClusteredConfiguration.getHazelcastConfiguration;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderDeployer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SenderDeployer.class);

    public static void main(String[] args) {
        ClusterManager mgr = new HazelcastClusterManager(getHazelcastConfiguration());
        VertxOptions options = new VertxOptions().setClusterManager(mgr);

        Vertx.clusteredVertx(options, cluster -> {
                if (cluster.succeeded()) {
                    cluster.result().deployVerticle(new SenderVerticle(), res -> {
                        if (res.succeeded()) {
                            LOGGER.info("Sender deployment id is: {}", res.result());
                        } else {
                            LOGGER.error("Deployment failed!");
                        }
                    });
                } else {
                    LOGGER.error("Cluster up failed: ", cluster.cause());
                }
            }
        );
    }

}
