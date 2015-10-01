package de.dhbw.meetme.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.UserTransaction;

/**
 *
 */
@ApplicationScoped
public class Transaction {
    private static final Logger log = LoggerFactory.getLogger(Transaction.class);

    @Resource
    UserTransaction userTransaction;

    public void begin() {
        try {
            userTransaction.begin();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new RuntimeException(e);
        }
    }

    public void commit() {
        try {
            userTransaction.commit();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new RuntimeException(e);
        }
    }
}
