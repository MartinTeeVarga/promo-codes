package com.czequered.promocodes.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Martin Varga
 */
@Configuration
public class DynamoDBConfig {

    @Value("${aws.dynamodb.endpoint:default}")
    private String dynamoDbEndpoint;

    @Value("${aws.accesskey:access}")
    private String accessKey;

    @Value("${aws.secretkey:secret}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Autowired private AmazonDynamoDB amazonDynamoDB;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)));

        if ("default".equals(dynamoDbEndpoint)) {
            builder = builder.withRegion(region);
        } else {
            builder = builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8088", region));
        }

        return builder.build();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(amazonDynamoDB);
    }
}