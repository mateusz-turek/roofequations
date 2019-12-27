package project1.roofequations.model;

import lombok.Data;


import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Entity
@Data
@Table(name = "roof_primary_values_by_user")
public class RoofModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double width;
    private Double angle;

    private double proportion;

    private Double roofHeight;
    private Double lengthOfRafter;
    private Double lowerPartOfRafter;
    private Double upperPartOfRafter;


    public RoofModel() {
    }

    public void setAngle(Double angle) {
        if (angle == 30 || angle == 45 || angle == 60) {
            this.angle = angle;
        }
    }

    public void setWidth(Double width) {
        if (width > 0)
            this.width = round(width,2);
    }

    public void setProportion() {
        this.proportion = 0.6;
    }

    public void setRoofHeight(Double width, Double angle) {
        if (angle == 30) {
            this.roofHeight = round(width * 0.5 * (Math.sqrt(3) / 3),2);
        }
        if (angle == 45) {
            this.roofHeight = round(width * 0.5 * 1,2);
        }
        if (angle == 60) {
            this.roofHeight = round(width * 0.5 * (Math.sqrt(3)),2);
        }
    }

    public void setLengthOfRafter(Double width, Double angle) {
        if (angle == 30) {
            this.lengthOfRafter = round(width / (2 * Math.sqrt(3) / 2),2);
        }
        if (angle == 45) {
            this.lengthOfRafter = round(width / (2 * Math.sqrt(2) / 2),2);
        }
        if (angle == 60) {
            this.lengthOfRafter = round(width / 0.5,2);
        }
    }

    public void setLowerPartOfRafter(double proportion, Double rafterLength) {
        this.lowerPartOfRafter = round(proportion * rafterLength,2);
    }

    public void setUpperPartOfRafter(Double rafterLength, Double lowerPartOfRafter) {
        this.upperPartOfRafter = round(rafterLength - lowerPartOfRafter,2);
    }

    public Double getAngle() {
        return angle;
    }

    public double getProportion() {
        return proportion;
    }

    public Double getRoofHeight() {
        return roofHeight;
    }

    public Double getLengthOfRafter() {
        return lengthOfRafter;
    }

    public Double getLowerPartOfRafter() {
        return lowerPartOfRafter;
    }

    public Double getUpperPartOfRafter() {
        return upperPartOfRafter;
    }

    public Double getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return "Dimensions: " +
                "width =" + round(width,2) + " [mm]"+
                ", angle =" + angle + " [degrees]"+
                ", proportion =" + proportion + " [mm]"+
                ", roofHeight =" + round(roofHeight,2) + " [mm]"+
                ", lengthOfRafter =" + round(lengthOfRafter,2) + " [mm]"+
                ", lowerPartOfRafter =" + round(lowerPartOfRafter,2) + " [mm]"+
                ", upperPartOfRafter =" + round(upperPartOfRafter,2) + " [mm]"
               ;
    }
    private static double round (double value, int places){
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

