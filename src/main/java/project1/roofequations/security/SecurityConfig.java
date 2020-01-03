package project1.roofequations.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import project1.roofequations.model.AdminModel;
import project1.roofequations.repository.AdminRepository;

import java.util.Optional;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements AdminRepository {

    private AdminRepository adminRepository;

    public SecurityConfig(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("User")
                .password("User")
                .roles("USER")
                .build();

            UserDetails admin = User.withDefaultPasswordEncoder() //password should not be strict String
                    .username(adminRepository.findById(1L).get().getName())
                    .password(adminRepository.findById(1L).get().getPassword())
                    .roles("ADMIN")
                    .build();
            return new InMemoryUserDetailsManager(user,admin);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
         .antMatchers("/panel")
                .hasRole("ADMIN")
                .anyRequest()
                .hasAnyRole("ADMIN","USER")
                .and()
                .formLogin().permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Override
    public <S extends AdminModel> S save(S s) {
        return null;
    }

    @Override
    public <S extends AdminModel> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<AdminModel> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<AdminModel> findAll() {
        return null;
    }

    @Override
    public Iterable<AdminModel> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(AdminModel adminModel) {

    }

    @Override
    public void deleteAll(Iterable<? extends AdminModel> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
