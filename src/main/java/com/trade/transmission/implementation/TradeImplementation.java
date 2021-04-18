package com.trade.transmission.implementation;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.trade.transmission.connection.TradeDemoConnection;
import com.trade.transmission.entity.TradeTable;
import com.trade.transmission.exception.LowerVersionException;
import com.trade.transmission.service.TradeService;

public class TradeImplementation extends TradeDemoConnection implements TradeService {

	public String addTrade(String tradeId, int version, String counterPartyId, String bookId, LocalDate maturityDate,
			LocalDate createdDate, String expired) {
		
		String status = "";
		LocalDate today = LocalDate.now();
		if (maturityDate.isBefore(today)) {
			status = "NotMatch";
			System.out.println("Maturity Date is before than Todays date");
		} else {
			Session session = factory.openSession();
			Transaction tx = null;
			TradeTable data = null;
			try {
				List listOfRecords = session.createQuery("FROM TradeTable where TradeId ='" + tradeId + "'").list();

				if (listOfRecords.isEmpty()) {
					status = insertData(tradeId, version, counterPartyId, bookId, maturityDate, createdDate, expired);
				} else {
					Iterator itr = listOfRecords.iterator();
					while (itr.hasNext()) {
						TradeTable trade = (TradeTable) itr.next();

						if (version == trade.getVersion()) {
							status = updateData(tradeId, version, counterPartyId, bookId, maturityDate, createdDate, expired);
						} else if (version < trade.getVersion()) {
							throw new LowerVersionException("Version for Trade Id " + tradeId
									+ " is lower than existing version.. Can not insert or update details..");
						} else {
							status = insertData(tradeId, version, counterPartyId, bookId, maturityDate, createdDate, expired);
						}
					}
				}
			} catch (HibernateException e) {
				e.printStackTrace();
			}
//			} catch (NoResultException e) {
//				status = insertData(tradeId, version, counterPartyId, bookId, maturityDate, createdDate, expired);
//			} 
			finally {
				session.close();
			}
		}
		return status;
	}

	public String getAllTrades() {
		Session session = factory.openSession();
		Transaction tx = null;
		String status = "NoUpdates";
		try {
			tx = session.beginTransaction();
			List trades = session.createQuery("FROM TradeTable").list();
			Iterator itr = trades.iterator();
			while (itr.hasNext()) {
				TradeTable trade = (TradeTable) itr.next();
				LocalDate today = LocalDate.now();
				if (trade.getMaturityDate().isBefore(today) && trade.getExpired().equals("N")) {
					updateExpiry(trade);
					status = "updated";
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return status;
	}
	
	
	public void getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List trades = session.createQuery("FROM TradeTable").list();
			Iterator itr = trades.iterator();
			while (itr.hasNext()) {
				TradeTable trade = (TradeTable) itr.next();
				System.out.println("Fetch All Data :" + trade);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public String updateData(String tradeId, int version, String counterPartyId, String bookId,
			LocalDate maturityDate, LocalDate createdDate, String expired) {
		String status = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			TradeTable trade = new TradeTable(tradeId, version, counterPartyId, bookId, maturityDate, createdDate,
					expired);
			session.update(trade);
			System.out.println("Updated record .." + trade);
			tx.commit();
			status = "updated";
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return status;
	}

	public String insertData(String tradeId, int version, String counterPartyId, String bookId,
			LocalDate maturityDate, LocalDate createdDate, String expired) {
		String status = "";
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			TradeTable trade = new TradeTable(tradeId, version, counterPartyId, bookId, maturityDate, createdDate,
					expired);
			session.save(trade);
			System.out.println("Inserted Record .. " + trade);
			tx.commit();
			status = "inserted";
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return status;
	}

	public void updateExpiry(TradeTable trade) {

		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			trade.setExpired("Y");
			session.update(trade);
			System.out.println("Updated record .." + trade);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	
//	private static TradeTable getTrade(String tradeId, int version) {
//
//		Session session = factory.openSession();
//		Transaction tx = null;
//		TradeTable data = null;
//		try {
//			tx = session.beginTransaction();
//			Object obj = session
//					.createQuery("FROM TradeTable where TradeId ='" + tradeId + "' and Version='" + version + "'")
//					.getSingleResult();
//			data = (TradeTable) obj;
//			System.out.println(data);
//		} catch (HibernateException e) {
//			if (tx != null)
//				tx.rollback();
//			e.printStackTrace();
//		} catch (NoResultException ex) {
//
//		} finally {
//			session.close();
//		}
//
//		return data;
//	}

}
