package daoPhysical;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.*;
import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class DaoCreditCards extends Dao {
	private static final Logger LOGGER = LogManager.getLogger();
	
	public DaoCreditCards() throws JDBCConnectionException {
		super();
	}
	
	public CreditCard readCreditCard(int id) throws DAOException {
		LOGGER.info("getting credit card");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<CreditCard> cq = cb.createQuery(CreditCard.class);
			Root<CreditCard> creditCard = cq.from(CreditCard.class);
			cq.where(cb.equal(creditCard.get(CreditCard_.cardId), id));
			cq.select(creditCard);
			TypedQuery<CreditCard> q = em.createQuery(cq);
			return q.getSingleResult();
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public void deleteCreditCard(int id) throws DAOException {
		LOGGER.info("deleting credit card");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaDelete<CreditCard> cq = cb.createCriteriaDelete(CreditCard.class);
			Root<CreditCard> creditCard = cq.from(CreditCard.class);
			cq.where(cb.equal(creditCard.get(CreditCard_.cardId), id));
			assert em.createQuery(cq).executeUpdate() == 1 : "deleted more than 1 object";
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public void createCreditCard(CreditCard creditCard) throws DAOException {
		LOGGER.info("creating credit card");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			em.persist(creditCard);
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public void updateCreditCard(CreditCard creditCard) throws DAOException {
		LOGGER.info("updating credit card");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			em.merge(creditCard);
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public void makePayment(CreditCard CreditCard, int to_account_id, double amount) throws DAOException, JDBCConnectionException {
		DaoAccounts da = new DaoAccounts();
		DaoPayments dp = new DaoPayments();
		Payment p = new Payment(
				CreditCard.getAccount(), da.readAccount(to_account_id), BigDecimal.valueOf(amount)
			);
		
		dp.createPayment(p);
	}
	
	public void deactivateCard(CreditCard creditCard) throws DAOException {
		creditCard.setActive(false);
		updateCreditCard(creditCard);
	}


}
