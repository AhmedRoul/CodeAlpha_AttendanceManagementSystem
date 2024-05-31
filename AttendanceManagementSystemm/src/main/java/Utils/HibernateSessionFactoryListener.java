package Utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.jboss.logging.Logger;


public class HibernateSessionFactoryListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(HibernateSessionFactoryListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SessionFactory sessionFactory = (SessionFactory) servletContextEvent.getServletContext().getAttribute("SessionFactory");
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (Exception e) {
                logger.error("Error closing session factory", e);
            } finally {
                servletContextEvent.getServletContext().removeAttribute("SessionFactory");
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

            SessionFactory sessionFactory =
                    configuration.buildSessionFactory(serviceRegistry);
            servletContextEvent.getServletContext().setAttribute("SessionFactory", sessionFactory);
            logger.info("Hibernate SessionFactory Configured successfully");
        } catch (Exception e) {
            logger.error("Error creating session factory", e);
        }
    }
}