package bg.sofia.uni.fmi.mjt.tavern.guest;

public abstract class Guest {
    private final String name;
    private Mood mood;
    private int tab;
    private int rakiasDrunk;

    public Guest(String name) {
        if(name==null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be null or blank");
        }

        this.name = name;
        this.mood=Mood.CALM;
    }

    public String getName(){
        return name;
    }

    public Mood getMood(){
        return mood;
    }

    public int getTab(){
        return tab;
    }

    public int getRakiasDrunk(){
        return rakiasDrunk;
    }

    public void drink(int rakias, int rakiaPrice){
        rakiasDrunk+=rakias;
        tab+=rakias*rakiaPrice;
    }

    public void pay(int amount){
        tab-=amount;
        if(tab<0){
            tab=0;
        }
    }

    public void reset(){
        mood=Mood.CALM;
        tab=0;
        rakiasDrunk=0;
    }

    public abstract int getDrunkThreshold();
    public abstract Mood reactToInsult();
    public abstract Mood reactToCheat();
    public abstract Mood interferes(Mood affectedMood);

    public boolean isDrunk(){
        return getRakiasDrunk()>=getDrunkThreshold();
    }

    public void setMood(Mood mood){
        if(mood==null){
            throw new IllegalArgumentException("mood cannot be null");
        }
        this.mood=mood;
    }
}
