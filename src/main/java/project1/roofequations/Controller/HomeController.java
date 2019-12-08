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
    public String Primary(@RequestParam(required = false) String width,
                          @RequestParam(required = false) Double angle,
                          ModelMap map) {

        StringBuilder widthChanger = new StringBuilder(width);
        if (width.equals("")||
                width.matches(".*[a-zA-Z]+.*")||
                width.startsWith(",")||
                width.endsWith(",")||
                width.endsWith(".")||
                width.matches(".*[!@#$%^&*()/?><|`~:+]+.*")||
                width.contains("*")) {
            return "WrongAttributes";
        }

        if (width.contains(",")) {
            int counter = 0;
            for (int i = 0; i < width.length(); i++) {
                if (width.charAt(i) == ',') {
                    break;
                }
                counter = counter + 1;
            }
            for (int i = counter+1; i <width.length(); i++) {
                if (width.charAt(i)==','||width.charAt(i)=='.')
                    return "WrongAttributes";

            }
            widthChanger.replace(counter, counter + 1, ".");
        }
        Double widthNumber = Double.valueOf(widthChanger.toString());

        if (angle == null || widthNumber <= 0) {
            return "WrongAttributes";
        }
        RoofModel roof = new RoofModel();
        roof.setWidth(widthNumber);
        roof.setAngle(angle);
        roof.setProportion();
        roof.setRoofHeight(roof.getWidth(), roof.getAngle());
        roof.setLengthOfRafter(roof.getWidth(), roof.getAngle());
        roof.setLowerPartOfRafter(roof.getProportion(), roof.getLengthOfRafter());
        roof.setUpperPartOfRafter(roof.getLengthOfRafter(), roof.getLowerPartOfRafter());

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
