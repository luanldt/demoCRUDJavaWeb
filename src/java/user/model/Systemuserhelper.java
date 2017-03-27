/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.model;

import org.hibernate.Session;
import user.pojo.*;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author cod.f
 */
public class Systemuserhelper {

    Session session = null;

//    public Systemuserhelper() {
//        try{
//            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
//        } catch(Exception e) {
//            this.session = HibernateUtil.getSessionFactory().openSession();
//        }
//    }

    public List<Systemuser> findAll() {
        List<Systemuser> systemusers = null;
        try {
            // Bat dau truy van
            //Transaction tx = session.getTransaction();
            session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("from Systemuser");
            systemusers = (List<Systemuser>) q.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally{
            session.close();
        }

        return systemusers;
    }
}
