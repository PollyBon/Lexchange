package ua.nure.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ua.nure.model.Message;
import ua.nure.model.bean.AppUserChatBean;
import ua.nure.model.enumerated.Age;
import ua.nure.model.enumerated.Country;
import ua.nure.model.enumerated.Language;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.ArrayList;
import java.util.HashMap;

public class AppInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext container) throws ServletException {

        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(AppConfig.class);
        setupContext(container);
        ctx.setServletContext(container);

        ServletRegistration.Dynamic servlet = container.addServlet(
                "dispatcher", new DispatcherServlet(ctx));

        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
        servlet.setAsyncSupported(true);
    }

    private void setupContext(ServletContext context) {
        context.setAttribute("countries", Country.values());
        context.setAttribute("languages", Language.values());
        context.setAttribute("ages", Age.values());

        context.setAttribute("chatUserMessages", new HashMap<AppUserChatBean, ArrayList<Message>>());

        context.setAttribute("online", new ArrayList<Long>());
    }
}
