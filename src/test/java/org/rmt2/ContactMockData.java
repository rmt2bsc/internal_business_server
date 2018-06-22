package org.rmt2;

import java.util.ArrayList;
import java.util.List;

import org.dao.mapping.orm.rmt2.VwBusinessAddress;
import org.dao.mapping.orm.rmt2.Zipcode;
import org.dto.BusinessContactDto;
import org.dto.ContactDto;
import org.dto.ZipcodeDto;
import org.dto.adapter.orm.Rmt2AddressBookDtoFactory;

public class ContactMockData {

    public ContactMockData() {
    }
    
    /**
     * 
     * @return
     */
    public static final List<ContactDto> createMockSingleContactDtoResponseData() {
        List<ContactDto> list = new ArrayList<ContactDto>();
        VwBusinessAddress bus = new VwBusinessAddress();
        bus.setBusinessId(1351);
        bus.setBusLongname("BusinessName_1");
        bus.setBusContactFirstname("firstname_1");
        bus.setBusContactLastname("lastname_1");
        bus.setContactEmail(bus.getBusContactFirstname() + "." + bus.getBusContactLastname() + "@gte.net");
        bus.setBusContactPhone("9999999991");
        bus.setAddrId(2001);
        bus.setBusinessId(1351);
        bus.setAddr1("address_line1_1");
        bus.setAddr2("address_line2_1");
        bus.setAddr3("address_line3_1");
        bus.setAddr4("address_line4_1");
        bus.setZipCity("Dallas");
        bus.setZipState("Tx");
        bus.setAddrZip(75232);
        bus.setAddrPhoneMain("2143738001");
        bus.setBusTaxId("750000001");
        bus.setBusWebsite("www.BusinessName_1.com");
        bus.setBusShortname("shortname");
        BusinessContactDto busDto = Rmt2AddressBookDtoFactory.getBusinessInstance(bus);
        list.add(busDto);
        return list;
    }

    public static final List<ContactDto> createMockContactDtoResponseListData() {
        List<ContactDto> list = new ArrayList<ContactDto>();
        
        VwBusinessAddress bus = new VwBusinessAddress();
        bus.setBusinessId(1351);
        bus.setBusLongname("BusinessName_1");
        bus.setBusContactFirstname("firstname_1");
        bus.setBusContactLastname("lastname_1");
        bus.setContactEmail(bus.getBusContactFirstname() + "." + bus.getBusContactLastname() + "@gte.net");
        bus.setBusContactPhone("9999999991");
        bus.setAddrId(2001);
        bus.setAddr1("address_line1_1");
        bus.setAddr2("address_line2_1");
        bus.setAddr3("address_line3_1");
        bus.setAddr4("address_line4_1");
        bus.setZipCity("Dallas");
        bus.setZipState("Tx");
        bus.setAddrZip(75232);
        bus.setAddrPhoneMain("2143738001");
        bus.setBusTaxId("750000001");
        bus.setBusWebsite("www.BusinessName_1.com");
        bus.setBusShortname("shortname");
        BusinessContactDto busDto = Rmt2AddressBookDtoFactory.getBusinessInstance(bus);
        list.add(busDto);
        
        bus = new VwBusinessAddress();
        bus.setBusinessId(1352);
        bus.setBusLongname("BusinessName_2");
        bus.setBusContactFirstname("firstname_2");
        bus.setBusContactLastname("lastname_2");
        bus.setContactEmail(bus.getBusContactFirstname() + "." + bus.getBusContactLastname() + "@gte.net");
        bus.setBusContactPhone("9999999992");
        bus.setAddrId(2002);
        bus.setBusinessId(1352);
        bus.setAddr1("address_line1_2");
        bus.setAddr2("address_line2_2");
        bus.setAddr3("address_line3_2");
        bus.setAddr4("address_line4_2");
        bus.setZipCity("Dallas");
        bus.setZipState("Tx");
        bus.setAddrZip(75232);
        bus.setAddrPhoneMain("2143738002");
        bus.setBusTaxId("750000002");
        bus.setBusWebsite("www.BusinessName_2.com");
        bus.setBusShortname("shortname");
         busDto = Rmt2AddressBookDtoFactory.getBusinessInstance(bus);
        list.add(busDto);
        
        bus = new VwBusinessAddress();
        bus.setBusinessId(1353);
        bus.setBusLongname("BusinessName_3");
        bus.setBusContactFirstname("firstname_3");
        bus.setBusContactLastname("lastname_3");
        bus.setContactEmail(bus.getBusContactFirstname() + "." + bus.getBusContactLastname() + "@gte.net");
        bus.setBusContactPhone("9999999993");
        bus.setAddrId(2003);
        bus.setBusinessId(1353);
        bus.setAddr1("address_line1_3");
        bus.setAddr2("address_line2_3");
        bus.setAddr3("address_line3_3");
        bus.setAddr4("address_line4_3");
        bus.setZipCity("Dallas");
        bus.setZipState("Tx");
        bus.setAddrZip(75232);
        bus.setAddrPhoneMain("2143738003");
        bus.setBusTaxId("750000003");
        bus.setBusWebsite("www.BusinessName_3.com");
        bus.setBusShortname("shortname");
         busDto = Rmt2AddressBookDtoFactory.getBusinessInstance(bus);
        list.add(busDto);
        
        return list;
    }
    
    /**
     * 
     * @param zipId
     * @param zipCode
     * @param state
     * @param city
     * @param areaCode
     * @param countyName
     * @param timeZoneId
     * @return
     */
    public static final ZipcodeDto createZipcodeOrm(int zipId, int zipCode,
            String state, String city, String areaCode, String countyName,
            int timeZoneId) {
        Zipcode obj = new Zipcode();
        obj.setZipId(zipId);
        obj.setZip(zipCode);
        obj.setState(state);
        obj.setCity(city);
        obj.setAreaCode(areaCode);
        obj.setCountyName(countyName);
        obj.setTimeZoneId(timeZoneId);
        obj.setLatitude(382372382323.3883828);
        obj.setLongitude(48484848.4843949);
        obj.setBlackPopulation(239000);
        obj.setWhitePopulation(10000000);
        obj.setHispanicPopulation(30000);
        obj.setCityAliasName(city + "_Alias");
        obj.setAverageHouseValue(87674.84);
        obj.setCbsa(123.88);
        obj.setCbsaDiv(23.88);
        obj.setCityAliasAbbr("AAB");
        obj.setCityTypeId("City_type");
        obj.setCountiesArea(333);
        obj.setCountyFips("Counties FIPS");
        obj.setDayLightSaving("TRUE");
        obj.setElevation(2345.89);
        obj.setHouseholdsPerZipcode(600);
        obj.setIncomePerHousehold(569.76);
        obj.setMsa(757575);
        obj.setPersonsPerHousehold(4);
        obj.setPmsa(123);
        obj.setZipcodePopulation(25000);
        
        ZipcodeDto dto = Rmt2AddressBookDtoFactory.getZipCodeInstance(obj);
        return dto;
    }
    
}
