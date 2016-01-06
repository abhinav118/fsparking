package com.sparking.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import com.sun.jersey.api.json.JSONWithPadding;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Request;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.sparking.hibernate.Spot;
import com.sparking.hibernate.Txn;
import com.sparking.hibernate.User;
import com.sparking.hibernate.UserHistory;
import com.sparking.service.SparkService;
import com.sparking.service.SparkServiceImpl;

/**
 * 
 * @author abhi
 *
 */
@Controller
@RequestMapping("/spot")
public class SpotController {
	
	
	
	@Autowired
	SparkService sparkService;
	/**
	 * Gateway Declaration
	 */
	private static BraintreeGateway gateway = new BraintreeGateway(com.braintreegateway.Environment.SANDBOX,
			"625vbtccgn4ypp7t",
			  "xf3pgpq8s5sbgfq2",
			  "a0d63d69546b9ab335f1876d3aa84595"
			);
	
	private static final Logger log = Logger.getLogger(SpotController.class);
	
	/**
	 * Rent My Spot Api
	 * @param spot
	 * @return
	 */
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
	
	/**
	 * Get All Spots API
	 * @param jsonpCallback
	 * @return @see com.sparking.hibernate.Spot
	 */
	@RequestMapping(value = "/getAllSpots", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public JSONWithPadding getAllSpots(@RequestParam("callback") String jsonpCallback) {
		
		
	//	List<Object> resultArray = new ArrayList<Object>();
		log.info("Spots all:"+sparkService.getAllSpots("","").toString());
		return new JSONWithPadding(sparkService.getAllSpots("",""),jsonpCallback);
		//return sparkService.getAllSpots("","");
		//return resultArray;
		
	}

	

	/**
	 * Get All Spots API
	 * @param jsonpCallback
	 * @return @see com.sparking.hibernate.Spot
	 */
	@RequestMapping(value = "/getAllSpotsByFilter", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public JSONWithPadding getAllSpotsByFilter(@RequestParam("callback") String jsonpCallback, @RequestParam("spotType") String spotType, @RequestParam("startTime") String sTime, @RequestParam("endTime") String eTime  ) {
		
		
		Long start=Long.parseLong(sTime);
		Long end=Long.parseLong(eTime);
		Date startTime=new Date(sTime);		
		Date endTime=new Date(eTime);
		
		//	List<Object> resultArray = new ArrayList<Object>();
		//log.info("Spots all:"+sparkService.getAllSpots("","").toString());
		return new JSONWithPadding(sparkService.getAllSpotsThrufilter(spotType, startTime, endTime),jsonpCallback);
		//return sparkService.getAllSpots("","");
		//return resultArray;
		
	}

	
	
//	/**
//	 * Get All Spots API
//	 * @return @see com.sparking.hibernate.Spot
//	 */
//	@RequestMapping(value = "/getAllSpots", method = RequestMethod.GET)
//	@ResponseBody
//	public Object getAllSpots() {
//		
//		
//	//	List<Object> resultArray = new ArrayList<Object>();
//		System.out.println("Spots all:"+sparkService.getAllSpots("","").toString());
//		//return new JSONPObject(jsonpCallback, sparkService.getAllSpots("",""));
//		return sparkService.getAllSpots("","");
//		//return resultArray;
//	}
	/**
	 * Save User Api
	 * @param @see com.sparking.hibernate.User
	 * @return status
	 */
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON)
	@ResponseBody
	public Response saveUser(@RequestBody User user) {
		
		System.out.println("............Saving user................\n"+user.toString());
		
		List<Object> resultArray = new ArrayList<Object>();
		Integer userID = sparkService.saveUser(user);
		String status;
	
		
		if(userID != 0) {
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
			return Response.serverError().entity("Spot already booked").build();
		}
		
	}
	
	
	
	/**
	 * get User Api
	 * @param @see com.sparking.hibernate.User
	 * @return status
	 */
	@RequestMapping(value = "/getUserById", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public JSONWithPadding getUserById(@RequestParam("callback") String jsonpCallback, @RequestParam("userId")Integer userId) {
		
		User user = sparkService.getUserById(userId);
		if(user!=null)
		log.info("user id:"+user.getId());
		return new JSONWithPadding(user,jsonpCallback);
		//return sparkService.getAllSpots("","");		
		//return resultArray;
		
	}
	
	
	/**
	 * get User Api
	 * @param @see com.sparking.hibernate.User
	 * @return status
	 */
	@RequestMapping(value = "/getUserByFbId", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public JSONWithPadding getUserByFbId(@RequestParam("callback") String jsonpCallback, @RequestParam("fbId")String fbId,@RequestParam("firstName")String fbFirstName, @RequestParam("lastName")String fbLastName, @RequestParam("email")String fbEmail , @RequestParam("displayPic")String displayPicURL ) {
		
		User user = sparkService.getUserByFbId(fbId);
		
		if(user!=null)
			log.info("user id:"+user.getId());
		else
		{
			User newUser=new User();
			newUser.setFirstName(fbFirstName);
			newUser.setLastName(fbLastName);
			newUser.setFbId(fbId);
			newUser.setEmail(fbEmail);
			newUser.setDisplayPic(displayPicURL);
			Integer userID=sparkService.saveUser(newUser);
			if(userID==0)
				return new JSONWithPadding(userID,jsonpCallback);
			else
			return new JSONWithPadding(newUser,jsonpCallback);
		}
		//existing user
		return new JSONWithPadding(user,jsonpCallback);
		//return sparkService.getAllSpots("","");	
		//return resultArray;
		
	}
	
	/**
	 * get User Api
	 * @param @see com.sparking.hibernate.User
	 * @return status
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public Response updateUser(@RequestBody User user) {
		
		System.out.println("............Updating user................\n"+user.toString());
		
		List<Object> resultArray = new ArrayList<Object>();
		Integer userID = sparkService.updateUser(user);
		String status;
	
		
		if(userID != 0) {
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
			return Response.serverError().entity("Spot already booked").build();
		}
		
	}
		/**
		 * Save Txn Api
		 * @param @see com.sparking.hibernate.Txn
		 * @return status
		 */
		@RequestMapping(value = "/saveTxn", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON)
		@ResponseBody
		public Response saveTxn(@RequestBody Txn txn) {
			
			log.info("............Saving txn................\n"+txn.toString());
			
			List<Object> resultArray = new ArrayList<Object>();
			Integer txnID = sparkService.saveTxn(txn);
			String status;
		
			
			if(txnID != 0) {
				status = "OK";
				resultArray.add(status); 
				resultArray.add(txnID);
				return Response.ok(resultArray).header("Access-Control-Allow-Origin", "*")
					      .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
					      .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
					      .header( "AccessControlAllowCredentials", true)
				            .build();
			}else{
				status = "FAIL";
				return Response.serverError().entity("Spot already booked or invalid spot").build();
			}
 
	}

		
		/**
		 * Get Spots for a user id
		 * @param jsonpCallback
		 * @return @see com.sparking.hibernate.Spot
		 */
		@RequestMapping(value = "/getUserSpotHistory", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON)
		@ResponseBody
		@JsonIgnore
		public JSONWithPadding getUserSpotHistory(@RequestParam("callback") String jsonpCallback, @RequestParam("userId") Integer userId) {
			
			List<UserHistory> resultArray = sparkService.getSpotsForUserId(userId);
			log.info("Spots all:"+resultArray.toString());
			return new JSONWithPadding(resultArray,jsonpCallback);
			//return sparkService.getAllSpots("","");
			//return resultArray;
			
			
		}
		
		/**
		 * Get client token
		 * @param jsonpCallback
		 * @return @see token
		 */
		@RequestMapping(value = "/client_token", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON)
		@ResponseBody
		public JSONWithPadding client_token(@RequestParam("callback") String jsonpCallback) {
			String token=gateway.clientToken().generate();
			log.info("---------token:-----------"+token);
			return new JSONWithPadding(token,jsonpCallback);
		}
		
		
		/**
		 * Accepting payment at checkout
		 */
		@RequestMapping(value = "/checkout", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON)
		@ResponseBody
		public Object handleCheckout(Request request, Response response) {
		    
		    // Use payment method nonce here
		    
		    TransactionRequest transactionRequest = new TransactionRequest()
		    	    .amount(new BigDecimal("100.00"))
		    	    .paymentMethodNonce(request.toQueryString("payment_method_nonce"));

		    	Result<Transaction> result = gateway.transaction().sale(transactionRequest);
		    	return result;
		  }
		
//		/**
//		 * Facebook Integration
//		 */
//		@RequestMapping(value = "/login", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON)
//		@ResponseBody
//		public Response login(Request request, Response response) {
//		    
//		  }

}
