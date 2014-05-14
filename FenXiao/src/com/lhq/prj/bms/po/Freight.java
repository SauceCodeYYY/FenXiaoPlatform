package com.lhq.prj.bms.po;

import java.io.Serializable;

public class Freight implements Serializable {
	public Freight()
	{
		
	}
	
	private Integer freightid;
	
	private String priovice;
	
	private Integer firstfreight;
	
	private Integer lastfreight;
	
	private String freightcompany;
	
	private String channel;



	public String getPriovice() {
		return priovice;
	}

	public void setPriovice(String priovice) {
		this.priovice = priovice;
	}

	



	public Integer getFreightid() {
		return freightid;
	}

	public void setFreightid(Integer freightid) {
		this.freightid = freightid;
	}

	public Integer getFirstfreight() {
		return firstfreight;
	}

	public void setFirstfreight(Integer firstfreight) {
		this.firstfreight = firstfreight;
	}

	public Integer getLastfreight() {
		return lastfreight;
	}

	public void setLastfreight(Integer lastfreight) {
		this.lastfreight = lastfreight;
	}

	public String getFreightcompany() {
		return freightcompany;
	}

	public void setFreightcompany(String freightcompany) {
		this.freightcompany = freightcompany;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	
	

}
