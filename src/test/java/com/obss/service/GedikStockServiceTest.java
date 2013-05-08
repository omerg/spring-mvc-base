package com.obss.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import jodd.jerry.Jerry;

import org.junit.Before;
import org.junit.Test;

import com.obss.util.JerryUtils;

public class GedikStockServiceTest {
	
	Jerry doc;
	
	@Before 
	public void initDoc() {		
		try {
			doc = JerryUtils.getJerry(GedikStockService.GEDIK_YATIRIM_MAIN_URL);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}		
	}
	
	@Test
	public void _1_testLoadDoc() {
		assertNotNull(doc);
	}
	
	@Test
	public void _2_testContentAreaClass() {		
		assertNotNull(doc.$(GedikStockService.HISSE_TABLE_CSS_CLASS));
	}

}
