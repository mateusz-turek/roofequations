package project1.roofequations.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WindStrainModel {

    private Double baseSpeedOfWind;

    private Double actualBaseSpeedOfWind;
    private Double baseValueOfSpeedPressure;
    private Double coefficientOfExposition;
    private Double topValueOfSpeedPressure;
    private Double pressureOfWindwardSide;
    private Double pressureOfLeewardSide;

    public WindStrainModel() {
    }

    public void setPressureOfWindwardSide(Double topValueOfSpeedPressure) {
        this.pressureOfWindwardSide = topValueOfSpeedPressure;
    }

    public void setPressureOfLeewardSide(Double topValueOfSpeedPressure) {
        this.pressureOfLeewardSide = round(-0.7*topValueOfSpeedPressure,2);
    }

    public void setTopValueOfSpeedPressure(Double coefficientOfExposition, Double actualBaseSpeedOfWind) {
        this.topValueOfSpeedPressure = round(coefficientOfExposition*actualBaseSpeedOfWind,2);
    }

    public void setCoefficientOfExposition(Double heightOfBuilding) {
        this.coefficientOfExposition = round(2.3*Math.pow((heightOfBuilding/10),0.24),2);
    }

    public void setActualBaseSpeedOfWind(Double baseSpeedOfWind, Double coefficientOfDirection,
                                         Double coefficientOfSeason) {
        this.actualBaseSpeedOfWind = round(baseSpeedOfWind*coefficientOfDirection*coefficientOfSeason,2);
    }

    public void setBaseValueOfSpeedPressure(Double airDensity, Double actualBaseSpeedOfWind) {
        this.baseValueOfSpeedPressure = round(0.5*airDensity*actualBaseSpeedOfWind*actualBaseSpeedOfWind,2);
    }

    public Double getPressureOfWindwardSide() {
        return pressureOfWindwardSide;
    }

    public Double getPressureOfLeewardSide() {
        return pressureOfLeewardSide;
    }

    public Double getBaseValueOfSpeedPressure() {
        return baseValueOfSpeedPressure;
    }

    public Double getCoefficientOfExposition() {
        return coefficientOfExposition;
    }

    public Double getBaseSpeedOfWind() {
        return baseSpeedOfWind;
    }

    public Double getCoefficientOfSeason() {
        return 1.0;
    }

    public Double getActualBaseSpeedOfWind() {
        return actualBaseSpeedOfWind;
    }

    public Double getAirDensity() {
        return 1.25;
    }

    public Double getCoefficientOfDirection() {
        return 1.0;
    }

    public Double getTopValueOfSpeedPressure() {
        return topValueOfSpeedPressure;
    }

    public void setBaseSpeedOfWind(Double high, Double zone) {
        if (zone == 1 && high<300){
            this.baseSpeedOfWind = 22.0;
        }
        if (zone == 2 && high< 300){
            this.baseSpeedOfWind = 26.0;
        }
        if (zone == 3 && high< 300){
            this.baseSpeedOfWind = 22.0;
        }
        if (zone ==1 && high>=300){
            this.baseSpeedOfWind = round(22*(1+0.0006*(zone -300)),2);
        }
        if (zone ==2 && high>=300){
            this.baseSpeedOfWind = 26.0;
        }
        if (zone == 3 && high>= 300){
            this.baseSpeedOfWind = round(22*(1+0.0006*(zone -300)),2);
        }



    }
    private static double round (double value, int places){
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
