package project1.roofequations.Interface;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
public interface StaticEquations {

     default Double bendingMomentsInRafterOnMDLinchpin(Double modifierK1
             ,Double PerpendicularOwnStrain
             ,Double lengthOfRafter){
        return round(modifierK1*PerpendicularOwnStrain*lengthOfRafter*lengthOfRafter,2);
     }
     default Double perpendicularReactionsToRoofPour( Double modifierN
             ,Double perpendicularStrain
             ,Double lengthOfRafter){
         return round(modifierN*perpendicularStrain*lengthOfRafter,2);
     }

     default Double compressiveStrengthOnDLinchpin(Double C1
             ,Double computationalParallelStrain
             ,Double upperPartOfRafter){
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
     default Double distanceFromLinchpinToPlaceOfMaximumMomentumOfMad(Double Ra
             , Double computationalPerpendicularStrain){
         return round(Ra/computationalPerpendicularStrain,2);
     }
     default Double valueOfNad(Double Rha
             ,Double computationalParallelStrain
             ,Double distanceFromLinchpinToPlaceOfMaximumMomentumOfMad){
         return round(Rha-(computationalParallelStrain*distanceFromLinchpinToPlaceOfMaximumMomentumOfMad)
                 ,2);
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
     default Double coefficientOfFlexibility(Double bC,Double relativeSlenderness){
         return round(0.5*(1 + bC *( relativeSlenderness-0.3 ) + Math.pow(relativeSlenderness,2)),2);
     }
     default Double coefficientOfBuckling (Double coefficientOfFlexibility,Double relativeSlenderness){
         return round((1.0 / (coefficientOfFlexibility + (Math
                 .sqrt((Math
                         .pow(coefficientOfFlexibility,2)-Math
                         .pow(relativeSlenderness,2)))))),2);
     }
     default Double materialEndurance (Double momentOfSecondDegreeInertia, Double hGirderHeight){
         return round((momentOfSecondDegreeInertia*10000)/(hGirderHeight/2),2);
     }
     default Double computationalCompressiveTensionAlongFibres(Double nAD,Double crossSectionalArea){
         return round((nAD*-1000)/crossSectionalArea,2);
     }
    default Double computationalTensionAlongYAxis (Double mMax,Double computationalCompressiveTensionAlongFibres){
         return round((mMax*1000)/computationalCompressiveTensionAlongFibres,2);
    }
    default Double bearingCapacity (Double computationalCompressiveTensionAlongFibres,
                                    Double coefficientOfBuckling,Double fcod, Double computationalTensionAlongYAxis,
                                    Double fmyd){
         return round((computationalCompressiveTensionAlongFibres/(coefficientOfBuckling+fcod))
         +(computationalTensionAlongYAxis/fmyd),2);
    }
    default Double bearingCapacityOnLinchpin(Double computationalCompressiveTensionAlongFibres
    ,Double fc0d,Double computationalTensionAlongYAxis,Double fmyd){
         return round((Math.pow((computationalCompressiveTensionAlongFibres/fc0d),2))
                 +(computationalTensionAlongYAxis/fmyd),2);
    }
    default Double serviceabilityLimitState (Double characteristicPerpendicularStrain
    ,Double lowerPartOfRafter, Double modulusOfElasticity,Double momentOfSecondDegreeInertia
    ,Double hGirderHeight){
         return round(((5 * characteristicPerpendicularStrain*(Math.pow((lowerPartOfRafter*1000),4)))
                 / (384 *(modulusOfElasticity*1000)*(momentOfSecondDegreeInertia*10000000)
                 *(1 + 19.2*Math.pow(hGirderHeight/(1000*lowerPartOfRafter),2)))),2);
    }
    default Double finalServiceabilityLimitStateOfOwnStrain(Double serviceabilityLimitState,Double kDef){
         return round(serviceabilityLimitState*(1+kDef),2);
    }
    default Double admissibleBend(Double lowerPartOfRafter){
         return round((lowerPartOfRafter*1000)/200,2);
    }
    default Double admissibleBendCheck(Double admissibleBend, Double finalServiceabilityLimitStateOfOwnStrain){
         return round(100*(finalServiceabilityLimitStateOfOwnStrain/admissibleBend),0);
    }


     static double round (double value, int places){
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
