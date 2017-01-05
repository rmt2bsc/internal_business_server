package testcases.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import testcases.messaging.addressbook.BuisnessContactApiMessagingTest;

@RunWith(Suite.class)
@SuiteClasses({ BuisnessContactApiMessagingTest.class })
public class TestSuite {

}
