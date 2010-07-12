package main;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 11:04:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String args[]) {
        Configuration config =
                new Configuration();
        config.configure("/utils/databaseAccess/hibernate.cfg.xml");
        // SessionFactory sessionFactory = config.buildSessionFactory();

        SchemaExport export = new SchemaExport(config);
        export.drop(false, true);
        export.create(false, true);
        List exceptions = export.getExceptions();
        for (Object o : exceptions) {
            System.out.println(o.toString());
        }


    }
}
