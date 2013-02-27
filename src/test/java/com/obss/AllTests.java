package com.obss;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.obss.integration.ajax.DeleteTest;
import com.obss.integration.ajax.InsertTest;
import com.obss.integration.ajax.ListAllPagifiedTest;
import com.obss.integration.ajax.UpdateTest;
import com.obss.model.AccountTest;
import com.obss.service.AccountServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ InsertTest.class, DeleteTest.class, ListAllPagifiedTest.class,
		UpdateTest.class, AccountServiceTest.class, AccountTest.class })
public class AllTests {

}