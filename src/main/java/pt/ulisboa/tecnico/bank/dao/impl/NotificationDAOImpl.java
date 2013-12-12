package pt.ulisboa.tecnico.bank.dao.impl;

import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.bank.dao.NotificationDAO;
import pt.ulisboa.tecnico.bank.domain.Notification;

/**
 * Created with IntelliJ IDEA.
 * Author: Aliaksandra Sankova
 * Date: 11/29/13
 * Time: 3:57 AM
 */
@Repository
public class NotificationDAOImpl extends  HibernateDAO<Notification> implements NotificationDAO {
    public NotificationDAOImpl() {
        super(Notification.class);
    }
}
