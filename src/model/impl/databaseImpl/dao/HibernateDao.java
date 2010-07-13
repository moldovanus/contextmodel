package model.impl.databaseImpl.dao;

import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 13, 2010
 * Time: 10:50:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateDao {
    public static void persist(Object o) {
        Session session = HibernateUtil.getSession();
        session.saveOrUpdate(o);
        session.flush();
    }
}
