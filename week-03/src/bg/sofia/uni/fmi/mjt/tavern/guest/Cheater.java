package bg.sofia.uni.fmi.mjt.tavern.guest;

public class Cheater extends Guest {
    public Cheater(String name) {
        super(name);
    }

    @Override
    public int getDrunkThreshold() {
        return 3;
    }

    @Override
    public Mood reactToInsult() {
        return getMood().escalate();
    }

    @Override
    public Mood reactToCheat() {
        return getMood().calmDown();
    }

    @Override
    public Mood interferes(Mood affectedMood) {
        return affectedMood;
    }
}
