package com.sparking.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;

import org.codehaus.jackson.map.util.JSONPObject;
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
import com.sparking.service.SparkService;

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
	@RequestMapping(value = "/getAllSpots", method = RequestMethod.GET)
	@ResponseBody
	public JSONPObject getAllSpots(@RequestParam("callback") String jsonpCallback) {
		
		
	//	List<Object> resultArray = new ArrayList<Object>();
		System.out.println("Spots all:"+sparkService.getAllSpots("","").toString());
		return new JSONPObject(jsonpCallback, sparkService.getAllSpots("",""));
		//return sparkService.getAllSpots("","");
		//return resultArray;
		
	}
	
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
		 * Save Txn Api
		 * @param @see com.sparking.hibernate.Txn
		 * @return status
		 */
		@RequestMapping(value = "/saveTxn", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON)
		@ResponseBody
		public Response saveTxn(@RequestBody Txn txn) {
			
			System.out.println("............Saving txn................\n"+txn.toString());
			
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
				return Response.serverError().entity("Spot already booked").build();
			}
 
	}

		
		/**
		 * Generate Client Token
		 */
		@RequestMapping(value = "/client_token", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON)
		@ResponseBody
		public Object handle(Request request, Response response){
			String token=gateway.clientToken().generate();
			System.out.println("[DEBUG] Genareted Token:" + token); 
			return token;
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
}
