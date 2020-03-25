package org.rmt2.handler.accounting.subsidiary;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dto.CreditorTypeDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.subsidiary.CreditorApi;
import org.modules.subsidiary.CreditorApiException;
import org.modules.subsidiary.SubsidiaryApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.AccountingMockData;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.subsidiary.CreditorTypeApiHandler;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the Creditor Type API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, CreditorTypeApiHandler.class, SubsidiaryApiFactory.class })
public class CreditorTypeJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "Test-Accounting-Queue";
    private CreditorApi mockApi;


    /**
     * 
     */
    public CreditorTypeJmsTest() {
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
        this.mockApi = Mockito.mock(CreditorApi.class);
        PowerMockito.mockStatic(SubsidiaryApiFactory.class);
        when(SubsidiaryApiFactory.createCreditorApi(isA(String.class))).thenReturn(mockApi);
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
    public void invokeHandlerSuccess_FetchCreditorTypes() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/subsidiary/CreditorTypeQueryRequest.xml");
        List<CreditorTypeDto> mockListData = AccountingMockData.createMockCreditorTypes();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getCreditorType(isA(CreditorTypeDto.class))).thenReturn(mockListData);
        } catch (CreditorApiException e) {
            e.printStackTrace();
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).getCreditorType(isA(CreditorTypeDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    

    @Test
    public void invokeHandlerError_Fetch_Incorrect_Trans_Code() {
        String request = RMT2File
                .getFileContentsAsString("xml/accounting/subsidiary/CreditorTypeHandlerInvalidTransCodeRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            this.startTest(); 
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
  
}
