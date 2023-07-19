package co.edu.iudigital.helpmeiud.models.dto.response.mapper;

import co.edu.iudigital.helpmeiud.models.dto.response.ConsumerDtoResponse;
import co.edu.iudigital.helpmeiud.models.entities.Consumer;

public class ConsumerDtoResponseMapper {
    
    private Consumer consumer;

    private ConsumerDtoResponseMapper() {
    }

    public static ConsumerDtoResponseMapper builder() {
        return new ConsumerDtoResponseMapper();
    }

    public ConsumerDtoResponseMapper setConsumer(Consumer consumer) {
        this.consumer = consumer;
        return this;
    }

    public ConsumerDtoResponse build() {
        return ConsumerDtoResponse.builder()
                .id(this.consumer.getId())
                .username(this.consumer.getUsername())
                .name(this.consumer.getName())
                .image(this.consumer.getImage())
                .bornDate(this.consumer.getBornDate())
                .build();
    }

}
