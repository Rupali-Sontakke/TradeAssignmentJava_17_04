package com.trade.transmission.implementation;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trade.transmission.connection.TradeDemoConnection;
import com.trade.transmission.dao.TradeDao;
import com.trade.transmission.exception.LowerVersionException;
import com.trade.transmission.service.TradeService;

public class TradeImplementationUnitTest{
	
	public static SessionFactory factory;
	
	TradeService implementation = new TradeDao();
	
	@Before
	public void setUp() {
		factory = TradeDemoConnection.getConfig();
		TradeDemoConnection.addDummyData("T1", 1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDate.now(), "N");
		TradeDemoConnection.addDummyData("T2", 2, "CP-2", "B1", LocalDate.now().plusDays(10), LocalDate.now(), "N");
		TradeDemoConnection.addDummyData("T2", 1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDate.now().minusYears(5), "N");
		TradeDemoConnection.addDummyData("T3", 3, "CP-3", "B2", LocalDate.now().minusDays(10), LocalDate.now(), "Y");
		TradeDemoConnection.addDummyData("T3", 4, "CP-3", "B2", LocalDate.now().minusDays(1), LocalDate.now(), "N");
	}  
	
	@Test
	public void maturityDtBeforeTodaysDt() {
		String status = implementation.addTrade("T1", 5, "CP", "B2", LocalDate.now().minusDays(20), LocalDate.now(), "N");
		assertEquals("NotMatch", status);
	}
	// TradeImplementation.addTrade
	@Test
	public void addTrade_Equal_TradeId_Version() {
		String status = implementation.addTrade("T1", 1, "CP", "B2", LocalDate.now().plusDays(20), LocalDate.now(), "N");
		assertEquals("updated", status);
	}
	
	@Test
	public void addTrade_Grater_Version() {
		String status = implementation.addTrade("T1", 4, "CP", "B2", LocalDate.now().plusDays(20), LocalDate.now(), "N");
		assertEquals("inserted", status);
	}
	
	@Test
	public void addTrade_New_TradeId() {
		String status = implementation.addTrade("T4", 1, "CP", "B2", LocalDate.now().plusDays(20), LocalDate.now(), "N");
		assertEquals("inserted", status);
	}
	
	@Test(expected = LowerVersionException.class)
	public void addTrade_Lower_Verion() {
		String status = implementation.addTrade("T1", 0, "CP", "B2", LocalDate.now().plusDays(20), LocalDate.now(), "N");
	}
	
	//daily once we can execute this test case to update Expired = Y
	@Test
	public void checkExpiredSetToY() {
		String status = implementation.getAllTrades();
		assertEquals("updated", status);
	}
	
	
	@After
	public void afterTestExecution() {
		factory.close();
	}
}
