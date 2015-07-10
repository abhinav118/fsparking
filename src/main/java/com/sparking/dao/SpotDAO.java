package com.sparking.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sparking.hibernate.Spot;
import com.sparking.hibernate.User;


@Repository("spotDAO")
public class SpotDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	private static final Logger log = Logger.getLogger(SpotDAO.class);
	
	/**
	 * Method to save a spot
	 * @param spot
	 * @return
	 */
	public Integer saveSpot(Spot spot) {
		
		Integer spotId = null;
		try{
			if(isValidSpot(spot)) {
				log.info("Saving spot");
				
				spotId = (Integer) sessionFactory.getCurrentSession().save(spot);
			}else{
				log.info("Spot is null or one or more of it properties are null or not set");
			}
 			
		}catch (Exception e) {
			log.debug("Exception occured while saving spot");
			e.printStackTrace();
		}
		
		return spotId;
	}
	
	
//public Integer saveUser(User user) {
//		
//		Integer userId = null;
//		try{
//					
//				userId = (Integer) userSessionFactory.getCurrentSession().save(user);
//			
// 			
//		}catch (Exception e) {
//			log.debug("Exception occured while saving spot");
//			e.printStackTrace();
//		}
//		
//		return userId;
//	}
//	
	@SuppressWarnings("unchecked")
	public List<Spot> getAllSpots() {
		List<Spot> resultList = null;
		try{
			
			log.info("Listing all spots");
			String hql = "FROM Spot where spotBooked is null";
			resultList = sessionFactory.getCurrentSession().createQuery(hql).list();
			
		}catch (Exception e) {
			log.debug("Exception occured while getting all spot");
			e.printStackTrace();
		}
		log.info("result:"+resultList);
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Spot> getSpotsOrderByDistance(String x1, String y1) {
		List<Spot> resultList = null;
		try{
			
			log.info("Listing all spots by distance");
			String hql = "SELECT * ,"+
  " ("+
    "3959 * acos ("+
     " cos ( radians("+x1+") )"+
      "* cos( radians( "+x1+" ) )"+
      "* cos( radians( "+y1+" ) - radians(-122) )"+
      "+ sin ( radians(34) )"+
      "* sin( radians( "+x1+" ) )"+
    ")"+
  ") AS distance "+
"FROM spark.Spot "+
"ORDER BY distance "+
"LIMIT 0 , 20";
			
			resultList = sessionFactory.getCurrentSession().createSQLQuery(hql).list();
			
		}catch (Exception e) {
			log.debug("Exception occured while getting all spot");
			e.printStackTrace();
		}
		log.info("result:"+resultList);
		
		return resultList;
	}
	
	@Transactional
	@SuppressWarnings("finally")
	public Spot getSpotById(Integer id) {
		Spot spot = null;
		List<Spot> resultList=null;
		try{
			if(id == null) {
				log.info("Spot id is null");
			}else {
				log.info("Getting spot from DB for spot id: "+id);
				spot = (Spot) sessionFactory.getCurrentSession().get(Spot.class, id);
//				String hql = "FROM Spot s WHERE s.id = :id";
//				//spot = (Spot)sessionFactory.getCurrentSession().createQuery(hql).list();
//				resultList = sessionFactory.getCurrentSession().createQuery(hql).list();
				log.info("Spot retrived "+resultList);
			}
			
		}catch (Exception e) {
			log.debug("Exception occured while retrieving spot with spot id: "+ id);
			e.printStackTrace();
		}finally {
			return spot;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Spot> getSpotsInZip(String zip) {
		List<Spot> resultList = null;
		try{
			
			log.info("Listing all spots");
			String hql = "FROM Spot s WHERE s.zip = :zip";
			resultList = sessionFactory.getCurrentSession().createQuery(hql).list();
			
		}catch (Exception e) {
			log.debug("Exception occured while getting all spot");
			e.printStackTrace();
		}
		
	
		return resultList;
	}
	
	/**
	 * Verifies if the spot object is valid
	 * @param spot
	 * @return
	 */
	private boolean isValidSpot(Spot spot) {
		if(spot == null || spot.getAddress1() == null || spot.getAddress1().length() < 1 
				|| spot.getAddress2() == null || spot.getAddress2().length() < 1 || spot.getCarSize() == null
				|| spot.getCity() == null || spot.getCity().length() < 1 || spot.getState() == null
				|| spot.getState().length() < 1 || spot.getZip() == null || spot.getZip().length() < 1
				|| spot.getDescription() == null || spot.getDescription().length() < 1 || spot.getRateHr() == null
				|| spot.getRateMonthly() == null || spot.getRateMonthly().length() < 1 || spot.getSpotType() == null
				|| spot.getSpotType().length() < 1) {
			return false;
		}else {
			return true;
		}
		
	}
	/**
	 * Book spot with spot ID
	 * @param spot
	 * @return
	 */
	@Transactional
	public Integer bookSpot(Spot spot){
	
	spot.setSpotBooked("true");
	try{
	sessionFactory.getCurrentSession().update(spot);
	}
	catch(Exception e){
		return 0;
	}
	return 1;
	}

}
