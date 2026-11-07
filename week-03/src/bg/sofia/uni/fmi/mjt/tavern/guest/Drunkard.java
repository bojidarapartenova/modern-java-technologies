package bg.sofia.uni.fmi.mjt.tavern.guest;

public class Drunkard extends Guest{
    public Drunkard(String name) {
        super(name);
    }

    @Override
    public int getDrunkThreshold() {
        return 2;
    }

    @Override
    public Mood reactToInsult() {
        return getMood().escalate();
    }

    @Override
    public Mood reactToCheat() {
        return getMood();
    }

    @Override
    public Mood interferes(Mood affectedMood) {
        return affectedMood;
    }
}
