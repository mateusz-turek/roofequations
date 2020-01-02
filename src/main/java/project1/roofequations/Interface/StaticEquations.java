package project1.roofequations.Interface;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Component
public interface StaticEquations {

     default Double bendingMomentsInRafterOnMDLinchpin(Double modifierK1,Double PerpendicularOwnStrain,Double lengthOfRafter){
        return round(modifierK1*PerpendicularOwnStrain*lengthOfRafter*lengthOfRafter,2);
     }
     default Double perpendicularReactionsToRoofPour( Double modifierN,Double perpendicularStrain,Double lengthOfRafter){
         return round(modifierN*perpendicularStrain*lengthOfRafter,2);
     }

     default Double compressiveStrengthOnDLinchpin(Double C1,Double computationalParallelStrain,Double upperPartOfRafter){
         return C1-(computationalParallelStrain*upperPartOfRafter);
     }
     default Double getC1(Double angle,Double computationalParallelStrain){
         Double b = Math.toRadians(90-angle*2);
         Double bSin = Math.sin(b);
         Double bCos = Math.cos(b);
         return (1+bSin)/bCos*computationalParallelStrain;
     }
     default Double strengthInADSpan(Double computationalParallelStrain,Double lowerPartOfRafter){
         return 0.5*computationalParallelStrain*lowerPartOfRafter;
     }

     static double round (double value, int places){
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
