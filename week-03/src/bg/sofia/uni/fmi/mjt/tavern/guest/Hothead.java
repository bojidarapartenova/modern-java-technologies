package bg.sofia.uni.fmi.mjt.tavern.guest;

public class Hothead extends Guest{
    public Hothead(String name) {
        super(name);
    }

    @Override
    public int getDrunkThreshold() {
        return 4;
    }

    @Override
    public Mood reactToInsult() {
        return Mood.BRAWLING;
    }

    @Override
    public Mood reactToCheat() {
        return getMood().escalate().escalate();
    }

    @Override
    public Mood interferes(Mood affectedMood) {
        return affectedMood.escalate();
    }
}
