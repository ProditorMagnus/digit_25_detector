package ee.digit25.detector.domain.account;

import ee.digit25.detector.domain.account.external.AccountRequester;
import ee.digit25.detector.domain.account.external.api.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountValidator {

    private final AccountRequester requester;

    public boolean isValidSenderAccount(String accountNumber, BigDecimal amount, String senderPersonCode) {
        log.info("Checking if account {} is valid sender account", accountNumber);
        boolean isValid = true;

        Account account = requester.get(accountNumber);
        isValid &= !isClosed(account);

        if (!isValid)
            return false;

        isValid &= isOwner(account, senderPersonCode);
        if (!isValid)
            return false;

        isValid &= hasBalance(account, amount);

        return isValid;
    }

    public boolean isValidRecipientAccount(String accountNumber, String recipientPersonCode) {
        log.info("Checking if account {} is valid recipient account", accountNumber);
        boolean isValid = true;

        Account account = requester.get(accountNumber);
        isValid &= !isClosed(account);
        if (!isValid)
            return false;

        isValid &= isOwner(account, recipientPersonCode);

        return isValid;
    }

    private boolean isOwner(Account account, String senderPersonCode) {
        //log.info("Checking if {} is owner of account {}", senderPersonCode, accountNumber);

        return senderPersonCode.equals(account.getOwner());
    }

    private boolean hasBalance(Account account, BigDecimal amount) {
        //log.info("Checking if account {} has balance for amount {}", accountNumber, amount);

        return account.getBalance().compareTo(amount) >= 0;
    }

    private boolean isClosed(Account account) {
        //log.info("Checking if account {} is closed", accountNumber);

        return account.getClosed();
    }
}
