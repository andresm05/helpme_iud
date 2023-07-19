package co.edu.iudigital.helpmeiud.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.iudigital.helpmeiud.exceptions.CrimeNotFoundException;
import co.edu.iudigital.helpmeiud.exceptions.NoValidUsernameException;
import co.edu.iudigital.helpmeiud.models.dto.request.CrimeDtoRequest;
import co.edu.iudigital.helpmeiud.models.dto.request.mapper.CrimeRequestMapper;
import co.edu.iudigital.helpmeiud.models.dto.response.CrimeDtoResponse;
import co.edu.iudigital.helpmeiud.models.dto.response.mapper.CrimeDtoResponseMapper;
import co.edu.iudigital.helpmeiud.models.entities.Consumer;
import co.edu.iudigital.helpmeiud.models.entities.Crime;
import co.edu.iudigital.helpmeiud.repositories.IConsumerRepository;
import co.edu.iudigital.helpmeiud.repositories.ICrimeRepository;
import co.edu.iudigital.helpmeiud.services.iface.ICrimeService;

@Service
public class CrimeServiceImpl implements ICrimeService {

    @Autowired
    private ICrimeRepository crimeRepository;

    @Autowired
    private IConsumerRepository consumerRepository;

    public CrimeServiceImpl(ICrimeRepository crimeRepository,
            IConsumerRepository consumerRepository) {
        this.crimeRepository = crimeRepository;
        this.consumerRepository = consumerRepository;
    }

    @Override
    @Transactional
    public CrimeDtoResponse create(CrimeDtoRequest crimeDtoRequest) throws NoValidUsernameException {
        Crime crime = CrimeRequestMapper.builder()
                .setCrimeDtoRequest(crimeDtoRequest)
                .dtoToEntity();

        Optional<Consumer> consumer = consumerRepository
                .findById(crimeDtoRequest.getUserId());

        if (!consumer.isPresent()) {
            throw new NoValidUsernameException("user id not found");
        }
        crime.setConsumer(consumer.get());
        this.crimeRepository.save(crime);

        return CrimeDtoResponseMapper
                .builder()
                .setCrime(crime)
                .build();

    }

    @Override
    public CrimeDtoResponse findById(Long id) throws CrimeNotFoundException {
        Optional<Crime> crime = crimeRepository.findById(id);
        if (crime.isPresent()) {
            return CrimeDtoResponseMapper.builder()
                    .setCrime(crime.get())
                    .build();
        }
        throw new CrimeNotFoundException("crime not found");
    }

    @Override
    @Transactional(readOnly = true)
    public List<CrimeDtoResponse> getAll() {
        List<Crime> crimes = crimeRepository.findAll();

        return crimes.stream().map(crime -> CrimeDtoResponseMapper.builder()
                .setCrime(crime)
                .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CrimeDtoResponse update(Long id, CrimeDtoRequest crimeDtoRequest) throws CrimeNotFoundException,
            NoValidUsernameException {
        Optional<Crime> crime = crimeRepository.findById(id);
        if (!crime.isPresent()) {
            throw new CrimeNotFoundException("crmie not found");
        }
        Optional<Consumer> consumerCrime = consumerRepository.findById(crimeDtoRequest.getUserId());

        if (!consumerCrime.isPresent()) {
            throw new NoValidUsernameException("user id not found");
        }
        Crime crimeToUpdate = crime.get();
        crimeToUpdate.setName(crimeDtoRequest.getName());
        crimeToUpdate.setDescription(crimeDtoRequest.getDescription());
        crimeToUpdate.setConsumer(consumerCrime.get());
        crimeRepository.save(crimeToUpdate);

        return CrimeDtoResponseMapper.builder()
                .setCrime(crimeToUpdate)
                .build();
    }

    @Override
    @Transactional
    public void delete(Long id) throws CrimeNotFoundException {
        Optional<Crime> crime = crimeRepository.findById(id);
        if (crime.isPresent()) {
            Crime crimeToDelete = crime.get();
            crimeRepository.deleteById(crimeToDelete.getId());
        } else {
            throw new CrimeNotFoundException("Crime not found");
        }
    }

}
