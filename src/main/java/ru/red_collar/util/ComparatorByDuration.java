package ru.red_collar.util;

import java.util.Comparator;
import ru.red_collar.dto.FilmDto;
import ru.red_collar.entity.Order;

public class ComparatorByDuration implements Comparator<FilmDto> {

    private final Order order;

    public ComparatorByDuration(final Order order) {
        this.order = order;
    }

    @Override
    public int compare(final FilmDto o1, final FilmDto o2) {
        if (order.equals(Order.DESC)) {
            return Integer.compare(o2.getDuration(), o1.getDuration());
        }
        return Integer.compare(o1.getDuration(), o2.getDuration());
    }
}
