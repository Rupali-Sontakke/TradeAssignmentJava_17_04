package com.trade.transmission.connection;

import java.time.LocalDate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.trade.transmission.entity.TradeTable;
import com.trade.transmission.implementation.TradeImplementation;
import com.trade.transmission.service.TradeService;

public class TradeDemoConnection {
	
	public static SessionFactory factory;
	
	static TradeService service =  new TradeImplementation();
	
	static {
		factory = getConfig();
		// dummy data
		addDummyData("T1", 1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDate.now(), "N");
		addDummyData("T2", 2, "CP-2", "B1", LocalDate.now().plusDays(10), LocalDate.now(), "N");
		addDummyData("T2", 1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDate.now().minusYears(5), "N");
		addDummyData("T3", 3, "CP-3", "B2", LocalDate.now().minusDays(10), LocalDate.now(), "Y");
	}
	
	public static void main(String[] args) {
		
		service.getAll();
		
		// Test Cases
//		addTrade("T1", 1, "CP", "B2", LocalDate.now().plusDays(20), LocalDate.now(), "N");
//		addTrade("T1", 4, "CP", "B2", LocalDate.now().plusDays(20), LocalDate.now(), "N");
//		addTrade("T4", 1, "CP", "B2", LocalDate.now().plusDays(20), LocalDate.now(), "N");
//		addTrade("T1", 0, "CP", "B2", LocalDate.now().plusDays(20), LocalDate.now(), "N");
		
//		addTrade("T1", 0, "CP", "B2", LocalDate.now().minusDays(20), LocalDate.now(), "N");
		

		//Daily one time activity need to execute ..
//		getAllTrades();

		factory.close();

	}

	public static SessionFactory getConfig() {
		
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		return factory = configuration.buildSessionFactory();
	}


	public static void addDummyData(String tradeId, int version, String counterPartyId, String bookId, LocalDate maturityDate,
			LocalDate createdDate, String expired) {
		
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			TradeTable trade = new TradeTable(tradeId, version, counterPartyId, bookId, maturityDate, createdDate,
					expired);
			session.save(trade);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
	}
	

}
