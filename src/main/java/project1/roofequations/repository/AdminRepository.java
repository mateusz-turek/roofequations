package project1.roofequations.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import project1.roofequations.model.AdminModel;


@Component
public interface AdminRepository extends CrudRepository<AdminModel,Long> {
}
