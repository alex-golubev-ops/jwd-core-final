package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;

public enum Rank implements BaseEntity {
    TRAINEE(1L),
    SECOND_OFFICER(2L),
    FIRST_OFFICER(3L),
    CAPTAIN(4L);

    private final Long id;

    Rank(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * todo via java.lang.enum methods!
     */
    @Override
    public String getName() {
        if (id == 1) {
            return TRAINEE.getName();
        } else if (id == 2) {
            return SECOND_OFFICER.getName();
        } else if (id == 3) {
            return FIRST_OFFICER.getName();
        } else {
            return CAPTAIN.getName();
        }
    }

    /**
     * todo via java.lang.enum methods!
     *
     * @throws UnknownEntityException if such id does not exist
     */
    public static Rank resolveRankById(int id) {
        switch (id) {
            case 1: {
                return Rank.TRAINEE;
            }
            case 2: {
                return Rank.SECOND_OFFICER;
            }
            case 3: {
                return Rank.FIRST_OFFICER;
            }
            case 4: {
                return Rank.CAPTAIN;
            }
            default:
                throw new UnknownEntityException(Integer.toString(id));
        }
    }
}
