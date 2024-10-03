package org.apicode.learnspringrest.hibernate;

import org.apicode.learnspringrest.api.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final Session session;

    static {
        try {
            session = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class).buildSessionFactory().openSession();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        return session;
    }
}