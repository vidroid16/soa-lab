package repository;

import dao.Discipline;
import dao.LabWork;

import java.math.BigInteger;
import java.util.ArrayList;

public interface LabWorkRepo {
    public void save(LabWork labWork);

    public void update(LabWork labWork);

    public void delete(int id);

    public int getAllWhereMinLowerThan(int a);

    public ArrayList<LabWork> getAllWhereMaxHigherThan(float a);

    public ArrayList<LabWork> get(int first, int amount, String sortfield);

    public int amount();

    public ArrayList<LabWork> getStartedWith(String str);

    public ArrayList<LabWork> getMaxPoint(double a);

    public int getWithDisciplineLike(Discipline discipline);
}
