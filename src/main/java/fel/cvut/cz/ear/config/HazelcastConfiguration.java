package fel.cvut.cz.ear.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        config.setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig()
                        .setName("default")
                        .setTimeToLiveSeconds(300));
        return config;
    }
}
