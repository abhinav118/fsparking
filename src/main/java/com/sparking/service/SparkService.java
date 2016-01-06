package com.sparking.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.sparking.hibernate.Spot;
import com.sparking.hibernate.Txn;
import com.sparking.hibernate.User;
import com.sparking.hibernate.UserHistory;

public interface SparkService {
	
	public abstract String updateParkingCount(String x, String y);
	
	public abstract Integer rentMySpot(Spot mySpot);
	
	public abstract Integer saveUser(User user);
	
	public abstract Integer saveTxn(Txn txn);
	
	public abstract Integer updateUser(User user);
	
	public abstract User getUserById(Integer id);
	
	public abstract User getUserByEmail(String email);
	
	public abstract List<Spot> getAllSpots(String x, String y);
	
	public abstract List<Spot> getAllSpotsThrufilter(String type, Date startTime, Date endTime);
	
	public abstract List<UserHistory> getSpotsForUserId(Integer userId);
	
	public abstract Spot getSpotById(Integer id);
	
	public abstract void getLatLongForAddress(String address1, String address2, String city, String state, String zip);

	public abstract User getUserByFbId(String fbId);

	
}
