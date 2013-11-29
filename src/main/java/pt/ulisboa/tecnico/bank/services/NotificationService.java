package pt.ulisboa.tecnico.bank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.bank.dao.NotificationDAO;
import pt.ulisboa.tecnico.bank.domain.Notification;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: Aliaksandra Sankova
 * Date: 11/29/13
 * Time: 4:19 AM
 */
@Service("notificationService")
public class NotificationService {

    @Autowired
    private NotificationDAO notificationDAO;

    @Transactional
    public Notification getById(Long id){
        return notificationDAO.read(id);
    }

    @Transactional
    public List<Notification> getAll(){
        return notificationDAO.list();
    }

    @Transactional
    public Long getCount() {
        return notificationDAO.size();
    }

    @Transactional
    public Long send(Notification notification) {
        return notificationDAO.create(notification);
    }

    @Transactional
    public void updateMail(Notification notification) {
        notificationDAO.update(notification);
    }

    @Transactional
    public void markAsSended(Notification notification) {
        notificationDAO.delete(notification);
    }

    @Transactional
    public List<Notification> getMails(int first, int count) {
        return notificationDAO.list(first, count);
    }
}
