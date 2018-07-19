package org.rmt2.handler.accounting;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.List;

import org.dto.AccountCategoryDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.generalledger.GeneralLedgerApiException;
import org.modules.generalledger.GeneralLedgerApiFactory;
import org.modules.generalledger.GlAccountApi;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.AccountingMockData;
import org.rmt2.BaseMockMessageDrivenBeanTest;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the GL Account API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class })
public class GlAccountCategoryJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "Test-Accounting-Queue";
    private GeneralLedgerApiFactory mockApiFactory;
    private GlAccountApi mockApi;


    /**
     * 
     */
    public GlAccountCategoryJmsTest() {
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
        this.mockApiFactory = Mockito.mock(GeneralLedgerApiFactory.class);
        this.mockApi = Mockito.mock(GlAccountApi.class);
        try {
            whenNew(GeneralLedgerApiFactory.class).withNoArguments().thenReturn(this.mockApiFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        when(mockApiFactory.createApi(isA(String.class))).thenReturn(mockApi);
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
    public void invokeHandelrSuccess_Fetch() {
        String request = RMT2File.getFileContentsAsString("xml/generalledger/AccountCategoryFetchRequest.xml");
        List<AccountCategoryDto> mockDtoDataResponse = AccountingMockData.createMockGlAccountCategories();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getAccountCategory(isA(AccountCategoryDto.class))).thenReturn(mockDtoDataResponse);
        } catch (GeneralLedgerApiException e) {

        }

        try {
            this.startTest();    
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
    
    @Test
    public void invokeHandelrError_Fetch_Incorrect_Trans_Code() {
        String request = RMT2File.getFileContentsAsString("xml/generalledger/AccountCategoryFetchIncorrectTransCodeRequest.xml");
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
