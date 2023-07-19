package co.edu.iudigital.helpmeiud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.models.dto.request.CrimeDtoRequest;
import co.edu.iudigital.helpmeiud.models.dto.response.CrimeDtoResponse;
import co.edu.iudigital.helpmeiud.services.iface.ICrimeService;
import co.edu.iudigital.helpmeiud.utils.IControllerMethods;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/crimes")
public class CrimeController {

    @Autowired
    ICrimeService crimeService;

    @GetMapping
    public ResponseEntity<List<CrimeDtoResponse>> getAll() {
        return ResponseEntity.ok().body(crimeService.getAll());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody CrimeDtoRequest crimeDtoRequest,
            BindingResult result) throws RestException {
        if (result.hasErrors()) {
            return IControllerMethods.validateRequest(result);
        }

        CrimeDtoResponse crimeDtoResponse = null;
        crimeDtoResponse = crimeService.create(crimeDtoRequest);
        if (crimeDtoResponse != null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(crimeDtoResponse); // response 201
        }
        // return response 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // response 500
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) throws RestException {
        CrimeDtoResponse crimeDtoResponse = null;
        crimeDtoResponse = crimeService.findById(id);
        if (crimeDtoResponse != null) {
            return ResponseEntity.ok().body(crimeDtoResponse); // response 200
        }
        return ResponseEntity.notFound().build(); // response 404
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Valid @PathVariable Long id,
            @RequestBody CrimeDtoRequest crimeDtoRequest,
            BindingResult result) throws RestException {
        if (result.hasErrors()) {
            return IControllerMethods.validateRequest(result);
        }

        CrimeDtoResponse crimeDtoResponse = null;

        crimeDtoResponse = crimeService.update(id, crimeDtoRequest);

        if (crimeDtoResponse != null) {
            return ResponseEntity.ok().body(crimeDtoResponse); // response 200
        }
        return ResponseEntity.notFound().build(); // response 404
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
            throws RestException {
        crimeService.delete(id);

        return ResponseEntity.noContent().build(); // response 204
    }
}
