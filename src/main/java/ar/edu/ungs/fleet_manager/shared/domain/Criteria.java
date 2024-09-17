package ar.edu.ungs.fleet_manager.shared.domain;

import java.util.Objects;

public abstract class Criteria {
    private final Integer size;
    private final Integer page;

    public Criteria(Integer size, Integer page) {
        this.size = size;
        this.page = page;
    }

    public Integer size() {
        return size;
    }

    public Integer page() {
        return page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Criteria criteria = (Criteria) o;
        return Objects.equals(size, criteria.size) && Objects.equals(page, criteria.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, page);
    }

    @Override
    public String toString() {
        return "Criteria{" +
                "size=" + size +
                ", page=" + page +
                '}';
    }
}
