package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.impl.NassaContext;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public enum GenerationParameterOfMission {
    INSTANCE;
    private final Random random = new Random();
    private final ApplicationContext context = new NassaContext();
    private final Map<String, Boolean> names = new HashMap<>();

    GenerationParameterOfMission() {
        names.put("Adventure", false);
        names.put("Soyuz", false);
        names.put("Shenzhou", false);
        names.put("SpaceShipTwo", false);
        names.put("Crew Dragon", false);
        names.put("Vostok", false);
        names.put("Mercury", false);
        names.put("X-15", false);
        names.put("Voskhod", false);
        names.put("Gemini", false);
        names.put("Apollo", false);
        names.put("Space Shuttle", false);
        names.put("Air", false);
        names.put("Bird", false);
        names.put("Body", false);
        names.put("Boy", false);
        names.put("City", false);
        names.put("Day", false);
        names.put("Dog", false);
        names.put("East", false);
        names.put("Example", false);
        names.put("Eye", false);
        names.put("Father", false);
        names.put("Hand", false);
        names.put("King", false);
        names.put("Letter", false);
        names.put("Life", false);
        names.put("Line", false);
        names.put("List", false);
        names.put("Love", false);
    }

    public Long generateDistance() {
        return Math.abs(random.nextLong());
    }

    public MissionResult generateMissionResult() {
        MissionResult[] missionResult = MissionResult.values();
        return missionResult[random.nextInt(missionResult.length)];
    }

    public String generateName() {
        String name;
        String[] strings = names.keySet().toArray(new String[0]);
        while (true) {
            name = strings[random.nextInt(strings.length)];
            if (!names.get(name)) {
                names.put(name, true);
                return name;
            }
        }
    }

    public LocalDate generateStartDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2020, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    public LocalDate generateEndDate(LocalDate startDate) {
        long minDay = startDate.toEpochDay();
        long maxDay = LocalDate.of(2020, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

}
