package com.nd.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;



import lombok.Data;


@Data
@ConfigurationProperties(prefix = "plan.module")
@Component
@EnableConfigurationProperties
public class AppConfigProperties {

	private Map<String, String> messages;
}
