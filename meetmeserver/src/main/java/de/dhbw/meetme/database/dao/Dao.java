package de.dhbw.meetme.database.dao;

import java.util.Collection;

/**
 *
 */
public interface Dao<ID, TYPE> { //Interface that is able to take the Types "ID" and "TYPE"

    void persist(TYPE entity);

    void delete(ID id);

    TYPE get(ID id);

    Collection<TYPE> list();
}
