package com.sparking.service;

import java.net.URLEncoder;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparking.dao.SpotDAO;
import com.sparking.dao.UserDAO;
import com.sparking.hibernate.Spot;
import com.sparking.hibernate.StreetSegements;
import com.sparking.hibernate.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service("sparkService")
public class SparkServiceImpl implements SparkService {
	
	@Autowired
	SpotDAO spotDAO;
	
	@Autowired
	UserDAO userDAO;
	
	
	private static String GEO_HEAD_URL ="https://maps.googleapis.com/maps/api/geocode/json?address=";
	private static String API_KEY = "&key=AIzaSyDftjrBhjX78W3ACCBq8fqXBvOY4XF3w4M";
	
	@Transactional
	public List<StreetSegements> getAvailabeParking(String x, String y) {
		// TODO Auto-generated method stub
		return null;
	}
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
		Spot spot=spotDAO.getSpotById(Integer.parseInt(user.getSpotId()));
		if(spot.getSpotBooked()==null||spot.getSpotBooked().isEmpty())
			return spotDAO.bookSpot(spot);
		else
			return 0;
		
	}

}
