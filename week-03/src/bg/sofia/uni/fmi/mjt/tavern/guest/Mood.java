package bg.sofia.uni.fmi.mjt.tavern.guest;

public enum Mood {
    CALM,
    TENSE,
    ANGRY,
    BRAWLING;

    public Mood escalate() {
        return switch (this){
            case CALM -> TENSE;
            case TENSE -> ANGRY;
            case ANGRY -> BRAWLING;
            case BRAWLING -> BRAWLING;
        };
    }

    public Mood calmDown() {
        return switch(this){
            case CALM -> CALM;
            case TENSE -> CALM;
            case ANGRY -> TENSE;
            case BRAWLING -> ANGRY;
        };
    }
}
