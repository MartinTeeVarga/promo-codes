package com.czequered.promocodes;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.czequered.promocodes.model.Code;
import org.junit.rules.ExternalResource;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Creates a local DynamoDB instance for testing.
 * http://stackoverflow.com/questions/26901613/easier-dynamodb-local-testing
 */
public class LocalDynamoDBCreationRule extends ExternalResource {

    private DynamoDBProxyServer server;
    private AmazonDynamoDB amazonDynamoDB;

    @Override
    protected void before() throws Throwable {

        try {
            final String port = getAvailablePort();
            this.server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory", "-port", port});
            server.start();
            amazonDynamoDB = new AmazonDynamoDBClient(new BasicAWSCredentials("access", "secret"));
            amazonDynamoDB.setEndpoint("http://localhost:" + port);

            createCodeTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createCodeTable() {

        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest createTableRequest = mapper.generateCreateTableRequest(Code.class);
        createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        amazonDynamoDB.createTable(createTableRequest);
    }

    @Override
    protected void after() {
        if (server == null) {
            return;
        }

        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AmazonDynamoDB getAmazonDynamoDB() {
        return amazonDynamoDB;
    }

    private String getAvailablePort() {
        try (final ServerSocket serverSocket = new ServerSocket(0)) {
            return String.valueOf(serverSocket.getLocalPort());
        } catch (IOException e) {
            throw new RuntimeException("Available port was not found", e);
        }
    }
}