package eu.chrost.simplebank.transfer;

import java.math.BigDecimal;

public record TransferRequest(long fromAccountId, long toAccountId, BigDecimal amount) {}
