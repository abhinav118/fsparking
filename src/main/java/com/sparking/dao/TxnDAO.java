package com.sparking.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sparking.hibernate.Txn;
import com.sparking.hibernate.User;


@Repository("txnDAO")
public class TxnDAO {


	@Autowired
	private SessionFactory txnSessionFactory;
	
	private static final Logger log = Logger.getLogger(TxnDAO.class);
	
	@Transactional
	public Integer saveTxn(Txn txn) {
			
			Integer txnId = null;
			SpotDAO spotDAO=new SpotDAO();
			boolean userCanBook;
			try{
						
				txnId = (Integer) txnSessionFactory.getCurrentSession().save(txn);
				log.debug("Booking spot");
				
			}catch (Exception e) {
				log.debug("Exception occured while saving spot");
				e.printStackTrace();
			}
			
			return txnId;
	}
	
}
