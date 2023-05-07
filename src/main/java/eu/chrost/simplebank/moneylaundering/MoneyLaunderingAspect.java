package eu.chrost.simplebank.moneylaundering;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Aspect
@Slf4j
public class MoneyLaunderingAspect {

    //@Before("execution(* eu.chrost.simplebank.transfer.TransferService.makeTransfer(..)) && args(fromAccountId, toAccountId, amount)")
    public void checkRiskyTransfers(long fromAccountId, long toAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(2000)) > 0) {
            throw new IllegalStateException("Money laundering detected");
        }
    }

    @Around("execution(* eu.chrost.simplebank.transfer.TransferService.makeTransfer(..)) && args(fromAccountId, toAccountId, amount)")
    public Object ignoreRiskyTransfers(ProceedingJoinPoint pjp, long fromAccountId, long toAccountId, BigDecimal amount) throws Throwable {
        if (amount.compareTo(BigDecimal.valueOf(2000)) > 0) {
            log.info("Ignoring transfer due to money laundering");
            return null;
        } else {
            return pjp.proceed();
        }
    }

}
