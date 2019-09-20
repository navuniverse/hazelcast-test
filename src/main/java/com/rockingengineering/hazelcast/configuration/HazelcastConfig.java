/**
 * 
 */
package com.rockingengineering.hazelcast.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

/**
 * @author naveen
 *
 * @date 04-Sep-2017
 */
@Configuration
@ComponentScan({
		"com.rockingengineering.hazelcast"
})
public class HazelcastConfig {

	@Value("${hazelcast.kubernetes.enabled:false}")
	private boolean kubernetesEnabled;

	@Value("${hazelcast.host}")
	private String hazelcastHosts;

	@Value("${hazelcast.port:5711}")
	private int hazelcastPort;

	@Value("${hazelcast.port.count:1}")
	private int hazelcastPortCount;

	@Bean(name = "commonMap")
	public IMap<String, String> commonMap() {

		HazelcastInstance instance = hazelcastInstance();

		IMap<String, String> commonMap = instance.getMap("commonMap");

		MapConfig mapConfig = instance.getConfig().getMapConfig("commonMap");
		mapConfig.setMaxIdleSeconds(900);
		mapConfig.setTimeToLiveSeconds(900);
		mapConfig.setEvictionPolicy(EvictionPolicy.LRU);

		return commonMap;
	}

	@Bean
	public HazelcastInstance hazelcastInstance() {
		return Hazelcast.newHazelcastInstance(hazelcastConfig());
	}

	private Config hazelcastConfig() {
		Config config = new Config();

		NetworkConfig networkConfig = getNetworkConfig(config);
		setJoinConfig(networkConfig);

		return config;
	}

	private NetworkConfig getNetworkConfig(Config config) {
		NetworkConfig networkConfig = config.getNetworkConfig();

		if (!kubernetesEnabled) {
			networkConfig.setPort(hazelcastPort).setPortCount(hazelcastPortCount);
			networkConfig.setPortAutoIncrement(true);
		}

		return networkConfig;
	}

	private void setJoinConfig(NetworkConfig networkConfig) {
		JoinConfig joinConfig = networkConfig.getJoin();

		joinConfig.getMulticastConfig().setEnabled(false);

		if (kubernetesEnabled) {
			joinConfig.getKubernetesConfig().setEnabled(true);
		} else {
			setTcpConfig(joinConfig);
		}
	}

	private void setTcpConfig(JoinConfig joinConfig) {
		TcpIpConfig tcpIpConfig = joinConfig.getTcpIpConfig();

		tcpIpConfig.setEnabled(true);

		boolean hostFound = addHostForTcpConfig(tcpIpConfig);

		if (!hostFound) {
			tcpIpConfig.addMember("localhost");
		}
	}

	private boolean addHostForTcpConfig(TcpIpConfig tcpIpConfig) {
		boolean hostFound = false;

		if (StringUtils.isNotBlank(hazelcastHosts)) {
			String[] hosts = hazelcastHosts.split(",");

			if (hosts != null && hosts.length > 0) {
				hostFound = true;

				for (String host : hosts) {
					tcpIpConfig.addMember(host);
				}
			}

		}

		return hostFound;
	}

}