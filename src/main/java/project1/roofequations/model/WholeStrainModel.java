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
//Own Strain
    private Double characteristicParallelOwnStrain;
    private Double characteristicPerpendicularOwnStrain;
    private Double computationalParallelOwnStrain;
    private Double computationalPerpendicularOwnStrain;
//Snow
    private Double characteristicParallelSnowStrainOfMajorPour;
    private Double computationalParallelSnowStrainOfMajorPour;
    private Double characteristicPerpendicularSnowStrainOfMajorPour;
    private Double computationalPerpendicularSnowStrainOfMajorPour;
    private Double characteristicParallelSnowStrainOfMinorPour;
    private Double computationalParallelSnowStrainOfMinorPour;
    private Double characteristicPerpendicularSnowStrainOfMinorPour;
    private Double computationalPerpendicularSnowStrainOfMinorPour;
//Wind
    private Double characteristicPerpendicularStrainOfWindwardSide;
    private Double computationalPerpendicularStrainOfWindwardSide;
    private Double characteristicPerpendicularStrainOfOfLeewardSide;
    private Double computationalPerpendicularStrainOfLeewardSide;

    public WholeStrainModel() {
    }

    public Double getCharacteristicParallelSnowStrainOfMinorPour() {
        return characteristicParallelSnowStrainOfMinorPour;
    }

    public Double getComputationalParallelSnowStrainOfMinorPour() {
        return computationalParallelSnowStrainOfMinorPour;
    }

    public void setComputationalParallelSnowStrainOfMinorPour(Double computationalSnowStrainOfMinorPour, Double angle) {
        if (angle == 30.0)
        this.computationalParallelSnowStrainOfMinorPour = round(computationalSnowStrainOfMinorPour*0.5*1.1,2);
    }

    public void setCharacteristicParallelSnowStrainOfMinorPour(Double characteristicSnowStrainOfMinorPour, Double angle) {
        if (angle == 30.0)
        this.characteristicParallelSnowStrainOfMinorPour = round(characteristicSnowStrainOfMinorPour*0.5*1.1,2);
    }

    public Double getCharacteristicPerpendicularSnowStrainOfMajorPour() {
        return characteristicPerpendicularSnowStrainOfMajorPour;
    }

    public Double getComputationalPerpendicularSnowStrainOfMajorPour() {
        return computationalPerpendicularSnowStrainOfMajorPour;
    }

    public void setComputationalPerpendicularSnowStrainOfMajorPour(Double computationalSnowStrainOfMajorPour, Double angle) {
        if (angle == 30.0)
        this.computationalPerpendicularSnowStrainOfMajorPour = round(computationalSnowStrainOfMajorPour*0.8660* spacing,2);
    }

    public void setCharacteristicPerpendicularSnowStrainOfMajorPour(Double characteristicSnowStrainOfMajorPour, Double angle) {
        if (angle == 30.0)
        this.characteristicPerpendicularSnowStrainOfMajorPour = round(characteristicSnowStrainOfMajorPour*0.8660* spacing,2);
    }


    public void setComputationalPerpendicularSnowStrainOfMinorPour(Double computationalSnowStrainOfMinorPour, Double angle) {
        if (angle == 30){
        this.computationalPerpendicularSnowStrainOfMinorPour = round(computationalSnowStrainOfMinorPour*spacing*0.8660,2);
    }}

    public void setCharacteristicPerpendicularSnowStrainOfMinorPour(Double characteristicSnowStrainOfMinorPour, Double angle) {
        if (angle == 30.0){
        this.characteristicPerpendicularSnowStrainOfMinorPour = round(characteristicSnowStrainOfMinorPour*spacing*0.8660,2);
    }}

    public void setCharacteristicParallelSnowStrainOfMajorPour(Double characteristicSnowStrainOfMajorPour, Double angle) {
        if (angle == 30) {
            this.characteristicParallelSnowStrainOfMajorPour = round(characteristicSnowStrainOfMajorPour * spacing * 0.5, 2);
        }
    }

    public void setComputationalParallelSnowStrainOfMajorPour(Double computationalParallelSnowStrainOfMajorPour, Double angle) {
        if (angle == 30) {
            this.computationalParallelSnowStrainOfMajorPour = round(computationalParallelSnowStrainOfMajorPour * spacing * 0.5,2);
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

    public void setCharacteristicPerpendicularOwnStrain(Double userOwnBuildingStrain, Double angle) {
        if (angle == 30.0) {
            this.characteristicPerpendicularOwnStrain = round(userOwnBuildingStrain*spacing*0.8660,2);
        }
    }

    public void setComputationalPerpendicularOwnStrain(Double characteristicPerpendicularOwnStrain) {
        this.computationalPerpendicularOwnStrain = characteristicPerpendicularOwnStrain * coefficientOfConstantStrain;
    }


    public Double getCharacteristicParallelSnowStrainOfMajorPour() {
        return characteristicParallelSnowStrainOfMajorPour;
    }

    public Double getComputationalParallelSnowStrainOfMajorPour() {
        return computationalParallelSnowStrainOfMajorPour;
    }

    public Double getCharacteristicPerpendicularSnowStrainOfMinorPour() {
        return characteristicPerpendicularSnowStrainOfMinorPour;
    }

    public Double getComputationalPerpendicularSnowStrainOfMinorPour() {
        return computationalPerpendicularSnowStrainOfMinorPour;
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