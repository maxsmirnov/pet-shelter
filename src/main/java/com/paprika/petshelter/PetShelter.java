package com.paprika.petshelter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

import static com.paprika.petshelter.Pet.Type;
import static com.paprika.petshelter.Pet.getByType;

/**
 * Pet shelter
 * <p>
 * Created by maxsmirnov on 24.08.2016.
 */
public class PetShelter {
    private final Map<Type, Queue<Pet>> petByTypeMap;

    public PetShelter() {
        petByTypeMap = new EnumMap<>(Type.class);
    }

    /**
     * Registers new pet in the shelter, time complexity O(1)
     *
     * @param type      pet type
     * @param name      pet's name
     * @param birthDate pet's birth date
     */
    public void registerPet(Type type, String name, LocalDate birthDate) {
        Objects.requireNonNull(type, "Pet's type must be specified");
        registerPet(getByType(type, name, birthDate));
    }

    /**
     * for testing
     *
     * @param pet pet
     */
    void registerPet(Pet pet) {
        pet.setRegisteredTs(LocalDateTime.now());
        petByTypeMap.computeIfAbsent(pet.getType(), type -> new LinkedList<>()).add(pet);
    }

    /**
     * @param type pet type
     * @return a collection of pets of specified type, sorted alphabetically by name, time complexity O(n + log(n))
     */
    public Collection<Pet> getPetsByType(Type type) {
        Objects.requireNonNull(type);
        List<Pet> pets = new ArrayList<>(petByTypeMap.getOrDefault(type, new LinkedList<>())); // O(n)
        Collections.sort(pets, (p1, p2) -> p1.getName().compareTo(p2.getName())); // O(log(n))
        return pets;
    }

    /**
     * @param type pet type
     * @return the most lately registered pet of specified type, time complexity O(1)
     */
    public Optional<Pet> getLatelyPetWithType(Type type) {
        Objects.requireNonNull(type);
        return Optional.ofNullable(petByTypeMap.getOrDefault(type, new LinkedList<>()).poll());
    }

    /**
     * @return the most lately registered pet, complexity O(1)
     */
    public Optional<Pet> getLatelyPet() {
        Pet pet = petByTypeMap.values().stream()
                .max((q1, q2) -> q2.peek().getRegisteredTs().compareTo(q1.peek().getRegisteredTs()))
                .orElseGet(LinkedList::new)
                .poll();
        return Optional.ofNullable(pet);
    }
}
