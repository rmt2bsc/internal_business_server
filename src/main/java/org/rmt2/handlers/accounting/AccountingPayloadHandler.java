package org.rmt2.handlers.accounting;

import org.rmt2.handlers.AbstractMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author appdev
 *
 */
public class AccountingPayloadHandler extends AbstractMessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(AccountingPayloadHandler.class);

    /**
     * @param payload
     */
    public AccountingPayloadHandler() {
        super();
        logger.info(AccountingPayloadHandler.class.getName() + " was instantiated successfully");
    }

    // /*
    // * (non-Javadoc)
    // *
    // * @see
    // *
    // com.api.messaging.jms.handler.AbstractMessageHandler#processRequest(java
    // * .lang.String, java.io.Serializable)
    // */
    // @Override
    // public MessageHandlerResults processMessage(String command, Serializable
    // payload)
    // throws MessageHandlerCommandException {
    // super.processMessage(command, payload);
    // String data = this.getPayloadAsString();
    //
    // List<AccountDto> list = this.getAccount("Accounts Payable");
    // MessageHandlerResults r = new MessageHandlerResults();
    // r.setPayload("GL Account description ==================> " +
    // list.get(0).getAcctDescription());
    // return r;
    // }
    //
    // private List<AccountDto> getAccount(String acctName) {
    // // Test config access
    // AppServerConfig config = SystemConfigurator.getConfig();
    // GeneralLedgerApiFactory f = new GeneralLedgerApiFactory();
    // GlAccountApi api = f.createApi();
    // AccountDto criteria = Rmt2AccountDtoFactory.createAccountInstance(null);
    // criteria.setAcctName(acctName);
    // try {
    // return api.getAccount(criteria);
    // } catch (Exception e) {
    // throw new RMT2RuntimeException(e);
    // }
    // }

}
