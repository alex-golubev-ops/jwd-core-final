package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NassaContextTest {
    private NassaContext context;

    @Before
    public void init() throws InvalidStateException {
        context = new NassaContext();
        context.init();
    }

    @Test
    public void retrieveBaseEntityListTest1() {
        Collection<CrewMember> crewMembers = context.retrieveBaseEntityList(CrewMember.class);
        Assert.assertTrue(crewMembers.size()>0);
    }
    @Test
    public void retrieveBaseEntityListTest2() {
        Collection<FlightMission> flightMissions = context.retrieveBaseEntityList(FlightMission.class);
        Assert.assertTrue(flightMissions.size()>0);
    }
    @Test
    public void retrieveBaseEntityListTest3() {
        Collection<Spaceship> spaceships = context.retrieveBaseEntityList(Spaceship.class);
        Assert.assertTrue(spaceships.size()>0);
    }
    @Test(expected = UnknownEntityException.class)
    public void retrieveBaseEntityListTest4() {
        Collection<Role> roles = context.retrieveBaseEntityList(Role.class);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void initTest1() {
        String name="Davey Bentley";
        Optional<CrewMember> entity = context.retrieveBaseEntityList(CrewMember.class)
                .stream()
                .filter(e -> e.getName().equals("Davey Bentley"))
                .findFirst();
        Assert.assertNotNull(entity.get());

    }

    @Test
    public void initTest2() {
        Map<Role, Short> crew = new HashMap<>();
        crew.put(Role.MISSION_SPECIALIST, (short) 5);
        crew.put(Role.FLIGHT_ENGINEER, (short) 9);
        crew.put(Role.PILOT, (short) 3);
        crew.put(Role.COMMANDER, (short) 3);
        Spaceship spaceship = new Spaceship("Challenger", crew, 201117L);
        Collection<Spaceship> spaceships = context.retrieveBaseEntityList(Spaceship.class);
        Assert.assertTrue(spaceships.contains(spaceship));

    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void initTest3() {

        Collection<FlightMission> flightMissions = context.retrieveBaseEntityList(FlightMission.class);
        Optional<FlightMission> air = flightMissions.stream().filter(e -> e.getName().equals("Air")).findFirst();
        Assert.assertNotNull(air.get());
    }
}