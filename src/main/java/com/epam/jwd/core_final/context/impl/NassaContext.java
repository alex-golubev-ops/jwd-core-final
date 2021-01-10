package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.GenerationParameterOfMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NassaContext implements ApplicationContext {

    // no getters/setters for them
    private Collection<CrewMember> crewMembers = new ArrayList<>();
    private Collection<Spaceship> spaceships = new ArrayList<>();
    private Collection<FlightMission> flightMissions = new ArrayList<>();
    private final EntityFactory<CrewMember> crewMemberFactory = CrewMemberFactory.getInstance();
    private final EntityFactory<Spaceship> spaceshipFactory = SpaceshipFactory.getInstance();
    private final EntityFactory<FlightMission> flightMissionFactory = FlightMissionFactory.getInstance();
    private final ApplicationProperties applicationProperties = ApplicationProperties.getInstance();
    private final String URICrewMember = applicationProperties.getInputRootDir() + "/" +
            applicationProperties.getCrewFileName();
    private final String URISpaceship = applicationProperties.getInputRootDir() + "/" +
            applicationProperties.getSpaceshipsFileName();
    private final GenerationParameterOfMission generation = GenerationParameterOfMission.INSTANCE;

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        if (tClass == CrewMember.class) {
            return (Collection<T>) crewMembers;
        } else if (tClass == Spaceship.class) {
            return (Collection<T>) spaceships;
        } else {
            return (Collection<T>) flightMissions;
        }
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        try {
            String PATTERN_CREW_MEMBER = "\\d,[\\w ]+,\\d";
            String PATTERN_SPACESHIP = "\\w+;\\d+;\\{[\\w:,]+\\}";
            addEntityToContext(URICrewMember, PATTERN_CREW_MEMBER, CrewMember.class);
            addEntityToContext(URISpaceship, PATTERN_SPACESHIP, Spaceship.class);
            generateMissions();
        } catch (IOException e) {
            throw new InvalidStateException("Nassa context was not initialized");
        }
    }

    private void addEntityToContext(String URI, String patternString,
                                    Class<? extends AbstractBaseEntity> type) throws IOException {
        try (FileReader fileReader = new FileReader(URI);
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    if (type == CrewMember.class) {
                        CrewMember newCrew = parsingToCrewMember(line.substring(matcher.start(), matcher.end()));
                        crewMembers.add(newCrew);
                    } else {
                        Spaceship newSpaceship = parsingToSpaceship(line.substring(matcher.start(), matcher.end()));
                        spaceships.add(newSpaceship);
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException();
        }
    }

    private CrewMember parsingToCrewMember(String string) {
        String[] information = string.split(",");
        Role role = Role.resolveRoleById(Integer.parseInt(information[0]));
        String name = information[1];
        Rank rank = Rank.resolveRankById(Integer.parseInt(information[2]));
        return crewMemberFactory.create(name, role, rank);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private Spaceship parsingToSpaceship(String string) {
        String[] information = string.split(";");
        String name = information[0];
        Long distance = Long.parseLong(information[1]);
        Pattern pattern = Pattern.compile("(\\d):(\\d+)");
        Matcher matcher = pattern.matcher(information[2]);
        Map<Role, Short> newCrew = new HashMap<>();
        while (matcher.find()) {
            int id = Integer.parseInt(matcher.group(1));
            short counter = Short.parseShort(matcher.group(2));
            Role role = Role.resolveRoleById(id);
            newCrew.put(role, counter);
        }
        return spaceshipFactory.create(name, newCrew, distance);
    }

    private void generateMissions() {
        for (int i = 0; i < 30; i++) {
            String name = generation.generateName();
            LocalDate startDate = generation.generateStartDate();
            LocalDate endDate = generation.generateEndDate(startDate);
            Long distance = generation.generateDistance();
            MissionResult missionResult = generation.generateMissionResult();
            List<CrewMember> crewMembers = new ArrayList<>();
            FlightMission flightMission = flightMissionFactory.create(name, startDate, endDate,
                    distance, null, crewMembers, missionResult);
            flightMissions.add(flightMission);
        }
    }
}
