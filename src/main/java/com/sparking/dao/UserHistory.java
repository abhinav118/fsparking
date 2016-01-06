/**
 * 
 */
package com.sparking.dao;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author abhi
 * UserHistory
 */
public class UserHistory {

	private Integer transactionId;
	private int userId;
	private String txnAmt;
	private String pmtMethod;
	private Integer userRating;
	private Integer spotRating;
	private Date date;
	private Integer spotId;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String spotType;
	private String rateHr;
	private String rateMonthly;
	private String description;
	private Integer carSize;
	private String image;
	private BigDecimal x1;
	private BigDecimal y1;
	private String spotBooked;
	private Integer count;
	private String neighborhood;
	private Integer spotOwnerUserId;
	private Date startTime;
	private Date endTime;
	
	/**
	 * @return the transactionId
	 */
	public Integer getTransactionId() {
		return transactionId;
	}
	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the txnAmt
	 */
	public String getTxnAmt() {
		return txnAmt;
	}
	/**
	 * @param txnAmt the txnAmt to set
	 */
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	/**
	 * @return the pmtMethod
	 */
	public String getPmtMethod() {
		return pmtMethod;
	}
	/**
	 * @param pmtMethod the pmtMethod to set
	 */
	public void setPmtMethod(String pmtMethod) {
		this.pmtMethod = pmtMethod;
	}
	/**
	 * @return the userRating
	 */
	public Integer getUserRating() {
		return userRating;
	}
	/**
	 * @param userRating the userRating to set
	 */
	public void setUserRating(Integer userRating) {
		this.userRating = userRating;
	}
	/**
	 * @return the spotRating
	 */
	public Integer getSpotRating() {
		return spotRating;
	}
	/**
	 * @param spotRating the spotRating to set
	 */
	public void setSpotRating(Integer spotRating) {
		this.spotRating = spotRating;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the spotId
	 */
	public Integer getSpotId() {
		return spotId;
	}
	/**
	 * @param spotId the spotId to set
	 */
	public void setSpotId(Integer spotId) {
		this.spotId = spotId;
	}
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return the spotType
	 */
	public String getSpotType() {
		return spotType;
	}
	/**
	 * @param spotType the spotType to set
	 */
	public void setSpotType(String spotType) {
		this.spotType = spotType;
	}
	/**
	 * @return the rateHr
	 */
	public String getRateHr() {
		return rateHr;
	}
	/**
	 * @param rateHr the rateHr to set
	 */
	public void setRateHr(String rateHr) {
		this.rateHr = rateHr;
	}
	/**
	 * @return the rateMonthly
	 */
	public String getRateMonthly() {
		return rateMonthly;
	}
	/**
	 * @param rateMonthly the rateMonthly to set
	 */
	public void setRateMonthly(String rateMonthly) {
		this.rateMonthly = rateMonthly;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the carSize
	 */
	public Integer getCarSize() {
		return carSize;
	}
	/**
	 * @param carSize the carSize to set
	 */
	public void setCarSize(Integer carSize) {
		this.carSize = carSize;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @return the x1
	 */
	public BigDecimal getX1() {
		return x1;
	}
	/**
	 * @param x1 the x1 to set
	 */
	public void setX1(BigDecimal x1) {
		this.x1 = x1;
	}
	/**
	 * @return the y1
	 */
	public BigDecimal getY1() {
		return y1;
	}
	/**
	 * @param y1 the y1 to set
	 */
	public void setY1(BigDecimal y1) {
		this.y1 = y1;
	}
	/**
	 * @return the spotBooked
	 */
	public String getSpotBooked() {
		return spotBooked;
	}
	/**
	 * @param spotBooked the spotBooked to set
	 */
	public void setSpotBooked(String spotBooked) {
		this.spotBooked = spotBooked;
	}
	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * @return the neighborhood
	 */
	public String getNeighborhood() {
		return neighborhood;
	}
	/**
	 * @param neighborhood the neighborhood to set
	 */
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	/**
	 * @return the spotOwnerUserId
	 */
	public Integer getSpotOwnerUserId() {
		return spotOwnerUserId;
	}
	/**
	 * @param spotOwnerUserId the spotOwnerUserId to set
	 */
	public void setSpotOwnerUserId(Integer spotOwnerUserId) {
		this.spotOwnerUserId = spotOwnerUserId;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
