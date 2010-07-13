package main;

import model.impl.databaseImpl.dao.HibernateUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 11:04:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String args[]) {
        HibernateUtil.recreateDatabase();

    }
}
