package eu.chrost.simplebank.account;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Account {
    private Long id;
    private BigDecimal balance;
    @Builder.Default
    private boolean locked = false;
}
