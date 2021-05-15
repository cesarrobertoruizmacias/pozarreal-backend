package org.uresti.pozarreal.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.uresti.pozarreal.dto.StreetInfo;
import org.uresti.pozarreal.model.House;
import org.uresti.pozarreal.model.Representative;
import org.uresti.pozarreal.model.Street;
import org.uresti.pozarreal.repository.HousesRepository;
import org.uresti.pozarreal.repository.RepresentativeRepository;
import org.uresti.pozarreal.repository.StreetRepository;

import java.util.*;

public class StreetServiceImplTests {

    @Test
    public void givenAnEmptyStreetList_whenGetStreets_thenEmptyListIsReturned(){
        // Given:
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository = null;
        HousesRepository housesRepository = null;
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        List<Street> lista = new LinkedList<>();

        Mockito.when(streetRepository.findAll()).thenReturn(lista);

        // When:
        List<Street> streets = streetService.getStreets();

        // Then:
        Assertions.assertTrue(streets.isEmpty());
    }

    @Test
    public void givenAnStreetListWithTwoElements_whenGetStreets_thenListWithTwoElementsIsReturned(){
        // Given:
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository = null;
        HousesRepository housesRepository = null;
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        List<Street> lista = new LinkedList<>();

        Street street1 = new Street();
        street1.setId("id1");
        street1.setName("Street 1");
        lista.add(street1);

        Street street2 = new Street();
        street2.setId("id2");
        street2.setName("Street 2");
        lista.add(street2);

        Mockito.when(streetRepository.findAll()).thenReturn(lista);


        // When:
        List<Street> streets = streetService.getStreets();

        // Then:
        Assertions.assertEquals(2, streets.size());
        Assertions.assertEquals("id1", streets.get(0).getId());
        Assertions.assertEquals("Street 1", streets.get(0).getName());
        Assertions.assertEquals("id2", streets.get(1).getId());
        Assertions.assertEquals("Street 2", streets.get(1).getName());
    }
@Test
    public void givenValidStreet_whengetStreetInfoReturned(){
        //Given
        String streetId = "1235123";
        Street street = buildTestStreet();
        //Optional<Street> optionalStreet;

        Representative representative = buldTestRepresentative();
        List<House> housesList = buildTestHouses("UnaCalle");

        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository = Mockito.mock(RepresentativeRepository.class);
        HousesRepository housesRepository = Mockito.mock(HousesRepository.class);
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository,representativeRepository,housesRepository);

        Mockito.when(streetRepository.findById(streetId)).thenReturn(Optional.of(street));

        Mockito.when(representativeRepository.findRepresentativeByStreet(streetId)).thenReturn(representative);


        Mockito.when(housesRepository.findAllByStreet(streetId)).thenReturn(housesList);



        // When:
        StreetInfo streetInfo = streetService.getStreetInfo(streetId);

        // Then:
        Assertions.assertEquals("1235123", streetInfo.getId());
        Assertions.assertEquals("UnaCalle", streetInfo.getName());
        Assertions.assertEquals("2343523", streetInfo.getRepresentative().getId());
        Assertions.assertEquals("UnaCalle", streetInfo.getRepresentative().getStreet());
        Assertions.assertEquals("Un Nombre", streetInfo.getRepresentative().getName());
        Assertions.assertEquals("Un address", streetInfo.getRepresentative().getAddress());
        Assertions.assertEquals("4441030970", streetInfo.getRepresentative().getPhone());
        Assertions.assertEquals(2,streetInfo.getHouses().size());




    }

    private List<House> buildTestHouses(String streetName) {
        List<House> houseList = new LinkedList<>();
        House house1 = new House();
        house1.setId(UUID.randomUUID().toString());
        house1.setStreet(streetName);
        house1.setChipsEnabled(true);
        house1.setNumber("100");

        houseList.add(house1);
        House house2 = new House();

        house2.setId(UUID.randomUUID().toString());
        house2.setStreet(streetName);
        house2.setChipsEnabled(false);
        house2.setNumber("120");

        houseList.add(house2);

        return houseList;

    }

    private Street buildTestStreet() {
        Street street = new Street();

        street.setName("UnaCalle");
        street.setId("784573487243");
        return street;
    }

    private Representative buldTestRepresentative() {
        Representative representative = new Representative();
        representative.setId("2343523");
        representative.setName("Un Nombre");
        representative.setStreet("UnaCalle");
        representative.setAddress("Un address");
        representative.setPhone("4441030970");
        return representative;
    }

}
