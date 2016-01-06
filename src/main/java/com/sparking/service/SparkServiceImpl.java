package com.sparking.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparking.dao.SpotDAO;
import com.sparking.dao.TxnDAO;
import com.sparking.dao.UserDAO;
import com.sparking.hibernate.Spot;
import com.sparking.hibernate.Txn;
import com.sparking.hibernate.User;
import com.sparking.hibernate.UserHistory;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service("sparkService")
public class SparkServiceImpl implements SparkService {
	
	@Autowired
	SpotDAO spotDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	TxnDAO txnDAO;
	
	
	private static String GEO_HEAD_URL ="https://maps.googleapis.com/maps/api/geocode/json?address=";
	private static String API_KEY = "&key=AIzaSyDftjrBhjX78W3ACCBq8fqXBvOY4XF3w4M";
	private static final Logger log = Logger.getLogger(SparkServiceImpl.class);
	
	@Transactional
	public String updateParkingCount(String x, String y) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Method to add a spot
	 */
	@Transactional
	public Integer rentMySpot(Spot mySpot) {
		Integer spotId;
		return spotId = spotDAO.saveSpot(mySpot);
	}
	
	@Transactional
	public List<Spot> getAllSpots(String x1,String y1) {
		return spotDAO.getAllSpots();
	}
	
	
	@Transactional
	public List<Spot> getAllSpots(String type, Date startTime, Date endTime) {
		return spotDAO.getAllSpotsThrufilter(type, startTime, endTime);
	}
	
	@Transactional
	public Spot getSpotById(Integer id) {
		return spotDAO.getSpotById(id);
	}
	
	
	
	public void getLatLongForAddress(String address1, String address2,
			String city, String state, String zip) {
		
		String parms = (address1).concat(",")
				.concat(address2).concat(",").concat(city).concat(",")
				.concat(state).concat(",").concat(zip);
		
		
		try {
			parms = URLEncoder.encode(parms, "UTF-8");
			
			System.out.println("parms: "+parms);
			
			String geoCodeURL = GEO_HEAD_URL.concat(parms).concat(API_KEY);
			System.out.println("geoCodeURL: "+geoCodeURL);
			
			Client client = Client.create();
			WebResource webResource = client.resource(geoCodeURL);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);
			if (response.getStatus() == 200) {
				String output = response.getEntity(String.class);
				System.out.println("Google API Response: "+output);
			}	
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Integer saveUser(User user) {
		Integer userId=userDAO.saveUser(user);
		if(user==null)
			return -1;
		
		else
			return userId;
		
	}
	
	
	public Integer updateUser(User user) {
		Integer userId=userDAO.updateUser(user);
		if(user==null)
			return -1;
		else
			return userId;
		
	}
	
	
	public User getUserById(Integer id) {
		User user=userDAO.getUserById(id);
		if(user==null)
			return null;
		else
			return user;
		
	}
	
	
	public User getUserByFbId(String id) {
		User user=userDAO.getUserByFbId(id);
		if(user==null)
			return null;
		else
			return user;
		
	}
	
	public User getUserByEmail(String email) {
		User user=userDAO.getUserByEmail(email);
		if(user==null)
			return null;
		else
			return user;
		
		
	}
	
	
	/**
	 * get spots booked by a user , history of user booked spots
	 */
	@Transactional
	public List<UserHistory> getSpotsForUserId(Integer id) {
		
		List<Object> resultArray =  spotDAO.getSpotsForUserId(id);
		List<UserHistory> histories=new ArrayList<UserHistory>();
		try {
		JSONArray jsonArray = new JSONArray();
		ObjectMapper mapper = new ObjectMapper();
		Txn txn=new Txn();
		Integer spotID;
		JSONObject jObject = null;
		//Object to JSON in file
		JSONObject jObjects=null;
		//Object to JSON in String
		
		for(Object obj:resultArray){
			
				String jsonInString = mapper.writeValueAsString(obj);
				jObject=new JSONObject(jsonInString);
				
				if(jObject.get("spotId") instanceof Long){
					 Long lval=(Long)jObject.get("spotId");
					 spotID=lval.intValue();
				 }else
					 spotID=(Integer)(jObject.get("spotId"));
				 
				 String spotJson=mapper.writeValueAsString(spotDAO.getSpotById(spotID));
				 
				 JSONObject spotJObject = new JSONObject(spotJson);
				 
				 //Merging jsonobject
				 JSONObject merged = new JSONObject(jObject, JSONObject.getNames(jObject));
				 for(String key : JSONObject.getNames(spotJObject))
				 {
				   merged.put(key, spotJObject.get(key));
				 }
				 jsonArray.put(merged);
				log.info("Txn object:"+merged.toString());	
				histories.add(mapper.readValue(merged.toString(),UserHistory.class));
		}
		
		log.info("Array:"+jsonArray);
		
		
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return histories;
	}
	


	
	public Integer saveTxn(Txn txn) {
		if(txn.getSpotId()==0||txn.getUserId()==0||getSpotById(txn.getSpotId())==null)//invalid spot id
			return 0;//owner spot
		Integer txnId=txnDAO.saveTxn(txn);	
		
		//BOOK SPOT with times
		spotDAO.bookSpot(getSpotById(txn.getSpotId()), txn.getStartTime(), txn.getEndTime());
		
		log.info("logging txn :"+txn.toString());
		return txnId;
		
	}

	@Transactional
	public List<Spot> getAllSpotsThrufilter(String type, Date startTime, Date endTime) {
		return spotDAO.getAllSpotsThrufilter(type, startTime, endTime);
	}

}
