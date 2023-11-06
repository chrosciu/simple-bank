package eu.chrost.simplebank.moneylaundering;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MoneyLaunderingProperties.class)
public class MoneyLaunderingConfiguration {
}
