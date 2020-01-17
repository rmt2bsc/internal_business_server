package org.rmt2.handler.accounting.transaction.sales;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dao.mapping.orm.rmt2.SalesOrderStatus;
import org.dao.mapping.orm.rmt2.SalesOrderStatusHist;
import org.dto.SalesOrderDto;
import org.dto.SalesOrderStatusDto;
import org.dto.adapter.orm.transaction.sales.Rmt2SalesOrderDtoFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.transaction.XactApiFactory;
import org.modules.transaction.sales.SalesApi;
import org.modules.transaction.sales.SalesApiConst;
import org.modules.transaction.sales.SalesApiException;
import org.modules.transaction.sales.SalesApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.transaction.sales.CreateSalesOrderAutoInvoiceCashReceiptApiHandler;
import org.rmt2.api.handlers.transaction.sales.SalesOrderRequestUtil;
import org.rmt2.handler.accounting.transaction.TransactionDatasourceMock;
import org.rmt2.jaxb.SalesOrderType;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;

/**
 * Test the idenity and invocation of the sales order create, invoice, and cash
 * receipt message handler Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, XactApiFactory.class, CreateSalesOrderAutoInvoiceCashReceiptApiHandler.class,
        SalesOrderRequestUtil.class, SalesApiFactory.class })
public class SalesOrderCreateAndInvoiceAndCashReceiptJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "Test-Accounting-Queue";
    private SalesApi mockApi;

    public static final int NEW_XACT_ID = 1234567;

    /**
     * 
     */
    public SalesOrderCreateAndInvoiceAndCashReceiptJmsTest() {

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
    public void invokeHandlerSuccess_Create() {
        String request = RMT2File
                .getFileContentsAsString("xml/transaction/sales/SalesOrderCreateAndInvoiceAndCashReceiptRequest.xml");

        SalesOrderStatusHist ormStatusHist = new SalesOrderStatusHist();
        ormStatusHist.setSoStatusId(SalesApiConst.STATUS_CODE_QUOTE);
        SalesOrderStatus ormStatus = new SalesOrderStatus();
        ormStatus.setSoStatusId(SalesApiConst.STATUS_CODE_QUOTE);
        ormStatus.setDescription("Quote");
        SalesOrderStatusDto mockStatusDto = Rmt2SalesOrderDtoFactory.createSalesOrderStatusInstance(ormStatus);

        this.setupMocks(DESTINATION, request);
        PowerMockito.mockStatic(SalesOrderRequestUtil.class);

        try {
            PowerMockito.when(SalesOrderRequestUtil.createSalesOrder(isA(SalesApi.class), isA(SalesOrderDto.class), isA(List.class), isA(SalesOrderType.class))).thenReturn(SalesOrderJmsMockData.NEW_XACT_ID);
        } catch (SalesApiException e) {
            Assert.fail("Unable to setup mock stub for creating a sales order transaction");
        }

        try {
            PowerMockito.when(SalesOrderRequestUtil.invoiceSalesOrder(isA(SalesApi.class), isA(SalesOrderDto.class),
                    isA(List.class), eq(true), isA(SalesOrderType.class))).thenReturn(SalesOrderJmsMockData.NEW_INVOICE_ID);
        } catch (SalesApiException e) {
            Assert.fail("Unable to setup mock stub for invoicing a sales order");
        }

        try {
            PowerMockito.doNothing()
                    .when(SalesOrderRequestUtil.class, isA(SalesApi.class), isA(SalesOrderType.class));
        } catch (Exception e) {
            Assert.fail("Unable to setup mock stub for creating a sales order status history DTO object");
        }

        try {
            when(this.mockApi.getStatus(isA(Integer.class))).thenReturn(mockStatusDto);
        } catch (SalesApiException e) {
            Assert.fail("Unable to setup mock stub for creating a sales order status DTO object");
        }

        try {
            this.startTest();
            PowerMockito.verifyStatic();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

    @Test
    public void invokeHandlerError_Incorrect_Trans_Code() {
        String request = RMT2File
                .getFileContentsAsString("xml/transaction/sales/SalesOrderCreateInvalidTransCodeRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            this.startTest();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

}
