package eu.chrost.simplebank.moneylaundering;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "simple-bank.money-laundering")
public record MoneyLaunderingProperties(long threshold) {
}
