package dev.aman.e_commerce_user_service.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aman.e_commerce_user_service.DTOs.SendEmailDTOs;
import dev.aman.e_commerce_user_service.Models.Tokens;
import dev.aman.e_commerce_user_service.Models.User;
import dev.aman.e_commerce_user_service.Repository.TokensRepository;
import dev.aman.e_commerce_user_service.Repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Date;

@Service
public class SelfUserService implements UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    TokensRepository tokensRepository;
    KafkaTemplate<String, String> kafkaTemplate;
    //Jackson library to convert our DTO object to JSON String
    ObjectMapper objectMapper;

    public SelfUserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokensRepository tokensRepository, KafkaTemplate<String, String> kafkaTemplate,
                           ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokensRepository = tokensRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Tokens login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User userDetails = user.get();
        //First parameter is raw password and other one is DB stored password
        if(bCryptPasswordEncoder.matches(password, userDetails.getHashPassword())){
            //Generate Token
            Tokens tokens = generateToken(userDetails);
            //Saving tokens in DB
            Tokens savedTokens = tokensRepository.save(tokens);
            return savedTokens;
        }
        return null;
    }

    @Override
    public User signUp(String name,String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        //Before storing password in DB we need to encrypt it beforehand
        user.setHashPassword(bCryptPasswordEncoder.encode(password));

        //Implementing Kafka
        SendEmailDTOs sendEmailDTOs = new SendEmailDTOs();
        sendEmailDTOs.setTo(email);
        sendEmailDTOs.setSubject("Welcome to Commerce User Service");
        sendEmailDTOs.setBody("We are happy to have you");
        try {
            kafkaTemplate.send("sendEmail"
                    //Instead of sending DTOs object we are sending JSON String over network
                    ,objectMapper.writeValueAsString(sendEmailDTOs));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return userRepository.save(user);
    }

    @Override
    public void logout(String token) {
        Optional<Tokens> optionalTokens = tokensRepository.findByValueAndDeletedAndExpiryAtGreaterThan(token, false, new Date());
        if(optionalTokens.isEmpty())
            throw new RuntimeException("Token not found");
        Tokens tokens = optionalTokens.get();
        //Marking deleted method as true
        tokens.setDeleted(true);
        tokensRepository.save(tokens);
    }

    @Override
    public User validateUser(String token) {
        //First we need to find if value is there in DB or not
        //Also need to check expiry time
        Optional<Tokens> DBTokens = tokensRepository.findByValueAndDeletedAndExpiryAtGreaterThan(token, false, new Date());
        if(DBTokens.isEmpty()) {
            return null;
        }
        //Returning the user after validating
        //get() method as we are working on optional object
        //First it will get the object than it will get the user
        return DBTokens.get().getUser();
    }

    public Tokens generateToken(User user) {
        Tokens tokens = new Tokens();
        tokens.setUser(user);
        //Generating 128 character random character using Apache Commons Lang
        tokens.setValue(RandomStringUtils.randomAlphanumeric(128));
        //Expiry time is 30 days ahead from creating time
        LocalDate current = LocalDate.now();
        LocalDate expire = current.plusDays(30);
        //Converting LocalDate to Date
        Date tokenExpiry = Date.from(expire.atStartOfDay(ZoneId.systemDefault()).toInstant());
        tokens.setExpiryAt(tokenExpiry);
        return tokens;
    }
}
