package project1.roofequations.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WholeStrainModel {
    //cant extend 2 classes
    private Double angle;
    private Double spacing;
    private Double userOwnBuildingStrain;
    private Double coefficientOfConstantStrain = 1.35;
    private Double computationalUserOwnBuildingStrain;

    private Double characteristicParallelOwnStrain;
    private Double characteristicPerpendicularOwnStrain;
    private Double computationalParallelOwnStrain;
    private Double computationalPerpendicularOwnStrain;

    private Double characteristicParallelSnowStrainOfMajorPour;
    private Double computationalParallelSnowStrainOfMajorPour;


    public WholeStrainModel() {
    }

    public void setCharacteristicParallelSnowStrainOfMajorPour(Double characteristicSnowStrainOfMajorPour, Double angle) {
        if (angle == 30) {
            this.characteristicParallelSnowStrainOfMajorPour = round(characteristicSnowStrainOfMajorPour * spacing * 0.5, 2);
        }
    }

    public void setComputationalParallelSnowStrainOfMajorPour(Double computationalParallelSnowStrainOfMajorPour, Double angle) {
        if (angle == 30) {
            this.computationalParallelSnowStrainOfMajorPour = computationalParallelSnowStrainOfMajorPour * spacing * 0.5;
        }
    }

    public void setAngle(Double angle) {
        this.angle = angle;
    }

    public void setSpacing(Double spacing) {
        this.spacing = spacing;
    }

    public void setUserOwnBuildingStrain(Double userOwnBuildingStrain) {
        this.userOwnBuildingStrain = userOwnBuildingStrain;
    }

    public void setComputationalUserOwnBuildingStrain(Double userOwnBuildingStrain) {
        this.computationalUserOwnBuildingStrain = round(userOwnBuildingStrain * coefficientOfConstantStrain, 2);
    }

    public void setCharacteristicParallelOwnStrain(Double userOwnBuildingStrain, Double angle) {
        if (angle == 30.0) {
            this.characteristicParallelOwnStrain = round(userOwnBuildingStrain * 0.5 * spacing, 2);
        }
    }

    public void setComputationalParallelOwnStrain(Double characteristicParallelOwnStrain) {
        this.computationalParallelOwnStrain = round(characteristicParallelOwnStrain * coefficientOfConstantStrain, 2);
    }

    public void setCharacteristicProstopadłyOwnStrain(Double userOwnBuildingStrain, Double angle) {
        if (angle == 30.0) {
            this.characteristicPerpendicularOwnStrain = round(userOwnBuildingStrain * spacing * 0.8660, 2);
        }
    }

    public void setComputationalPerpendicularOwnStrain(Double characteristicProstopadłyOwnStrain) {
        this.computationalPerpendicularOwnStrain = characteristicProstopadłyOwnStrain * coefficientOfConstantStrain;
    }

    public Double getComputationalPerpendicularOwnStrain() {
        return computationalPerpendicularOwnStrain;
    }

    public Double getCoefficientOfConstantStrain() {
        return coefficientOfConstantStrain;
    }

    public Double getComputationalParallelOwnStrain() {
        return computationalParallelOwnStrain;
    }

    public Double getAngle() {
        return angle;
    }

    public Double getSpacing() {
        return spacing;
    }

    public Double getUserOwnBuildingStrain() {
        return userOwnBuildingStrain;
    }

    public Double getCharacteristicParallelOwnStrain() {
        return characteristicParallelOwnStrain;
    }

    public Double getCharacteristicPerpendicularOwnStrain() {
        return characteristicPerpendicularOwnStrain;
    }

    public Double getComputationalUserOwnBuildingStrain() {
        return computationalUserOwnBuildingStrain;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}