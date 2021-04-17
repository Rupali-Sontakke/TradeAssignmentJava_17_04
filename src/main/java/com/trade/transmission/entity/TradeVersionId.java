package com.trade.transmission.entity;

import java.io.Serializable;

public class TradeVersionId implements Serializable{
	
	private String TradeId;
	
	private int Version;
	
	public TradeVersionId() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((TradeId == null) ? 0 : TradeId.hashCode());
		result = prime * result + Version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeVersionId other = (TradeVersionId) obj;
		if (TradeId == null) {
			if (other.TradeId != null)
				return false;
		} else if (!TradeId.equals(other.TradeId))
			return false;
		if (Version != other.Version)
			return false;
		return true;
	}
	

}
