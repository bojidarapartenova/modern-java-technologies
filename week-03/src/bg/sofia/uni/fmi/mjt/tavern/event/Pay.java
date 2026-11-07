package bg.sofia.uni.fmi.mjt.tavern.event;

public final class Pay implements Event {
    private final String guest;
    private final int amount;

    public Pay(String guest, int amount){
        if(guest==null || guest.isBlank()){
            throw new IllegalArgumentException("guest cannot be null or blank");
        }

        if(amount<=0){
            throw new IllegalArgumentException("amount must be positive");
        }

        this.guest=guest;
        this.amount=amount;
    }

    public String guest() {
        return guest;
    }

    public int amount() {
        return amount;
    }
}
