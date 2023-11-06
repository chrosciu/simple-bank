package eu.chrost.simplebank.moneylaundering;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Aspect
public class MoneyLaunderingAspect {
    @Before("execution(* eu.chrost.simplebank.transfer.TransferService.makeTransfer(..)) && args(*, *, amount)")
    public void checkRiskyTransfers(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(2000)) > 0) {
            throw new IllegalStateException("Money laundering detected");
        }
    }
}
