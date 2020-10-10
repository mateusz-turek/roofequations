package project1.roofequations.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import project1.roofequations.model.HGirderModel;

@Repository
public interface HGirderRepository extends CrudRepository<HGirderModel,Long> {
}
