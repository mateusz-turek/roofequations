package project1.roofequations.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "wood_based_material")
public class HGirderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double ownWeight; //g mean
    private Double momentOfInertia; // r
    private Double modulusOfElasticity; // E mean
    private Double momentOfSecondDegreeInertia; // I4
    private Double height;
  //  private Double widthOfHardBoard = 8.0;
    private Double width;
  //  private Double footlingOfLVLHeight = 39.0;

}
