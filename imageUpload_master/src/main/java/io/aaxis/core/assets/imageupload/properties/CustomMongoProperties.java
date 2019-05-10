package io.aaxis.core.assets.imageupload.properties;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The custom mongo DB properties
 *
 * Created by terrence on 2019/03/29.
 */
@Component
@ConfigurationProperties(prefix = "spring.data.mongodb.custom")
@Getter
@Setter
@ToString
public class CustomMongoProperties {

    @NotNull
    private String        database;
    @NotNull
    private List<String>  hosts;
    @NotNull
    private List<Integer> ports;
    private String        replicaSet;
    private String        username;
    private String        password;
    private String        authenticationDatabase;
    private Integer       connectionPoolMinSize;
    private Integer       connectionPoolMaxSize;
    private Integer       connectionPoolMaxWaitTime;
    private Integer       socketConnectTimeout;
    private Integer       socketReadTimeout;
    private Long          clusterSettingsServerSelectionTimeout;

}