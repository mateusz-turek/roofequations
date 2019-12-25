package project1.roofequations.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project1.roofequations.model.AdminModel;
import project1.roofequations.repository.AdminRepository;

import java.util.Optional;

@Controller
public class AdminController implements AdminRepository {

    private AdminRepository adminRepository;

    public AdminController( AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

   // @GetMapping("/admin")
   // public String adminLogin() {
   //     return "AdminLogin";
   // }

    @GetMapping(value = "/panel")
    public String panel(
            //@RequestParam(required = false) String adminName,
              //          @RequestParam(required = false) String adminPassword,
                        ModelMap map) {
       // if (adminRepository.findAdminModelByName(adminName).isPresent()) {
         //   if (adminRepository.findPassByName(adminName).getPassword().equals(adminPassword)) {

                //map.put("a", adminRepository.findAdminModelByName(adminName).isPresent());
                //map.put("b", adminRepository.findAdminModelByName(adminName).get().getName());
                //map.put("c", adminRepository.findAdminModelByName(adminName).get().getPassword());
                return "Panel";
          //  }
        }


       // return "WrongAttributes";
    //}

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


    @Override
    public Optional<AdminModel> findAdminModelByName(String name) {
        return Optional.empty();
    }

    @Override
    public AdminModel findPasswordByName(Long id) {
        return null;
    }

    @Override
    public AdminModel findPassByName(String name) {
        return null;
    }
}
