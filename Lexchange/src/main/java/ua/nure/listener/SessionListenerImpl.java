package ua.nure.listener;

import ua.nure.model.AppUser;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

public class SessionListenerImpl implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        List<Long> online = (List<Long>) event.getSession().getServletContext().getAttribute("online");
        AppUser user = (AppUser) event.getSession().getAttribute("user");
        if(user != null) {
            online.remove(user.getId());
        }
    }
}