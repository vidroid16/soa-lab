package service;

import dao.Discipline;
import dao.LabWork;

import java.io.InputStream;
import java.util.ArrayList;

public interface LabWorkService {
    public void save(LabWork labWork);

    public void update(InputStream stream);

    public void delete(int id);

    public ArrayList<LabWork> get(int first, int amount, String sortfield);

    public int getAllWhereMinLowerThan(int a);

    public void generateLab(int a);

    public ArrayList<LabWork> getStartedWith(String str);

    public ArrayList<LabWork> getMaxPoint(double a);

    public LabWork deleteWhereMaxHigherThan(float a);

    public int getWithDisciplineLike(Discipline discipline);
}
