package com.sparking.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sparking.hibernate.Spot;
import com.sparking.hibernate.User;


@Repository("userDAO")
public class UserDAO {


	@Autowired
	private SessionFactory userSessionFactory;
	
	private static final Logger log = Logger.getLogger(UserDAO.class);
	
	@Transactional
	public Integer saveUser(User user) {
			
			Integer userId = null;
			SpotDAO spotDAO=new SpotDAO();
			boolean userCanBook;
			try{
						
				userId = (Integer) userSessionFactory.getCurrentSession().save(user);
				log.debug("Saving user with id"+userId);
//				userCanBook=spotDAO.bookSpot(Integer.parseInt(user.getSpotId()));
//				if(!userCanBook)
//					return null;
				
			}catch (Exception e) {
				log.debug("Exception occured while saving spot");
				e.printStackTrace();
			}
			
			return userId;
	}
	
	
	@SuppressWarnings("finally")
	@Transactional
	public User getUserById(Integer id) {
		User user = null;
		try{
			if(id == null) {
				log.info("User id is null");
				return null;
			}else {
				log.info("Getting user from DB for user id: "+id);
				user = (User) userSessionFactory.getCurrentSession().get(User.class, id);
//				String hql = "FROM Spot s WHERE s.id = :id";
			}
			
		}catch (Exception e) {
			log.debug("Exception occured while retrieving user with user id: "+ id);
			e.printStackTrace();
		}finally {
			return user;
		}
		
	}
	
	
	@SuppressWarnings("finally")
	@Transactional
	public User getUserByFbId(String fbId) {
		User user = null;
		List<Object> list = null;
		try{
			if(fbId == null) {
				log.info("fb id is null");
				return null;
			}else {
				String hql = "FROM User u WHERE u.fbId=:fbId)";
				list =  userSessionFactory.getCurrentSession().createQuery(hql).setString("fbId", fbId).list();
				if(list == null || list.isEmpty())
					return null;
				user = (User) list.get(0);
				log.info("Getting user from DB for fb id: "+fbId);
			}
		}catch (Exception e) {
			log.debug("Exception occured while retrieving user with fb id: "+ fbId);
			e.printStackTrace();
		}finally {
			return user;
		}
		
	}
	
	@SuppressWarnings("finally")
	@Transactional
	public User getUserByEmail(String email) {
		User user = null;
		try{
			if(email == null) {
				log.info("User email is null");
				return null;
			}else {
				log.info("Getting user from DB for user email: "+email);
				user = (User) userSessionFactory.getCurrentSession().get(User.class, email);
//				String hql = "FROM Spot s WHERE s.email = :email";
			}
			
		}catch (Exception e) {
			log.debug("Exception occured while retrieving user with user email: "+ email);
			e.printStackTrace();
		}finally {
			return user;
		}
		
	}
	
	
	@SuppressWarnings("finally")
	@Transactional
	public Integer updateUser( User u2) {
		User user = u2;
		try{
			if(u2 == null) {
				log.info("User  is null");
				return null;
			}else {
				log.info("Updatting user from DB for user id: "+u2.getId());
				userSessionFactory.getCurrentSession().update(u2);
				
				//				String hql = "FROM Spot s WHERE s.id = :id";
			}
			
		}catch (Exception e) {
			log.debug("Exception occured while retrieving user with user");
			e.printStackTrace();
		}finally {
			return user.getId();
		}
		
	}
	
	
	
	
	
	
	
}
