package com.paprika.petshelter;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static java.lang.Thread.sleep;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by maxsmirnov on 24.08.2016.
 */

public class PetShelterTest {
    private PetShelter petShelter;

    @BeforeSuite
    public void setUp() throws Exception {
        petShelter = new PetShelter();
    }

    @Test
    public void should_register_pet_and_return_it_back() throws Exception {
        Pet expected = Pet.getCat("Tom", LocalDate.of(2012, 11, 5));
        petShelter.registerPet(expected);
        Optional<Pet> result = petShelter.getLatelyPet();

        assertTrue(result.isPresent());
        Pet actual = result.get();
        assertEquals(actual, expected);
        assertTrue(!petShelter.getLatelyPet().isPresent());
    }

    @Test
    public void should_return_nothing_if_there_is_no_pets() throws Exception {
        Arrays.stream(Pet.Type.values()).forEach(type -> {
            assertTrue(petShelter.getPetsByType(type).isEmpty());
            assertTrue(!petShelter.getLatelyPetWithType(type).isPresent());
        });
        assertTrue(!petShelter.getLatelyPet().isPresent());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void should_return_pets_in_right_order() throws Exception {
        Pet tom = Pet.getCat("Tom", LocalDate.of(2012, 11, 5));
        Pet garfield = Pet.getCat("Garfield", LocalDate.of(2005, 5, 5));

        Pet doge = Pet.getDog("Doge", LocalDate.of(2010, 3, 11));
        Pet hachiko = Pet.getDog("Hachiko", LocalDate.of(1923, 11, 10));

        Pet leonardo = Pet.getTurtle("Leonardo", LocalDate.of(1980, 1, 1));
        Pet donatello = Pet.getTurtle("Donatello", LocalDate.of(1980, 1, 1));

        petShelter.registerPet(tom);
        sleep(30);
        petShelter.registerPet(garfield);
        sleep(30);
        petShelter.registerPet(hachiko);
        sleep(30);
        petShelter.registerPet(doge);
        sleep(30);
        petShelter.registerPet(leonardo);
        sleep(30);
        petShelter.registerPet(donatello);

        assertEquals(petShelter.getPetsByType(Pet.Type.CAT), Arrays.asList(garfield, tom));
        assertEquals(petShelter.getPetsByType(Pet.Type.DOG), Arrays.asList(doge, hachiko));
        assertEquals(petShelter.getPetsByType(Pet.Type.TURTLE), Arrays.asList(donatello, leonardo));
        assertEquals(petShelter.getLatelyPet().get(), tom);
        assertEquals(petShelter.getPetsByType(Pet.Type.CAT), Collections.singletonList(garfield));
        assertEquals(petShelter.getLatelyPet().get(), garfield);
        assertEquals(petShelter.getLatelyPetWithType(Pet.Type.DOG).get(), hachiko);
        assertEquals(petShelter.getLatelyPetWithType(Pet.Type.DOG).get(), doge);
        assertEquals(petShelter.getPetsByType(Pet.Type.CAT), Collections.emptyList());
        assertEquals(petShelter.getPetsByType(Pet.Type.DOG), Collections.emptyList());
    }
}
