package bg.sofia.uni.fmi.mjt.tavern.event;

public final class Leave implements Event{
    private final String guest;

    public Leave(String guest){
        if(guest==null || guest.isBlank()){
            throw new IllegalArgumentException("guest cannot be null or blank");
        }

        this.guest=guest;
    }

    public String guest() {
        return guest;
    }
}
