package pt.ulisboa.tecnico.bank.dao;

import junit.extensions.TestSetup;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.bank.domain.Notification;
import pt.ulisboa.tecnico.bank.services.NotificationService;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * Author: Aliaksandra Sankova
 * Date: 11/29/13
 * Time: 4:16 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring-application-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class NotificationDAOTest {
    private final Logger logger = Logger.getLogger(NotificationDAOTest.class);

    @Autowired
    private NotificationService notificationService;

    @Test
    @Transactional
    @Rollback(true)
    public void testReadById(){
        Notification notification = generateNotification();
        notificationService.send(notification);
        assertNotNull("Couldn't read mail with id#" + notification.getId(), notificationService.getById(notification.getId()));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdate(){
       Notification notification = generateNotification();
       notificationService.send(notification);
       notification = notificationService.getById(notification.getId());
       notification.setText("Updated text");
       notificationService.updateMail(notification);
       assertEquals("Notification entry hasn't been updated", notificationService.getById(notification.getId()).getText(),
               notification.getText());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete(){
       Notification notification = generateNotification();
       notificationService.send(notification);
       Long id = notification.getId();
       notification = notificationService.getById(notification.getId());
       notificationService.markAsSended(notification);
       assertTrue("Mail hasn't been deleted", notificationService.getById(id).getDeleteFlag());
       assertFalse("Deleted mail is in list of not deleted mails", notificationService.getAll().contains(notification));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testGetAllNotifications(){
        Notification notification1 = generateNotification();
        notificationService.send(notification1);
        Notification notification2 = generateNotification();
        notificationService.send(notification2);
        notificationService.markAsSended(notification2);
        assertTrue("Notification is not in list", notificationService.getAll().contains(notification1));
        assertFalse("Deleted notification is in list",notificationService.getAll().contains(notification2));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCount(){
        int size = notificationService.getAll().size();
        assertEquals("Size of mail list is not equals to dao.size", size + 1, notificationService.getCount() + 1);
    }

    public Notification generateNotification(){
        Notification mail = new Notification();
        mail.setText("Which was generated automatically");
        mail.setTo("sankova@informatics.by");
        mail.setSubject("Important message");
        return mail;
    }
}
