package project1.roofequations.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project1.roofequations.model.RoofDimensionsMap;
import project1.roofequations.model.RoofModel;

@Controller
public class HomeController extends RoofModel {
    @GetMapping("/")
    public String PrimaryAtributes() {

        return "PrimaryRoof";
    }
    @GetMapping("/page1")
    public String Primary (@RequestParam Double width, @RequestParam Double angle,ModelMap map) {
        RoofModel roof = new RoofModel();

        roof.setWidth(width);
        roof.setAngle(angle);
        roof.setProportion();
        roof.setRoofHeight(roof.getWidth(),roof.getAngle());
        roof.setLengthOfRafter(roof.getWidth(),roof.getAngle());
        roof.setLowerPartOfRafter(roof.getProportion(),roof.getLengthOfRafter());
        roof.setUpperPartOfRafter(roof.getLengthOfRafter(),roof.getLowerPartOfRafter());

        RoofDimensionsMap dimensionsMap = new RoofDimensionsMap();
        dimensionsMap.addRoofDimension("Width",roof.getWidth());
        dimensionsMap.addRoofDimension("Angle",roof.getAngle());
        dimensionsMap.addRoofDimension("Proportion",roof.getProportion());
        dimensionsMap.addRoofDimension("Roof Height",roof.getRoofHeight());
        dimensionsMap.addRoofDimension("Length of Rafter",roof.getLengthOfRafter());
        dimensionsMap.addRoofDimension("Length of lower part of a rafter",
                roof.getLowerPartOfRafter());
        dimensionsMap.addRoofDimension("Length of upper part of a rafter",
                roof.getUpperPartOfRafter());

        map.put("values",dimensionsMap.toString());

        return "RoofDimensions";
    }
}
