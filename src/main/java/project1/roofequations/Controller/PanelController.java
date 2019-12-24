package project1.roofequations.Controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project1.roofequations.model.HGirderModel;
import project1.roofequations.repository.HGirderRepository;

import java.util.Optional;

@Controller
public class PanelController implements HGirderRepository {
    private HGirderRepository hGirderRepository;

    public PanelController(@Qualifier("HGirderRepository") HGirderRepository hGirderRepository) {
        this.hGirderRepository = hGirderRepository;
    }

    @GetMapping("/panel/saved")
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

    @Override
    public <S extends HGirderModel> S save(S s) {
        return null;
    }

    @Override
    public <S extends HGirderModel> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<HGirderModel> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<HGirderModel> findAll() {
        return null;
    }

    @Override
    public Iterable<HGirderModel> findAllById(Iterable<Long> iterable) {
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
    public void delete(HGirderModel hGirderModel) {

    }

    @Override
    public void deleteAll(Iterable<? extends HGirderModel> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
