package bg.sofia.uni.fmi.mjt.tavern.event;

public final class Drink implements Event {
    private final String guest;
    private final int rakias;

    public Drink(String guest, int rakias){
        if(guest==null || guest.isBlank()){
            throw new IllegalArgumentException("guest cannot be null or blank");
        }

        if(rakias<=0){
            throw new IllegalArgumentException("rakias must be positive");
        }

        this.guest=guest;
        this.rakias=rakias;
    }

    public String guest(){
        return guest;
    }

    public int rakias(){
        return rakias;
    }
}
