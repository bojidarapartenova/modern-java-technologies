package bg.sofia.uni.fmi.mjt.laundry;

import bg.sofia.uni.fmi.mjt.laundry.basket.LaundryBasket;
import bg.sofia.uni.fmi.mjt.laundry.exception.AlreadyQueuedException;
import bg.sofia.uni.fmi.mjt.laundry.exception.EmptyQueueException;
import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;
import bg.sofia.uni.fmi.mjt.laundry.program.WashProgram;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class LaundryRoom implements LaundryRoomAPI {
    private Map<String, Integer> tokens;
    private Queue<WashJob<?>> washJobs;
    public LaundryRoom() {
        this.tokens = new HashMap<>();
        this.washJobs = new ArrayDeque<>();
    }

    @Override
    public <G extends Garment> WashJob<G> scheduleWash(LaundryBasket<G> basket, WashProgram<? super G> program) {
        if (basket == null || program == null || basket.size() == 0) {
            throw new IllegalArgumentException("Basket cannot be null or empty and program cannot be null");
        }

        WashJob<G> washJob = new WashJob<>(basket, program);

        for (var job: washJobs) {
            if (job.basket() == basket) {
                throw new AlreadyQueuedException("Basket is already queued");
            }
        }

        washJobs.add(washJob);
        if (!tokens.containsKey(washJob.basket().getOwner())) {
            tokens.put(washJob.basket().getOwner(), 0);
        }

        return washJob;
    }

    @Override
    public boolean cancelWash(LaundryBasket<?> basket) {
        if (basket == null) {
            throw new IllegalArgumentException("Basket cannot be null");
        }

        LaundryBasket<?> basketToRemove = null;
        WashJob<?> washJobToRemove = null;
        for (var washJob: washJobs) {
            if (washJob.basket().equals(basket)) {
                basketToRemove = washJob.basket();
                washJobToRemove = washJob;
                break;
            }
        }

        if (basketToRemove == null) {
            return false;
        }

        washJobs.remove(washJobToRemove);
        return true;
    }

    @Override
    public WashJob<?> runNext() {
        if (washJobs.isEmpty()) {
            throw new EmptyQueueException("Queue is empty");
        }

        WashJob<?> washJob = washJobs.remove();

        washJob.wash();

        String owner = washJob.basket().getOwner();

        if (tokens.containsKey(owner)) {
            tokens.put(owner, tokens.get(owner) + 1);
        } else {
            tokens.put(owner, 1);
        }

        return washJob;
    }

    @Override
    public List<WashJob<?>> getScheduledWashes() {
        return List.copyOf(washJobs);
    }

    @Override
    public int getTokensSpent(String owner) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner cannot be null or blank");
        }

        if (tokens.get(owner) == null) {
            return 0;
        }
        return tokens.get(owner);
    }
}
