package vertx.common;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.JoinConfig;
import io.vertx.spi.cluster.hazelcast.ConfigUtil;

public class ClusteredConfiguration {

    public ClusteredConfiguration() {
        throw new UnsupportedOperationException("Not for instantiation");
    }

    public static Config getHazelcastConfiguration() {
        Config config = ConfigUtil.loadConfig();

        GroupConfig groupConfig = config.getGroupConfig();
        groupConfig.setName("dev");

        JoinConfig joinConfig = config.getNetworkConfig().getJoin();
        joinConfig.getMulticastConfig().setEnabled(true);

        return config;
    }

}
