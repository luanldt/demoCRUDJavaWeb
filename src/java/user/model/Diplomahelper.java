/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.model;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import user.pojo.Userdiploma;

/**
 *
 * @author cod.f
 */
public class Diplomahelper {

    Session session = null;

//    public Userdiplomahelper() {
//        try{
//            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
//        } catch(Exception e) {
//            this.session = HibernateUtil.getSessionFactory().openSession();
//        }
//    }
    public List<Userdiploma> findAll() {
        List<Userdiploma> userdiplomas = null;
        try {
            // Bat dau truy van
            //Transaction tx = session.getTransaction();
            session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("from Userdiploma");
            userdiplomas = (List<Userdiploma>) q.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return userdiplomas;
    }

    public List<Userdiploma> findPagination(int firstResult, int maxResult) {
        List<Userdiploma> userdiplomas = null;
        try {
            // Bat dau truy van
            //Transaction tx = session.getTransaction();
            session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("from Userdiploma");
            q.setMaxResults(maxResult);
            q.setFirstResult(firstResult);
            userdiplomas = (List<Userdiploma>) q.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return userdiplomas;
    }

    public Userdiploma findOne(int id) {
        Userdiploma userdiploma = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            userdiploma = this.getSingle(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return userdiploma;
    }

    public String create(Userdiploma userdiploma) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.getTransaction();
            tx.begin();
            Serializable id = session.save(userdiploma);
            tx.commit();
            return id.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return null;
    }

    public boolean update(Userdiploma userdiploma) {
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
//            Transaction tx = session.getTransaction();
            Transaction tx = session.beginTransaction();
            session.update(userdiploma);
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
            Userdiploma userdiploma = this.getSingle(id);
            session.delete(userdiploma);
            tx.commit();
            result = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    public Userdiploma getSingle(int id) {
        Query q = session.createQuery("from Userdiploma where id = :id");
        return (Userdiploma) q.setInteger("id", id).uniqueResult();
    }

    public List<Userdiploma> query(String keyword) {
        List<Userdiploma> result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("from Userdiploma where tenBangCap like :keyword");
            q.setString("keyword", "%" + keyword + "%");
            result = q.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }
}
