package daoPhysical;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.*;
import entities.*;

public class DaoClients extends Dao {	
	private static final Logger LOGGER = LogManager.getLogger();
	public DaoClients() throws JDBCConnectionException {
		super();
	}
	
	public Client readClient(int id) throws DAOException {
		LOGGER.info("getting client");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Client> cq = cb.createQuery(Client.class);
			Root<Client> client = cq.from(Client.class);
			cq.where(cb.equal(client.get(Client_.clientId), id));
			cq.select(client);
			TypedQuery<Client> q = em.createQuery(cq);
			return q.getSingleResult();
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public void deletePayment(int id) throws DAOException {
		LOGGER.info("deleting client");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaDelete<Client> cq = cb.createCriteriaDelete(Client.class);
			Root<Client> client = cq.from(Client.class);
			cq.where(cb.equal(client.get(Client_.clientId), id));
			assert em.createQuery(cq).executeUpdate() == 1 : "deleted more than 1 object";
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public void createClient(Client client) throws DAOException {
		LOGGER.info("creating client");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			em.persist(client);
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}
	
	public void updateClient(Client client) throws DAOException {
		LOGGER.info("updating client");
		EntityManager em = null;
		try {
			em = cnr.getEntityManager();
			em.merge(client);
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(em);
		}
	}

}
