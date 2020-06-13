package com.jds.loaderapi;

import com.jds.loaderapi.entity.Authority;
import com.jds.loaderapi.entity.User;
import com.jds.loaderapi.repository.AuthorityRepository;
import com.jds.loaderapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class LoaderApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoaderApiApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public CommandLineRunner addBaseUsers(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder encoder) {
        return args -> {
            Authority admin = new Authority("admin");
            Authority user = new Authority("user");
            Authority readonly = new Authority("readonly");

            authorityRepository.save(admin);
            authorityRepository.save(user);
            authorityRepository.save(readonly);

            userRepository.save(new User("josh", encoder.encode("temp"), true, List.of(admin, user)));
            userRepository.save(new User("rhian", encoder.encode("temp"), true, List.of(user)));
            userRepository.save(new User("basic", encoder.encode("temp"), true, List.of(readonly)));
        };
    }
}
