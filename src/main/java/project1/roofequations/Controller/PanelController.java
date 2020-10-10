package project1.roofequations.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import project1.roofequations.model.HGirderModel;
import project1.roofequations.repository.HGirderRepository;

import java.util.Optional;

@Controller
public class PanelController  {

    private HGirderRepository hGirderRepository;

    @Autowired
    public PanelController(@Qualifier("HGirderRepository") HGirderRepository hGirderRepository) {
        this.hGirderRepository = hGirderRepository;
    }

    @GetMapping(value = "/panel")
    public String panel() {
        return "Panel";
    }
    @GetMapping (value = "/panel/saved")
    public String saved(@RequestParam(required = false) Double ownWeight,
                        @RequestParam(required = false) Double momentOfInertia,
                        @RequestParam(required = false) Double modulusOfElasticity,
                        @RequestParam(required = false) Double momentOfSecondDegreeInertia,
                        @RequestParam(required = false) Double height,
                        @RequestParam(required = false) Double width,
                        ModelMap map) {
        HGirderModel hGirderModel = new HGirderModel();
        hGirderModel.setOwnWeight(ownWeight);
        hGirderModel.setMomentOfInertia(momentOfInertia);
        hGirderModel.setModulusOfElasticity(modulusOfElasticity);
        hGirderModel.setMomentOfSecondDegreeInertia(momentOfSecondDegreeInertia);
        hGirderModel.setHeight(height);
        hGirderModel.setWidth(width);

        hGirderRepository.save(hGirderModel);

        map.put("a", hGirderModel);
        return "Saved";
    }
    @RequestMapping(value = "/panel/{materials}")
    @ResponseBody
    public ResponseEntity<Object> findAllById(@PathVariable String materials){
        return new ResponseEntity<>(hGirderRepository.findAll(), HttpStatus.OK);
    }

}
