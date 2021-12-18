package repository.impl;

import dao.Discipline;
import dao.LabWork;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.DisciplineRepo;

public class DisciplineRepoImpl implements DisciplineRepo {
    private final SessionFactory sessionFactory;

    public DisciplineRepoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Discipline discipline){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(discipline);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            throw e;
        }
    }
}
