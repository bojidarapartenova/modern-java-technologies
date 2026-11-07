package bg.sofia.uni.fmi.mjt.tavern.event;

public final class Insult implements Event{
    private final String from;
    private final String to;

    public Insult(String from, String to){
        if(from==null || from.isBlank()){
            throw new IllegalArgumentException("name cannot be null or blank");
        }

        if(to==null || to.isBlank()){
            throw new IllegalArgumentException("name cannot be null or blank");
        }

        if(from.equals(to)){
            throw new IllegalArgumentException("names must be different");
        }

        this.from=from;
        this.to=to;
    }

    public String from(){
        return from;
    }

    public String to(){
        return to;
    }
}
