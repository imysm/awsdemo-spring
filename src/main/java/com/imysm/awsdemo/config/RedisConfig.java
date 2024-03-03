package com.imysm.awsdemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.util.Map;

@Configuration
public class RedisConfig {
    private String redisHost;
    private String redisUsername;
    private String redisPassword;

    private int redisPort;

    public RedisConfig() {
        String secretName = "dev/demo/redis";
        Region region = Region.of("ap-east-1");
        GetSecretValueResponse getSecretValueResponse;
        try (SecretsManagerClient client = SecretsManagerClient.builder().region(region).build()) {
            GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder().secretId(secretName).build();
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        }
        Map<String, String> secretMap = parseSecretString(getSecretValueResponse.secretString());
        this.redisHost = secretMap.get("host");
        this.redisPort = Integer.parseInt(secretMap.get("port"));
        this.redisUsername = secretMap.get("username");
        this.redisPassword = secretMap.get("password");
    }

    private Map<String, String> parseSecretString(String secretString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(secretString, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse secret string", e);
        }
    }

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
        redisStandaloneConfiguration.setUsername(redisUsername);
        redisStandaloneConfiguration.setPassword(redisPassword);

        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfiguration = LettuceClientConfiguration.builder();
        lettuceClientConfiguration.useSsl().disablePeerVerification();

        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration.build());
    }

    @Bean
    StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(lettuceConnectionFactory());
    }
}
