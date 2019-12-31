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


     static double round (double value, int places){
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
