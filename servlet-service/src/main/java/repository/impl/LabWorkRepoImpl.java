package repository.impl;

import dao.Discipline;
import dao.LabWork;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import repository.LabWorkRepo;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class LabWorkRepoImpl implements LabWorkRepo {
    private final SessionFactory sessionFactory;

    public LabWorkRepoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(LabWork labWork){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(labWork);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            throw e;
        }
    }

    @Override
    public void update(LabWork labWork) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(labWork);
            transaction.commit();
            session.close();
        }catch (Exception e){
            transaction.rollback();
            session.close();
            throw e;
        }
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            LabWork lab = (LabWork) session.load(LabWork.class,id);
            session.delete(lab);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            throw e;
        }
    }

    @Override
    public ArrayList<LabWork> get(int first, int amount, String sortfield) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<LabWork> labs = new ArrayList<>();
        if (sortfield.equals("x")||sortfield.equals("y")) sortfield = "co."+sortfield;
        if(sortfield.equals("lecture_hours")) sortfield = "di."+sortfield;
        if(sortfield.equals("dname")) sortfield = "di.name";
        try {
            if(amount > 0){
                if(sortfield.startsWith("co.")){
                    labs = (ArrayList<LabWork>) session.createSQLQuery(
                            "SELECT * FROM labwork JOIN coordinates AS co on labwork.coordinates_id = co.coordinates_id WHERE labwork.lab_id > "+first+" ORDER BY "+sortfield+" LIMIT "+amount+";"
                    ).addEntity(LabWork.class).list();
                }
                else if(sortfield.startsWith("di.")){
                    labs = (ArrayList<LabWork>) session.createSQLQuery(
                            "SELECT * FROM labwork JOIN discipline AS di on labwork.discipline_id = di.discipline_id WHERE labwork.lab_id > "+first+" ORDER BY "+sortfield+" LIMIT "+amount+";"
                    ).addEntity(LabWork.class).list();
                }
                else {
                    labs = (ArrayList<LabWork>) session.createSQLQuery(
                            "SELECT * FROM labwork WHERE lab_id > "+first+" ORDER BY "+sortfield+" LIMIT "+amount+";"
                    ).addEntity(LabWork.class).list();
                }
            }
            if (amount<0){
                if(sortfield.startsWith("co.")){
                    labs = (ArrayList<LabWork>) session.createSQLQuery(
                            "SELECT * FROM labwork JOIN coordinates AS co on labwork.coordinates_id = co.coordinates_id WHERE labwork.lab_id < "+first+" ORDER BY "+sortfield+" DESC LIMIT "+Math.abs(amount)+";"
                    ).addEntity(LabWork.class).list();
                }
                else if(sortfield.startsWith("di.")){
                    labs = (ArrayList<LabWork>) session.createSQLQuery(
                            "SELECT * FROM labwork JOIN discipline AS di on labwork.discipline_id = di.discipline_id WHERE labwork.lab_id < "+first+" ORDER BY "+sortfield+" DESC LIMIT "+Math.abs(amount)+";"
                    ).addEntity(LabWork.class).list();
                }
                else {
                    labs = (ArrayList<LabWork>) session.createSQLQuery(
                            "SELECT * FROM labwork WHERE lab_id < " + first + "ORDER BY " + sortfield + " DESC LIMIT " + Math.abs(amount) + ";"
                    ).addEntity(LabWork.class).list();
                }
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
        return labs;
    }
    @Override
    public ArrayList<LabWork> getStartedWith(String str){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<LabWork> labs = new ArrayList<>();
        try {
                labs = (ArrayList<LabWork>) session.createSQLQuery(
                        "select * from labwork where name like '"+str+"%'").addEntity(LabWork.class).list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
        return labs;
    }

    @Override
    public ArrayList<LabWork> getMaxPoint(double a) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<LabWork> labs = new ArrayList<>();
        try {
            labs = (ArrayList<LabWork>) session.createSQLQuery(
                    "select * from labwork where maximal_point <"+a+";");
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
        return labs;
    }

    @Override
    public int getWithDisciplineLike(Discipline discipline) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int res = 0;
        try {

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
        return res;
    }

    @Override
    public Long amount() {
        Session session = sessionFactory.openSession();
        Long count = 0L;

        try {
            Query query = session.createSQLQuery("Select count(*) from labwork");

            final List<BigInteger> obj = query.list();
            for (BigInteger l : obj) {
                if (l != null) {
                    count = l.longValue();
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return count;
    }
}
