package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.exception.EntityIsNotBeAssignedException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrewServiceImplTest {
    private CrewServiceImpl service;
    private NassaContext context;

    @Before
    public void init() throws InvalidStateException {
        context = new NassaContext();
        service = CrewServiceImpl.getInstance(context);
        context.init();

    }


    @Test
    public void findAllCrewMembersByCriteriaTest() {
        List<CrewMember> collect = service.findAllCrewMembers()
                .stream()
                .filter(e -> e.getRank() == Rank.CAPTAIN || e.getRole() == Role.PILOT)
                .collect(Collectors.toList());
        CrewMemberCriteria criteria = new CrewMemberCriteria();
        criteria.setRank(Rank.CAPTAIN);
        criteria.setRole(Role.PILOT);
        List<CrewMember> allCrewMembersByCriteria = service.findAllCrewMembersByCriteria(criteria);
        Assert.assertEquals(collect.size(), allCrewMembersByCriteria.size());
    }

    @Test
    public void findCrewMemberByCriteria() {
        CrewMemberCriteria criteria = new CrewMemberCriteria();
        criteria.setRole(Role.MISSION_SPECIALIST);
        Optional<CrewMember> crewMemberByCriteria = service.findCrewMemberByCriteria(criteria);
        Assert.assertEquals(crewMemberByCriteria.get().getName(), "Zoe Day");
    }

    @Test
    public void updateCrewMemberDetailsTest() {
        CrewMember crewMember = new CrewMember(1L, null, Role.PILOT, Rank.CAPTAIN, false);
        CrewMember newCrewMember = service.updateCrewMemberDetails(crewMember);
        Assert.assertNotEquals(newCrewMember.getRank(), Rank.SECOND_OFFICER);
    }


    @Test
    public void createCrewMemberTest() throws DuplicateEntityException {
        CrewMember crewMember = new CrewMember("Alex Golubev", Role.PILOT, Rank.SECOND_OFFICER);
        service.createCrewMember(crewMember);
        List<CrewMember> allCrewMembers = service.findAllCrewMembers();
        Assert.assertTrue(allCrewMembers.contains(crewMember));
    }

    @Test(expected = RuntimeException.class)
    public void createCrewMemberTest2() throws DuplicateEntityException {
        CrewMember crewMember = new CrewMember("Davey Bentley", Role.PILOT, Rank.SECOND_OFFICER);
        service.createCrewMember(crewMember);

    }


}
