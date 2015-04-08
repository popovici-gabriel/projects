package com.jpmorgan.model;

import java.io.Serializable;

/**
 * Group ID.
 *
 * @param <ID>
 */
public class Group<ID> implements Serializable {

    private ID id;

    public Group(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (!id.equals(group.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}