package project1.roofequations.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project1.roofequations.Interface.StaticEquations;
import project1.roofequations.model.*;
import project1.roofequations.repository.HGirderRepository;
import project1.roofequations.service.HomeControllerService;

import java.util.Optional;


@Controller
public class HomeController extends HomeControllerService implements HGirderRepository,StaticEquations {
    private HGirderRepository hGirderRepository;

    @Autowired
    private StaticEquations staticEquations;


    public HomeController(@Qualifier("HGirderRepository") HGirderRepository hGirderRepository) {
        this.hGirderRepository = hGirderRepository;

    }


    @GetMapping("/")
    public String PrimaryAttributes(ModelMap map) {
        map.put("H",hGirderRepository.findAll().toString());

        return "PrimaryRoof";
    }

    @GetMapping("/PrimaryAttributes")
    public String Primary(@RequestParam(required = false) String width,
                          @RequestParam(required = false) Double angle,
                          @RequestParam(required = false) String city,
                          @RequestParam(required = false) Double zone,
                          // TODO: 2019-12-27 change double to string and parse it to double with validation methods
                          @RequestParam(required = false) Double high,
                          @RequestParam(required = false) Double heightOfBuilding,
                          @RequestParam(required = false) Double spacing,
                          @RequestParam(required = false) Double userOwnBuildingStrain,
                       //   @RequestParam(required = false) Object hGirder,
                          ModelMap map) {



        if (!widthValidator(width) ||
                !angleValidator(angle) ||
                !cityValidator(city) ||
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

            SnowStrainModel snowStrain = new SnowStrainModel();
            snowStrain.setCoefficientOfRoofShapeOfMajorPour(angle);
            snowStrain.setCoefficientOfRoofShapeOfMinorPour(angle);
            snowStrain.setCoefficientOfVariableStrain();
            snowStrain.setCoefficientOfExposure(city);
            snowStrain.setThermalCoefficient(city);
            snowStrain.setCharacteristicStrainOfSnow();
            snowStrain.setCharacteristicStrainOfMinorPour(snowStrain.getCoefficientOfRoofShapeOfMinorPour(),
                    snowStrain.getCoefficientOfExposure(), snowStrain.getThermalCoefficient(), snowStrain.getCharacteristicStrainOfSnow());
            snowStrain.setCharacteristicStrainOfMajorPour(snowStrain.getCoefficientOfRoofShapeOfMajorPour(),
                    snowStrain.getCoefficientOfExposure(), snowStrain.getThermalCoefficient(), snowStrain.getCharacteristicStrainOfSnow());
            snowStrain.setComputationalStrainOfMinorPour(snowStrain.getCharacteristicStrainOfMinorPour(),
                    snowStrain.getCoefficientOfVariableStrain());
            snowStrain.setComputationalStrainOfMajorPour(snowStrain.getCharacteristicStrainOfMajorPour(),
                    snowStrain.getCoefficientOfVariableStrain());

            RoofDimensionsMap snowStrainMap = new RoofDimensionsMap();
            snowStrainMap.addRoofDimension("CoefficientOfRoofShapeOfMajorPour",
                    snowStrain.getCoefficientOfRoofShapeOfMajorPour());
            snowStrainMap.addRoofDimension("CoefficientOfRoofShapeOfMinorPour",
                    snowStrain.getCoefficientOfRoofShapeOfMinorPour());
            snowStrainMap.addRoofDimension("CoefficientOfExposure",
                    snowStrain.getCoefficientOfExposure());
            snowStrainMap.addRoofDimension("ThermalCoefficient",
                    snowStrain.getThermalCoefficient());
            snowStrainMap.addRoofDimension("CharacteristicStrainOfSnow",
                    snowStrain.getCharacteristicStrainOfSnow());
            snowStrainMap.addRoofDimension("CoefficientOfVariableStrain",
                    snowStrain.getCoefficientOfVariableStrain());
            snowStrainMap.addRoofDimension("CharacteristicStrainOfMinorPour",
                    snowStrain.getCharacteristicStrainOfMinorPour());
            snowStrainMap.addRoofDimension("CharacteristicStrainOfMajorPour",
                    snowStrain.getCharacteristicStrainOfMajorPour());
            snowStrainMap.addRoofDimension("ComputationalStrainOfMinorPour",
                    snowStrain.getComputationalStrainOfMinorPour());
            snowStrainMap.addRoofDimension("ComputationalStrainOfMajorPour",
                    snowStrain.getComputationalStrainOfMajorPour());

            map.put("values1", snowStrainMap.toString());

            WindStrainModel windStrain = new WindStrainModel();
            windStrain.setBaseSpeedOfWind(high, zone);
            windStrain.setActualBaseSpeedOfWind(windStrain.getBaseSpeedOfWind(),
                    windStrain.getCoefficientOfDirection(), windStrain.getCoefficientOfSeason());
            windStrain.setBaseValueOfSpeedPressure(windStrain.getAirDensity(), windStrain.getActualBaseSpeedOfWind());
            windStrain.setCoefficientOfExposition(heightOfBuilding);
            windStrain.setTopValueOfSpeedPressure(windStrain.getCoefficientOfExposition()
                    , windStrain.getBaseValueOfSpeedPressure());
            windStrain.setPressureOfWindwardSide(windStrain.getTopValueOfSpeedPressure());
            windStrain.setPressureOfLeewardSide(windStrain.getTopValueOfSpeedPressure());

            RoofDimensionsMap windStrainMap = new RoofDimensionsMap();
            windStrainMap.addRoofDimension("Base speed of wind", windStrain.getBaseSpeedOfWind());
            windStrainMap.addRoofDimension("Actual base speed of wind", windStrain.getActualBaseSpeedOfWind());
            windStrainMap.addRoofDimension("Base value of speed pressure", windStrain.getBaseValueOfSpeedPressure());
            windStrainMap.addRoofDimension("Coefficient of exposition", windStrain.getCoefficientOfExposition());
            windStrainMap.addRoofDimension("Top value of speed pressure", windStrain.getTopValueOfSpeedPressure());
            windStrainMap.addRoofDimension("Pressure of windward side", windStrain.getPressureOfWindwardSide());
            windStrainMap.addRoofDimension("Pressure of leeward side", windStrain.getPressureOfLeewardSide());

            map.put("values2", windStrainMap.toString());

            WholeStrainModel wholeStrainModel = new WholeStrainModel();
            wholeStrainModel.setAngle(angle);
            wholeStrainModel.setSpacing(spacing);
            wholeStrainModel.setUserOwnBuildingStrain(userOwnBuildingStrain);
            wholeStrainModel.setComputationalUserOwnBuildingStrain(wholeStrainModel.getUserOwnBuildingStrain());

            wholeStrainModel.setCharacteristicParallelOwnStrain(wholeStrainModel.getUserOwnBuildingStrain()
                    , wholeStrainModel.getAngle());
            wholeStrainModel.setComputationalParallelOwnStrain(wholeStrainModel.getCharacteristicParallelOwnStrain());
            wholeStrainModel.setCharacteristicPerpendicularOwnStrain(wholeStrainModel.getUserOwnBuildingStrain()
                    , wholeStrainModel.getAngle());
            wholeStrainModel.setComputationalPerpendicularOwnStrain(wholeStrainModel.getCharacteristicPerpendicularOwnStrain());
            //snow strain

            wholeStrainModel.setCharacteristicParallelSnowStrainOfMajorPour(snowStrain.getCharacteristicStrainOfMajorPour(), wholeStrainModel.getAngle());
            wholeStrainModel.setComputationalParallelSnowStrainOfMajorPour(snowStrain.getComputationalStrainOfMajorPour(), wholeStrainModel.getAngle());
            wholeStrainModel.setCharacteristicPerpendicularSnowStrainOfMinorPour(snowStrain.getCharacteristicStrainOfMinorPour(), wholeStrainModel.getAngle());
            wholeStrainModel.setComputationalPerpendicularSnowStrainOfMinorPour(snowStrain.getComputationalStrainOfMinorPour(), wholeStrainModel.getAngle());
            wholeStrainModel.setCharacteristicPerpendicularSnowStrainOfMajorPour(snowStrain.getCharacteristicStrainOfMajorPour(), wholeStrainModel.getAngle());
            wholeStrainModel.setComputationalPerpendicularSnowStrainOfMajorPour(snowStrain.getComputationalStrainOfMajorPour(), wholeStrainModel.getAngle());
            wholeStrainModel.setCharacteristicParallelSnowStrainOfMinorPour(snowStrain.getCharacteristicStrainOfMinorPour(), wholeStrainModel.getAngle());
            wholeStrainModel.setComputationalParallelSnowStrainOfMinorPour(snowStrain.getComputationalStrainOfMinorPour(), wholeStrainModel.getAngle());


            RoofDimensionsMap wholeStrainModelMap = new RoofDimensionsMap();
            wholeStrainModelMap.addRoofDimension("angle", wholeStrainModel.getAngle());
            wholeStrainModelMap.addRoofDimension("spacing", wholeStrainModel.getSpacing());
            wholeStrainModelMap.addRoofDimension("userOwnBuildingStrain", wholeStrainModel.getUserOwnBuildingStrain());
            wholeStrainModelMap.addRoofDimension("coefficientOfConstantStrain", wholeStrainModel.getCoefficientOfConstantStrain());
            wholeStrainModelMap.addRoofDimension("characteristicParallelOwnStrain", wholeStrainModel.getCharacteristicParallelOwnStrain());
            wholeStrainModelMap.addRoofDimension("characteristicPerpendicularOwnStrain", wholeStrainModel.getCharacteristicPerpendicularOwnStrain());
            wholeStrainModelMap.addRoofDimension("computationalParallelOwnStrain", wholeStrainModel.getComputationalParallelOwnStrain());
            wholeStrainModelMap.addRoofDimension("computationalPerpendicularOwnStrain", wholeStrainModel.getComputationalPerpendicularOwnStrain());
            wholeStrainModelMap.addRoofDimension("setComputationalUserOwnBuildingStrain", wholeStrainModel.getComputationalUserOwnBuildingStrain());

            //Snow Strain

            wholeStrainModelMap.addRoofDimension("CharacteristicParallelSnowStrainOfMajorPour", wholeStrainModel.getCharacteristicParallelSnowStrainOfMajorPour());
            wholeStrainModelMap.addRoofDimension("ComputationalParallelSnowStrainOfMajorPour", wholeStrainModel.getComputationalParallelSnowStrainOfMajorPour());
            wholeStrainModelMap.addRoofDimension("CharacteristicPerpendicularSnowStrainOfMinorPour", wholeStrainModel.getCharacteristicPerpendicularSnowStrainOfMinorPour());
            wholeStrainModelMap.addRoofDimension("ComputationalPerpendicularSnowStrainOfMinorPour", wholeStrainModel.getComputationalPerpendicularSnowStrainOfMinorPour());
            wholeStrainModelMap.addRoofDimension("CharacteristicPerpendicularSnowStrainOfMajorPour", wholeStrainModel.getCharacteristicPerpendicularSnowStrainOfMajorPour());
            wholeStrainModelMap.addRoofDimension("ComputationalPerpendicularSnowStrainOfMajorPour", wholeStrainModel.getComputationalPerpendicularSnowStrainOfMajorPour());
            wholeStrainModelMap.addRoofDimension("CharacteristicParallelSnowStrainOfMinorPour", wholeStrainModel.getCharacteristicParallelSnowStrainOfMinorPour());
            wholeStrainModelMap.addRoofDimension("ComputationalParallelSnowStrainOfMinorPour", wholeStrainModel.getComputationalParallelSnowStrainOfMinorPour());

            //windStrain
            wholeStrainModel.setCharacteristicPerpendicularStrainOfWindwardSide(windStrain.getPressureOfWindwardSide(),wholeStrainModel.getAngle());
            wholeStrainModel.setComputationalPerpendicularStrainOfWindwardSide(wholeStrainModel.getCharacteristicPerpendicularStrainOfWindwardSide());
            wholeStrainModel.setCharacteristicPerpendicularStrainOfOfLeewardSide(windStrain.getPressureOfLeewardSide(),wholeStrainModel.getAngle());
            wholeStrainModel.setComputationalPerpendicularStrainOfLeewardSide(wholeStrainModel.getCharacteristicPerpendicularStrainOfOfLeewardSide());

            wholeStrainModelMap.addRoofDimension("CharacteristicPerpendicularStrainOfWindwardSide",wholeStrainModel.getCharacteristicPerpendicularStrainOfWindwardSide());
            wholeStrainModelMap.addRoofDimension("ComputationalPerpendicularStrainOfWindwardSide",wholeStrainModel.getComputationalPerpendicularStrainOfWindwardSide());
            wholeStrainModelMap.addRoofDimension("CharacteristicPerpendicularStrainOfOfLeewardSide",wholeStrainModel.getCharacteristicPerpendicularStrainOfOfLeewardSide());
            wholeStrainModelMap.addRoofDimension("ComputationalPerpendicularStrainOfLeewardSide",wholeStrainModel.getComputationalPerpendicularStrainOfLeewardSide());
            map.put("values3", wholeStrainModelMap.toString());


            //bending moments in rafters
            if (roof.getProportion()==0.6)
                //own Strain

            staticEquations.bendingMomentsInRafterOnMDLinchpin(-0.035,wholeStrainModel
                    .getComputationalPerpendicularOwnStrain(),roof.getLengthOfRafter());
                //snow Strain
            staticEquations.bendingMomentsInRafterOnMDLinchpin(-0.035,wholeStrainModel
                    .getComputationalPerpendicularSnowStrainOfMajorPour(),roof.getLengthOfRafter());
                //wind Strain
            staticEquations.bendingMomentsInRafterOnMDLinchpin(-0.035,wholeStrainModel
                    .getComputationalPerpendicularStrainOfWindwardSide(),roof.getLengthOfRafter());

           map.put("A",staticEquations.bendingMomentsInRafterOnMDLinchpin(-0.035,wholeStrainModel
                   .getComputationalPerpendicularOwnStrain(),roof.getLengthOfRafter())+staticEquations.bendingMomentsInRafterOnMDLinchpin(-0.035,wholeStrainModel
                   .getComputationalPerpendicularSnowStrainOfMajorPour(),roof.getLengthOfRafter())+staticEquations.bendingMomentsInRafterOnMDLinchpin(-0.035,wholeStrainModel
                   .getComputationalPerpendicularStrainOfWindwardSide(),roof.getLengthOfRafter()));


            return "RoofDimensions";
        }
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