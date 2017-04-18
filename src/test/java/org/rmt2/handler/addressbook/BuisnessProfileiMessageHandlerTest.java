package org.rmt2.handler.addressbook;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.ArrayList;
import java.util.List;

import org.dao.mapping.orm.rmt2.Address;
import org.dao.mapping.orm.rmt2.Business;
import org.dto.BusinessContactDto;
import org.dto.ContactDto;
import org.dto.adapter.orm.Rmt2AddressBookDtoFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.contacts.ContactsApi;
import org.modules.contacts.ContactsApiException;
import org.modules.contacts.ContactsApiFactory;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.handlers.addressbook.profile.BusinessProfilePayloadHandler;

import com.api.messaging.jms.JmsClientManager;

/**
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ BusinessProfilePayloadHandler.class, JmsClientManager.class })
public class BuisnessProfileiMessageHandlerTest extends
        BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "Test-AddressBook-Queue";
    private ContactsApiFactory mockContactsApiFactory;
    private ContactsApi mockApi;
    private String mockSingleProfileRequest;
    private List<ContactDto> mockSinglePRofileResponse;



    /**
     * 
     */
    public BuisnessProfileiMessageHandlerTest() {
        // TODO Auto-generated constructor stub
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
        this.mockContactsApiFactory = Mockito.mock(ContactsApiFactory.class);
        this.mockApi = Mockito.mock(ContactsApi.class);
        try {
            whenNew(ContactsApiFactory.class).withNoArguments().thenReturn(this.mockContactsApiFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        when(this.mockContactsApiFactory.createApi()).thenReturn(this.mockApi);
        this.mockSingleProfileRequest = this.createSingleProfileRequestData();
        this.mockSinglePRofileResponse = this.createcreateSingleProfileResponseData();
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

    private String createSingleProfileRequestData() {
        StringBuilder r = new StringBuilder();
        r.append("<AddressBookRequest>");
        r.append("  <header>");
        r.append("    <routing>JMS: addressbook-p2p-dest</routing>");
        r.append("    <application>addressbook</application>");
        r.append("    <module>profile</module>");
        r.append("    <transaction>GET_BUSINESS_CONTACT</transaction>");
        r.append("    <delivery_mode>SYNC</delivery_mode>");
        r.append("    <message_mode>REQUEST</message_mode>");
        r.append("    <delivery_date>2017-04-16 02:01:12</delivery_date>");
        r.append("</header>");
        r.append("<criteria>");
        r.append("    <business_criteria>");
        r.append("        <contact_id>1351</contact_id>");
        r.append("       <entity_type xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\"/>");
        r.append("        <service_type xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\"/>");
        r.append("    </business_criteria>");
        r.append(" </criteria>");
        r.append("</AddressBookRequest>");
        return r.toString();
    }

    private List<ContactDto> createcreateSingleProfileResponseData() {
        Business bus = new Business();
        Address addr = new Address();

        bus.setBusinessId(1351);
        bus.setLongname("Ticket Master");

        addr.setAddrId(2258);
        addr.setBusinessId(1351);
        addr.setPhoneMain("2143738000");

        BusinessContactDto busDto = Rmt2AddressBookDtoFactory.getBusinessInstance(bus, addr);

        List<ContactDto> list = new ArrayList<ContactDto>();
        list.add(busDto);
        return list;
    }

    @Test
    public void fetchSingleBusinessContact() {
        this.setupMocks(DESTINATION, this.mockSingleProfileRequest);
        try {
            when(this.mockApi.getContact(any(ContactDto.class))).thenReturn(this.mockSinglePRofileResponse);
        } catch (ContactsApiException e) {

        }

        this.startTest();
    }
}
