package org.rmt2.handler.accounting.transaction.sales;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.transaction.XactApiFactory;
import org.modules.transaction.sales.SalesApi;
import org.modules.transaction.sales.SalesApiException;
import org.modules.transaction.sales.SalesApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.transaction.sales.CancelSalesOrderApiHandler;
import org.rmt2.handler.accounting.transaction.TransactionDatasourceMock;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;

/**
 * Test the idenity and invocation of the sales order cancel message handler
 * Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, XactApiFactory.class, CancelSalesOrderApiHandler.class, SalesApiFactory.class })
public class SalesOrderCancelJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "Test-Accounting-Queue";
    private SalesApi mockApi;

    public static final int NEW_XACT_ID = 1234567;

    /**
     * 
     */
    public SalesOrderCancelJmsTest() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see testcases.messaging.MessageToListenerToHandlerTest#setUp()
     */
    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        // Setup Transaction Datasource mocks
        TransactionDatasourceMock.setupXactMocks();

        // Setup Sales Order API Mocks
        mockApi = Mockito.mock(SalesApi.class);
        PowerMockito.mockStatic(SalesApiFactory.class);
        PowerMockito.when(SalesApiFactory.createApi()).thenReturn(this.mockApi);
        doNothing().when(this.mockApi).close();
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see testcases.messaging.MessageToListenerToHandlerTest#tearDown()
     */
    @After
    public void tearDown() throws Exception {
        return;
    }

    @Test
    public void invokeHandlerSuccess_Cancel() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/transaction/sales/SalesOrderCancelRequest.xml");

        this.setupMocks(DESTINATION, request);

        try {
            when(this.mockApi.cancelSalesOrder(isA(Integer.class))).thenReturn(1);
        } catch (SalesApiException e) {
            Assert.fail("Unable to setup mock stub for cancelling a sales order transaction");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi, times(3)).cancelSalesOrder(isA(Integer.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

    @Test
    public void invokeHandlerError_Incorrect_Trans_Code() {
        String request = RMT2File
                .getFileContentsAsString("xml/accounting/transaction/sales/SalesOrderCreateInvalidTransCodeRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            this.startTest();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
}
