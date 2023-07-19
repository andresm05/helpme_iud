package co.edu.iudigital.helpmeiud.models.dto.request.mapper;

import co.edu.iudigital.helpmeiud.models.dto.request.ConsumerDtoRequest;
import co.edu.iudigital.helpmeiud.models.entities.Consumer;

public class ConsumerRequestMapper {
    
    private ConsumerDtoRequest consumerDtoRequest;

    private ConsumerRequestMapper() {
    }

    public static ConsumerRequestMapper builder() {
        return new ConsumerRequestMapper();
    }

    public ConsumerRequestMapper setConsumerDtoRequest(ConsumerDtoRequest consumerDtoRequest) {
        this.consumerDtoRequest = consumerDtoRequest;
        return this;
    }

    public Consumer doToEntity() {
        return new Consumer(
            consumerDtoRequest.getUsername(),
            consumerDtoRequest.getName(),
            consumerDtoRequest.getPassword(),
            consumerDtoRequest.getBornDate(),
            consumerDtoRequest.getSocialMedia(),
            consumerDtoRequest.getEnabled(),
            consumerDtoRequest.getImage(),
            consumerDtoRequest.getRoles()
        );
    }
}
