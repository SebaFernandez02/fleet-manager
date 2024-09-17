package ar.edu.ungs.fleet_manager.shared.domain;

import java.util.List;

public record Pagination<T>(Integer size, Integer page, List<T> values) {
    @Override
    public String toString() {
        return "Pagination{" +
                "size=" + size +
                ", page=" + page +
                ", values=" + values +
                '}';
    }
}
