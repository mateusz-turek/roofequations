package project1.roofequations.model;

class RoofModel {
    private Double width;
    private int angle;

    private double proportion;

    private Double roofHeight;
    private Double lengthOfRafter;
    private Double lowerPartOfRafter;
    private Double upperPartOfRafter;

    RoofModel() {
    }

    void setAngle(int angle) {
        if (angle == 30 || angle == 45 || angle == 60) {
            this.angle = angle;
        } else
            System.out.println("apropriate angles: 30, 45, 60");
    }

    void setWidth(Double width) {
        if (width > 0)
            this.width = width;
    }

    void setProportion() {
        this.proportion = 0.6;
    }

    void setRoofHeight(Double width, int angle) {
        if (angle == 30) {
            this.roofHeight = width * 0.5 * (Math.sqrt(3) / 3);
        }
        if (angle == 45) {
            this.roofHeight = width * 0.5 * 1;
        }
        if (angle == 60) {
            this.roofHeight = width * 0.5 * (Math.sqrt(3));
        }
    }

    void setLengthOfRafter(Double width, int angle) {
        if (angle == 30) {
            this.lengthOfRafter = width / (2 * Math.sqrt(3) / 2);
        }
        if (angle == 45) {
            this.lengthOfRafter = width / (2 * Math.sqrt(2) / 2);
        }
        if (angle == 60) {
            this.lengthOfRafter = width / 0.5;
        }
    }

    void setLowerPartOfRafter(double proportion, Double rafterLength) {
        this.lowerPartOfRafter = proportion * rafterLength;
    }

    void setUpperPartOfRafter(Double rafterLength, Double lowerPartOfRafter) {
        this.upperPartOfRafter = rafterLength - lowerPartOfRafter;
    }

    int getAngle() {
        return angle;
    }

    double getProportion() {
        return proportion;
    }

    Double getRoofHeight() {
        return roofHeight;
    }

    Double getLengthOfRafter() {
        return lengthOfRafter;
    }

    Double getLowerPartOfRafter() {
        return lowerPartOfRafter;
    }

    Double getUpperPartOfRafter() {
        return upperPartOfRafter;
    }

    Double getWidth() {
        return width;
    }
}

