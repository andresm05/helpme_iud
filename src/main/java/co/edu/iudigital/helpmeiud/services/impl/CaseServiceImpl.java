package co.edu.iudigital.helpmeiud.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.iudigital.helpmeiud.exceptions.EntityNotFoundException;
import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.exceptions.UnauthorizedException;
import co.edu.iudigital.helpmeiud.models.dto.request.CaseDtoRequest;
import co.edu.iudigital.helpmeiud.models.dto.request.mapper.CaseRequestMapper;
import co.edu.iudigital.helpmeiud.models.dto.response.CaseDtoResponse;
import co.edu.iudigital.helpmeiud.models.dto.response.mapper.CaseDtoResponseMapper;
import co.edu.iudigital.helpmeiud.models.entities.Case;
import co.edu.iudigital.helpmeiud.models.entities.Consumer;
import co.edu.iudigital.helpmeiud.models.entities.Crime;
import co.edu.iudigital.helpmeiud.repositories.ICaseRepository;
import co.edu.iudigital.helpmeiud.repositories.IConsumerRepository;
import co.edu.iudigital.helpmeiud.repositories.ICrimeRepository;
import co.edu.iudigital.helpmeiud.services.iface.ICaseService;
import co.edu.iudigital.helpmeiud.utils.TokenSetUp;
import io.jsonwebtoken.Claims;

@Service
public class CaseServiceImpl implements ICaseService {

    @Autowired
    private ICaseRepository caseRepository;

    @Autowired
    private IConsumerRepository consumerRepository;

    @Autowired
    private ICrimeRepository crimeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CaseDtoResponse> findAll(Pageable pageable) {
        Page<Case> cases = caseRepository.findAll(pageable);
        return cases.getContent().stream().map(c -> CaseDtoResponseMapper.builder().setCase(c).build())
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public CaseDtoResponse create(CaseDtoRequest caseDtoRequest)
            throws RestException {
        Case caseEntity = CaseRequestMapper.builder().setCaseDtoRequest(caseDtoRequest)
                .doToEntity();

        String token = TokenSetUp.token;

        Claims claims = TokenSetUp.getClaims(token);
        String username = claims.getSubject();

        Consumer userCase = consumerRepository.findByUsername(username);

        if (userCase == null) {
            throw new EntityNotFoundException("User",
                    "Debe validar el token");
        }

        Optional<Crime> crimeCase = crimeRepository.findById(caseDtoRequest.getCrimeId());

        if (!crimeCase.isPresent()) {
            throw new EntityNotFoundException(Crime.class.getSimpleName(),
                    "Crime not found");
        }

        caseEntity.setConsumer(userCase);
        caseEntity.setCrime(crimeCase.get());

        return CaseDtoResponseMapper.builder()
                .setCase(caseRepository.save(caseEntity))
                .build();

    }

    @Override
    public List<CaseDtoResponse> findByConsumer() throws RestException {

        String token = TokenSetUp.token;

        Claims claims = TokenSetUp.getClaims(token);
        String username = claims.getSubject();
        Consumer consumer = consumerRepository.findByUsername(username);

        if (consumer == null) {
            throw new EntityNotFoundException("User", "User not found");
        }

        List<Case> cases = caseRepository.findByConsumer(consumer);
        return cases.stream()
                .map(c -> CaseDtoResponseMapper.builder()
                        .setCase(c).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CaseDtoResponse update(Long id, CaseDtoRequest caseDtoRequest)
            throws RestException {
        Optional<Case> caseEntity = caseRepository.findById(id);
        Case caseOptional = null;
        if (!caseEntity.isPresent()) {
            throw new EntityNotFoundException(Case.class.getSimpleName(), "not found");
        }
        String token = TokenSetUp.token;

        Claims claims = TokenSetUp.getClaims(token);
        String username = claims.getSubject();
        Consumer consumer = consumerRepository.findByUsername(username);

        if (consumer.getId() != caseEntity.get().getConsumer().getId()) {
            throw new UnauthorizedException(username + " is not authorized to update this case");
        }

        Optional<Crime> crimeCase = crimeRepository.findById(caseDtoRequest.getCrimeId());
        if (!crimeCase.isPresent()) {
            throw new EntityNotFoundException(Crime.class.getSimpleName(), "not found");
        }
        Case caseFound = caseEntity.orElseThrow();
        caseFound.setLatitude(caseDtoRequest.getLatitude());
        caseFound.setLongitude(caseDtoRequest.getLongitude());
        caseFound.setAltitude(caseDtoRequest.getAltitude());
        caseFound.setDescription(caseDtoRequest.getDescription());
        caseFound.setIsViewed(caseDtoRequest.getIsViewed());
        caseFound.setCrime(crimeCase.get());

        caseOptional = caseRepository.save(caseFound);
        if (caseOptional == null) {
            throw new UnsupportedOperationException("There was an error updating the case");
        }

        return CaseDtoResponseMapper.builder()
                .setCase(caseOptional)
                .build();

    }

    @Override
    @Transactional
    public void delete(Long id) throws RestException {

        Optional<Case> caseEntity = caseRepository.findById(id);
        if (!caseEntity.isPresent()) {
            throw new EntityNotFoundException(Case.class.getSimpleName(),
                    "not found");
        }
        caseRepository.deleteById(id);
    }

}
