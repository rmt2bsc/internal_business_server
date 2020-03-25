package org.rmt2.handler.accounting.inventory;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.List;

import org.dto.ItemMasterTypeDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.inventory.InventoryApi;
import org.modules.inventory.InventoryApiException;
import org.modules.inventory.InventoryApiFactory;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.AccountingMockData;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.inventory.ItemTypeApiHandler;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the Inventory Item Type API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, ItemTypeApiHandler.class, InventoryApiFactory.class })
public class InventoryItemTypeJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "Test-Accounting-Queue";
    private InventoryApiFactory mockApiFactory;
    private InventoryApi mockApi;


    /**
     * 
     */
    public InventoryItemTypeJmsTest() {
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
        this.mockApiFactory = Mockito.mock(InventoryApiFactory.class);
        this.mockApi = Mockito.mock(InventoryApi.class);
        try {
            whenNew(InventoryApiFactory.class).withNoArguments().thenReturn(this.mockApiFactory);
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
    public void invokeHandlerSuccess_Fetch() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/inventory/ItemtypeFetchRequest.xml");
        List<ItemMasterTypeDto> mockListData = AccountingMockData.createMockItemType();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getItemType(isA(ItemMasterTypeDto.class))).thenReturn(mockListData);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).getItemType(isA(ItemMasterTypeDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
    
    @Test
    public void invokeHandelrError_Fetch_Incorrect_Trans_Code() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/inventory/ItemtypeFetchIncorrectTransCodeRequest.xml");
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
