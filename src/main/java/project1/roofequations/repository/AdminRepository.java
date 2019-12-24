package project1.roofequations.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Component;
import project1.roofequations.model.AdminModel;

import java.util.Optional;

@Component
public interface AdminRepository extends CrudRepository<AdminModel,Long> {

    Optional<AdminModel> findAdminModelByName (String name);

    @Query(value = "SELECT * FROM admins where id=?1 Limit 1",nativeQuery = true)
    AdminModel findPasswordByName (Long id);
   // @Query("SELECT a FROM AdminModel a WHERE a.name=?1")
   // AdminModel findAdminModelByName(String name);
   @Query("SELECT p FROM AdminModel p WHERE p.name=?1")
   AdminModel findPassByName (String name);

}
