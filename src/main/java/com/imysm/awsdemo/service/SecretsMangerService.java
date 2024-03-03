package com.imysm.awsdemo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class SecretsMangerService {

    public JsonNode getSecret(String secretName) {
        Region region = Region.of("ap-east-1");
        // Create a Secrets Manager client
        GetSecretValueResponse getSecretValueResponse;
        try (SecretsManagerClient client = SecretsManagerClient.builder().region(region).build()) {
            GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder().secretId(secretName).build();
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        }
        String secret = getSecretValueResponse.secretString();
        return stringToJsonNode(secret);
    }

    private JsonNode stringToJsonNode(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(jsonString);
            return jsonNode;
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        return jsonNode;
    }
}
