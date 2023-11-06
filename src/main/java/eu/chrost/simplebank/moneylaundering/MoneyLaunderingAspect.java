package eu.chrost.simplebank.moneylaundering;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class MoneyLaunderingAspect {
    private final MoneyLaunderingProperties properties;

    @PostConstruct
    void init() {
        log.info("Initalized with threshold: {}", properties.threshold());
    }

    @Before("execution(* eu.chrost.simplebank.transfer.TransferService.makeTransfer(..)) && args(*, *, amount)")
    public void checkRiskyTransfers(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(properties.threshold())) > 0) {
            throw new IllegalStateException("Money laundering detected");
        }
    }
}
