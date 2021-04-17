package com.trade.transmission.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(TradeVersionId.class)
public class TradeTable {

	@Id
	private String TradeId;

	@Id
	private int Version;

	@Column
	private String CounterPartyId;

	@Column
	private String BookId;

	@Column
	private LocalDate MaturityDate;

	@Column
	private LocalDate CreatedDate;

	@Column
	private String Expired;

	public TradeTable() {

	}

	public TradeTable(String tradeId, int version, String counterPartyId, String bookId, LocalDate maturityDate,
			LocalDate createdDate, String expired) {
		super();
		TradeId = tradeId;
		Version = version;
		CounterPartyId = counterPartyId;
		BookId = bookId;
		MaturityDate = maturityDate;
		CreatedDate = createdDate;
		Expired = expired;
	}

	public String getTradeId() {
		return TradeId;
	}

	public void setTradeId(String tradeId) {
		TradeId = tradeId;
	}

	public int getVersion() {
		return Version;
	}

	public void setVersion(int version) {
		Version = version;
	}

	public String getCounterPartyId() {
		return CounterPartyId;
	}

	public void setCounterPartyId(String counterPartyId) {
		CounterPartyId = counterPartyId;
	}

	public String getBookId() {
		return BookId;
	}

	public void setBookId(String bookId) {
		BookId = bookId;
	}

	public LocalDate getMaturityDate() {
		return MaturityDate;
	}

	public void setMaturityDate(LocalDate maturityDate) {
		MaturityDate = maturityDate;
	}

	public LocalDate getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		CreatedDate = createdDate;
	}

	public String getExpired() {
		return Expired;
	}

	public void setExpired(String expired) {
		Expired = expired;
	}

	@Override
	public String toString() {
		return "TradeTable [TradeId=" + TradeId + ", Version=" + Version + ", CounterPartyId=" + CounterPartyId
				+ ", BookId=" + BookId + ", MaturityDate=" + MaturityDate + ", CreatedDate=" + CreatedDate
				+ ", Expired=" + Expired + "]";
	}

}
