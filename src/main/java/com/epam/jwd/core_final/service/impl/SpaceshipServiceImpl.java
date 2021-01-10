package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.exception.EntityIsNotBeAssignedException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.SpaceshipService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpaceshipServiceImpl implements SpaceshipService {
    private static SpaceshipServiceImpl instance;
    private ApplicationContext context;

    private SpaceshipServiceImpl(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        return new ArrayList<>(context.retrieveBaseEntityList(Spaceship.class));
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        Spaceship entity = (Spaceship) criteria.create();
        return context.retrieveBaseEntityList(Spaceship.class)
                .stream()
                .filter(e -> e.getId().equals(entity.getId()) ||
                        e.getName().equals(entity.getName()) ||
                        e.getReadyForNextMissions() == entity.getReadyForNextMissions() ||
                        e.getFlightDistance().equals(entity.getFlightDistance()) ||
                        e.getCrew().equals(entity.getCrew()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        Spaceship entity = (Spaceship) criteria.create();
        return context.retrieveBaseEntityList(Spaceship.class)
                .stream()
                .filter(e -> e.getId().equals(entity.getId()) ||
                        e.getName().equals(entity.getName()) ||
                        e.getReadyForNextMissions() == entity.getReadyForNextMissions() ||
                        e.getFlightDistance().equals(entity.getFlightDistance()) ||
                        e.getCrew().equals(entity.getCrew()))
                .findAny();
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        Collection<Spaceship> crewMembers = context.retrieveBaseEntityList(Spaceship.class);
        Optional<Spaceship> entity = crewMembers
                .stream()
                .filter(e -> spaceship.getId().equals(e.getId()))
                .findAny();
        if (entity.isPresent()) {
            Spaceship newSpaceship = entity.get();
            updateParameterSpaceship(newSpaceship, spaceship);
            return newSpaceship;
        } else {
            throw new UnknownEntityException(spaceship.getName(), new Object[]{spaceship.getId(),
                    spaceship.getFlightDistance(), spaceship.getReadyForNextMissions()});
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public void assignSpaceshipOnMission(Spaceship crewMember) throws EntityIsNotBeAssignedException {
        if (!crewMember.getReadyForNextMissions()) {
            throw new EntityIsNotBeAssignedException("This spaceship was died", crewMember.getName());
        }
        Collection<Spaceship> spaceships = context.retrieveBaseEntityList(Spaceship.class);
        Collection<FlightMission> flightMissions = context.retrieveBaseEntityList(FlightMission.class);
        Optional<FlightMission> any = flightMissions
                .stream()
                .filter(e -> e.getAssignedSpaceShift().getName().equals(crewMember.getName()))
                .findAny();
        if (any.isPresent()) {
            throw new EntityIsNotBeAssignedException("This cream member has another mission", crewMember.getName());

        } else {
            FlightMission mission = flightMissions.stream().findAny().get();
            if (mission.getMissionResult() == MissionResult.FAILED) {
                crewMember.setReadyForNextMissions(false);
            }
            mission.setAssignedSpaceShift(crewMember);
        }
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws DuplicateEntityException {
        Collection<Spaceship> spaceships = context.retrieveBaseEntityList(Spaceship.class);
        if (spaceships
                .stream()
                .filter(e -> e.getName().equals(spaceship.getName()))
                .findAny().isEmpty()) {
            spaceships.add(spaceship);
            return spaceship;
        }
        throw new DuplicateEntityException(spaceship.getName());
    }

    public static SpaceshipServiceImpl getInstance(ApplicationContext context) {
        if (instance == null) {
            instance = new SpaceshipServiceImpl(context);
        }
        return instance;
    }

    private void updateParameterSpaceship(Spaceship spaceship, Spaceship newSpaceship) {
        if (newSpaceship.getCrew() != null) {
            spaceship.setCrew(newSpaceship.getCrew());
        }
        if (newSpaceship.getFlightDistance() != null) {
            spaceship.setFlightDistance(newSpaceship.getFlightDistance());
        }
        if (newSpaceship.getReadyForNextMissions() != null) {
            spaceship.setReadyForNextMissions(newSpaceship.getReadyForNextMissions());
        }
    }
}
