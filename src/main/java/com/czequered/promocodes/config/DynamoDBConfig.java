package com.czequered.promocodes.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Martin Varga
 */
@Configuration
@EnableDynamoDBRepositories(basePackages = "com.czequered.promocodes.repository")
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint:default}")
    private String dynamoDbEndpoint;

    @Value("${amazon.aws.accesskey:access}")
    private String accessKey;

    @Value("${amazon.aws.secretkey:default}")
    private String secretKey;

    @Value("${amazon.aws.region}")
    private String region;

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

}