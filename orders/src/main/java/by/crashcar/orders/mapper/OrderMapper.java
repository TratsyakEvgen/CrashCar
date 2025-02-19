package by.crashcar.orders.mapper;

import by.crashcar.core.dto.CreateOrderEvent;
import by.crashcar.orders.dto.CreateOrder;
import by.crashcar.orders.entity.Media;
import by.crashcar.orders.entity.Order;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class OrderMapper {
    @Value("${media.download}")
    private String link;

    public abstract Order toOrder(CreateOrder createOrder);

    @Mapping(target = "links", source = "media", qualifiedByName = "getLinks")
    public abstract CreateOrderEvent toCreateOrderMessage(Order order);

    @Named("getLinks")
    protected List<String> getLinks(List<Media> media) {
        return media.stream()
                .map(m -> String.format(link, m.getId(), m.getSecret()))
                .toList();
    }


}