package bg.sofia.uni.fmi.mjt.laundry.basket;

import bg.sofia.uni.fmi.mjt.laundry.exception.BasketOverloadedException;
import bg.sofia.uni.fmi.mjt.laundry.exception.EmptyBasketException;
import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

public class LaundryBasket<G extends Garment> implements Iterable<G> {
    private String owner;
    private double capacityKg;
    private Stack<G> basket;
    /**
     * @param owner      the owner of the basket
     * @param capacityKg the maximum total weight of the garments in the basket, in kilograms
     * @throws IllegalArgumentException if the owner is null or blank, or the capacity is not positive
     */
    public LaundryBasket(String owner, double capacityKg) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner cannot be null");
        }

        if (capacityKg <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        this.owner = owner;
        this.capacityKg = capacityKg;
        this.basket = new Stack<>();
    }

    /**
     * @return the owner of the basket
     */
    public String getOwner() {
        return this.owner;
    }

    /**
     * @return the capacity of the basket in kilograms
     */
    public double getCapacityKg() {
        return this.capacityKg;
    }

    /**
     * @return the total weight of the garments currently in the basket, in kilograms
     */
    public double getWeightKg() {
        double total = 0;

        for (var garment: basket) {
            total += garment.getWeightKg();
        }

        return total;
    }

    /**
     * @return the number of garments currently in the basket
     */
    public int size() {
        return basket.size();
    }

    /**
     * Puts a garment on top of the basket.
     *
     * @param garment the garment to put in
     * @throws IllegalArgumentException  if the garment is null
     * @throws BasketOverloadedException if the total weight would exceed the capacity of the basket
     */
    public void add(G garment) {
        if (garment == null) {
            throw new IllegalArgumentException("Garment cannot be null");
        }

        if (getWeightKg() + garment.getWeightKg() > capacityKg) {
            throw new BasketOverloadedException("Weight would exceed the capacity");
        }

        basket.push(garment);
    }

    /**
     * Puts all given garments on top of the basket in the iteration order of the collection.
     * Either all garments are put in, or none of them.
     *
     * @param garments the garments to put in
     * @throws IllegalArgumentException  if the collection is null or contains null
     * @throws BasketOverloadedException if the total weight would exceed the capacity of the basket
     */
    public void addAll(Collection<? extends G> garments) {
        if (garments == null) {
            throw new IllegalArgumentException("Garments cannot be null");
        }

        double totalWeight = getWeightKg();

        for (var garment: garments) {
            if (garment == null) {
                throw new IllegalArgumentException("Garments cannot contain null");
            }
            totalWeight += garment.getWeightKg();
        }

        if (totalWeight > capacityKg) {
            throw new BasketOverloadedException("Weight would exceed the capacity");
        }

        for (var garment: garments) {
            basket.push(garment);
        }
    }

    /**
     * Moves all garments from this basket on top of the other one, preserving their stack order
     * (this basket's top becomes the other basket's top). After a move, this basket is empty.
     * Either all garments are moved, or none of them.
     *
     * @param other the basket to move the garments to
     * @return the number of moved garments
     * @throws IllegalArgumentException  if other is null or is the same object as this basket
     * @throws BasketOverloadedException if the total weight would exceed the capacity of the other basket
     */
    public int moveAllTo(LaundryBasket<? super G> other) {
        int count = 0;

        if (other == null || other.equals(this)) {
            throw new IllegalArgumentException("Other cannot be null or the same object");
        }

        if (other.getWeightKg() + this.getWeightKg() > other.getCapacityKg()) {
            throw new BasketOverloadedException("Weight would exceed the capacity");
        }

        for (var garment: this.basket) {
            other.basket.push(garment);
            count++;
        }

        this.basket.clear();
        return count;
    }

    /**
     * Takes out and returns the garment on top of the basket.
     *
     * @return the garment on top of the basket
     * @throws EmptyBasketException if the basket is empty
     */
    public G takeTop() {
        if (this.basket.empty()) {
            throw new EmptyBasketException("Basket is already empty");
        }

        var garmentToTake = this.basket.peek();
        this.basket.pop();

        return garmentToTake;
    }

    /**
     * @return an iterator over the garments in the basket in LIFO order (from top to bottom)
     */
    @Override
    public Iterator<G> iterator() {
        return this.basket.reversed().iterator();
    }

}