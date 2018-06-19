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
        bus.setBusLongname("Business Name 1");
        bus.setBusContactFirstname("john");
        bus.setBusContactLastname("smith");
        bus.setContactEmail("john.smith@gte.net");
        bus.setBusContactPhone("9999999999");
        bus.setAddrId(2258);
        bus.setBusinessId(1351);
        bus.setAddr1("address 1");
        bus.setAddr1("address 2");
        bus.setAddr1("address 3");
        bus.setAddr1("address 4");
        bus.setZipCity("dallas");
        bus.setZipState("Tx");
        bus.setAddrZip(75232);
        bus.setAddrPhoneMain("2143738000");
        BusinessContactDto busDto = Rmt2AddressBookDtoFactory.getBusinessInstance(bus);
        list.add(busDto);
        return list;
    }

    public static final List<ContactDto> createMockContactDtoResponseListData() {
        List<ContactDto> list = new ArrayList<ContactDto>();
        
        VwBusinessAddress bus = new VwBusinessAddress();
        bus.setBusinessId(1351);
        bus.setBusLongname("Business Name 1");
        bus.setBusContactFirstname("john");
        bus.setBusContactLastname("smith");
        bus.setContactEmail("john.smith@gte.net");
        bus.setBusContactPhone("9999999999");
        bus.setAddrId(2258);
        bus.setBusinessId(1351);
        bus.setAddr1("address 1");
        bus.setAddr1("address 2");
        bus.setAddr1("address 3");
        bus.setAddr1("address 4");
        bus.setZipCity("dallas");
        bus.setZipState("Tx");
        bus.setAddrZip(75232);
        bus.setAddrPhoneMain("2143738000");
        BusinessContactDto busDto = Rmt2AddressBookDtoFactory.getBusinessInstance(bus);
        list.add(busDto);
        
        bus = new VwBusinessAddress();
        bus.setBusinessId(1351);
        bus.setBusLongname("Business Name 2");
        bus.setBusContactFirstname("frank");
        bus.setBusContactLastname("good");
        bus.setContactEmail("frank.good@gte.net");
        bus.setBusContactPhone("9999999999");
        bus.setAddrId(2258);
        bus.setBusinessId(1351);
        bus.setAddr1("address 1");
        bus.setAddr1("address 2");
        bus.setAddr1("address 3");
        bus.setAddr1("address 4");
        bus.setZipCity("dallas");
        bus.setZipState("Tx");
        bus.setAddrZip(75232);
        bus.setAddrPhoneMain("2143738000");
         busDto = Rmt2AddressBookDtoFactory.getBusinessInstance(bus);
        list.add(busDto);
        
        bus = new VwBusinessAddress();
        bus.setBusinessId(1351);
        bus.setBusLongname("Business Name 3");
        bus.setBusContactFirstname("steve");
        bus.setBusContactLastname("gadd");
        bus.setContactEmail("steve.gadd@gte.net");
        bus.setBusContactPhone("9999999999");
        bus.setAddrId(2258);
        bus.setBusinessId(1351);
        bus.setAddr1("address 1");
        bus.setAddr1("address 2");
        bus.setAddr1("address 3");
        bus.setAddr1("address 4");
        bus.setZipCity("dallas");
        bus.setZipState("Tx");
        bus.setAddrZip(75232);
        bus.setAddrPhoneMain("2143738000");
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
