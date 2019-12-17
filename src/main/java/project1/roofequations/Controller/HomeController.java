package project1.roofequations.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project1.roofequations.model.RoofDimensionsMap;
import project1.roofequations.model.RoofModel;
import project1.roofequations.repository.RoofRepository;
import project1.roofequations.service.HomeControllerService;


@Controller
public class HomeController extends HomeControllerService {

    @Autowired
    private RoofRepository roofRepository;

    @GetMapping("/")
    public String PrimaryAttributes() {

        return "PrimaryRoof";
    }

    @GetMapping("/page1")
    public String Primary(@RequestParam(required = false) String width,
                          @RequestParam(required = false) Double angle,
                          ModelMap map) {
        if (!widthValidator(width) ||
                !angleValidator(angle) ||
                widthChanger(width) == null ||
                widthChanger(width) <= 0) {
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
}
