/*
 * CrudDao.java
 *
 * @formatter:off
 *
 * @author Andrew
 *
 * Created: 16 Jan 2019
 *
 * @formatter:on
 */

package com.github.awferg.restex.database;

import java.util.List;

public interface CrudDao<T> {

    int count();

    void delete(int id);

    boolean exists(int id);

    T find(int id);

    List<T> findAll();

    int insert(T record);

    void update(T record);
}
