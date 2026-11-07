package bg.sofia.uni.fmi.mjt.tavern;

import bg.sofia.uni.fmi.mjt.tavern.event.Event;
import bg.sofia.uni.fmi.mjt.tavern.event.Drink;
import bg.sofia.uni.fmi.mjt.tavern.event.Insult;
import bg.sofia.uni.fmi.mjt.tavern.event.Cheat;
import bg.sofia.uni.fmi.mjt.tavern.event.Pay;
import bg.sofia.uni.fmi.mjt.tavern.event.Leave;

import bg.sofia.uni.fmi.mjt.tavern.exception.BannedGuestException;
import bg.sofia.uni.fmi.mjt.tavern.exception.BrawlException;
import bg.sofia.uni.fmi.mjt.tavern.exception.CheatingException;
import bg.sofia.uni.fmi.mjt.tavern.exception.GuestAlreadyInsideException;
import bg.sofia.uni.fmi.mjt.tavern.exception.InsultException;
import bg.sofia.uni.fmi.mjt.tavern.exception.NoSuchGuestException;
import bg.sofia.uni.fmi.mjt.tavern.exception.UnpaidTabException;

import bg.sofia.uni.fmi.mjt.tavern.guest.Guest;
import bg.sofia.uni.fmi.mjt.tavern.guest.Mood;

import java.util.Arrays;

public class TavernAPIImpl implements TavernAPI {

    private static final int INITIAL_CAPACITY = 10;

    private final int rakiaPrice;

    private Guest[] guests;
    private int guestsCount;

    private String[] bannedGuests;
    private int bannedGuestsCount;

    public TavernAPIImpl(int rakiaPrice) {
        if (rakiaPrice <= 0) {
            throw new IllegalArgumentException("Rakia price must be positive");
        }

        this.rakiaPrice = rakiaPrice;

        guests = new Guest[INITIAL_CAPACITY];
        guestsCount = 0;

        bannedGuests = new String[INITIAL_CAPACITY];
        bannedGuestsCount = 0;
    }

    @Override
    public void admit(Guest guest) {
        if (guest == null) {
            throw new IllegalArgumentException("Guest cannot be null");
        }

        String name = guest.getName();

        if (isBanned(name)) {
            throw new BannedGuestException("Guest is banned: " + name);
        }

        if (findGuestByName(name) != -1) {
            throw new GuestAlreadyInsideException("Guest is already inside: " + name);
        }

        ensureGuestsCapacity();

        guest.reset();

        guests[guestsCount] = guest;
        guestsCount++;
    }

    @Override
    public void handle(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }

        if (event instanceof Drink drink) {
            Guest guest = findGuest(drink.guest());

            guest.drink(drink.rakias(), rakiaPrice);

        } else if (event instanceof Pay pay) {
            Guest guest = findGuest(pay.guest());

            guest.pay(pay.amount());

        } else if (event instanceof Leave leave) {
            Guest guest = findGuest(leave.guest());

            removeGuest(guest);

            if (guest.getTab() > 0) {
                addBannedGuest(guest.getName());

                throw new UnpaidTabException("Guest left without paying: " + guest.getName());
            }

        } else if (event instanceof Insult insult) {
            Guest from = findGuest(insult.from());
            Guest to = findGuest(insult.to());

            handleProvocation(from, to, true);

        } else if (event instanceof Cheat cheat) {
            Guest cheater = findGuest(cheat.cheater());
            Guest victim = findGuest(cheat.victim());

            handleProvocation(cheater, victim, false);
        }
    }


    private void handleProvocation(Guest provocateur, Guest victim, boolean isInsult) {
        Mood previousMood = victim.getMood();

        Mood newMood;

        if (isInsult) {
            newMood = victim.reactToInsult();
        } else {
            newMood = victim.reactToCheat();
        }
        if (victim.isDrunk() && newMood.ordinal() > previousMood.ordinal()) {
            newMood = newMood.escalate();
        }

        for (int i = 0; i < guestsCount; i++) {
            Guest guest = guests[i];

            if (guest != provocateur && guest != victim) {
                newMood = guest.interferes(newMood);
            }
        }

        victim.setMood(newMood);

        if (newMood == Mood.BRAWLING) {
            removeGuest(provocateur);
            removeGuest(victim);

            addBannedGuest(provocateur.getName());
            addBannedGuest(victim.getName());

            if (isInsult) {
                throw new BrawlException("Brawl started after insult",
                        new InsultException("Insult caused the brawl"));
            }

            throw new BrawlException("Brawl started after cheating",
                    new CheatingException("Cheating caused the brawl"));
        }
    }


    @Override
    public Mood moodOf(String name) {
        return findGuest(name).getMood();
    }

    @Override
    public int tabOf(String name) {
        return findGuest(name).getTab();
    }

    @Override
    public String[] guestsInside() {
        String[] result = new String[guestsCount];

        for (int i = 0; i < guestsCount; i++) {
            result[i] = guests[i].getName();
        }

        return result;
    }

    @Override
    public String[] bannedGuests() {
        return Arrays.copyOf(bannedGuests, bannedGuestsCount);
    }

    private Guest findGuest(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }

        int index = findGuestByName(name);

        if (index == -1) {
            throw new NoSuchGuestException("No such guest: " + name);
        }

        return guests[index];
    }

    private int findGuestByName(String name) {
        for (int i = 0; i < guestsCount; i++) {
            if (guests[i].getName().equals(name)) {
                return i;
            }
        }

        return -1;
    }

    private boolean isBanned(String name) {
        for (int i = 0; i < bannedGuestsCount; i++) {
            if (bannedGuests[i].equals(name)) {
                return true;
            }
        }

        return false;
    }

    private void addBannedGuest(String name) {
        if (isBanned(name)) {
            return;
        }

        ensureBannedGuestsCapacity();

        bannedGuests[bannedGuestsCount] = name;
        bannedGuestsCount++;
    }

    private void removeGuest(Guest guest) {
        int index = findGuestByName(guest.getName());

        if (index == -1) {
            return;
        }

        for (int i = index; i < guestsCount - 1; i++) {
            guests[i] = guests[i + 1];
        }

        guestsCount--;
        guests[guestsCount] = null;
    }

    private void ensureGuestsCapacity() {
        if (guestsCount == guests.length) {
            guests = Arrays.copyOf(guests, guests.length * 2);
        }
    }

    private void ensureBannedGuestsCapacity() {
        if (bannedGuestsCount == bannedGuests.length) {
            bannedGuests = Arrays.copyOf(
                    bannedGuests, bannedGuests.length * 2);
        }
    }
}
