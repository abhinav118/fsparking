package com.sparking.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sparking.hibernate.Spot;
import com.sparking.hibernate.User;
import com.sparking.service.SparkService;


@Controller
@RequestMapping("/spot")
public class SpotController {
	
	@Autowired
	SparkService sparkService;
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	@ResponseBody
	public Object ping() {
		List<Object> resultArray = new ArrayList<Object>();
		String status = "OK";
		String pingValue = "Ping Success";
		resultArray.add(status);
		
		Spot spot = sparkService.getSpotById(1);
		
		resultArray.add(pingValue);
		
		resultArray.add(spot);
		
		sparkService.getLatLongForAddress(spot.getAddress1(), spot.getAddress2(), spot.getCity(),
				spot.getState(), spot.getZip());
		
		
		return resultArray;
		
	}
	
	@RequestMapping(value = "/rentMySpot", method = RequestMethod.POST)
	@ResponseBody
	public Object rentMySpot(@RequestBody Spot spot) {
		
		List<Object> resultArray = new ArrayList<Object>();
		Integer spotId = sparkService.rentMySpot(spot);
		String status;
	
		if(spotId != null) {
			status = "OK";
			resultArray.add(status);
			resultArray.add(spotId);
		}else{
			status = "FAIL";
		}
		
		return resultArray;
	}
	
	@RequestMapping(value = "/getAllSpots", method = RequestMethod.GET)
	@ResponseBody
	public JSONPObject getAllSpots(@RequestParam("callback") String jsonpCallback) {
		
		
	//	List<Object> resultArray = new ArrayList<Object>();
		System.out.println("Spots all:"+sparkService.getAllSpots("","").toString());
		return new JSONPObject(jsonpCallback, sparkService.getAllSpots("",""));
		//return sparkService.getAllSpots("","");
		//return resultArray;
		
	}
	
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON)
	@ResponseBody
	public Response saveUser(@RequestBody User user) {
		
		List<Object> resultArray = new ArrayList<Object>();
		Integer userID = sparkService.saveUser(user);
		String status;
		Integer SpotBooked=0;
	
		if(user != null && userID!=SpotBooked ) {
			status = "OK";
			resultArray.add(status);
			resultArray.add(userID);
			return Response.ok(resultArray).header("Access-Control-Allow-Origin", "*")
				      .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
				      .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
				      .header( "AccessControlAllowCredentials", true)
			            .build();
		}else{
			status = "FAIL";
		}
		
	
		return Response.serverError().header("Access-Control-Allow-Origin", "*")
			      .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
			      .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
			      .header( "AccessControlAllowCredentials", true)
		            .build();

 
	}
	

}
