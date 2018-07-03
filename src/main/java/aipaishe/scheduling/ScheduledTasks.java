package aipaishe.scheduling;

import aipaishe.models.Event;
import aipaishe.models.LinkEventUser;
import aipaishe.models.User;
import aipaishe.services.EmailSender;
import aipaishe.services.SmsSender;
import aipaishe.services.repositories.EventDao;
import aipaishe.services.repositories.LinkEventUserDao;
import aipaishe.services.repositories.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(fixedRate = 43200000)
    public void checkOutstandingReminder() {
        log.info("[Schedule Task] checkOutstandingReminder started.");

        //Current Date
        LocalDate todayLocalDate = LocalDate.now();
        log.info("Current Date=" + todayLocalDate);

        //Creating LocalDate by providing input arguments
        LocalDate firstDay_2018 = LocalDate.of(2018, 1, 1);
        log.info("Specific Date=" + firstDay_2018);

        //Try creating date by providing invalid inputs
        //LocalDate feb29_2014 = LocalDate.of(2014, Month.FEBRUARY, 29);
        //Exception in thread "main" java.time.DateTimeException:
        //Invalid date 'February 29' as '2014' is not a leap year

        //Current date in "Asia/HongKong", you can get it from ZoneId javadoc
        LocalDate todayUS = LocalDate.now(ZoneId.of("US/Pacific"));
        log.info("Current Date in US Pacific=" + todayUS);

        try {
            // Convert LocalDate to java.util.date
            Date refDate = Date.from(todayLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            log.info("Reference date for checking: " + dateFormat.format(refDate));
            List<Event> eventList = eventDao.getEventListByDate(refDate);
            if (eventList != null && eventList.size() > 0) {
                log.info("Number of events found on the specific date: " + eventList.size());
                if (eventList.size() > 0) {
                    for (Event event : eventList) {
                        List<LinkEventUser> listLink = linkEventUserDao.getListByEventId(event.getEventId());
                        if (listLink != null && listLink.size() > 0) {
                            log.info(listLink.size() + " participants found for Event ID " + event.getEventId());
                            for (LinkEventUser link : listLink) {
                                if (!link.isEmailReminderSent()) {
                                    sendEmailReminder(link.getUserId(), link.getEventId());
                                    link.setEmailReminderSent(true);
                                    linkEventUserDao.update(link);
                                    log.info("==========Email Reminder Completed==========");
                                }
                                if (!link.isSmsReminderSent()) {
                                    sendSmsReminder(link.getUserId(), link.getEventId());
                                    link.setSmsReminderSent(true);
                                    linkEventUserDao.update(link);
                                    log.info("==========SMS Reminder Completed==========");
                                }
                            }
                        }
                    }
                }
            } else {
                log.info("No event can be found for reference date: " + todayLocalDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error encountered: " + e.getMessage());
        }
    }

    private void sendEmailReminder(long userId, long eventId) throws Exception {
        User targetUser = userDao.getById(userId);
        Event targetEvent = eventDao.getById(eventId);
        if (targetUser != null && !targetUser.getEmail().isEmpty()) {
            log.info("Sending reminder email to address:" + targetUser.getEmail());

            try {
                emailSender.sendReminderEmail(targetUser, targetEvent);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Send email reminder error: " + e.getMessage());
                throw e;
            }

        } else {
            log.info("Email cannot be found for user ID " + userId);
        }
    }

    private void sendSmsReminder(long userId, long eventId) throws Exception {
        User targetUser = userDao.getById(userId);
        Event targetEvent = eventDao.getById(eventId);
        if (targetUser != null && !targetUser.getPhoneNo().isEmpty()) {
            log.info("Sending reminder SMS to phone no.:" + targetUser.getPhoneNo());

            try {
                smsSender.sendReminderSms(targetUser, targetEvent);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Send SMS reminder error: " + e.getMessage());
                throw e;
            }
        } else {
            log.info("Phone number cannot be found for user ID " + userId);
        }
    }

    // Wire the UserDao used inside this controller.
    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LinkEventUserDao linkEventUserDao;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private SmsSender smsSender;
}