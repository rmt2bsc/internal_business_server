/**
 * 
 */
package testcases.messaging.addressbook;

import java.math.BigInteger;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBElement;

import org.junit.Assert;
import org.junit.Test;
import org.rmt2.jaxb.AddressBookRequest;
import org.rmt2.jaxb.AddressBookResponse;
import org.rmt2.jaxb.AddressType;
import org.rmt2.jaxb.BusinessContactCriteria;
import org.rmt2.jaxb.BusinessType;
import org.rmt2.jaxb.CodeDetailType;
import org.rmt2.jaxb.ContactCriteriaGroup;
import org.rmt2.jaxb.ContactDetailGroup;
import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.jaxb.ZipcodeType;

import testcases.messaging.MessageToListenerToHandlerTest;

/**
 * @author appdev
 *
 */
public class BuisnessContactApiMessagingTest extends
        MessageToListenerToHandlerTest {

    private static final String DESTINATION = "Contacts";

    /**
     * 
     */
    public BuisnessContactApiMessagingTest() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see testcases.messaging.MessageToListenerToHandlerTest#setUp()
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    /*
     * (non-Javadoc)
     * 
     * @see testcases.messaging.MessageToListenerToHandlerTest#tearDown()
     */
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void fetchSingleBusinessContact() {
        Message response = null;
        try {
            response = this.testSynchronizedListener(
                    BuisnessContactApiMessagingTest.DESTINATION,
                    this.getSingleBusinessCriteriaMessage(1475));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertNotNull(response);
        Assert.assertTrue(response instanceof TextMessage);

        try {
            AddressBookResponse respObj = (AddressBookResponse) this.jaxb
                    .unMarshalMessage(((TextMessage) response).getText());

            BusinessType busTypeObj = respObj.getProfile().getValue()
                    .getBusinessContacts().get(0);
            Assert.assertEquals(0, respObj.getReplyStatus().getReturnCode()
                    .intValue());
            Assert.assertNotNull(busTypeObj);
            Assert.assertNotNull(busTypeObj.getBusinessId());
            String busName = busTypeObj.getLongName();
            Assert.assertNotNull(busName);
            Assert.assertEquals("Crescent Solutions", busName);

        } catch (JMSException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void fetchMultipleBusinessContact() {
        Message response = null;
        try {
            response = this.testSynchronizedListener(
                    BuisnessContactApiMessagingTest.DESTINATION,
                    this.getMultipleBusinessCriteriaMessage());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertNotNull(response);
        Assert.assertTrue(response instanceof TextMessage);

        try {
            AddressBookResponse respObj = (AddressBookResponse) this.jaxb
                    .unMarshalMessage(((TextMessage) response).getText());

            List<BusinessType> busTypeObj = respObj.getProfile().getValue()
                    .getBusinessContacts();
            Assert.assertEquals(0, respObj.getReplyStatus().getReturnCode()
                    .intValue());
            Assert.assertNotNull(busTypeObj);
            Assert.assertTrue(busTypeObj.size() > 2);

        } catch (JMSException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void invalidProfileBusinessContactMessage() {
        Message response = null;
        try {
            response = this.testSynchronizedListener(
                    BuisnessContactApiMessagingTest.DESTINATION,
                    this.getBusinessContactMessageWithInvalidProfile());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertNotNull(response);
        Assert.assertTrue(response instanceof TextMessage);
        try {
            AddressBookResponse respObj = (AddressBookResponse) this.jaxb
                    .unMarshalMessage(((TextMessage) response).getText());
            Assert.assertEquals(1, respObj.getReplyStatus().getReturnCode()
                    .intValue());
        } catch (JMSException e) {
            Assert.fail(e.getMessage());
        }
        try {
            System.out.println("Reply: " + ((TextMessage) response).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void emptyBusinessContactMessage() {
        Message response = null;
        try {
            response = this.testSynchronizedListener(
                    BuisnessContactApiMessagingTest.DESTINATION,
                    this.getBusinessContactMessageWithEmptyContacts());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertNotNull(response);
        Assert.assertTrue(response instanceof TextMessage);
        try {
            AddressBookResponse respObj = (AddressBookResponse) this.jaxb
                    .unMarshalMessage(((TextMessage) response).getText());
            Assert.assertEquals(1, respObj.getReplyStatus().getReturnCode()
                    .intValue());
        } catch (JMSException e) {
            Assert.fail(e.getMessage());
        }
        try {
            System.out.println("Reply: " + ((TextMessage) response).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addDeleteBusinessContact() {
        Message response = null;
        try {
            response = this.testSynchronizedListener(
                    BuisnessContactApiMessagingTest.DESTINATION,
                    this.getValidBusinessMessage());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertNotNull(response);
        Assert.assertTrue(response instanceof TextMessage);

        int newBusId = 0;
        try {
            AddressBookResponse respObj = (AddressBookResponse) this.jaxb
                    .unMarshalMessage(((TextMessage) response).getText());

            BusinessType busTypeObj = respObj.getProfile().getValue()
                    .getBusinessContacts().get(0);
            Assert.assertEquals(0, respObj.getReplyStatus().getReturnCode()
                    .intValue());
            Assert.assertNotNull(busTypeObj);
            Assert.assertNotNull(busTypeObj.getBusinessId());
            newBusId = busTypeObj.getBusinessId().intValue();
            Assert.assertTrue(newBusId > 0);

        } catch (JMSException e) {
            Assert.fail(e.getMessage());
        }
        try {
            System.out.println("Reply: " + ((TextMessage) response).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }

        // Delete record
        try {
            response = this.testSynchronizedListener(
                    BuisnessContactApiMessagingTest.DESTINATION,
                    this.getDeleteBusinessMessage(newBusId));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void addUpdateDeleteBusinessContact() {
        Message response = null;
        try {
            response = this.testSynchronizedListener(
                    BuisnessContactApiMessagingTest.DESTINATION,
                    this.getValidBusinessMessage());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertNotNull(response);
        Assert.assertTrue(response instanceof TextMessage);

        int newBusId = 0;
        try {
            AddressBookResponse respObj = (AddressBookResponse) this.jaxb
                    .unMarshalMessage(((TextMessage) response).getText());

            BusinessType busTypeObj = respObj.getProfile().getValue()
                    .getBusinessContacts().get(0);
            Assert.assertEquals(0, respObj.getReplyStatus().getReturnCode()
                    .intValue());
            Assert.assertNotNull(busTypeObj);
            Assert.assertNotNull(busTypeObj.getBusinessId());
            newBusId = busTypeObj.getBusinessId().intValue();
            Assert.assertTrue(newBusId > 0);

        } catch (JMSException e) {
            Assert.fail(e.getMessage());
        }
        try {
            System.out.println("Reply: " + ((TextMessage) response).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }

        // Fetch Changes
        BusinessType busTypeUpd = null;
        try {
            response = this.testSynchronizedListener(
                    BuisnessContactApiMessagingTest.DESTINATION,
                    this.getSingleBusinessCriteriaMessage(newBusId));
            AddressBookResponse respObj = (AddressBookResponse) this.jaxb
                    .unMarshalMessage(((TextMessage) response).getText());

            busTypeUpd = respObj.getProfile().getValue().getBusinessContacts()
                    .get(0);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        // Update record
        AddressBookRequest req = this.getRequestWithoutContacts();
        req.getHeader().setTransaction("UpdateBusiness");
        busTypeUpd.setBusinessId(BigInteger.valueOf(newBusId));
        busTypeUpd.setContactEmail("rterrell2@gmail.com");
        req.getProfile().getValue().getBusinessContacts().add(busTypeUpd);
        String xml = this.jaxb.marshalMessage(req);
        try {
            response = this.testSynchronizedListener(
                    BuisnessContactApiMessagingTest.DESTINATION, xml);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        // Fetch Changes
        try {
            response = this.testSynchronizedListener(
                    BuisnessContactApiMessagingTest.DESTINATION,
                    this.getSingleBusinessCriteriaMessage(newBusId));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertNotNull(response);
        Assert.assertTrue(response instanceof TextMessage);

        try {
            AddressBookResponse respObj = (AddressBookResponse) this.jaxb
                    .unMarshalMessage(((TextMessage) response).getText());

            BusinessType busTypeObj = respObj.getProfile().getValue()
                    .getBusinessContacts().get(0);
            Assert.assertEquals(0, respObj.getReplyStatus().getReturnCode()
                    .intValue());
            Assert.assertNotNull(busTypeObj);
            Assert.assertNotNull(busTypeObj.getBusinessId());
            String busName = busTypeObj.getLongName();
            Assert.assertNotNull(busName);
            Assert.assertEquals("Test MDB Company", busName);
            Assert.assertEquals("rterrell2@gmail.com",
                    busTypeObj.getContactEmail());

        } catch (JMSException e) {
            Assert.fail(e.getMessage());
        }

        // Delete record
        try {
            response = this.testSynchronizedListener(
                    BuisnessContactApiMessagingTest.DESTINATION,
                    this.getDeleteBusinessMessage(newBusId));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * contacts.business.AddBusiness
     * 
     * @return
     */
    private String getValidBusinessMessage() {
        ObjectFactory f = new ObjectFactory();
        AddressBookRequest r = f.createAddressBookRequest();

        HeaderType h = f.createHeaderType();
        h.setRouting("rmt2.queue.contacts");
        h.setApplication("contacts");
        h.setModule("business");
        h.setTransaction("AddBusiness");
        h.setDeliveryMode("SYNC");
        h.setDeliveryDate("2016-02-25");
        h.setMessageMode("REQUEST");
        h.setSessionId("kfdksdiewkdkd9el2393w");
        h.setUserId("rterrell_test");

        ContactDetailGroup cdg = f.createContactDetailGroup();
        BusinessType bus = f.createBusinessType();
        bus.setContactFirstname("Roy");
        bus.setContactLastname("Terrell");
        bus.setContactEmail("royroy@gte.net");
        bus.setContactPhone("9999999999");
        bus.setContactExt("123");
        bus.setLongName("Test MDB Company");
        bus.setShortName("TMC");
        bus.setWebsite("www.royroy.com");
        bus.setTaxId(null);
        bus.setCategory(null);

        CodeDetailType cdtEntity = f.createCodeDetailType();
        cdtEntity.setCodeId(BigInteger.valueOf(26));
        bus.setEntityType(cdtEntity);

        CodeDetailType cdtServ = f.createCodeDetailType();
        cdtServ.setCodeId(BigInteger.valueOf(41));
        bus.setServiceType(cdtServ);

        cdg.getBusinessContacts().add(bus);

        AddressType addr = f.createAddressType();
        addr.setAddr1("610 Hoover Dr");
        addr.setAddr2("Ste 234");
        addr.setAddr3("P.O. Box 45");
        addr.setPhoneMain("3189993339");

        ZipcodeType zip = f.createZipcodeType();
        zip.setZipcode(BigInteger.valueOf(71118));
        addr.setZip(zip);
        bus.setAddress(addr);

        JAXBElement<ContactDetailGroup> detailGrp = f
                .createAddressBookRequestProfile(cdg);

        r.setProfile(detailGrp);
        r.setHeader(h);

        String xml = this.jaxb.marshalMessage(r);
        return xml;

    }

    private String getUpdateBusinessMessage(long businessId) {
        ObjectFactory f = new ObjectFactory();
        AddressBookRequest r = f.createAddressBookRequest();

        HeaderType h = f.createHeaderType();
        h.setRouting("rmt2.queue.contacts");
        h.setApplication("contacts");
        h.setModule("business");
        h.setTransaction("UpdateBusiness");
        h.setDeliveryMode("SYNC");
        h.setDeliveryDate("2016-02-25");
        h.setMessageMode("REQUEST");
        h.setSessionId("kfdksdiewkdkd9el2393w");
        h.setUserId("rterrell_test");

        ContactDetailGroup cdg = f.createContactDetailGroup();
        BusinessType bus = f.createBusinessType();
        bus.setBusinessId(BigInteger.valueOf(businessId));
        bus.setContactFirstname("Roy");
        bus.setContactLastname("Terrell");
        bus.setContactEmail("rterrell@gte.net");
        bus.setContactPhone("9726981234");
        bus.setContactExt("123");
        bus.setLongName("Test MDB Company");
        bus.setShortName("TMC");
        bus.setWebsite("www.royroy.com");
        bus.setTaxId(null);
        bus.setCategory(null);

        CodeDetailType cdtEntity = f.createCodeDetailType();
        cdtEntity.setCodeId(BigInteger.valueOf(26));
        bus.setEntityType(cdtEntity);

        CodeDetailType cdtServ = f.createCodeDetailType();
        cdtServ.setCodeId(BigInteger.valueOf(41));
        bus.setServiceType(cdtServ);

        cdg.getBusinessContacts().add(bus);

        AddressType addr = f.createAddressType();
        addr.setAddr1("9328 Hall Ave");
        addr.setPhoneMain("3189993339");

        ZipcodeType zip = f.createZipcodeType();
        zip.setZipcode(BigInteger.valueOf(75232));
        addr.setZip(zip);
        bus.setAddress(addr);

        JAXBElement<ContactDetailGroup> detailGrp = f
                .createAddressBookRequestProfile(cdg);

        r.setProfile(detailGrp);
        r.setHeader(h);

        String xml = this.jaxb.marshalMessage(r);
        return xml;

    }

    private AddressBookRequest getRequestWithoutContacts() {
        ObjectFactory f = new ObjectFactory();
        AddressBookRequest r = f.createAddressBookRequest();

        HeaderType h = f.createHeaderType();
        h.setRouting("rmt2.queue.contacts");
        h.setApplication("contacts");
        h.setModule("business");
        h.setTransaction("UpdateBusiness");
        h.setDeliveryMode("SYNC");
        h.setDeliveryDate("2016-02-25");
        h.setMessageMode("REQUEST");
        h.setSessionId("kfdksdiewkdkd9el2393w");
        h.setUserId("rterrell_test");

        ContactDetailGroup cdg = f.createContactDetailGroup();

        JAXBElement<ContactDetailGroup> detailGrp = f
                .createAddressBookRequestProfile(cdg);

        r.setProfile(detailGrp);
        r.setHeader(h);

        return r;
    }

    private String getDeleteBusinessMessage(long businessId) {
        ObjectFactory f = new ObjectFactory();
        AddressBookRequest r = f.createAddressBookRequest();

        HeaderType h = f.createHeaderType();
        h.setRouting("rmt2.queue.contacts");
        h.setApplication("contacts");
        h.setModule("business");
        h.setTransaction("DeleteBusiness");
        h.setDeliveryMode("SYNC");
        h.setDeliveryDate("2016-02-25");
        h.setMessageMode("REQUEST");
        h.setSessionId("kfdksdiewkdkd9el2393w");
        h.setUserId("rterrell_test");

        ContactDetailGroup cdg = f.createContactDetailGroup();
        BusinessType bus = f.createBusinessType();
        bus.setBusinessId(BigInteger.valueOf(businessId));
        cdg.getBusinessContacts().add(bus);

        JAXBElement<ContactDetailGroup> detailGrp = f
                .createAddressBookRequestProfile(cdg);

        r.setProfile(detailGrp);
        r.setHeader(h);

        String xml = this.jaxb.marshalMessage(r);
        return xml;

    }

    private String getBusinessContactMessageWithInvalidProfile() {
        ObjectFactory f = new ObjectFactory();
        AddressBookRequest r = f.createAddressBookRequest();

        HeaderType h = f.createHeaderType();
        h.setRouting("rmt2.queue.contacts");
        h.setApplication("contacts");
        h.setModule("business");
        h.setTransaction("AddBusiness");
        h.setDeliveryMode("SYNC");
        h.setDeliveryDate("2016-02-25");
        h.setMessageMode("REQUEST");
        h.setSessionId("kfdksdiewkdkd9el2393w");
        h.setUserId("rterrell_test");

        r.setHeader(h);

        String xml = this.jaxb.marshalMessage(r);
        return xml;

    }

    private String getBusinessContactMessageWithEmptyContacts() {
        ObjectFactory f = new ObjectFactory();
        AddressBookRequest r = f.createAddressBookRequest();

        HeaderType h = f.createHeaderType();
        h.setRouting("rmt2.queue.contacts");
        h.setApplication("contacts");
        h.setModule("business");
        h.setTransaction("AddBusiness");
        h.setDeliveryMode("SYNC");
        h.setDeliveryDate("2016-02-25");
        h.setMessageMode("REQUEST");
        h.setSessionId("kfdksdiewkdkd9el2393w");
        h.setUserId("rterrell_test");

        ContactDetailGroup cdg = f.createContactDetailGroup();

        JAXBElement<ContactDetailGroup> detailGrp = f
                .createAddressBookRequestProfile(cdg);

        r.setProfile(detailGrp);
        r.setHeader(h);

        String xml = this.jaxb.marshalMessage(r);
        return xml;

    }

    private String getSingleBusinessCriteriaMessage(long businessId) {
        ObjectFactory f = new ObjectFactory();
        AddressBookRequest r = f.createAddressBookRequest();

        HeaderType h = f.createHeaderType();
        h.setRouting("rmt2.queue.contacts");
        h.setApplication("contacts");
        h.setModule("business");
        h.setTransaction("FetchBusiness");
        h.setDeliveryMode("SYNC");
        h.setDeliveryDate("2016-02-25");
        h.setMessageMode("REQUEST");
        h.setSessionId("kfdksdiewkdkd9el2393w");
        h.setUserId("rterrell_test");

        ContactCriteriaGroup ccg = f.createContactCriteriaGroup();
        BusinessContactCriteria bcc = f.createBusinessContactCriteria();
        bcc.setContactId(BigInteger.valueOf(businessId));
        ccg.setBusinessCriteria(bcc);
        JAXBElement<ContactCriteriaGroup> criteriaGrp = f
                .createAddressBookRequestCriteria(ccg);
        r.setCriteria(criteriaGrp);
        r.setHeader(h);

        String xml = this.jaxb.marshalMessage(r);
        return xml;

    }

    private String getMultipleBusinessCriteriaMessage() {
        ObjectFactory f = new ObjectFactory();
        AddressBookRequest r = f.createAddressBookRequest();

        HeaderType h = f.createHeaderType();
        h.setRouting("rmt2.queue.contacts");
        h.setApplication("contacts");
        h.setModule("business");
        h.setTransaction("FetchBusiness");
        h.setDeliveryMode("SYNC");
        h.setDeliveryDate("2016-02-25");
        h.setMessageMode("REQUEST");
        h.setSessionId("kfdksdiewkdkd9el2393w");
        h.setUserId("rterrell_test");

        ContactCriteriaGroup ccg = f.createContactCriteriaGroup();
        BusinessContactCriteria bcc = f.createBusinessContactCriteria();
        bcc.setBusinessName("Medical");
        ccg.setBusinessCriteria(bcc);
        JAXBElement<ContactCriteriaGroup> criteriaGrp = f
                .createAddressBookRequestCriteria(ccg);
        r.setCriteria(criteriaGrp);
        r.setHeader(h);

        String xml = this.jaxb.marshalMessage(r);
        return xml;

    }
}
