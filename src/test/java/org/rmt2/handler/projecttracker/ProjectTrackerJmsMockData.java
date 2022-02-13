package org.rmt2.handler.projecttracker;

import java.util.ArrayList;
import java.util.List;

import org.dao.mapping.orm.rmt2.Customer;
import org.dao.mapping.orm.rmt2.ProjClient;
import org.dao.mapping.orm.rmt2.ProjEmployee;
import org.dao.mapping.orm.rmt2.ProjEmployeeTitle;
import org.dao.mapping.orm.rmt2.ProjEmployeeType;
import org.dao.mapping.orm.rmt2.ProjEvent;
import org.dao.mapping.orm.rmt2.ProjProject;
import org.dao.mapping.orm.rmt2.ProjTask;
import org.dao.mapping.orm.rmt2.ProjTimesheetHist;
import org.dao.mapping.orm.rmt2.VwBusinessAddress;
import org.dao.mapping.orm.rmt2.VwEmployeeExt;
import org.dao.mapping.orm.rmt2.VwEmployeeProjects;
import org.dao.mapping.orm.rmt2.VwProjectClient;
import org.dao.mapping.orm.rmt2.VwTimesheetList;
import org.dao.mapping.orm.rmt2.VwTimesheetProjectTask;
import org.dao.mapping.orm.rmt2.VwTimesheetSummary;
import org.dao.timesheet.TimesheetConst;
import org.dto.BusinessContactDto;
import org.dto.ClientDto;
import org.dto.ContactDto;
import org.dto.CustomerDto;
import org.dto.EmployeeDto;
import org.dto.EmployeeTitleDto;
import org.dto.EmployeeTypeDto;
import org.dto.EventDto;
import org.dto.ProjectClientDto;
import org.dto.ProjectEmployeeDto;
import org.dto.TaskDto;
import org.dto.TimesheetDto;
import org.dto.adapter.orm.EmployeeObjectFactory;
import org.dto.adapter.orm.ProjectObjectFactory;
import org.dto.adapter.orm.Rmt2AddressBookDtoFactory;
import org.dto.adapter.orm.TimesheetObjectFactory;
import org.dto.adapter.orm.account.subsidiary.Rmt2SubsidiaryDtoFactory;

/**
 * Project Tracker Administration testing facility that is mainly responsible for
 * setting up mock data.
 * <p>
 * All derived project tracker related Api unit tests should inherit this class
 * to prevent duplicating common functionality.
 * 
 * @author rterrell
 * 
 */
public class ProjectTrackerJmsMockData {

    public static final List<ProjEvent> createMockMultiple_Day_Task_Events(int projectTaskId) {
        List<ProjEvent> list = new ArrayList<ProjEvent>();
        int eventId = ProjectTrackerJmsOrmDataFactory.TEST_EVENT_ID;
        // Day 1
        ProjEvent o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEvent(eventId, projectTaskId, "2018-01-01", 0);
        list.add(o);
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEvent(++eventId, projectTaskId, "2018-01-02", 2);
        list.add(o);
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEvent(++eventId, projectTaskId, "2018-01-03", 2);
        list.add(o);
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEvent(++eventId, projectTaskId, "2018-01-04", 2);
        list.add(o);
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEvent(++eventId, projectTaskId, "2018-01-05", 1);
        list.add(o);
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEvent(++eventId, projectTaskId, "2018-01-06", 1);
        list.add(o);
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEvent(++eventId, projectTaskId, "2018-01-07", 0);
        list.add(o);
        return list;
    }

    public static final List<CustomerDto> createMockCustomer() {
        List<CustomerDto> list = new ArrayList<>();
        Customer o = ProjectTrackerJmsOrmDataFactory.createMockOrmCustomer(100, 1456, 0, 333, "C1234580", "Customer 1");
        CustomerDto d = Rmt2SubsidiaryDtoFactory.createCustomerInstance(o, null);
        list.add(d);

        return list;
    }
    
    public static final List<VwTimesheetProjectTask> createMockMultipleVwTimesheetProjectTask() {
        List<VwTimesheetProjectTask> list = new ArrayList<VwTimesheetProjectTask>();
        VwTimesheetProjectTask o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwTimesheetProjectTask(444441, 111, 4440,
                1112220, 1110, "Project 2220", "2018-01-01",
                "2018-01-07", "Design and Analysis", true);
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwTimesheetProjectTask(444442, 111, 4440,
                1112221, 1110, "Project 2220", "2018-01-01",
                "2018-01-07", "Development", true);
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwTimesheetProjectTask(444443, 111, 4440,
                1112222, 1110, "Project 2220", "2018-01-01",
                "2018-01-07", "Meetings", true);
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwTimesheetProjectTask(444444, 111, 4440,
                1112223, 1110, "Project 2220", "2018-01-01",
                "2018-01-07", "Testing", true);
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwTimesheetProjectTask(444445, 111, 4440,
                1112224, 1110, "Project 2220", "2018-01-01",
                "2018-01-07", "Holiday", false);
        list.add(o);

        return list;
    }

    public static final List<ProjTimesheetHist> createMockTimesheetStatusHistory() {
        List<ProjTimesheetHist> list = new ArrayList<ProjTimesheetHist>();
        ProjTimesheetHist o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjTimesheetHist(
                ProjectTrackerJmsOrmDataFactory.TEST_TIMESHEET_HIST_ID,
                ProjectTrackerJmsOrmDataFactory.TEST_TIMESHEET_ID,
                TimesheetConst.STATUS_NEW, "2018-01-01", "2018-01-02");
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjTimesheetHist(
                ProjectTrackerJmsOrmDataFactory.TEST_TIMESHEET_HIST_ID,
                ProjectTrackerJmsOrmDataFactory.TEST_TIMESHEET_ID,
                TimesheetConst.STATUS_DRAFT, "2018-01-03", "2018-01-04");
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjTimesheetHist(
                ProjectTrackerJmsOrmDataFactory.TEST_TIMESHEET_HIST_ID,
                ProjectTrackerJmsOrmDataFactory.TEST_TIMESHEET_ID,
                TimesheetConst.STATUS_SUBMITTED, "2018-01-05", "2018-01-06");
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjTimesheetHist(
                ProjectTrackerJmsOrmDataFactory.TEST_TIMESHEET_HIST_ID,
                ProjectTrackerJmsOrmDataFactory.TEST_TIMESHEET_ID,
                TimesheetConst.STATUS_RECVD, "2018-01-07", "2018-01-08");
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjTimesheetHist(
                ProjectTrackerJmsOrmDataFactory.TEST_TIMESHEET_HIST_ID,
                ProjectTrackerJmsOrmDataFactory.TEST_TIMESHEET_ID,
                TimesheetConst.STATUS_APPROVED, "2018-01-09", null);
        list.add(o);

        return list;
    }

    public static final List<ClientDto> createMockSingleClient() {
        List<ClientDto> list = new ArrayList<>();
        ProjClient o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjClient(
                1110, 1350, "1110 Company", 70.00, 80.00, "000-111", "steve",
                "gadd", "0000000000", "stevegadd@gte.net");
        ClientDto dto = ProjectObjectFactory.createClientDtoInstance(o);
        list.add(dto);
        return list;
    }

    public static final List<ClientDto> createMockMultipleClient() {
        List<ClientDto> list = new ArrayList<>();
        ProjClient o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjClient(
                1110, 1350, "1110 Company", 70.00, 80.00, "000-111", "firstname0",
                "lastname0", "0000000000", "firstname0lastname0@gte.net");
        ClientDto dto = ProjectObjectFactory.createClientDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjClient(1111, 1351,
                "1111 Company", 80.00, 90.00, "111-111", "firstname1", "lastname1",
                "1111111111", "firstname1lastname1@gte.net");
        dto = ProjectObjectFactory.createClientDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjClient(1112, 1352,
                "1112 Company", 90.00, 100.00, "222-111", "firstname2", "lastname2",
                "2222222222", "firstname2lastname2@gte.net");
        dto = ProjectObjectFactory.createClientDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjClient(1113, 1353,
                "1113 Company", 100.00, 110.00, "333-111", "firstname3", "lastname3",
                "3333333333", "firstname3lastname3@gte.net");
        dto = ProjectObjectFactory.createClientDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjClient(1114, 1354,
                "1114 Company", 110.00, 120.00, "444-111", "firstname4", "lastname4",
                "4444444444", "firstname4lastname4@gte.net");
        dto = ProjectObjectFactory.createClientDtoInstance(o);
        list.add(dto);
        return list;
    }

    public static final List<ProjProject> createMockSingleProject() {
        List<ProjProject> list = new ArrayList<ProjProject>();
        ProjProject o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjProject(
                2220, 1110, "Project 2220", "2018-01-01", "2018-02-01");
        list.add(o);
        return list;
    }

    public static final List<ProjProject> createMockMultipleProject() {
        List<ProjProject> list = new ArrayList<ProjProject>();
        ProjProject o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjProject(
                2220, 1110, "Project 2220", "2018-01-01", "2018-02-01");
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjProject(2221, 1110,
                "Project 2221", "2018-02-01", "2018-03-01");
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjProject(2222, 1110,
                "Project 2222", "2018-03-01", "2018-04-01");
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjProject(2223, 1110,
                "Project 2223", "2018-04-01", "2018-05-01");
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjProject(2224, 1110,
                "Project 2224", "2018-05-01", "2018-06-01");
        list.add(o);
        return list;
    }
    
    /**
     * 
     * @return
     */
    public static final List<ProjectClientDto> createMockProjectClientDto() {
        List<ProjectClientDto> list = new ArrayList<>();
        VwProjectClient o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwProjectClient(
                2220, 1110, "Project 2220", "2018-01-01", "2018-02-01", "Client 1110", 1440);
        ProjectClientDto dto = ProjectObjectFactory.createProjectClientDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwProjectClient(2221, 1110,
                "Project 2221", "2018-02-01", "2018-03-01", "Client 1110", 1440);
        dto = ProjectObjectFactory.createProjectClientDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwProjectClient(2222, 1110,
                "Project 2222", "2018-03-01", "2018-04-01", "Client 1110", 1440);
        dto = ProjectObjectFactory.createProjectClientDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwProjectClient(2223, 1110,
                "Project 2223", "2018-04-01", "2018-05-01", "Client 1110", 1440);
        dto = ProjectObjectFactory.createProjectClientDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwProjectClient(2224, 1110,
                "Project 2224", "2018-05-01", "2018-06-01", "Client 1110", 1440);
        dto = ProjectObjectFactory.createProjectClientDtoInstance(o);
        list.add(dto);
        return list;
    }

    public static final List<ProjEmployeeTitle> createMockSingleEmployeeTitle() {
        List<ProjEmployeeTitle> list = new ArrayList<ProjEmployeeTitle>();
        ProjEmployeeTitle o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployeeTitle(101, "Employee Title 1");
        list.add(o);
        return list;
    }
    
    public static final List<EmployeeTitleDto> createMockEmployeeTitle() {
        List<EmployeeTitleDto> list = new ArrayList<>();
        ProjEmployeeTitle o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployeeTitle(101, "Employee Title 1");
        EmployeeTitleDto dto = EmployeeObjectFactory.createEmployeeTitleDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployeeTitle(102, "Employee Title 2");
        dto = EmployeeObjectFactory.createEmployeeTitleDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployeeTitle(103, "Employee Title 3");
        dto = EmployeeObjectFactory.createEmployeeTitleDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployeeTitle(104, "Employee Title 4");
        dto = EmployeeObjectFactory.createEmployeeTitleDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployeeTitle(105, "Employee Title 5");
        dto = EmployeeObjectFactory.createEmployeeTitleDtoInstance(o);
        list.add(dto);
        return list;
    }
    
    public static final List<EmployeeTypeDto> createMockSingleEmployeeType() {
        List<EmployeeTypeDto> list = new ArrayList<>();
        ProjEmployeeType o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployeeType(201, "Employee Type 1");
        EmployeeTypeDto dto = EmployeeObjectFactory.createEmployeeTypeDtoInstance(o);
        list.add(dto);
        return list;
    }
    
    public static final List<EmployeeTypeDto> createMockEmployeeType() {
        List<EmployeeTypeDto> list = new ArrayList<>();
        ProjEmployeeType o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployeeType(201, "Employee Type 1");
        EmployeeTypeDto dto = EmployeeObjectFactory.createEmployeeTypeDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployeeType(202, "Employee Type 2");
        dto = EmployeeObjectFactory.createEmployeeTypeDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployeeType(203, "Employee Type 3");
        dto = EmployeeObjectFactory.createEmployeeTypeDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployeeType(204, "Employee Type 4");
        dto = EmployeeObjectFactory.createEmployeeTypeDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployeeType(205, "Employee Type 5");
        dto = EmployeeObjectFactory.createEmployeeTypeDtoInstance(o);
        list.add(dto);
        return list;
    }
    
    public static final List<ProjEmployee> createMockSingleManager() {
        List<ProjEmployee> list = new ArrayList<ProjEmployee>();
        ProjEmployee o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployee(5550, 201, 1, 9999, 202, 222221,
                "2010-01-01", "2018-01-01", "login_name_1", "mgr_first_name_1", "mgr_last_name_1",
                "111-11-5000", "ABC Company");
        list.add(o);
        return list;
    }
    
    public static final List<ProjEmployee> createMockSingleEmployee() {
        List<ProjEmployee> list = new ArrayList<ProjEmployee>();
        ProjEmployee o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployee(2220, 201, 0, 3330, 101, 999991,
                "2010-01-01", "2018-01-01", "login_name_1", "first_name_1", "last_name_1",
                "111-11-5000", "ABC Company");
        list.add(o);
        return list;
    }
    
    public static final List<ProjEmployee> createMockMultipleEmployee() {
        List<ProjEmployee> list = new ArrayList<ProjEmployee>();
        ProjEmployee o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployee(2220, 201, 0, 3330, 101, 999991,
                "2010-01-01", null, "login_name_1", "first_name_1", "last_name_1",
                "111-11-5000", "ABC Company");
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployee(2221, 201, 0, 3330, 102, 999992,
                "2011-01-01", null, "login_name_2", "first_name_2", "last_name_2",
                "111-11-5001", "ABC Company");
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployee(2222, 201, 0, 3330, 103, 999993,
                "2012-01-01", null, "login_name_3", "first_name_3", "last_name_3",
                "111-11-5002", "ABC Company");
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployee(2223, 202, 1, 3330, 104, 999994,
                "2013-01-01", "2018-01-01", "login_name_4", "first_name_4", "last_name_4",
                "111-11-5003", "ABC Company");
        list.add(o);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEmployee(2224, 202, 1, 3330, 105, 999995,
                "2014-01-01", "2018-01-01", "login_name_5", "first_name_5", "last_name_5",
                "111-11-5004", "ABC Company");
        list.add(o);
        return list;
    }
    
    public static final List<EmployeeDto> createMockSingleExtEmployee() {
        List<EmployeeDto> list = new ArrayList<>();
        VwEmployeeExt o = ProjectTrackerJmsOrmDataFactory.createMockOrmExtEmployee(2220, "EmployeeType", 0, 3330, "EmployeeTitle", 999991,
                "2010-01-01", "2018-01-01", "login_name_1", "first_name_1", "last_name_1",
                "111-11-5000", "ABC Company");
        EmployeeDto dto = EmployeeObjectFactory.createEmployeeExtendedDtoInstance(o);
        list.add(dto);
        return list;
    }
    
    public static final List<EmployeeDto> createMockMultipleExtEmployee() {
        List<EmployeeDto> list = new ArrayList<>();
        VwEmployeeExt o = ProjectTrackerJmsOrmDataFactory.createMockOrmExtEmployee(2220, "EmployeeType", 0, 3330,
                "EmployeeTitle_1", 999991,
                "2010-01-01", "2018-01-01", "login_name_1", "first_name_1", "last_name_1",
                "111-11-5001", "ABC Company");
        EmployeeDto dto = EmployeeObjectFactory.createEmployeeExtendedDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmExtEmployee(2221, "EmployeeType", 0, 3330, "EmployeeTitle_2", 999992,
                "2010-01-01", "2018-01-01", "login_name_2", "first_name_2", "last_name_2",
                "111-11-5002", "ABC Company");
        dto = EmployeeObjectFactory.createEmployeeExtendedDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmExtEmployee(2222, "EmployeeType", 0, 3330, "EmployeeTitle_3", 999993,
                "2010-01-01", "2018-01-01", "login_name_3", "first_name_3", "last_name_3",
                "111-11-5003", "ABC Company");
        dto = EmployeeObjectFactory.createEmployeeExtendedDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmExtEmployee(2223, "EmployeeType", 0, 3330, "EmployeeTitle_4", 999994,
                "2010-01-01", "2018-01-01", "login_name_4", "first_name_4", "last_name_4",
                "111-11-5004", "ABC Company");
        dto = EmployeeObjectFactory.createEmployeeExtendedDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmExtEmployee(2224, "EmployeeType", 0, 3330, "EmployeeTitle_5", 999995,
                "2010-01-01", "2018-01-01", "login_name_5", "first_name_5", "last_name_5",
                "111-11-5005", "ABC Company");
        dto = EmployeeObjectFactory.createEmployeeExtendedDtoInstance(o);
        list.add(dto);

        return list;
    }

    public static final List<VwEmployeeProjects> createMockSingleVwEmployeeProjects() {
        List<VwEmployeeProjects> list = new ArrayList<VwEmployeeProjects>();
        VwEmployeeProjects o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwEmployeeProjects(55551, 4440, "Project 2220",
                        1110, "1110 Company", 1350, "000-111", 2220,
                        "2018-01-01", "2018-02-01", "2018-01-01", "2018-02-01",
                        50.00, 55.00, 0.00, 70.00, 80.00);
        list.add(o);
        return list;
    }
    
    public static final List<ProjectEmployeeDto> createMockMultipleVwEmployeeProjects() {
        List<ProjectEmployeeDto> list = new ArrayList<>();
        VwEmployeeProjects o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwEmployeeProjects(55551, 4440, "Project 2220",
                        1110, "1110 Company", 1350, "000-111", 2220,
                        "2018-01-01", "2018-02-01", "2018-01-01", "2018-02-01",
                        50.00, 55.00, 0.00, 70.00, 80.00);
        ProjectEmployeeDto dto = ProjectObjectFactory.createEmployeeProjectDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwEmployeeProjects(55552,
                4441, "Project 2221", 1111, "1111 Company", 1350, "000-111",
                2220, "2018-01-01", "2018-02-01", "2018-01-01", "2018-02-01",
                50.00, 55.00, 0.00, 70.00, 80.00);
        dto = ProjectObjectFactory.createEmployeeProjectDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwEmployeeProjects(55553,
                4442, "Project 2222", 1112, "1112 Company", 1350, "000-111",
                2220, "2018-01-01", "2018-02-01", "2018-01-01", "2018-02-01",
                50.00, 55.00, 0.00, 70.00, 80.00);
        dto = ProjectObjectFactory.createEmployeeProjectDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwEmployeeProjects(55554,
                4443, "Project 2223", 1113, "1113 Company", 1350, "000-111",
                2220, "2018-01-01", "2018-02-01", "2018-01-01", "2018-02-01",
                50.00, 55.00, 0.00, 70.00, 80.00);
        dto = ProjectObjectFactory.createEmployeeProjectDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwEmployeeProjects(55555,
                4444, "Project 2224", 1114, "1114 Company", 1350, "000-111",
                2220, "2018-01-01", "2018-02-01", "2018-01-01", "2018-02-01",
                50.00, 55.00, 0.00, 70.00, 80.00);
        dto = ProjectObjectFactory.createEmployeeProjectDtoInstance(o);
        list.add(dto);
        
        return list;
    }
    
    public static final List<ProjEvent> createMockSingleEvent() {
        List<ProjEvent> list = new ArrayList<ProjEvent>();
        ProjEvent o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEvent(123401, 444441, "2018-01-01", 8);
        list.add(o);
        return list;
    }
    
    public static final List<EventDto> createMockEvent() {
        List<EventDto> list = new ArrayList<>();
        ProjEvent o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEvent(123401, 444441, "2018-01-01", 8);
        EventDto dto = ProjectObjectFactory.createEventDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEvent(123402, 444442, "2018-01-02", 8);
        dto = ProjectObjectFactory.createEventDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEvent(123403, 444443, "2018-01-03", 8);
        dto = ProjectObjectFactory.createEventDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEvent(123404, 444444, "2018-01-04", 8);
        dto = ProjectObjectFactory.createEventDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjEvent(123405, 444445, "2018-01-05", 8);
        dto = ProjectObjectFactory.createEventDtoInstance(o);
        list.add(dto);
        return list;
    }
    
    public static final List<ProjTask> createMockSingleTask() {
        List<ProjTask> list = new ArrayList<ProjTask>();
        ProjTask o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjTask(1112220, "Design and Analysis", true);
        list.add(o);
        return list;
    }
    
    public static final List<TaskDto> createMockMultipleTask() {
        List<TaskDto> list = new ArrayList<>();
        ProjTask o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjTask(1112220, "Task Description 1", true);
        TaskDto dto = ProjectObjectFactory.createTaskDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjTask(1112221, "Task Description 2", true);
        dto = ProjectObjectFactory.createTaskDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjTask(1112222, "Task Description 3", true);
        dto = ProjectObjectFactory.createTaskDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjTask(1112223, "Task Description 4", true);
        dto = ProjectObjectFactory.createTaskDtoInstance(o);
        list.add(dto);
        
        o = ProjectTrackerJmsOrmDataFactory.createMockOrmProjTask(1112224, "Task Description 5", true);
        dto = ProjectObjectFactory.createTaskDtoInstance(o);
        list.add(dto);
        return list;
    }

    public static final List<TimesheetDto> createMockExtTimesheetList() {
        List<TimesheetDto> list = new ArrayList<>();
        VwTimesheetList o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwTimesheetList(111, 1110, 1234, 2220,
                "INVREF1230", "2018-01-01", "2018-01-07", "ExtReNo1000",
                3330, "DRAFT", "ACCT-111", 40, 0, 70.00, 80.00);
        TimesheetDto dto = TimesheetObjectFactory.createTimesheetExtendedDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwTimesheetList(112, 1110, 1234, 2220,
                "INVREF1231", "2018-01-08", "2018-01-14", "ExtReNo1001",
                3330, "DRAFT", "ACCT-111", 40, 0, 70.00, 80.00);
        dto = TimesheetObjectFactory.createTimesheetExtendedDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwTimesheetList(113, 1110, 1234, 2220,
                "INVREF1232", "2018-01-15", "2018-01-21", "ExtReNo1002",
                3330, "DRAFT", "ACCT-111", 40, 0, 70.00, 80.00);
        dto = TimesheetObjectFactory.createTimesheetExtendedDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwTimesheetList(114, 1110, 1234, 2220,
                "INVREF1233", "2018-01-22", "2018-01-28", "ExtReNo1003",
                3330, "DRAFT", "ACCT-111", 40, 0, 70.00, 80.00);
        dto = TimesheetObjectFactory.createTimesheetExtendedDtoInstance(o);
        list.add(dto);

        o = ProjectTrackerJmsOrmDataFactory.createMockOrmVwTimesheetList(115, 1110, 1234, 2220,
                "INVREF1234", "2018-01-29", "2018-02-04", "ExtReNo1004",
                3330, "DRAFT", "ACCT-111", 40, 0, 70.00, 80.00);
        dto = TimesheetObjectFactory.createTimesheetExtendedDtoInstance(o);
        list.add(dto);
        return list;
    }

    /**
     * 
     * @return
     */
    public static final List<VwTimesheetSummary> createMockTimesheetSummaryList() {
        List<VwTimesheetSummary> list = new ArrayList<>();
        VwTimesheetSummary o = ProjectTrackerJmsOrmDataFactory
                .createMockOrmVwTimesheetSummary(111, "john", "doe", "2020-5-15", 8);
        list.add(o);
        return list;
    }

    /**
     * 
     * @return
     */
    public static final List<ContactDto> createMockSingleBusinessContactDto() {
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
}