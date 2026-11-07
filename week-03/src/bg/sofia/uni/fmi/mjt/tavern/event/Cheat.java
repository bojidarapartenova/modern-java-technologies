package bg.sofia.uni.fmi.mjt.tavern.event;

public final class Cheat implements Event{
    private final String cheater;
    private final String victim;

    public Cheat(String cheater, String victim){
        if(cheater==null || cheater.isBlank()){
            throw new IllegalArgumentException("name cannot be null or blank");
        }

        if(victim==null || victim.isBlank()){
            throw new IllegalArgumentException("name cannot be null or blank");
        }

        if(cheater.equals(victim)){
            throw new IllegalArgumentException("names must be different");
        }

        this.cheater=cheater;
        this.victim=victim;
    }

    public String cheater() {
        return cheater;
    }

    public String victim() {
        return victim;
    }
}
