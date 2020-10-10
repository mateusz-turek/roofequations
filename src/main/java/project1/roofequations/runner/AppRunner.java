package project1.roofequations.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import project1.roofequations.model.AdminModel;
import project1.roofequations.repository.AdminRepository;

import java.util.Optional;

@Component
public class AppRunner implements ApplicationRunner {

    private AdminRepository adminRepository;

    @Autowired
    public AppRunner(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (adminRepository.count()==0){
            adminRepository.save(new AdminModel("Admin","Admin1"));
        }
    }

}
