package pt.ulisboa.tecnico.bank.cron;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.quartz.QuartzJobBean;
import pt.ulisboa.tecnico.bank.domain.Notification;
import pt.ulisboa.tecnico.bank.services.NotificationService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: Aliaksandra Sankova
 * Date: 11/29/13
 * Time: 11:56 AM
 */
public class NotificationJob extends QuartzJobBean {
    private final Logger log = Logger.getLogger(this.getClass());

    private NotificationService notificationService;

    private Integer notificationsToSend;

    private JavaMailSender mailSender;

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public Integer getNotificationsToSend() {
        return notificationsToSend;
    }

    public void seNotificationsToSend(Integer mailsToSend) {
        this.notificationsToSend = mailsToSend;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.debug("Executing notification job");
        List<Notification> notifications;
        if(notificationsToSend == null || notificationsToSend < 0){
            notifications = notificationService.getAll();
        } else if(notificationsToSend == 0){
            return;
        } else {
            notifications = notificationService.getMails(0, notificationsToSend);
        }
        for(Notification notification: notifications){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notification.getTo());
            message.setSubject(notification.getSubject());
            message.setText(notification.getText());

            mailSender.send(message);

            notificationService.markAsSended(notification);
            log.debug("Mail successfully sent to " + message.getTo());
        }
    }
}
