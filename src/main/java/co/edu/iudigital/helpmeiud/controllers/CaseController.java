package co.edu.iudigital.helpmeiud.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.models.dto.request.CaseDtoRequest;
import co.edu.iudigital.helpmeiud.models.dto.response.CaseDtoResponse;
import co.edu.iudigital.helpmeiud.services.iface.ICaseService;
import co.edu.iudigital.helpmeiud.utils.IControllerMethods;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cases")
public class CaseController {

    @Autowired
    private ICaseService caseService;

    @GetMapping
    public ResponseEntity<?> findByConsumer() throws RestException {

        List<CaseDtoResponse> cases = caseService.findByConsumer();
        return ResponseEntity.ok().body(cases);

    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size) {
        Pageable pagin = PageRequest.of(page, size);
        List<CaseDtoResponse> cases = caseService.findAll(pagin);
        return ResponseEntity.ok().body(cases);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CaseDtoRequest caseDtoRequest, BindingResult result)
            throws RestException {
        if (result.hasErrors()) {
            return IControllerMethods.validateRequest(result);
        }

        CaseDtoResponse caseDtoResponse = caseService.create(caseDtoRequest);

        if (caseDtoResponse == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // response 500
        }
        return ResponseEntity.ok().body(caseDtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id,
            @RequestBody CaseDtoRequest caseDtoRequest, BindingResult result) 
            throws RestException {
        if (result.hasErrors()) {
            return IControllerMethods.validateRequest(result);
        }
        CaseDtoResponse caseDtoResponse;

            caseDtoResponse = caseService.update(id, caseDtoRequest);

        if (caseDtoResponse == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // response 500
        }
        return ResponseEntity.ok().body(caseDtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) 
    throws RestException {

            caseService.delete(id);

        return ResponseEntity.ok().build();
    }

}
