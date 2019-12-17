package project1.roofequations.service;

public class HomeControllerService {
    protected boolean widthValidator(String width) {
        return !width.equals("") &&
                !width.matches(".*[a-zA-Z]+.*") &&
                !width.startsWith(",") &&
                !width.endsWith(",") &&
                !width.endsWith(".") &&
                !width.matches(".*[!@#$%^&*()/?><|`~:+]+.*") &&
                !width.contains("*");
    }

    protected Double widthChanger(String width) {
        StringBuilder widthChanger = new StringBuilder(width);
        if (width.contains(",")) {
            int counter = 0;
            for (int i = 0; i < width.length(); i++) {
                if (width.charAt(i) == ',') {
                    break;
                }
                counter = counter + 1;
            }
            for (int i = counter + 1; i < width.length(); i++) {
                if (width.charAt(i) == ',' || width.charAt(i) == '.')
                    return null;

                widthChanger.replace(counter, counter + 1, ".");
            }
            return Double.valueOf(widthChanger.toString());
        }
        if (width.contains(".")) {
            int counter = 0;
            for (int i = 0; i < width.length(); i++) {
                if (width.charAt(i) == '.') {
                    break;
                }
                counter = counter + 1;
            }
            for (int i = counter + 1; i < width.length(); i++) {
                if (width.charAt(i) == ',' || width.charAt(i) == '.')
                    return null;

            }
            return Double.valueOf(widthChanger.toString());
        }
        return Double.valueOf(widthChanger.toString());
    }

    protected boolean angleValidator(Double angle) {
        return angle != null;
    }
}
