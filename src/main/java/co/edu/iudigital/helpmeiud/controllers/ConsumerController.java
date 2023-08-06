package co.edu.iudigital.helpmeiud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.models.dto.request.ConsumerDtoRequest;
import co.edu.iudigital.helpmeiud.models.dto.response.ConsumerDtoResponse;
import co.edu.iudigital.helpmeiud.services.iface.IConsumerService;
import co.edu.iudigital.helpmeiud.utils.IControllerMethods;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class ConsumerController {


    @Autowired
    private IConsumerService consumerService;

    @GetMapping
    public ResponseEntity<List<ConsumerDtoResponse>> getAll(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size) {

        // this is for pagination
        Pageable pagin = PageRequest.of(page, size);

        token = token.replace("Bearer ", "");
        System.out.println("token: " + token);

        List<ConsumerDtoResponse> consumersDtoResponse = consumerService.findAll(pagin);

        return ResponseEntity.ok().body(consumersDtoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) throws RestException {
        ConsumerDtoResponse consumerDtoResponse = null;

        Optional<ConsumerDtoResponse> consumer = consumerService.findById(id);
        consumerDtoResponse = consumer.get();

        if (consumerDtoResponse == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // response 500
        }

        return ResponseEntity.ok().body(consumerDtoResponse);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ConsumerDtoRequest consumerDtoRequest,
            BindingResult result) throws RestException, MessagingException {

        if (result.hasErrors()) {
            return IControllerMethods.validateRequest(result);
        }

        ConsumerDtoResponse consumerDtoRequestCreated;

        consumerDtoRequestCreated = consumerService
                .create(consumerDtoRequest);

        if (consumerDtoRequestCreated == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(consumerDtoRequestCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id,
            @RequestBody ConsumerDtoRequest consumerDtoRequest,
            BindingResult result) throws RestException {
        if (result.hasErrors()) {
            return IControllerMethods.validateRequest(result);
        }
        ConsumerDtoResponse consumerDtoResponse = consumerService.update(id, consumerDtoRequest);

        if (consumerDtoResponse == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // response 500
        }

        return ResponseEntity.ok().body(consumerDtoResponse);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
            throws RestException {

        consumerService.delete(id);

        // return status 204
        return ResponseEntity.noContent().build();
    }

@GetMapping("/renew")
//renew token
public ResponseEntity<?> renewToken(@RequestHeader("Authorization") String token) throws RestException {
    String renew = consumerService.renewToken(token);
    return ResponseEntity.ok().body(renew);
}

}
