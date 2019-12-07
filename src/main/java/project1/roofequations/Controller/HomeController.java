package project1.roofequations.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project1.roofequations.model.RoofModel;

@Controller
public class HomeController extends RoofModel {
    @GetMapping("/")
    public String PrimaryAtributes() {

        return "PrimaryRoof";
    }
    @GetMapping("/page1")
    public String Primary (@RequestParam Double width, @RequestParam int angle,ModelMap map) {
        RoofModel roof = new RoofModel();
        roof.setWidth(width);
        roof.setAngle(angle);
        roof.setProportion();
        roof.setRoofHeight(roof.getWidth(),roof.getAngle());
        roof.setLengthOfRafter(roof.getWidth(),roof.getAngle());
        roof.setLowerPartOfRafter(roof.getProportion(),roof.getLengthOfRafter());
        roof.setUpperPartOfRafter(roof.getLengthOfRafter(),roof.getLowerPartOfRafter());

        map.put("values",roof.toString());
        return "RoofDimensions";
    }
}
