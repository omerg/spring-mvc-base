package tr.com.lucidcode;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tr.com.lucidcode.integration.ajax.DeleteTest;
import tr.com.lucidcode.integration.ajax.InsertTest;
import tr.com.lucidcode.integration.ajax.ListAllPagifiedTest;
import tr.com.lucidcode.integration.ajax.UpdateTest;
import tr.com.lucidcode.model.AccountTest;
import tr.com.lucidcode.service.AccountServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ InsertTest.class, DeleteTest.class, ListAllPagifiedTest.class,
		UpdateTest.class, AccountServiceTest.class, AccountTest.class })
public class AllTests {

}