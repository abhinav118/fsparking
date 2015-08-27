package com.sparking.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sparking.exception.SpotAlreadyBookedException;
import com.sparking.hibernate.User;

@Repository("userDAO")
public class UserDAO {

	@Autowired
	private SessionFactory userSessionFactory;

	private static final Logger log = Logger.getLogger(UserDAO.class);

	@Transactional
	public Integer saveUser(User user)  throws Exception{

		Integer userId = null;
//		try {
			
			//Making sure one user can book only one spot. But what if user comes again after a few days? Is entry deleted from db after user leaves?

			User exisingUser = (User) userSessionFactory.getCurrentSession().get(User.class, user.getVlicense());
			if (exisingUser == null) {
				userId = (Integer) userSessionFactory.getCurrentSession().save(user);
				log.debug("Booking spot");
			}else{
				throw new SpotAlreadyBookedException("Spot already booked by user");
			}

			// userCanBook=spotDAO.bookSpot(Integer.parseInt(user.getSpotId()));
			// if(!userCanBook)
			// return null;

//		} catch (Exception e) {
//			log.debug("Exception occured while saving spot");
//			e.printStackTrace();
//		}

		return userId;
	}

}
