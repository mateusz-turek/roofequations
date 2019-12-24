package project1.roofequations.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import project1.roofequations.model.AdminModel;
import project1.roofequations.repository.AdminRepository;

import java.util.Optional;

@Component
public class AppRunner implements ApplicationRunner, AdminRepository {


    private AdminRepository adminRepository;

    public AppRunner(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (adminRepository.count()==0){
            adminRepository.save(new AdminModel("Admin","Admin"));
        }
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
