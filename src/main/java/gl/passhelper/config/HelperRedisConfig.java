package gl.passhelper.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "redis.helper")
public class HelperRedisConfig {

    @Bean("helperRedisConnectionFactory")
    @Primary
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(new GenericObjectPoolConfig()).build();

        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(host, port);

        return new LettuceConnectionFactory(standaloneConfiguration, clientConfiguration);
    }

    @Bean("helperClusterRedisConnectionFactory")
    @Profile("ho")
    public RedisConnectionFactory clusterRedisConnectionFactory() {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(new GenericObjectPoolConfig()).build();
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(clusters);
        return new LettuceConnectionFactory(clusterConfiguration, clientConfiguration);
    }

    @Bean("script")
    public DefaultRedisScript redisScript() {
        DefaultRedisScript script = new DefaultRedisScript();
        script.setLocation(new ClassPathResource("lua/test.lua"));
        script.setResultType(Long.class);
        return script;
    }

    private String host;
    private int port;
    private Set<String> clusters;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setClusters(Set<String> clusters) {
        this.clusters = clusters;
        System.out.println(clusters);
    }
}
