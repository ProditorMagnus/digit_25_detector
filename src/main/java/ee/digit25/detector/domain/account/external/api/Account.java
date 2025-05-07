package ee.digit25.detector.domain.account.external.api;

import lombok.*;

import java.math.BigDecimal;

@Value
public class Account {

    private String number;
    private String owner;
    private Boolean closed;
    private BigDecimal balance;
}
