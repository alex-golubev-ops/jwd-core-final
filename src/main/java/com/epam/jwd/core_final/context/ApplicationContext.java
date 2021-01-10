package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.domain.BaseEntity;

import java.util.Collection;

public interface ApplicationContext {

    <T extends BaseEntity>Collection<T> retrieveBaseEntityList(Class<T> tClass);

    void init() throws InvalidStateException;

}
