package project1.roofequations.repository;

import org.springframework.data.repository.CrudRepository;
import project1.roofequations.model.RoofModel;

public interface RoofRepository extends CrudRepository<RoofModel,Long> {
}
