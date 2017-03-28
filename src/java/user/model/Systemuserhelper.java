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
    
    public Systemuser findOne(int id) {
        Systemuser systemuser = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("from Systemuser where id = :id");
            systemuser = (Systemuser) q.setInteger("id", id).list().get(0);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return systemuser;
    }
    
    public boolean create(Systemuser systemuser) {
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.getTransaction();
            tx.begin();
            session.saveOrUpdate(systemuser);
            tx.commit();
            result = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }
    
    public boolean update(Systemuser systemuser) {
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
//            Transaction tx = session.getTransaction();
            Transaction tx = session.beginTransaction();
            session.update(systemuser);
            tx.commit();
            result = true;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        
        return result;
    }
    
    public boolean delete(int id) {
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
//            Transaction tx = session.getTransaction();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Systemuser where id = :id");
            Systemuser systemuser = (Systemuser) q.setInteger("id", id).list().get(0);
            session.delete(systemuser);
            tx.commit();
            result = true;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }
}
