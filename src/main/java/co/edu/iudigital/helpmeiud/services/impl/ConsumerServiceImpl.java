package co.edu.iudigital.helpmeiud.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.iudigital.helpmeiud.exceptions.EntityNotFoundException;
import co.edu.iudigital.helpmeiud.exceptions.NoValidUsernameException;
import co.edu.iudigital.helpmeiud.exceptions.NotEnabledUserException;
import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.models.dto.request.ConsumerDtoRequest;
import co.edu.iudigital.helpmeiud.models.dto.request.mapper.ConsumerRequestMapper;
import co.edu.iudigital.helpmeiud.models.dto.response.ConsumerDtoResponse;
import co.edu.iudigital.helpmeiud.models.dto.response.mapper.ConsumerDtoResponseMapper;
import co.edu.iudigital.helpmeiud.models.entities.Consumer;
import co.edu.iudigital.helpmeiud.models.entities.EmailDetails;
import co.edu.iudigital.helpmeiud.models.entities.Role;
import co.edu.iudigital.helpmeiud.repositories.IConsumerRepository;
import co.edu.iudigital.helpmeiud.repositories.IRoleRepository;
import co.edu.iudigital.helpmeiud.services.iface.IConsumerService;
import co.edu.iudigital.helpmeiud.services.iface.IEmailService;
import jakarta.mail.MessagingException;

@Service
public class ConsumerServiceImpl implements IConsumerService {

    @Autowired
    private IConsumerRepository consumerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IEmailService emailService;

    // optional
    public ConsumerServiceImpl(IConsumerRepository consumerRepository,
            IRoleRepository roleRepository) {
        this.consumerRepository = consumerRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public ConsumerDtoResponse findByUsername(String username) {
        Consumer consumer = consumerRepository.findByUsername(username);
        return ConsumerDtoResponseMapper.builder()
                .setConsumer(consumer)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConsumerDtoResponse> findById(Long id) throws RestException {
        Optional<Consumer> consumerFound = consumerRepository.findById(id);
        if(!consumerFound.isPresent()){
            throw new EntityNotFoundException("User","user not found");
        }
        return consumerFound
                .map(consumer -> ConsumerDtoResponseMapper.builder()
                        .setConsumer(consumer)
                        .build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsumerDtoResponse> findAll(Pageable pagin) {
        Page<Consumer> consumers = consumerRepository.findByEnabled(true, pagin);
        return consumers.getContent().stream().map(consumer -> ConsumerDtoResponseMapper.builder()
                .setConsumer(consumer)
                .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ConsumerDtoResponse create(ConsumerDtoRequest consumerDtoRequest) 
    throws RestException, MessagingException {

        Consumer existConsumer = consumerRepository.findByUsername(consumerDtoRequest.getUsername());
        if (existConsumer != null) {
            throw new NoValidUsernameException("username already exist");
        }
        Consumer consumer = ConsumerRequestMapper.builder()
                .setConsumerDtoRequest(consumerDtoRequest)
                .doToEntity();

        consumer.setPassword(passwordEncoder.encode(consumerDtoRequest.getPassword()));
        List<Role> roles = new ArrayList<>();
        Optional<Role> role = roleRepository.findByName("ROLE_USER");
        if (role.isPresent()) {
            roles.add(role.get());
        }
        consumer.setRoles(roles);
        consumerRepository.save(consumer);

        EmailDetails emailDetails = new EmailDetails(
                consumer.getUsername(),
                "Bienvenido a HelpMeIUD",
                "Registro exitoso",
                null
        );

        emailService.sendSimpleMail(emailDetails);

        return ConsumerDtoResponseMapper.builder()
                .setConsumer(consumer)
                .build();
    }

    @Override
    @Transactional
    public ConsumerDtoResponse update(Long id, ConsumerDtoRequest consumerDtoRequest) 
    throws RestException {
        Optional<Consumer> consumer = consumerRepository.findById(id);
        Consumer consumerOptional = null;
        if (!consumer.isPresent()) {
            throw new EntityNotFoundException("User","user not found");
        }
        Consumer consumerFound = consumer.orElseThrow(() -> new RuntimeException("Consumer not found"));
        consumerFound.setName(consumerDtoRequest.getName());
        consumerFound.setPassword(passwordEncoder.encode(consumerDtoRequest.getPassword()));
        consumerFound.setBornDate(consumerDtoRequest.getBornDate());
        consumerFound.setSocialMedia(consumerDtoRequest.getSocialMedia());
        consumerFound.setImage(consumerDtoRequest.getImage());
        consumerOptional = consumerRepository.save(consumerFound);
        if (consumerOptional != null) {
            return ConsumerDtoResponseMapper.builder().setConsumer(consumerOptional).build();
        }

        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    @Transactional
    public void delete(Long id) throws RestException {
        Consumer consumer = consumerRepository.findById(id).orElse(null);
        if (consumer != null) {
            if(!consumer.getEnabled()){
                throw new NotEnabledUserException("user not enabled");
            }
            consumer.setEnabled(false);
            consumerRepository.save(consumer);
        } else {
            throw new EntityNotFoundException("User","user not found");
        }
    }

}
