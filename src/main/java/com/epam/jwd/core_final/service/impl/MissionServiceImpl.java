package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.MissionService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MissionServiceImpl implements MissionService {
    private static MissionServiceImpl instance;
    private ApplicationContext context;

    private MissionServiceImpl(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public List<FlightMission> findAllMissions() {
        return new ArrayList<>(context.retrieveBaseEntityList(FlightMission.class));
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        FlightMission entity = (FlightMission) criteria.create();
        return context.retrieveBaseEntityList(FlightMission.class)
                .stream()
                .filter(e -> e.getId().equals(entity.getId()) ||
                        e.getName().equals(entity.getName()) ||
                        e.getStartDate() == entity.getStartDate() ||
                        e.getEndDate() == entity.getEndDate() ||
                        e.getDistance().equals(entity.getDistance()) ||
                        e.getAssignedCrew().equals(entity.getAssignedCrew()) ||
                        e.getMissionResult() == entity.getMissionResult())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        FlightMission entity = (FlightMission) criteria.create();
        return context.retrieveBaseEntityList(FlightMission.class)
                .stream()
                .filter(e -> e.getId().equals(entity.getId()) ||
                        e.getName().equals(entity.getName()) ||
                        e.getStartDate() == entity.getStartDate() ||
                        e.getEndDate() == entity.getEndDate() ||
                        e.getDistance().equals(entity.getDistance()) ||
                        e.getAssignedSpaceShift().equals(entity.getAssignedSpaceShift()) ||
                        e.getAssignedCrew().equals(entity.getAssignedCrew()) ||
                        e.getMissionResult() == entity.getMissionResult())
                .findAny();
    }

    @Override
    public FlightMission updateSpaceshipDetails(FlightMission flightMission) {
        Collection<FlightMission> flightMissions = context.retrieveBaseEntityList(FlightMission.class);
        Optional<FlightMission> entity = flightMissions
                .stream()
                .filter(e -> flightMission.getId().equals(e.getId()))
                .findAny();
        if (entity.isPresent()) {
            FlightMission newFlightMission = entity.get();
            updateParameterFlightMission(newFlightMission, flightMission);
            return newFlightMission;
        }
        throw new UnknownEntityException(flightMission.getName(), new Object[]{flightMission.getStartDate(),
                flightMission.getEndDate(), flightMission.getDistance(), flightMission.getMissionResult()});

    }

    @Override
    public FlightMission createMission(FlightMission flightMission) throws DuplicateEntityException {
        Collection<FlightMission> flightMissions = context.retrieveBaseEntityList(FlightMission.class);
        Optional<FlightMission> any = flightMissions
                .stream()
                .filter(e -> e.getName().equals(flightMission.getName()))
                .findAny();
        if (any.isEmpty()) {
            flightMissions.add(flightMission);
            return flightMission;
        }
        throw new DuplicateEntityException("The entity is exist");
    }

    private void updateParameterFlightMission(FlightMission flightMission, FlightMission updateEntity) {
        if (updateEntity.getStartDate() != null) {
            flightMission.setStartDate(updateEntity.getStartDate());
        }
        if (updateEntity.getEndDate() != null) {
            flightMission.setEndDate(updateEntity.getEndDate());
        }
        if (updateEntity.getMissionResult() != null) {
            flightMission.setMissionResult(updateEntity.getMissionResult());
            if (flightMission.getMissionResult() == MissionResult.FAILED ||
                    updateEntity.getMissionResult() == MissionResult.FAILED) {
                boolean check = updateEntity.getMissionResult() != MissionResult.FAILED;
                flightMission.getAssignedCrew().forEach(e -> e.setReadyForNextMissions(check));
                flightMission.getAssignedSpaceShift().setReadyForNextMissions(check);
            }
        }
        if (updateEntity.getAssignedCrew() != null) {
            flightMission.setAssignedCrew(updateEntity.getAssignedCrew());
        }
        if (updateEntity.getAssignedSpaceShift() != null) {
            flightMission.setAssignedSpaceShift(updateEntity.getAssignedSpaceShift());
        }
        if (updateEntity.getDistance() != null) {
            flightMission.setDistance(updateEntity.getDistance());
        }
    }

    public static MissionServiceImpl getInstance(ApplicationContext context) {
        if (instance == null) {
            instance = new MissionServiceImpl(context);
        }
        return instance;
    }
}
