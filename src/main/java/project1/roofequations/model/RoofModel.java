package project1.roofequations.model;

public class RoofModel {
    private Double width;
    private int angle;

    public RoofModel(Double width, int angle) {
        this.width = width;
        this.angle = angle;
    }

    public Double getWidth() {
        return width;
    }

    public void setAngle(int angle) {
        if (angle == 30 || angle == 45 || angle == 60) {
            this.angle = angle;
        } else
            this.angle = 0;
    }

    public int getAngle() {
        return angle;
    }

    public static Double roofHeight(Double width, int angle) {
        switch (angle) {
            case 30:
                return width * 0.5 * (Math.sqrt(3) / 3);
            case 45:
                return width * 0.5 * 1;
            case 60:
                return width * 0.5 * (Math.sqrt(3));
        }
        return null;
    }

    public static Double lengthOfRafter(Double width, int angle) {
        switch (angle) {
            case 30:
                return width / (2 * Math.sqrt(3) / 2);
            case 45:
                return width / (2 * Math.sqrt(2) / 2);
            case 60:
                return width / 0.5;
        }
        return null;
    }

    public static Double lowerPartOfRafter(double proportion, Double rafterLength) {
        return proportion * rafterLength;
    }

    public static Double upperPartOfRafter(Double rafterLength, Double upperPartOfRafter) {
        return rafterLength - upperPartOfRafter;
    }

}

