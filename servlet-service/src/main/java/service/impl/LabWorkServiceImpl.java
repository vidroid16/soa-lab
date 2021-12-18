package service.impl;

import dao.Coordinates;
import dao.Discipline;
import dao.LabWork;
import dao.enums.Difficulty;
import org.hibernate.SessionFactory;
import repository.impl.LabWorkRepoImpl;
import service.LabWorkService;
import util.xml.Randomizer;
import util.xml.XMLParser;

import java.io.InputStream;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class LabWorkServiceImpl implements LabWorkService {
    LabWorkRepoImpl labWorkRepo;

    public LabWorkServiceImpl(SessionFactory sessionFactory) {
        labWorkRepo = new LabWorkRepoImpl(sessionFactory);
    }

    @Override
    public void save(LabWork labWork) {
        //check all not null
        labWorkRepo.save(labWork);
        //check discipline for

    }

    @Override
    public void update(InputStream stream) {
        XMLParser<LabWork> xmlParser = new XMLParser<>(LabWork.class);
        LabWork labWork = xmlParser.xmlToObject(stream);
        labWorkRepo.update(labWork);
    }

    @Override
    public void delete(int id) {
        labWorkRepo.delete(id);
    }

    @Override
    public ArrayList<LabWork> get(int first, int amount, String sortfield) {
        return labWorkRepo.get(first,amount,sortfield);
    }

    @Override
    public void generateLab(int a){
        Randomizer r = new Randomizer();
        LabWork lab = new LabWork();
        String dif ="";
        for (int i = 0; i < a; i++) {
            lab.setAveragePoint(r.randomLong(4,7));
            lab.setCoordinates(new Coordinates(r.randomLong(1,100), r.randomInt(1,100)));
            lab.setCreationDate(ZonedDateTime.now());
            dif = r.randomString("VERY_EASY","NORMAL","VERY_HARD","INSANE","TERRIBLE");
            lab.setDifficulty(Difficulty.valueOf(dif));
            lab.setDifficultyName(dif);
            lab.setMaximumPoint(r.randomFloat(7,10));
            lab.setMinimalPoint(r.randomInt(1,4));
            lab.setDiscipline(new Discipline(r.randomString("SOA","MSPI","Testing","Math","PIP","BLPS"),r.randomInt(100,200)));
            lab.setName(lab.getDiscipline().getName()+r.randomInt(1,6));
            labWorkRepo.save(lab);
        }
    }

    public Long amount() {
        return labWorkRepo.amount();
    }

    @Override
    public ArrayList<LabWork> getStartedWith(String str) {
        return labWorkRepo.getStartedWith(str);
    }

    @Override
    public ArrayList<LabWork> getMaxPoint(double a) {
        return labWorkRepo.getMaxPoint(a);
    }

    @Override
    public int getWithDisciplineLike(Discipline discipline) {
        return 0;
    }
}
