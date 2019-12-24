package project1.roofequations.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import project1.roofequations.model.AdminModel;

import java.util.Optional;


public interface AdminRepository extends CrudRepository<AdminModel,Long> {

    Optional<AdminModel> findAdminModelByName (String name);

   // @Query("SELECT a FROM AdminModel a WHERE a.name=?1")
   // AdminModel findAdminModelByName(String name);

}
