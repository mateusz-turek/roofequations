package project1.roofequations.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import project1.roofequations.model.HGirderModel;

@Component (value = "HGirder")
public interface HGirderRepository extends CrudRepository<HGirderModel,Long> {
}
