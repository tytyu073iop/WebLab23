package daoPhysical;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.time.ZoneId;
import java.util.Date;
import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.*;
import entities.*;

public class DaoPayments extends Dao {
	private static final Logger LOGGER = LogManager.getLogger();
	
	public DaoPayments() throws JDBCConnectionException {
		super();
	}
	
	public Payment readPayment(int id) throws DAOException {
		LOGGER.info("reading payment");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Payment> cq = cb.createQuery(Payment.class);
			Root<Payment> payment = cq.from(Payment.class);
			cq.where(cb.equal(payment.get(Payment_.paymentId), id));
			cq.select(payment);
			TypedQuery<Payment> q = em.createQuery(cq);
			return q.getSingleResult();
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public void deletePayment(int id) throws DAOException {
		LOGGER.info("deleting payment");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaDelete<Payment> cq = cb.createCriteriaDelete(Payment.class);
			Root<Payment> payment = cq.from(Payment.class);
			cq.where(cb.equal(payment.get(Payment_.paymentId), id));
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			assert em.createQuery(cq).executeUpdate() == 1 : "deleted more than 1 object";
			tx.commit();
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public void createPayment(Payment payment) throws DAOException {
		LOGGER.info("creating payment");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(payment);
			tx.commit();
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public void updatePayment(Payment payment) throws DAOException {
		LOGGER.info("updating payment");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.merge(payment);
			tx.commit();
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public Double getClientPayments(int client_id, Date from, Date to) throws DAOException {
		LOGGER.info("getting client payments sum");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
			Root<Payment> payment = cq.from(Payment.class);
			Join<Payment, Account> accountJoin = payment.join(Payment_.fromAccount);
			Predicate clientPredicate = cb.equal(accountJoin.get(Account_.client).get(Client_.clientId), client_id);
	        // 3. Fix: Get date from PAYMENT root, not from ACCOUNT join
	        Predicate datePredicate = cb.between(payment.get(Payment_.paymentDate), from.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), to.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
	        cq.where(cb.and(clientPredicate, datePredicate));
	        cq.select(cb.sum(payment.get(Payment_.amount)));
	        
	        TypedQuery<BigDecimal> q = em.createQuery(cq);
	        BigDecimal result = q.getSingleResult();
	        return result.doubleValue();
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}

}
