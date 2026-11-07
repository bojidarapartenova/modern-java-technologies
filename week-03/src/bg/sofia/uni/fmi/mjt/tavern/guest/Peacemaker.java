package bg.sofia.uni.fmi.mjt.tavern.guest;

public class Peacemaker extends Guest {
    public Peacemaker(String name) {
        super(name);
    }

    @Override
    public int getDrunkThreshold() {
        return 5;
    }

    @Override
    public Mood reactToInsult() {
        return getMood();
    }

    @Override
    public Mood reactToCheat() {
        return getMood();
    }

    @Override
    public Mood interferes(Mood affectedMood) {
        return affectedMood.calmDown();
    }
}
