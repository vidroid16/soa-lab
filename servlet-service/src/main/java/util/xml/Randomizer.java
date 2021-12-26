package util.xml;

public class Randomizer {
    public int randomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public String randomString(String... strings) {
        int index = randomInt(1, strings.length);
        return strings[index - 1];
    }

    public double randomDouble(int min, int max) {
        return (double) ((Math.random() * (max - min)) + min);
    }

    public float randomFloat(int min, int max) {
        return (float) ((Math.random() * (max - min)) + min);
    }

    public Long randomLong(int min, int max) {
        return (long) ((Math.random() * (max - min)) + min);
    }
}
