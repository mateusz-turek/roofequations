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
public class HomeController implements StaticEquations {

    private HomeControllerService homeControllerService;

    private HGirderRepository hGirderRepository;


    @Autowired
    public HomeController(HomeControllerService homeControllerService, @Qualifier("HGirderRepository") HGirderRepository hGirderRepository) {
        this.homeControllerService = homeControllerService;
        this.hGirderRepository = hGirderRepository;
    }

    @GetMapping("/")
    public String PrimaryAttributes(ModelMap map) {
        map.put("H", hGirderRepository.findAll().toString());

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
                          @RequestParam(required = false) Long HGirderId,
                          ModelMap map) {


        if (!homeControllerService.widthValidator(width) ||
                !homeControllerService.angleValidator(angle) ||
                !homeControllerService.cityValidator(city) ||
                homeControllerService.widthChanger(width) == null ||
                homeControllerService.widthChanger(width) <= 0) {
            return "WrongAttributes";
        } else {

            RoofModel roof = new RoofModel();
            roof.setWidth(homeControllerService.widthChanger(width));
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
                    snowStrain.getCoefficientOfExposure(), snowStrain.getThermalCoefficient(),
                    snowStrain.getCharacteristicStrainOfSnow());
            snowStrain.setCharacteristicStrainOfMajorPour(snowStrain.getCoefficientOfRoofShapeOfMajorPour(),
                    snowStrain.getCoefficientOfExposure(), snowStrain.getThermalCoefficient(),
                    snowStrain.getCharacteristicStrainOfSnow());
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
            windStrain.setBaseValueOfSpeedPressure(windStrain.getAirDensity(),
                    windStrain.getActualBaseSpeedOfWind());
            windStrain.setCoefficientOfExposition(heightOfBuilding);
            windStrain.setTopValueOfSpeedPressure(windStrain.getCoefficientOfExposition()
                    , windStrain.getBaseValueOfSpeedPressure());
            windStrain.setPressureOfWindwardSide(windStrain.getTopValueOfSpeedPressure());
            windStrain.setPressureOfLeewardSide(windStrain.getTopValueOfSpeedPressure());

            RoofDimensionsMap windStrainMap = new RoofDimensionsMap();
            windStrainMap.addRoofDimension("Base speed of wind", windStrain
                    .getBaseSpeedOfWind());
            windStrainMap.addRoofDimension("Actual base speed of wind", windStrain
                    .getActualBaseSpeedOfWind());
            windStrainMap.addRoofDimension("Base value of speed pressure", windStrain
                    .getBaseValueOfSpeedPressure());
            windStrainMap.addRoofDimension("Coefficient of exposition", windStrain
                    .getCoefficientOfExposition());
            windStrainMap.addRoofDimension("Top value of speed pressure", windStrain
                    .getTopValueOfSpeedPressure());
            windStrainMap.addRoofDimension("Pressure of windward side", windStrain
                    .getPressureOfWindwardSide());
            windStrainMap.addRoofDimension("Pressure of leeward side", windStrain
                    .getPressureOfLeewardSide());

            map.put("values2", windStrainMap.toString());

            WholeStrainModel wholeStrainModel = new WholeStrainModel();
            wholeStrainModel.setAngle(angle);
            wholeStrainModel.setSpacing(spacing);
            wholeStrainModel.setUserOwnBuildingStrain(userOwnBuildingStrain);
            wholeStrainModel.setComputationalUserOwnBuildingStrain(wholeStrainModel
                    .getUserOwnBuildingStrain());

            wholeStrainModel.setCharacteristicParallelOwnStrain(wholeStrainModel.getUserOwnBuildingStrain()
                    , wholeStrainModel.getAngle());
            wholeStrainModel.setComputationalParallelOwnStrain(wholeStrainModel
                    .getCharacteristicParallelOwnStrain());
            wholeStrainModel.setCharacteristicPerpendicularOwnStrain(wholeStrainModel
                            .getUserOwnBuildingStrain()
                    , wholeStrainModel.getAngle());
            wholeStrainModel.setComputationalPerpendicularOwnStrain(wholeStrainModel
                    .getCharacteristicPerpendicularOwnStrain());
            //snow strain

            wholeStrainModel.setCharacteristicParallelSnowStrainOfMajorPour(snowStrain
                    .getCharacteristicStrainOfMajorPour(), wholeStrainModel.getAngle());
            wholeStrainModel.setComputationalParallelSnowStrainOfMajorPour(snowStrain
                    .getComputationalStrainOfMajorPour(), wholeStrainModel.getAngle());
            wholeStrainModel.setCharacteristicPerpendicularSnowStrainOfMinorPour(snowStrain
                    .getCharacteristicStrainOfMinorPour(), wholeStrainModel.getAngle());
            wholeStrainModel.setComputationalPerpendicularSnowStrainOfMinorPour(snowStrain
                    .getComputationalStrainOfMinorPour(), wholeStrainModel.getAngle());
            wholeStrainModel.setCharacteristicPerpendicularSnowStrainOfMajorPour(snowStrain
                    .getCharacteristicStrainOfMajorPour(), wholeStrainModel.getAngle());
            wholeStrainModel.setComputationalPerpendicularSnowStrainOfMajorPour(snowStrain
                    .getComputationalStrainOfMajorPour(), wholeStrainModel.getAngle());
            wholeStrainModel.setCharacteristicParallelSnowStrainOfMinorPour(snowStrain
                    .getCharacteristicStrainOfMinorPour(), wholeStrainModel.getAngle());
            wholeStrainModel.setComputationalParallelSnowStrainOfMinorPour(snowStrain
                    .getComputationalStrainOfMinorPour(), wholeStrainModel.getAngle());


            RoofDimensionsMap wholeStrainModelMap = new RoofDimensionsMap();
            wholeStrainModelMap.addRoofDimension("angle", wholeStrainModel.getAngle());
            wholeStrainModelMap.addRoofDimension("spacing", wholeStrainModel.getSpacing());
            wholeStrainModelMap
                    .addRoofDimension("userOwnBuildingStrain", wholeStrainModel.getUserOwnBuildingStrain());
            wholeStrainModelMap
                    .addRoofDimension("coefficientOfConstantStrain", wholeStrainModel
                            .getCoefficientOfConstantStrain());
            wholeStrainModelMap
                    .addRoofDimension("characteristicParallelOwnStrain", wholeStrainModel
                            .getCharacteristicParallelOwnStrain());
            wholeStrainModelMap
                    .addRoofDimension("characteristicPerpendicularOwnStrain", wholeStrainModel
                            .getCharacteristicPerpendicularOwnStrain());
            wholeStrainModelMap
                    .addRoofDimension("computationalParallelOwnStrain", wholeStrainModel
                            .getComputationalParallelOwnStrain());
            wholeStrainModelMap
                    .addRoofDimension("computationalPerpendicularOwnStrain", wholeStrainModel
                            .getComputationalPerpendicularOwnStrain());
            wholeStrainModelMap
                    .addRoofDimension("setComputationalUserOwnBuildingStrain", wholeStrainModel
                            .getComputationalUserOwnBuildingStrain());

            //Snow Strain

            wholeStrainModelMap
                    .addRoofDimension("CharacteristicParallelSnowStrainOfMajorPour", wholeStrainModel
                            .getCharacteristicParallelSnowStrainOfMajorPour());
            wholeStrainModelMap
                    .addRoofDimension("ComputationalParallelSnowStrainOfMajorPour", wholeStrainModel
                            .getComputationalParallelSnowStrainOfMajorPour());
            wholeStrainModelMap
                    .addRoofDimension("CharacteristicPerpendicularSnowStrainOfMinorPour", wholeStrainModel
                            .getCharacteristicPerpendicularSnowStrainOfMinorPour());
            wholeStrainModelMap
                    .addRoofDimension("ComputationalPerpendicularSnowStrainOfMinorPour", wholeStrainModel
                            .getComputationalPerpendicularSnowStrainOfMinorPour());
            wholeStrainModelMap
                    .addRoofDimension("CharacteristicPerpendicularSnowStrainOfMajorPour", wholeStrainModel
                            .getCharacteristicPerpendicularSnowStrainOfMajorPour());
            wholeStrainModelMap
                    .addRoofDimension("ComputationalPerpendicularSnowStrainOfMajorPour", wholeStrainModel
                            .getComputationalPerpendicularSnowStrainOfMajorPour());
            wholeStrainModelMap
                    .addRoofDimension("CharacteristicParallelSnowStrainOfMinorPour", wholeStrainModel
                            .getCharacteristicParallelSnowStrainOfMinorPour());
            wholeStrainModelMap
                    .addRoofDimension("ComputationalParallelSnowStrainOfMinorPour", wholeStrainModel
                            .getComputationalParallelSnowStrainOfMinorPour());

            //windStrain
            wholeStrainModel
                    .setCharacteristicPerpendicularStrainOfWindwardSide(windStrain
                            .getPressureOfWindwardSide(), wholeStrainModel
                            .getAngle());
            wholeStrainModel
                    .setComputationalPerpendicularStrainOfWindwardSide(wholeStrainModel
                            .getCharacteristicPerpendicularStrainOfWindwardSide());
            wholeStrainModel
                    .setCharacteristicPerpendicularStrainOfOfLeewardSide(windStrain
                            .getPressureOfLeewardSide(), wholeStrainModel
                            .getAngle());
            wholeStrainModel
                    .setComputationalPerpendicularStrainOfLeewardSide(wholeStrainModel
                            .getCharacteristicPerpendicularStrainOfOfLeewardSide());

            wholeStrainModelMap
                    .addRoofDimension("CharacteristicPerpendicularStrainOfWindwardSide", wholeStrainModel
                            .getCharacteristicPerpendicularStrainOfWindwardSide());
            wholeStrainModelMap
                    .addRoofDimension("ComputationalPerpendicularStrainOfWindwardSide", wholeStrainModel
                            .getComputationalPerpendicularStrainOfWindwardSide());
            wholeStrainModelMap
                    .addRoofDimension("CharacteristicPerpendicularStrainOfOfLeewardSide", wholeStrainModel
                            .getCharacteristicPerpendicularStrainOfOfLeewardSide());
            wholeStrainModelMap
                    .addRoofDimension("ComputationalPerpendicularStrainOfLeewardSide", wholeStrainModel
                            .getComputationalPerpendicularStrainOfLeewardSide());

            map.put("values3", wholeStrainModelMap.toString());

            //bending moments in rafters
            if (roof.getProportion() == 0.6)
                //own Strain

                bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                        .getComputationalPerpendicularOwnStrain(), roof.getLengthOfRafter());
            //snow Strain
            bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof.getLengthOfRafter());
            //wind Strain
            bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                    .getComputationalPerpendicularStrainOfWindwardSide(), roof.getLengthOfRafter());

            map.put("A", bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                    .getComputationalPerpendicularOwnStrain(), roof
                    .getLengthOfRafter()) + bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                    .getLengthOfRafter()) + bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                    .getComputationalPerpendicularStrainOfWindwardSide(), roof
                    .getLengthOfRafter()));

            bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                    .getComputationalPerpendicularOwnStrain(), roof.getLengthOfRafter());

            bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                    .getLengthOfRafter());

            bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                    .getComputationalPerpendicularStrainOfWindwardSide(), roof
                    .getLengthOfRafter());

            map.put("B", bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                    .getComputationalPerpendicularOwnStrain(), roof
                    .getLengthOfRafter()) + bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                    .getLengthOfRafter()) + bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                    .getComputationalPerpendicularStrainOfWindwardSide(), roof
                    .getLengthOfRafter()));

            map.put("C", perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                    .getComputationalPerpendicularOwnStrain(), roof
                    .getLengthOfRafter()) + perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                    .getLengthOfRafter()) + perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                    .getComputationalPerpendicularStrainOfWindwardSide(), roof.getLengthOfRafter()));

            map.put("D", perpendicularReactionsToRoofPour(0.113, wholeStrainModel
                    .getComputationalPerpendicularOwnStrain(), roof
                    .getLengthOfRafter()) + perpendicularReactionsToRoofPour(0.113, wholeStrainModel
                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                    .getLengthOfRafter()) + perpendicularReactionsToRoofPour(0.113, wholeStrainModel
                    .getComputationalPerpendicularStrainOfWindwardSide(), roof.getLengthOfRafter()));

            map.put("E", perpendicularReactionsToRoofPour(0.113, wholeStrainModel
                    .getComputationalPerpendicularOwnStrain(), roof.getLengthOfRafter()) + perpendicularReactionsToRoofPour(0.113, wholeStrainModel
                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                    .getLengthOfRafter()) + perpendicularReactionsToRoofPour(0.113, wholeStrainModel
                    .getComputationalPerpendicularStrainOfLeewardSide(), roof.getLengthOfRafter()));

            map.put("F", perpendicularReactionsToRoofPour(0.6457, wholeStrainModel
                    .getComputationalPerpendicularOwnStrain(), roof
                    .getLengthOfRafter()) + perpendicularReactionsToRoofPour(0.6457, wholeStrainModel
                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                    .getLengthOfRafter()) +
                    perpendicularReactionsToRoofPour(0.6457, wholeStrainModel
                            .getComputationalPerpendicularStrainOfWindwardSide(), roof.getLengthOfRafter()));

            //  staticEquations.getC1(wholeStrainModel.getAngle(),wholeStrainModel.getComputationalParallelOwnStrain()+wholeStrainModel.getComputationalParallelSnowStrainOfMajorPour());
            map.put("G", compressiveStrengthOnDLinchpin(getC1(wholeStrainModel
                            .getAngle(), wholeStrainModel
                            .getComputationalParallelOwnStrain() + wholeStrainModel
                            .getComputationalParallelSnowStrainOfMajorPour())
                    , wholeStrainModel
                            .getComputationalParallelOwnStrain() + wholeStrainModel
                            .getComputationalParallelSnowStrainOfMajorPour(), roof.getUpperPartOfRafter()));

            map.put("H", strengthInADSpan(wholeStrainModel
                            .getComputationalParallelOwnStrain() + wholeStrainModel
                            .getComputationalParallelSnowStrainOfMajorPour(),
                    roof.getLowerPartOfRafter()));

            map.put("I", distanceFromLinchpinToPlaceOfMaximumMomentumOfMad(perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                    .getComputationalPerpendicularOwnStrain(), roof
                    .getLengthOfRafter()) + perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                    .getLengthOfRafter()) + perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                    .getComputationalPerpendicularStrainOfWindwardSide(), roof
                    .getLengthOfRafter()), wholeStrainModel
                    .getComputationalPerpendicularOwnStrain() + wholeStrainModel
                    .getComputationalPerpendicularSnowStrainOfMajorPour()));

            map.put("J", -1 * (valueOfNad(strengthInADSpan(wholeStrainModel
                            .getComputationalParallelOwnStrain() + wholeStrainModel
                            .getComputationalParallelSnowStrainOfMajorPour(),
                    roof.getLowerPartOfRafter()), wholeStrainModel
                            .getComputationalParallelOwnStrain() + wholeStrainModel
                            .getComputationalParallelSnowStrainOfMajorPour(),
                    distanceFromLinchpinToPlaceOfMaximumMomentumOfMad(
                            perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                                    .getComputationalPerpendicularOwnStrain(), roof
                                    .getLengthOfRafter()) +
                                    perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                                            .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                                            .getLengthOfRafter()) + perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                                    .getComputationalPerpendicularStrainOfWindwardSide(), roof
                                    .getLengthOfRafter()), wholeStrainModel
                                    .getComputationalPerpendicularOwnStrain() + wholeStrainModel
                                    .getComputationalParallelSnowStrainOfMajorPour()))));

            map.put("K", crossSectionalAreaOfWoodBasedMaterial(hGirderRepository
                    .findById(HGirderId)
                    .get()
                    .getWidth(), hGirderRepository
                    .findById(HGirderId)
                    .get()
                    .getHeight()));

            map.put("L", bucklingLength(1.0, roof.getLowerPartOfRafter()));

            map.put("M", defaultSlim(bucklingLength(1.0, roof
                    .getLowerPartOfRafter()), hGirderRepository
                    .findById(HGirderId)
                    .get()
                    .getMomentOfInertia()));
            //lvl
            map.put("N", relativeSlenderness(defaultSlim(bucklingLength(1.0, roof
                    .getLowerPartOfRafter()), hGirderRepository
                    .findById(HGirderId)
                    .get()
                    .getMomentOfInertia()), 36.00, hGirderRepository
                    .findById(HGirderId)
                    .get()
                    .getModulusOfElasticity()));
            //HB
            map.put("O", relativeSlenderness(defaultSlim(bucklingLength(1.0, roof
                    .getLowerPartOfRafter()), hGirderRepository
                    .findById(HGirderId)
                    .get()
                    .getMomentOfInertia()), 21.00, hGirderRepository
                    .findById(HGirderId)
                    .get()
                    .getModulusOfElasticity()));
            //flex
            map.put("P", coefficientOfFlexibility(0.1, relativeSlenderness(defaultSlim(bucklingLength(1.0, roof
                    .getLowerPartOfRafter()), hGirderRepository
                    .findById(HGirderId)
                    .get()
                    .getMomentOfInertia()), 21.00, hGirderRepository
                    .findById(HGirderId)
                    .get()
                    .getModulusOfElasticity())));
            //buckling
            map.put("R", coefficientOfBuckling(coefficientOfFlexibility(0.1,
                    relativeSlenderness(defaultSlim(bucklingLength(1.0, roof
                            .getLowerPartOfRafter()), hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getMomentOfInertia()), 21.00, hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getModulusOfElasticity())),
                    relativeSlenderness(defaultSlim(bucklingLength(1.0, roof
                            .getLowerPartOfRafter()), hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getMomentOfInertia()), 21.00, hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getModulusOfElasticity())));
            //computationalCompressiveTensionAlongFibres
            map.put("S", computationalCompressiveTensionAlongFibres(valueOfNad(strengthInADSpan(wholeStrainModel
                            .getComputationalParallelOwnStrain() + wholeStrainModel
                            .getComputationalParallelSnowStrainOfMajorPour(),
                    roof
                            .getLowerPartOfRafter()), wholeStrainModel
                            .getComputationalParallelOwnStrain() + wholeStrainModel
                            .getComputationalParallelSnowStrainOfMajorPour()
                    , distanceFromLinchpinToPlaceOfMaximumMomentumOfMad(
                            perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                                    .getComputationalPerpendicularOwnStrain(), roof
                                    .getLengthOfRafter()) +
                                    perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                                            .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                                            .getLengthOfRafter()) +
                                    perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                                            .getComputationalPerpendicularStrainOfWindwardSide(), roof
                                            .getLengthOfRafter()), wholeStrainModel
                                    .getComputationalPerpendicularOwnStrain() + wholeStrainModel
                                    .getComputationalParallelSnowStrainOfMajorPour())),
                    crossSectionalAreaOfWoodBasedMaterial(hGirderRepository.findById(HGirderId)
                            .get()
                            .getWidth(), hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getHeight())));


            map.put("U", materialEndurance(hGirderRepository
                    .findById(HGirderId)
                    .get()
                    .getMomentOfSecondDegreeInertia(), hGirderRepository
                    .findById(HGirderId)
                    .get()
                    .getHeight()));

            map.put("W", computationalTensionAlongYAxis(
                    bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                            .getComputationalPerpendicularOwnStrain(), roof
                            .getLengthOfRafter()) +
                            bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                                    .getLengthOfRafter()) +
                            bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                                    .getComputationalPerpendicularStrainOfWindwardSide(), roof
                                    .getLengthOfRafter()),
                    materialEndurance(hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getMomentOfSecondDegreeInertia(), hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getHeight())));

            //lvl
            map.put("Z", bearingCapacity(computationalCompressiveTensionAlongFibres(
                    valueOfNad(
                            strengthInADSpan(
                                    wholeStrainModel
                                            .getComputationalParallelOwnStrain() + wholeStrainModel
                                            .getComputationalParallelSnowStrainOfMajorPour(),
                                    roof
                                            .getLowerPartOfRafter()), wholeStrainModel
                                    .getComputationalParallelOwnStrain() + wholeStrainModel
                                    .getComputationalParallelSnowStrainOfMajorPour(),
                            distanceFromLinchpinToPlaceOfMaximumMomentumOfMad(
                                    perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                                            .getComputationalPerpendicularOwnStrain(), roof
                                            .getLengthOfRafter()) +
                                            perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                                                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                                                    .getLengthOfRafter()) +
                                            perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                                                    .getComputationalPerpendicularStrainOfWindwardSide(), roof
                                                    .getLengthOfRafter()), wholeStrainModel
                                            .getComputationalPerpendicularOwnStrain() + wholeStrainModel
                                            .getComputationalParallelSnowStrainOfMajorPour())),
                    crossSectionalAreaOfWoodBasedMaterial(hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getWidth(), hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getHeight()))
                    ,
                    coefficientOfBuckling(
                            coefficientOfFlexibility(0.1,
                                    relativeSlenderness(
                                            defaultSlim(
                                                    bucklingLength(1.0, roof
                                                            .getLowerPartOfRafter()), hGirderRepository
                                                            .findById(HGirderId)
                                                            .get()
                                                            .getMomentOfInertia())
                                            , 21.00
                                            , hGirderRepository
                                                    .findById(HGirderId)
                                                    .get().getModulusOfElasticity())),
                            relativeSlenderness(defaultSlim(bucklingLength(1.0, roof
                                            .getLowerPartOfRafter())
                                    , hGirderRepository
                                            .findById(HGirderId)
                                            .get()
                                            .getMomentOfInertia())
                                    , 21.00
                                    , hGirderRepository
                                            .findById(HGirderId)
                                            .get()
                                            .getModulusOfElasticity())), 18.00,
                    computationalTensionAlongYAxis(bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                            .getComputationalPerpendicularOwnStrain(), roof
                            .getLengthOfRafter()) + bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                            .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                            .getLengthOfRafter()) + bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                            .getComputationalPerpendicularStrainOfWindwardSide(), roof
                            .getLengthOfRafter()), materialEndurance(hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getMomentOfSecondDegreeInertia(), hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getHeight()))
                    , 28.80));

            //HB
            map.put("A1", bearingCapacity(
                    computationalCompressiveTensionAlongFibres(valueOfNad(
                            strengthInADSpan(wholeStrainModel
                                            .getComputationalParallelOwnStrain() + wholeStrainModel
                                            .getComputationalParallelSnowStrainOfMajorPour(),
                                    roof
                                            .getLowerPartOfRafter()), wholeStrainModel
                                    .getComputationalParallelOwnStrain() + wholeStrainModel
                                    .getComputationalParallelSnowStrainOfMajorPour()
                            , distanceFromLinchpinToPlaceOfMaximumMomentumOfMad(
                                    perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                                            .getComputationalPerpendicularOwnStrain(), roof
                                            .getLengthOfRafter()) +
                                            perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                                                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                                                    .getLengthOfRafter()) +
                                            perpendicularReactionsToRoofPour(0.2413, wholeStrainModel
                                                    .getComputationalPerpendicularStrainOfWindwardSide(), roof
                                                    .getLengthOfRafter()), wholeStrainModel
                                            .getComputationalPerpendicularOwnStrain() + wholeStrainModel
                                            .getComputationalParallelSnowStrainOfMajorPour())), crossSectionalAreaOfWoodBasedMaterial(hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getWidth(), hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getHeight())),
                    coefficientOfBuckling(
                            coefficientOfFlexibility(0.1,
                                    relativeSlenderness(
                                            defaultSlim(
                                                    bucklingLength(1.0, roof
                                                            .getLowerPartOfRafter()), hGirderRepository
                                                            .findById(HGirderId)
                                                            .get()
                                                            .getMomentOfInertia()), 21.00, hGirderRepository
                                                    .findById(HGirderId)
                                                    .get()
                                                    .getModulusOfElasticity())),
                            relativeSlenderness(
                                    defaultSlim(
                                            bucklingLength(1.0, roof
                                                    .getLowerPartOfRafter()), hGirderRepository
                                                    .findById(HGirderId)
                                                    .get()
                                                    .getMomentOfInertia()), 21.00, hGirderRepository
                                            .findById(HGirderId)
                                            .get()
                                            .getModulusOfElasticity()))
                    , 9.69, computationalTensionAlongYAxis(
                            bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                                    .getComputationalPerpendicularOwnStrain(), roof
                                    .getLengthOfRafter()) + bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                                    .getLengthOfRafter()) + bendingMomentsInRafterOnMDLinchpin(0.0292, wholeStrainModel
                                    .getComputationalPerpendicularStrainOfWindwardSide(), roof
                                    .getLengthOfRafter()), materialEndurance(hGirderRepository.findById(HGirderId)
                                    .get()
                                    .getMomentOfSecondDegreeInertia(), hGirderRepository
                                    .findById(HGirderId)
                                    .get()
                                    .getHeight())), 14.31));

            map.put("B1", computationalCompressiveTensionAlongFibres(
                    compressiveStrengthOnDLinchpin(
                            getC1(wholeStrainModel
                                    .getAngle(), wholeStrainModel
                                    .getComputationalParallelOwnStrain() + wholeStrainModel
                                    .getComputationalParallelSnowStrainOfMajorPour())
                            , wholeStrainModel
                                    .getComputationalParallelOwnStrain() + wholeStrainModel
                                    .getComputationalParallelSnowStrainOfMajorPour(), roof
                                    .getUpperPartOfRafter())
                    , crossSectionalAreaOfWoodBasedMaterial(hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getWidth(), hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getHeight())));

            map.put("C1", (-1 * computationalTensionAlongYAxis(
                    bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                            .getComputationalPerpendicularOwnStrain(), roof
                            .getLengthOfRafter()) +
                            bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                                    .getLengthOfRafter()) +
                            bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                                    .getComputationalPerpendicularStrainOfWindwardSide(), roof
                                    .getLengthOfRafter()),
                    materialEndurance(hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getMomentOfSecondDegreeInertia(), hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getHeight()))));

            //lvl
            map.put("D1", bearingCapacityOnLinchpin(computationalCompressiveTensionAlongFibres(
                    compressiveStrengthOnDLinchpin(
                            getC1(wholeStrainModel
                                    .getAngle(), wholeStrainModel
                                    .getComputationalParallelOwnStrain() + wholeStrainModel
                                    .getComputationalParallelSnowStrainOfMajorPour())
                            , wholeStrainModel
                                    .getComputationalParallelOwnStrain() + wholeStrainModel
                                    .getComputationalParallelSnowStrainOfMajorPour(), roof
                                    .getUpperPartOfRafter()),
                    crossSectionalAreaOfWoodBasedMaterial(hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getWidth(), hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getHeight())), 18.00, (-1 *
                            computationalTensionAlongYAxis(
                                    bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                                            .getComputationalPerpendicularOwnStrain(), roof
                                            .getLengthOfRafter()) +
                                            bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                                                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                                                    .getLengthOfRafter()) +
                                            bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                                                    .getComputationalPerpendicularStrainOfWindwardSide(), roof
                                                    .getLengthOfRafter()),
                                    materialEndurance(hGirderRepository
                                            .findById(HGirderId)
                                            .get()
                                            .getMomentOfSecondDegreeInertia(), hGirderRepository
                                            .findById(HGirderId)
                                            .get()
                                            .getHeight())))
                    , 28.80));
            //HB
            map.put("E1", bearingCapacityOnLinchpin(
                    computationalCompressiveTensionAlongFibres(
                            compressiveStrengthOnDLinchpin(
                                    getC1(wholeStrainModel
                                            .getAngle(), wholeStrainModel
                                            .getComputationalParallelOwnStrain() + wholeStrainModel
                                            .getComputationalParallelSnowStrainOfMajorPour())
                                    , wholeStrainModel
                                            .getComputationalParallelOwnStrain() + wholeStrainModel
                                            .getComputationalParallelSnowStrainOfMajorPour(), roof
                                            .getUpperPartOfRafter()),
                            crossSectionalAreaOfWoodBasedMaterial(hGirderRepository
                                    .findById(HGirderId)
                                    .get()
                                    .getWidth(), hGirderRepository
                                    .findById(HGirderId)
                                    .get()
                                    .getHeight())), 9.69, (-1 * computationalTensionAlongYAxis(
                            bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                                    .getComputationalPerpendicularOwnStrain(), roof
                                    .getLengthOfRafter()) + bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                                    .getComputationalPerpendicularSnowStrainOfMajorPour(), roof
                                    .getLengthOfRafter()) + bendingMomentsInRafterOnMDLinchpin(-0.035, wholeStrainModel
                                    .getComputationalPerpendicularStrainOfWindwardSide(), roof
                                    .getLengthOfRafter()),
                            materialEndurance(hGirderRepository
                                    .findById(HGirderId)
                                    .get()
                                    .getMomentOfSecondDegreeInertia(), hGirderRepository
                                    .findById(HGirderId)
                                    .get()
                                    .getHeight()))), 14.31));

            map.put("F1",
                    serviceabilityLimitState(wholeStrainModel
                                    .getCharacteristicPerpendicularOwnStrain()
                            , roof
                                    .getLowerPartOfRafter()
                            , hGirderRepository
                                    .findById(HGirderId)
                                    .get()
                                    .getModulusOfElasticity()
                            , hGirderRepository
                                    .findById(HGirderId)
                                    .get()
                                    .getMomentOfSecondDegreeInertia()
                            , hGirderRepository
                                    .findById(HGirderId)
                                    .get()
                                    .getHeight()));

            map.put("G1", finalServiceabilityLimitStateOfOwnStrain(
                    serviceabilityLimitState(wholeStrainModel
                                    .getCharacteristicPerpendicularOwnStrain()
                            , roof
                                    .getLowerPartOfRafter()
                            , hGirderRepository
                                    .findById(HGirderId)
                                    .get()
                                    .getModulusOfElasticity()
                            , hGirderRepository
                                    .findById(HGirderId)
                                    .get()
                                    .getMomentOfSecondDegreeInertia()
                            , hGirderRepository
                                    .findById(HGirderId)
                                    .get()
                                    .getHeight()), 1.6));

            map.put("H1", serviceabilityLimitState(wholeStrainModel
                            .getCharacteristicPerpendicularSnowStrainOfMajorPour(), roof
                            .getLowerPartOfRafter()
                    , hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getModulusOfElasticity()
                    , hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getMomentOfSecondDegreeInertia()
                    , hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getHeight()));

            map.put("I1", serviceabilityLimitState(wholeStrainModel
                            .getCharacteristicPerpendicularStrainOfWindwardSide()
                    , roof.getLowerPartOfRafter(), hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getModulusOfElasticity()
                    , hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getMomentOfSecondDegreeInertia()
                    , hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getHeight()));

            map.put("J1", (serviceabilityLimitState(wholeStrainModel
                            .getCharacteristicPerpendicularStrainOfWindwardSide(), roof.getLowerPartOfRafter(),
                    hGirderRepository.findById(HGirderId).get()
                            .getModulusOfElasticity()
                    , hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getMomentOfSecondDegreeInertia()
                    , hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getHeight()))
                    * 0.6);

            map.put("K1", (serviceabilityLimitState(wholeStrainModel
                            .getCharacteristicPerpendicularStrainOfWindwardSide()
                    , roof
                            .getLowerPartOfRafter(), hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getModulusOfElasticity()
                    , hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getMomentOfSecondDegreeInertia()
                    , hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getHeight())) * 0.6
                    + serviceabilityLimitState(wholeStrainModel
                            .getCharacteristicPerpendicularSnowStrainOfMajorPour()
                    , roof
                            .getLowerPartOfRafter(), hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getModulusOfElasticity()
                    , hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getMomentOfSecondDegreeInertia()
                    , hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getHeight())
                    + finalServiceabilityLimitStateOfOwnStrain(serviceabilityLimitState(wholeStrainModel
                            .getCharacteristicPerpendicularOwnStrain(),
                    roof
                            .getLowerPartOfRafter(), hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getModulusOfElasticity()
                    , hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getMomentOfSecondDegreeInertia()
                    , hGirderRepository
                            .findById(HGirderId)
                            .get()
                            .getHeight()), 1.6));

            map.put("L1", admissibleBend(roof
                    .getLowerPartOfRafter()));

            map.put("M1", admissibleBendCheck(admissibleBend(roof
                            .getLowerPartOfRafter()),
                    (serviceabilityLimitState(wholeStrainModel
                                    .getCharacteristicPerpendicularStrainOfWindwardSide()
                            , roof.getLowerPartOfRafter(), hGirderRepository
                                    .findById(HGirderId)
                                    .get()
                                    .getModulusOfElasticity()
                            , hGirderRepository
                                    .findById(HGirderId)
                                    .get()
                                    .getMomentOfSecondDegreeInertia()
                            , hGirderRepository
                                    .findById(HGirderId)
                                    .get()
                                    .getHeight())) * 0.6 +
                            serviceabilityLimitState(wholeStrainModel
                                            .getCharacteristicPerpendicularSnowStrainOfMajorPour()
                                    , roof
                                            .getLowerPartOfRafter(), hGirderRepository
                                            .findById(HGirderId)
                                            .get()
                                            .getModulusOfElasticity()
                                    , hGirderRepository
                                            .findById(HGirderId)
                                            .get()
                                            .getMomentOfSecondDegreeInertia()
                                    , hGirderRepository
                                            .findById(HGirderId)
                                            .get()
                                            .getHeight())
                            + finalServiceabilityLimitStateOfOwnStrain(
                            serviceabilityLimitState(wholeStrainModel
                                            .getCharacteristicPerpendicularOwnStrain(),
                                    roof
                                            .getLowerPartOfRafter(), hGirderRepository
                                            .findById(HGirderId)
                                            .get()
                                            .getModulusOfElasticity()
                                    , hGirderRepository
                                            .findById(HGirderId)
                                            .get()
                                            .getMomentOfSecondDegreeInertia()
                                    , hGirderRepository
                                            .findById(HGirderId)
                                            .get()
                                            .getHeight()), 1.6)));

            return "RoofDimensions";
        }
    }
}