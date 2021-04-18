package com.trade.transmission.dao;

import java.time.LocalDate;

import com.trade.transmission.entity.TradeTable;

public interface TradeDao{
	
	String getAllTrades();
	
	void getAll();
	
	String updateData(String tradeId, int version, String counterPartyId, String bookId,
			LocalDate maturityDate, LocalDate createdDate, String expired);
	
	String insertData(String tradeId, int version, String counterPartyId, String bookId,
			LocalDate maturityDate, LocalDate createdDate, String expired);
	
	void updateExpiry(TradeTable trade);
	
	String addTrade(String tradeId, int version, String counterPartyId, String bookId,
			LocalDate maturityDate, LocalDate createdDate, String expired);
}
