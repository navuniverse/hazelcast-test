/**
 * 
 */
package com.rockingengineering.hazelcast.configuration;

import java.util.concurrent.ConcurrentMap;

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

	@Bean(name = "commonMap")
	public ConcurrentMap<String, String> commonMap() {
		ConcurrentMap<String, String> commonMap = hazelcastInstance().getMap("commonMap");

		MapConfig mapConfig = hazelcastInstance().getConfig().getMapConfig("commonMap");
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

	private void setJoinConfig(NetworkConfig networkConfig) {
		JoinConfig joinConfig = networkConfig.getJoin();
		joinConfig.getMulticastConfig().setEnabled(false);

		setTcpConfig(joinConfig);
	}

	private void setTcpConfig(JoinConfig joinConfig) {
		TcpIpConfig tcpIpConfig = joinConfig.getTcpIpConfig();

		tcpIpConfig.setEnabled(true);

		tcpIpConfig.addMember("10.0.0.240");
		tcpIpConfig.addMember("10.0.0.88");

	}

	private NetworkConfig getNetworkConfig(Config config) {
		NetworkConfig networkConfig = config.getNetworkConfig();
		networkConfig.setPort(5711).setPortCount(10);
		networkConfig.setPortAutoIncrement(true);

		return networkConfig;
	}

}
