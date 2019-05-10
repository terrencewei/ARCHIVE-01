package io.aaxis.core.assets.imageupload.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import io.aaxis.core.assets.imageupload.properties.CustomMongoProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * Custom mongo config to support more features
 *
 * Created by terrence on 2019/03/29.
 *
 * @see org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration
 * @see org.springframework.boot.autoconfigure.mongo.ReactiveMongoClientFactory
 */
@Configuration
@Slf4j
public class CustomMongoConfig {

    @Autowired
    private CustomMongoProperties customMongoProperties;



    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() throws Exception {
        return new ReactiveMongoTemplate(getReactiveMongoDatabaseFactory());
    }



    @Bean
    public ReactiveMongoDatabaseFactory getReactiveMongoDatabaseFactory() throws Exception {
        return new SimpleReactiveMongoDatabaseFactory(getMongoClient(), customMongoProperties.getDatabase());

    }



    @Bean
    public MongoClient getMongoClient() {
        List<ServerAddress> serverAddresses = new ArrayList<>();
        for (String host : customMongoProperties.getHosts()) {
            Integer index = customMongoProperties
                    .getHosts()
                    .indexOf(host);
            Integer port = customMongoProperties
                    .getPorts()
                    .get(index);

            ServerAddress serverAddress = new ServerAddress(host, port);
            serverAddresses.add(serverAddress);
        }
        log.info("getMongoClient() Using serverAddresses:{}", serverAddresses);

        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(customMongoProperties.getUsername(),
                customMongoProperties.getAuthenticationDatabase() != null ?
                        customMongoProperties.getAuthenticationDatabase() :
                        customMongoProperties.getDatabase(), customMongoProperties
                        .getPassword()
                        .toCharArray());

        log.info("getMongoClient() Using mongoCredential:{}", mongoCredential);

        MongoClientSettings settings = MongoClientSettings
                .builder()
                .applyToConnectionPoolSettings(pBuilder -> pBuilder
                        .minSize(customMongoProperties.getConnectionPoolMinSize())
                        .maxSize(customMongoProperties.getConnectionPoolMaxSize())
                        .maxWaitTime(customMongoProperties.getConnectionPoolMaxWaitTime(), TimeUnit.SECONDS)
                        .build())
                .applyToSocketSettings(pBuilder -> pBuilder
                        .connectTimeout(customMongoProperties.getSocketConnectTimeout(), TimeUnit.SECONDS)
                        .readTimeout(customMongoProperties.getSocketReadTimeout(), TimeUnit.SECONDS)
                        .build())
                .credential(mongoCredential)
                .applyToClusterSettings(pBuilder -> pBuilder
                        .hosts(serverAddresses)
                        .serverSelectionTimeout(customMongoProperties.getClusterSettingsServerSelectionTimeout(),
                                TimeUnit.SECONDS)
                        .build())
                .build();

        return MongoClients.create(settings);
    }

}