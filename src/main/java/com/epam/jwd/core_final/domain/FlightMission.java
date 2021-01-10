package com.epam.jwd.core_final.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceShift {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionResult}
 */
public class FlightMission extends AbstractBaseEntity {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long distance;
    private Spaceship assignedSpaceShift;
    private List<CrewMember> assignedCrew;
    private MissionResult missionResult;

    public FlightMission(String name, LocalDate startDate, LocalDate endDate, Long distance,
                         Spaceship assignedSpaceShift, List<CrewMember> assignedCrew, MissionResult missionResult) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.assignedSpaceShift = assignedSpaceShift;
        this.assignedCrew = assignedCrew;
        this.missionResult = missionResult;
    }

    public FlightMission(Long id, String name, LocalDate startDate, LocalDate endDate, Long distance,
                         Spaceship assignedSpaceShift, List<CrewMember> assignedCrew, MissionResult missionResult) {
        super(id, name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.assignedSpaceShift = assignedSpaceShift;
        this.assignedCrew = assignedCrew;
        this.missionResult = missionResult;
    }

    public FlightMission(String name, LocalDate startDate, LocalDate endDate,
                         Long distance, List<CrewMember> assignedCrew, MissionResult missionResult) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.missionResult = missionResult;
        this.assignedCrew = assignedCrew;
    }

    public FlightMission(String name, LocalDate startDate, LocalDate endDate,
                         Long distance, MissionResult missionResult) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.missionResult = missionResult;
    }

    public FlightMission(Long id, LocalDate startDate, LocalDate endDate,
                         Long distance, MissionResult missionResult) {
        super(id, null);
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.missionResult = missionResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightMission that = (FlightMission) o;
        return Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(distance, that.distance) &&
                Objects.equals(assignedSpaceShift, that.assignedSpaceShift) &&
                Objects.equals(assignedCrew, that.assignedCrew) &&
                missionResult == that.missionResult &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, distance, assignedSpaceShift, assignedCrew, missionResult);
    }

    @Override
    public String toString() {
        return getId() + ", " + getName() + ", " + startDate +
                ", " + endDate +
                ", " + distance +
                ", " + assignedSpaceShift +
                ", " + assignedCrew +
                ", " + missionResult;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Spaceship getAssignedSpaceShift() {
        return assignedSpaceShift;
    }

    public void setAssignedSpaceShift(Spaceship assignedSpaceShift) {
        this.assignedSpaceShift = assignedSpaceShift;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public void setAssignedCrew(List<CrewMember> assignedCrew) {
        this.assignedCrew = assignedCrew;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public void setMissionResult(MissionResult missionResult) {
        this.missionResult = missionResult;
    }
}
