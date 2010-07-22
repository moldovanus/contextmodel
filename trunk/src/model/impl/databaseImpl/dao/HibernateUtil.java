package model.impl.databaseImpl.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 12, 2010
 * Time: 1:27:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateUtil {
    public static final String HIBERNATE_CONFIG_FILE = "model/impl/databaseImpl/dao/hibernate.cfg.xml";
    private static SessionFactory factory;
    private static Configuration configuration;

    static {
        configuration = new Configuration();
        configuration.configure(HIBERNATE_CONFIG_FILE);
        factory = configuration.buildSessionFactory();
    }

    public static Configuration getInitializedConfiguration() {
        return configuration;
    }

    public static Session getSession() {
        Session session = factory.getCurrentSession();
        if (!session.isOpen()) {
            session = factory.openSession();
        }
        return session;
    }

    public static void closeSession() {
        HibernateUtil.getSession().close();
    }

    public static void recreateDatabase() {
        Configuration config;
        config = HibernateUtil.getInitializedConfiguration();
        SchemaExport schemaExport = new SchemaExport(config);
        schemaExport.create(true, true);
    }

    public static Session beginTransaction() {
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSession();
        hibernateSession.beginTransaction();
        return hibernateSession;
    }

    public static void commitTransaction() {
        HibernateUtil.getSession()
                .getTransaction().commit();
    }

    public static void rollbackTransaction() {
        HibernateUtil.getSession()
                .getTransaction().rollback();
    }

}
