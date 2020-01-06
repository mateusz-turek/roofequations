package project1.roofequations.Interface;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
public interface StaticEquations {

     default Double bendingMomentsInRafterOnMDLinchpin(Double modifierK1,Double PerpendicularOwnStrain,Double lengthOfRafter){
        return round(modifierK1*PerpendicularOwnStrain*lengthOfRafter*lengthOfRafter,2);
     }
     default Double perpendicularReactionsToRoofPour( Double modifierN,Double perpendicularStrain,Double lengthOfRafter){
         return round(modifierN*perpendicularStrain*lengthOfRafter,2);
     }

     default Double compressiveStrengthOnDLinchpin(Double C1,Double computationalParallelStrain,Double upperPartOfRafter){
         return round(C1-(computationalParallelStrain*upperPartOfRafter),2);
     }
     default Double getC1(Double angle,Double computationalParallelStrain){
         double b = Math.toRadians(90-(angle*2));
         double bSin = Math.sin(b);
         Double bCos = Math.cos(b);
         return round((1+bSin)/(bCos*computationalParallelStrain),2);
     }
     default Double strengthInADSpan(Double computationalParallelStrain,Double lowerPartOfRafter){
         return round(0.5*computationalParallelStrain*lowerPartOfRafter,2);
     }
     default Double distanceFromLinchpinToPlaceOfMaximumMomentumOfMad(Double Ra, Double computationalPerpendicularStrain){
         return round(Ra/computationalPerpendicularStrain,2);
     }
     default Double valueOfNad(Double Rha,Double computationalParallelStrain,Double distanceFromLinchpinToPlaceOfMaximumMomentumOfMad){
         return round(Rha-(computationalParallelStrain*distanceFromLinchpinToPlaceOfMaximumMomentumOfMad),2);
     }
     default Double crossSectionalAreaOfWoodBasedMaterial (Double width,Double height){
         return round((2*39*width)+(8*(height-2*39)),2);
     }
     default Double bucklingLength (Double u,Double LowerPartOfRafter){
         return round(u*LowerPartOfRafter,2);
     }
     default Double defaultSlim (Double bucklingLength,Double momentOfInertia){
         return round((bucklingLength*1000)/momentOfInertia,2);
     }
     default Double relativeSlenderness( Double defaultSlim, Double fck,Double modulusOfElasticity){
         return round((defaultSlim/Math.PI)*Math.sqrt((fck/(modulusOfElasticity*1000))),2);
     }


     static double round (double value, int places){
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
