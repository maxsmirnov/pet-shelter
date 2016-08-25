package com.paprika.petshelter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents generic pet
 * <p>
 * Created by maxsmirnov on 24.08.2016.
 */
public class Pet {
    private final Type type;
    private final String name;
    private final LocalDate birthDate;
    private LocalDateTime registeredTs;

    private Pet(Type type, String name, LocalDate birthDate) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(birthDate);

        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
    }

    static Pet getCat(String name, LocalDate birthDate) {
        return new Pet(Type.CAT, name, birthDate);
    }

    static Pet getDog(String name, LocalDate birthDate) {
        return new Pet(Type.DOG, name, birthDate);
    }

    static Pet getTurtle(String name, LocalDate birthDate) {
        return new Pet(Type.TURTLE, name, birthDate);
    }

    static Pet getByType(Type type, String name, LocalDate birthDate) {
        Pet pet;
        switch (type) {
            case CAT: {
                pet = getCat(name, birthDate);
                break;
            }
            case DOG: {
                pet = getDog(name, birthDate);
                break;
            }
            case TURTLE: {
                pet = getTurtle(name, birthDate);
                break;
            }
            default: {
                throw new IllegalArgumentException("unknown pet type");
            }
        }
        return pet;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Duration getAge() {
        return Duration.between(birthDate, LocalDate.now());
    }

    LocalDateTime getRegisteredTs() {
        return registeredTs;
    }

    void setRegisteredTs(LocalDateTime registeredTs) {
        this.registeredTs = registeredTs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pet pet = (Pet) o;

        if (type != pet.type) return false;
        if (name != null ? !name.equals(pet.name) : pet.name != null) return false;
        return birthDate != null ? birthDate.equals(pet.birthDate) : pet.birthDate == null;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", registeredTs=" + registeredTs +
                '}';
    }

    public enum Type {
        CAT, DOG, TURTLE
    }
}
