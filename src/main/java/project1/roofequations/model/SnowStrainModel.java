package project1.roofequations.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SnowStrainModel {
    private Double coefficientOfRoofShapeOfMajorPour;
    private Double coefficientOfRoofShapeOfMinorPour;
    private Double coefficientOfExposure;
    private Double thermalCoefficient;
    private Double characteristicStrainOfSnow;
    private Double coefficientOfVariableStrain;

    private Double characteristicStrainOfMinorPour;
    private Double characteristicStrainOfMajorPour;
    private Double computationalStrainOfMinorPour;
    private Double computationalStrainOfMajorPour;

    public void setCoefficientOfRoofShapeOfMajorPour(Double angleOfPour) {
        if (angleOfPour == 30.0)
        this.coefficientOfRoofShapeOfMajorPour = 0.8;
    }

    public void setCoefficientOfRoofShapeOfMinorPour(Double angleOfPour) {
        if (angleOfPour == 30.0)
        this.coefficientOfRoofShapeOfMinorPour = 0.4;
    }

    public void setCoefficientOfVariableStrain() {
        this.coefficientOfVariableStrain = 1.5;
    }

    public void setCoefficientOfExposure(String area) {
        if (area.equals("Warsaw"))
        this.coefficientOfExposure = 1.0;
    }

    public void setThermalCoefficient(String area) {
        if (area.equals("Warsaw"))
        this.thermalCoefficient = 1.0;
    }

    public void setCharacteristicStrainOfSnow() {
        this.characteristicStrainOfSnow = 0.9;
    }

    public void setCharacteristicStrainOfMinorPour(Double coefficientOfRoofShapeOfMinorPour,
                                                   Double coefficientOfExposure,
                                                   Double thermalCoefficient,
                                                   Double characteristicStrainOfSnow) {
        this.characteristicStrainOfMinorPour = round(coefficientOfRoofShapeOfMinorPour*
                coefficientOfExposure*thermalCoefficient*characteristicStrainOfSnow,2);
    }

    public void setCharacteristicStrainOfMajorPour(Double coefficientOfRoofShapeOfMajorPour,
                                                   Double coefficientOfExposure,
                                                   Double thermalCoefficient,
                                                   Double characteristicStrainOfSnow) {
        this.characteristicStrainOfMajorPour = round(coefficientOfRoofShapeOfMajorPour*
                coefficientOfExposure*thermalCoefficient*characteristicStrainOfSnow,2);
    }

    public void setComputationalStrainOfMinorPour(Double characteristicStrainOfMinorPour,
                                                  Double coefficientOfVariableStrain) {
        this.computationalStrainOfMinorPour = round(characteristicStrainOfMinorPour*
                coefficientOfVariableStrain,2);
    }

    public void setComputationalStrainOfMajorPour(Double characteristicStrainOfMajorPour,
                                                  Double coefficientOfVariableStrain) {
        this.computationalStrainOfMajorPour = round(characteristicStrainOfMajorPour*
                coefficientOfVariableStrain,2);
    }

    public Double getCoefficientOfRoofShapeOfMajorPour() {
        return coefficientOfRoofShapeOfMajorPour;
    }

    public Double getCoefficientOfRoofShapeOfMinorPour() {
        return coefficientOfRoofShapeOfMinorPour;
    }

    public Double getCoefficientOfExposure() {
        return coefficientOfExposure;
    }

    public Double getThermalCoefficient() {
        return thermalCoefficient;
    }

    public Double getCharacteristicStrainOfSnow() {
        return characteristicStrainOfSnow;
    }

    public Double getCoefficientOfVariableStrain() {
        return coefficientOfVariableStrain;
    }

    public Double getCharacteristicStrainOfMinorPour() {
        return characteristicStrainOfMinorPour;
    }

    public Double getCharacteristicStrainOfMajorPour() {
        return characteristicStrainOfMajorPour;
    }

    public Double getComputationalStrainOfMinorPour() {
        return computationalStrainOfMinorPour;
    }

    public Double getComputationalStrainOfMajorPour() {
        return computationalStrainOfMajorPour;
    }

    private static double round (double value, int places){
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
