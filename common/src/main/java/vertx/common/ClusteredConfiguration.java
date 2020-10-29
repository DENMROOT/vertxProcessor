package vertx.common;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.JoinConfig;
import io.vertx.spi.cluster.hazelcast.ConfigUtil;

public class ClusteredConfiguration {

    public static Config getHazelcastConfiguration() {
        Config config = ConfigUtil.loadConfig();

        GroupConfig groupConfig = config.getGroupConfig();
        groupConfig.setName("dev");

        JoinConfig joinConfig = config.getNetworkConfig().getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(true).addMember("127.0.0.1");

        InterfacesConfig interfaceConfig = config.getNetworkConfig().getInterfaces();
        interfaceConfig.setEnabled(true).addInterface("127.0.0.1");

        return config;
    }
}
