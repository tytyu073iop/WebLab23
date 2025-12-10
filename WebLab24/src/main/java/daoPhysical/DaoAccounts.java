package daoPhysical;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.*;
import entities.*;

public class DaoAccounts extends Dao {
	private static final Logger LOGGER = LogManager.getLogger();
	String ReadAccountsqlRequest = "SELECT account_id, client_id, balance, is_active, created_at FROM accounts WHERE account_id= ?";
	
	public DaoAccounts() throws JDBCConnectionException {
		super();
	}
	
	public Account readAccount(int id) throws DAOException {
		LOGGER.info("Getting account");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Account> cq = cb.createQuery(Account.class);
			Root<Account> account = cq.from(Account.class);
			cq.where(cb.equal(account.get(Account_.accountId), id));
			cq.select(account);
			TypedQuery<Account> q = em.createQuery(cq);
			return q.getSingleResult();
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} catch (NoResultException e)  {
			throw new DAOException("No results", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public void deleteAccount(int id) throws DAOException {
		LOGGER.info("Deleting account");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaDelete<Account> cq = cb.createCriteriaDelete(Account.class);
			Root<Account> account = cq.from(Account.class);
			cq.where(cb.equal(account.get(Account_.accountId), id));
			assert em.createQuery(cq).executeUpdate() == 1 : "deleted more than 1 object";
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public void createAccount(Account account) throws DAOException {
		LOGGER.info("Creating account");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			em.persist(account);
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public void updateAccount(Account account) throws DAOException {
		LOGGER.info("Updating account");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			em.merge(account);
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public List<Account> getClientAccounts(int client_id) throws DAOException {
		LOGGER.info("Getting client accounts");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Account> cq = cb.createQuery(Account.class);
			Root<Account> account = cq.from(Account.class);
			cq.where(cb.equal(account.get(Account_.client).get(Client_.clientId), client_id));
			cq.select(account);
			TypedQuery<Account> q = em.createQuery(cq);
			return q.getResultList();
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
}
