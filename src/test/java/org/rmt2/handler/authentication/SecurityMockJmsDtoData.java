package org.rmt2.handler.authentication;

import java.util.ArrayList;
import java.util.List;

import org.dao.mapping.orm.rmt2.AppRole;
import org.dao.mapping.orm.rmt2.Application;
import org.dao.mapping.orm.rmt2.ApplicationAccess;
import org.dao.mapping.orm.rmt2.GroupRoles;
import org.dao.mapping.orm.rmt2.Roles;
import org.dao.mapping.orm.rmt2.UserAppRole;
import org.dao.mapping.orm.rmt2.UserGroup;
import org.dao.mapping.orm.rmt2.UserLogin;
import org.dao.mapping.orm.rmt2.UserResource;
import org.dao.mapping.orm.rmt2.UserResourceAccess;
import org.dao.mapping.orm.rmt2.UserResourceSubtype;
import org.dao.mapping.orm.rmt2.UserResourceType;
import org.dao.mapping.orm.rmt2.VwAppRoles;
import org.dao.mapping.orm.rmt2.VwResource;
import org.dao.mapping.orm.rmt2.VwResourceType;
import org.dao.mapping.orm.rmt2.VwUser;
import org.dao.mapping.orm.rmt2.VwUserAppRoles;
import org.dao.mapping.orm.rmt2.VwUserGroup;
import org.dao.mapping.orm.rmt2.VwUserResourceAccess;
import org.dto.ApplicationDto;
import org.dto.adapter.orm.Rmt2OrmDtoFactory;

/**
 * Security testing facility that is mainly responsible for setting up mock data.
 * <p>
 * All derived media related Api unit tests should inherit this class
 * to prevent duplicating common functionality.
 * 
 * @author rterrell
 * 
 */
public class SecurityMockJmsDtoData {
    // protected List<AppRole> mockAppRoleData;
    // protected List<Application> mockApplicationData;
    // protected List<ApplicationAccess> mockApplicationAccessData;
    // protected List<GroupRoles> mockGroupRolesData;
    // protected List<Roles> mockRolesData;
    // protected List<UserAppRole> mockUserAppRoleData;
    // protected List<UserGroup> mockUserGroupData;
    // protected List<UserLogin> mockUserLoginData;
    // protected List<UserResource> mockUserResourceData;
    // protected List<UserResourceAccess> mockUserResourceAccessData;
    // protected List<UserResourceSubtype> mockUserResourceSubtypeData;
    // protected List<UserResourceType> mockUserResourceTypeData;
    // protected List<VwAppRoles> mockVwAppRolesData;
    // protected List<VwResource> mockVwResourceData;
    // protected List<VwResourceType> mockVwResourceTypeData;
    // protected List<VwUser> mockVwUserData;
    // protected List<VwUser> mockVwUserSingleData;
    // protected List<VwUserAppRoles> mockVwUserAppRolesData;
    // protected List<VwUserGroup> mockVwUserGroupData;
    // protected List<VwUserResourceAccess> mockVwUserResourceAccessData;

    // /**
    // * @throws java.lang.Exception
    // */
    // @Before
    // public void setUp() throws Exception {
    //
    // this.mockAppRoleData = this.createAppRoleMockData();
    // this.mockApplicationData = this.createApplicationMockData();
    // this.mockApplicationAccessData = this.createApplicationAccessMockData();
    // this.mockGroupRolesData = this.createGroupRolesMockData();
    // this.mockRolesData = this.createRolesMockData();
    // this.mockUserAppRoleData = this.createUserAppRoleMockData();
    // this.mockUserGroupData = this.createUserGroupMockData();
    // this.mockUserLoginData = this.createUserLoginMockData();
    // this.mockUserResourceData = this.createUserResourceMockData();
    // this.mockUserResourceAccessData =
    // this.createUserResourceAccessMockData();
    // this.mockUserResourceSubtypeData =
    // this.createUserResourceSubtypeMockData();
    // this.mockUserResourceTypeData = this.createUserResourceTypeMockData();
    // this.mockVwAppRolesData = this.createVwAppRolesMockData();
    // this.mockVwResourceData = this.createVwResourceMockData();
    // this.mockVwResourceTypeData = this.createVwResourceTypeMockData();
    // this.mockVwUserData = this.createVwUserMockData();
    // this.mockVwUserSingleData = this.createVwUserSingleMockData();
    // this.mockVwUserAppRolesData = this.createVwUserAppRolesMockData();
    // this.mockVwUserGroupData = this.createVwUserGroupMockData();
    // this.mockVwUserResourceAccessData =
    // this.createVwUserResourceAccessMockData();
    // return;
    // }

    public static final List<AppRole> createAppRoleMockData() {
        List<AppRole> list = new ArrayList<>();
        int appRoleId = SecurityMockJmsOrmDataFactory.TEST_APP_ROLE_ID;
        int roleId = SecurityMockJmsOrmDataFactory.TEST_ROLE_ID;
        AppRole o = SecurityMockJmsOrmDataFactory.createOrmAppRole(appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID, roleId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmAppRole(++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID, ++roleId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmAppRole(++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID, ++roleId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmAppRole(++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID, ++roleId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmAppRole(++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID, ++roleId);
        list.add(o);
        
        return list;
    }

    
    public static final List<ApplicationDto> createApplicationMockData() {
        List<ApplicationDto> list = new ArrayList<>();
        int appId = SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID;
        Application o = SecurityMockJmsOrmDataFactory.createOrmApplication(appId);
        ApplicationDto d = Rmt2OrmDtoFactory.getAppDtoInstance(o);
        list.add(d);
        o = SecurityMockJmsOrmDataFactory.createOrmApplication(++appId);
        d = Rmt2OrmDtoFactory.getAppDtoInstance(o);
        list.add(d);
        o = SecurityMockJmsOrmDataFactory.createOrmApplication(++appId);
        d = Rmt2OrmDtoFactory.getAppDtoInstance(o);
        list.add(d);
        o = SecurityMockJmsOrmDataFactory.createOrmApplication(++appId);
        d = Rmt2OrmDtoFactory.getAppDtoInstance(o);
        list.add(d);
        o = SecurityMockJmsOrmDataFactory.createOrmApplication(++appId);
        d = Rmt2OrmDtoFactory.getAppDtoInstance(o);
        list.add(d);
        
        return list;
    }
    
    public static final List<ApplicationAccess> createApplicationAccessMockData() {
        List<ApplicationAccess> list = new ArrayList<>();
        int appAccessId = SecurityMockJmsOrmDataFactory.TEST_APP_ACCESS_ID;
        int appId = SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID;
        ApplicationAccess o = SecurityMockJmsOrmDataFactory
                .createOrmApplicationAccess(appAccessId, appId,
                        SecurityMockJmsOrmDataFactory.TEST_USER_ID, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmApplicationAccess(++appAccessId,
                ++appId, SecurityMockJmsOrmDataFactory.TEST_USER_ID, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmApplicationAccess(++appAccessId,
                ++appId, SecurityMockJmsOrmDataFactory.TEST_USER_ID, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmApplicationAccess(++appAccessId,
                ++appId, SecurityMockJmsOrmDataFactory.TEST_USER_ID, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmApplicationAccess(++appAccessId,
                ++appId, SecurityMockJmsOrmDataFactory.TEST_USER_ID, true);
        list.add(o);
        
        return list;
    }
    
    
    public static final List<GroupRoles> createGroupRolesMockData() {
        List<GroupRoles> list = new ArrayList<>();
        int grpRoleId = SecurityMockJmsOrmDataFactory.TEST_GROUP_ROLD_ID;
        GroupRoles o = SecurityMockJmsOrmDataFactory.createOrmGroupRoles(grpRoleId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                SecurityMockJmsOrmDataFactory.TEST_ROLE_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmGroupRoles(++grpRoleId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                SecurityMockJmsOrmDataFactory.TEST_ROLE_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmGroupRoles(++grpRoleId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                SecurityMockJmsOrmDataFactory.TEST_ROLE_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmGroupRoles(++grpRoleId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                SecurityMockJmsOrmDataFactory.TEST_ROLE_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmGroupRoles(++grpRoleId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                SecurityMockJmsOrmDataFactory.TEST_ROLE_ID);
        list.add(o);
        
        return list;
    }
    
    public static final List<Roles> createRolesMockData() {
        List<Roles> list = new ArrayList<>();
        int roleId = SecurityMockJmsOrmDataFactory.TEST_ROLE_ID;
        Roles o = SecurityMockJmsOrmDataFactory.createOrmRoles(roleId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmRoles(++roleId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmRoles(++roleId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmRoles(++roleId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmRoles(++roleId);
        list.add(o);
        
        return list;
    }
    
    public static final List<UserAppRole> createUserAppRoleMockData() {
        List<UserAppRole> list = new ArrayList<>();
        int userAppRoleId = SecurityMockJmsOrmDataFactory.TEST_USER_APP_ROLE_ID;
        UserAppRole o = SecurityMockJmsOrmDataFactory.createOrmUserAppRole(userAppRoleId,
                SecurityMockJmsOrmDataFactory.TEST_APP_ROLE_ID,
                SecurityMockJmsOrmDataFactory.TEST_USER_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserAppRole(++userAppRoleId,
                SecurityMockJmsOrmDataFactory.TEST_APP_ROLE_ID,
                SecurityMockJmsOrmDataFactory.TEST_USER_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserAppRole(++userAppRoleId,
                SecurityMockJmsOrmDataFactory.TEST_APP_ROLE_ID,
                SecurityMockJmsOrmDataFactory.TEST_USER_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserAppRole(++userAppRoleId,
                SecurityMockJmsOrmDataFactory.TEST_APP_ROLE_ID,
                SecurityMockJmsOrmDataFactory.TEST_USER_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserAppRole(++userAppRoleId,
                SecurityMockJmsOrmDataFactory.TEST_APP_ROLE_ID,
                SecurityMockJmsOrmDataFactory.TEST_USER_ID);
        list.add(o);
        
        return list;
    }
    
    
    public static final List<UserGroup> createUserGroupMockData() {
        List<UserGroup> list = new ArrayList<>();
        int groupId = SecurityMockJmsOrmDataFactory.TEST_GROUP_ID;
        UserGroup o = SecurityMockJmsOrmDataFactory.createOrmUserGroup(groupId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserGroup(++groupId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserGroup(++groupId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserGroup(++groupId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserGroup(++groupId);
        list.add(o);
        
        return list;
    }

    public static final List<UserLogin> createUserLoginMockData() {
        List<UserLogin> list = new ArrayList<>();
        int loginId = SecurityMockJmsOrmDataFactory.TEST_USER_ID;
        UserLogin o = SecurityMockJmsOrmDataFactory.createOrmUserLogin(loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "password", "2018-01-01");
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserLogin(++loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "password", "2018-01-01");
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserLogin(++loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "password", "2018-01-01");
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserLogin(++loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "password", "2018-01-01");
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserLogin(++loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "password", "2018-01-01");
        list.add(o);
        
        return list;
    }
    
    public static final List<UserResource> createUserResourceMockData() {
        List<UserResource> list = new ArrayList<>();
        int resourceId = SecurityMockJmsOrmDataFactory.TEST_RESOURCE_ID;
        UserResource o = SecurityMockJmsOrmDataFactory.createOrmUserResource(resourceId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID,
                "URL_" + resourceId, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResource(++resourceId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID,
                "URL_" + resourceId, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResource(++resourceId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID,
                "URL_" + resourceId, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResource(++resourceId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID,
                "URL_" + resourceId, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResource(++resourceId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID,
                "URL_" + resourceId, true);
        list.add(o);
        
        return list;
    }
    
    
    public static final List<UserResourceAccess> createUserResourceAccessMockData() {
        List<UserResourceAccess> list = new ArrayList<>();
        int resourceAccessId = SecurityMockJmsOrmDataFactory.TEST_RESOURCE_ACCESS_ID;
        UserResourceAccess o = SecurityMockJmsOrmDataFactory
                .createOrmUserResourceAccess(resourceAccessId,
                        SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                        SecurityMockJmsOrmDataFactory.TEST_RESOURCE_ID,
                        SecurityMockJmsOrmDataFactory.TEST_USER_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResourceAccess(++resourceAccessId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_ID,
                SecurityMockJmsOrmDataFactory.TEST_USER_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResourceAccess(++resourceAccessId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_ID,
                SecurityMockJmsOrmDataFactory.TEST_USER_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResourceAccess(++resourceAccessId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_ID,
                SecurityMockJmsOrmDataFactory.TEST_USER_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResourceAccess(++resourceAccessId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_ID,
                SecurityMockJmsOrmDataFactory.TEST_USER_ID);
        list.add(o);
        
        return list;
    }
    
    public static final List<UserResourceSubtype> createUserResourceSubtypeMockData() {
        List<UserResourceSubtype> list = new ArrayList<>();
        int userResourceSubtypeId = SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID;
        UserResourceSubtype o = SecurityMockJmsOrmDataFactory.createOrmUserResourceSubtype(userResourceSubtypeId,
                        SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResourceSubtype(++userResourceSubtypeId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResourceSubtype(++userResourceSubtypeId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResourceSubtype(++userResourceSubtypeId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResourceSubtype(++userResourceSubtypeId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID);
        list.add(o);
        
        return list;
    }
    
    public static final List<UserResourceType> createUserResourceTypeMockData() {
        List<UserResourceType> list = new ArrayList<>();
        int resourceTypeId = SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID;
        UserResourceType o = SecurityMockJmsOrmDataFactory.createOrmUserResourceType(resourceTypeId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResourceType(++resourceTypeId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResourceType(++resourceTypeId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResourceType(++resourceTypeId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmUserResourceType(++resourceTypeId);
        list.add(o);
        
        return list;
    }
    
    public static final List<VwAppRoles> createVwAppRolesMockData() {
        List<VwAppRoles> list = new ArrayList<>();
        int appRoleId = SecurityMockJmsOrmDataFactory.TEST_APP_ROLE_ID;
        int roleId = SecurityMockJmsOrmDataFactory.TEST_ROLE_ID;
        VwAppRoles o = SecurityMockJmsOrmDataFactory.createOrmVwAppRoles(appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID, roleId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwAppRoles(++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID, ++roleId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwAppRoles(++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID, ++roleId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwAppRoles(++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID, ++roleId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwAppRoles(++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID, ++roleId);
        list.add(o);
        
        return list;
    }
  
    public static final List<VwResource> createVwResourceMockData() {
        List<VwResource> list = new ArrayList<>();
        int resourceId = SecurityMockJmsOrmDataFactory.TEST_RESOURCE_ID;
        int resourceTypeId = SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID;
        VwResource o = SecurityMockJmsOrmDataFactory.createOrmVwResource(resourceId, "URL_" + resourceId,
                resourceTypeId, SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwResource(++resourceId, "URL_" + resourceId,
                ++resourceTypeId, SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwResource(++resourceId, "URL_" + resourceId,
                ++resourceTypeId, SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwResource(++resourceId, "URL_" + resourceId,
                ++resourceTypeId, SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwResource(++resourceId, "URL_" + resourceId,
                ++resourceTypeId, SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID, true);
        list.add(o);
        
        return list;
    }
    
    public static final List<VwResourceType> createVwResourceTypeMockData() {
        List<VwResourceType> list = new ArrayList<>();
        int appRoleId = SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID;
        VwResourceType o = SecurityMockJmsOrmDataFactory.createOrmVwResourceType(
                appRoleId, SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwResourceType(++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwResourceType(++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwResourceType(++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwResourceType(++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID);
        list.add(o);
        
        return list;
    }
    
    public static final List<VwUser> createVwUserSingleMockData() {
        List<VwUser> list = new ArrayList<>();
        int loginId = SecurityMockJmsOrmDataFactory.TEST_USER_ID;
        VwUser o = SecurityMockJmsOrmDataFactory.createOrmVwUser(loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "test1234" + loginId, "2018-01-01", "ShortName_" + loginId);
        list.add(o);
        return list;
    }
    
    public static final List<VwUser> createVwUserMockData() {
        List<VwUser> list = new ArrayList<>();
        int loginId = SecurityMockJmsOrmDataFactory.TEST_USER_ID;
        VwUser o = SecurityMockJmsOrmDataFactory.createOrmVwUser(loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "test1234" + loginId, "2018-01-01", "ShortName_" + loginId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUser(++loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "test1234" + loginId, "2018-01-01", "ShortName_" + loginId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUser(++loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "test1234" + loginId, "2018-01-01", "ShortName_" + loginId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUser(++loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "test1234" + loginId, "2018-01-01", "ShortName_" + loginId);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUser(++loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "test1234" + loginId, "2018-01-01", "ShortName_" + loginId);
        list.add(o);
        
        return list;
    }
    
    public static final List<VwUserAppRoles> createVwUserAppRolesMockData() {
        List<VwUserAppRoles> list = new ArrayList<>();
        int appRoleId = SecurityMockJmsOrmDataFactory.TEST_APP_ROLE_ID;
        VwUserAppRoles o = SecurityMockJmsOrmDataFactory.createOrmVwUserAppRoles(SecurityMockJmsOrmDataFactory.TEST_USER_ID,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID,
                SecurityMockJmsOrmDataFactory.TEST_ROLE_ID,
                appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "user_name",
                "2018-01-01");
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUserAppRoles(SecurityMockJmsOrmDataFactory.TEST_USER_ID,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID,
                SecurityMockJmsOrmDataFactory.TEST_ROLE_ID,
                ++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "user_name",
                "2018-01-01");
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUserAppRoles(SecurityMockJmsOrmDataFactory.TEST_USER_ID,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID,
                SecurityMockJmsOrmDataFactory.TEST_ROLE_ID,
                ++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "user_name",
                "2018-01-01");
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUserAppRoles(SecurityMockJmsOrmDataFactory.TEST_USER_ID,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID,
                SecurityMockJmsOrmDataFactory.TEST_ROLE_ID,
                ++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "user_name",
                "2018-01-01");
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUserAppRoles(SecurityMockJmsOrmDataFactory.TEST_USER_ID,
                SecurityMockJmsOrmDataFactory.TEST_NEW_APP_ID,
                SecurityMockJmsOrmDataFactory.TEST_ROLE_ID,
                ++appRoleId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "user_name",
                "2018-01-01");
        list.add(o);
        
        return list;
    }
    
    public static final List<VwUserGroup> createVwUserGroupMockData() {
        List<VwUserGroup> list = new ArrayList<>();
        int loginId = SecurityMockJmsOrmDataFactory.TEST_USER_ID;
        VwUserGroup o = SecurityMockJmsOrmDataFactory.createOrmVwUserGroup(loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "2018-01-01");
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUserGroup(++loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "2018-01-01");
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUserGroup(++loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "2018-01-01");
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUserGroup(++loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "2018-01-01");
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUserGroup(++loginId,
                SecurityMockJmsOrmDataFactory.TEST_GROUP_ID, "UserName_" + loginId,
                "2018-01-01");
        list.add(o);
        
        return list;
    }
    
    
    public static final List<VwUserResourceAccess> createVwUserResourceAccessMockData() {
        List<VwUserResourceAccess> list = new ArrayList<>();
        int loginId = SecurityMockJmsOrmDataFactory.TEST_USER_ID;
        VwUserResourceAccess o = SecurityMockJmsOrmDataFactory
                .createOrmVwUserResourceAccess(loginId, "UserName_" + loginId,
                        SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                        SecurityMockJmsOrmDataFactory.TEST_RESOURCE_ID,
                        "URL_" + loginId,
                        SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID,
                        SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUserResourceAccess(++loginId,
                "UserName_" + loginId, SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_ID, "URL_" + loginId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUserResourceAccess(++loginId,
                "UserName_" + loginId, SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_ID, "URL_" + loginId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUserResourceAccess(++loginId,
                "UserName_" + loginId, SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_ID, "URL_" + loginId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID, true);
        list.add(o);
        o = SecurityMockJmsOrmDataFactory.createOrmVwUserResourceAccess(++loginId,
                "UserName_" + loginId, SecurityMockJmsOrmDataFactory.TEST_GROUP_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_ID, "URL_" + loginId,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_TYPE_ID,
                SecurityMockJmsOrmDataFactory.TEST_RESOURCE_SUBTYPE_ID, true);
        list.add(o);
        
        return list;
    }
    
}