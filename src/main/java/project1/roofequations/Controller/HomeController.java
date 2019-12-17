package project1.roofequations.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project1.roofequations.model.RoofDimensionsMap;
import project1.roofequations.model.RoofModel;
import project1.roofequations.repository.RoofRepository;
import project1.roofequations.service.HomeControllerService;

import java.util.Optional;


@Controller
public class HomeController extends HomeControllerService implements RoofRepository {


    private RoofRepository roofRepository;

    public HomeController(RoofRepository roofRepository) {
        this.roofRepository = roofRepository;
    }

    @GetMapping("/")
    public String PrimaryAttributes() {

        return "PrimaryRoof";
    }

    @GetMapping("/PrimaryAttributes")
    public String Primary(@RequestParam(required = false) String width,
                          @RequestParam(required = false) Double angle,
                          ModelMap map) {
        if (!widthValidator(width) ||
                !angleValidator(angle) ||
                widthChanger(width) == null||
                widthChanger(width) <=0)
         {
            return "WrongAttributes";
        } else {

            RoofModel roof = new RoofModel();
            roof.setWidth(widthChanger(width));
            roof.setAngle(angle);
            roof.setProportion();
            roof.setRoofHeight(roof.getWidth(), roof.getAngle());
            roof.setLengthOfRafter(roof.getWidth(), roof.getAngle());
            roof.setLowerPartOfRafter(roof.getProportion(), roof.getLengthOfRafter());
            roof.setUpperPartOfRafter(roof.getLengthOfRafter(), roof.getLowerPartOfRafter());

            roofRepository.save(roof);

            RoofDimensionsMap dimensionsMap = new RoofDimensionsMap();
            dimensionsMap.addRoofDimension("Width", roof.getWidth());
            dimensionsMap.addRoofDimension("Angle", roof.getAngle());
            dimensionsMap.addRoofDimension("Proportion", roof.getProportion());
            dimensionsMap.addRoofDimension("Roof Height", roof.getRoofHeight());
            dimensionsMap.addRoofDimension("Length of Rafter", roof.getLengthOfRafter());
            dimensionsMap.addRoofDimension("Length of lower part of a rafter",
                    roof.getLowerPartOfRafter());
            dimensionsMap.addRoofDimension("Length of upper part of a rafter",
                    roof.getUpperPartOfRafter());

            map.put("values", dimensionsMap.toString());

            return "RoofDimensions";
        }
    }

    @Override
    public <S extends RoofModel> S save(S s) {
        return null;
    }

    @Override
    public <S extends RoofModel> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<RoofModel> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<RoofModel> findAll() {
        return null;
    }

    @Override
    public Iterable<RoofModel> findAllById(Iterable<Long> iterable) {
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
    public void delete(RoofModel roofModel) {

    }

    @Override
    public void deleteAll(Iterable<? extends RoofModel> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
