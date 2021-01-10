package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.exception.EntityIsNotBeAssignedException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.CrewService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrewServiceImpl implements CrewService {
    private static CrewServiceImpl instance;
    private final ApplicationContext context;

    private CrewServiceImpl(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return new ArrayList<>(context.retrieveBaseEntityList(CrewMember.class));
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        CrewMember entity = (CrewMember) criteria.create();
        return context.retrieveBaseEntityList(CrewMember.class)
                .stream()
                .filter(e -> e.getId().equals(entity.getId()) ||
                        e.getName().equals(entity.getName()) ||
                        e.getRole() == entity.getRole() ||
                        e.getRank() == entity.getRank() ||
                        e.getReadyForNextMissions() == entity.getReadyForNextMissions())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        CrewMember entity = (CrewMember) criteria.create();
        return context.retrieveBaseEntityList(CrewMember.class)
                .stream()
                .filter(e -> e.getId().equals(entity.getId()) ||
                        e.getName().equals(entity.getName()) ||
                        e.getRole() == entity.getRole() ||
                        e.getRank() == entity.getRank() ||
                        e.getReadyForNextMissions() == entity.getReadyForNextMissions()).findAny();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        Collection<CrewMember> crewMembers = context.retrieveBaseEntityList(CrewMember.class);
        Optional<CrewMember> entity = crewMembers
                .stream()
                .filter(e -> crewMember.getId().equals(e.getId()))
                .findAny();
        if (entity.isPresent()) {
            CrewMember newCrewMember = entity.get();
            updateParameterCrewMember(newCrewMember, crewMember);
            return newCrewMember;
        } else {
            throw new UnknownEntityException(crewMember.getName(),
                    new Object[]{crewMember.getId(), crewMember.getRole(), crewMember.getRank()});
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws EntityIsNotBeAssignedException {
        if (!crewMember.getReadyForNextMissions()) {
            throw new EntityIsNotBeAssignedException("This crew member was died", crewMember.getName());
        }
        Collection<FlightMission> flightMissions = context.retrieveBaseEntityList(FlightMission.class);
        Optional<FlightMission> any = flightMissions
                .stream()
                .filter(e -> e.getAssignedCrew().contains(crewMember))
                .findAny();
        if (any.isPresent()) {
            throw new EntityIsNotBeAssignedException("This cream member has another mission", crewMember.getName());
        } else {
            FlightMission mission = flightMissions.stream().findAny().get();
            if (mission.getMissionResult() == MissionResult.FAILED) {
                crewMember.setReadyForNextMissions(false);
            }
            mission.getAssignedCrew().add(crewMember);
        }

    }

    @Override
    public CrewMember createCrewMember(CrewMember spaceship) throws DuplicateEntityException {
        Collection<CrewMember> crewMembers = context.retrieveBaseEntityList(CrewMember.class);
        if (crewMembers
                .stream()
                .filter(e -> e.getName().equals(spaceship.getName()))
                .findAny().isEmpty()) {
            crewMembers.add(spaceship);
            return spaceship;
        }
        throw new DuplicateEntityException(spaceship.getName());
    }

    public synchronized static CrewServiceImpl getInstance(ApplicationContext context) {
        if (instance == null) {
            instance = new CrewServiceImpl(context);
        }
        return instance;
    }

    private void updateParameterCrewMember(CrewMember crewMember, CrewMember updateEntity) {
        if (updateEntity.getRole() != null) {
            crewMember.setRole(updateEntity.getRole());
        }
        if (updateEntity.getRank() != null) {
            crewMember.setRank(updateEntity.getRank());
        }
        if (updateEntity.getReadyForNextMissions() != null) {
            crewMember.setReadyForNextMissions(updateEntity.getReadyForNextMissions());
        }
    }
}
