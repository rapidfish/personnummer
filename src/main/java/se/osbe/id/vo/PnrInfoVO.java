package se.osbe.id.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.osbe.id.enums.GenderType;
import se.osbe.id.enums.IDType;

@Data
@NoArgsConstructor
public class PnrInfoVO {

    @JsonProperty("personnummer10")
    private String personnummer10;

    @JsonProperty("personnummer11")
    private String personnummer11;

    @JsonProperty("personnummer12")
    private String personnummer12;

    @JsonProperty("personnummer13")
    private String personnummer13;

    @JsonProperty("lastFourDigits")
    private String lastFourDigits;

    @JsonProperty("isForgiving")
    private boolean isForgiving;

    @JsonProperty("correctChecksum")
    private int correctChecksum;

    @JsonProperty("birthDate")
    private String birthDate;

    @JsonProperty("age")
    private int age;

    @JsonProperty("daysSinceBirth")
    private int daysSinceBirth;

    @JsonProperty("gender")
    private GenderType gender;

    @JsonProperty("zodiacSign")
    private String zodiacSign;

    @JsonProperty("zodiacSignSwe")
    private String zodiacSignSwe;

    @JsonProperty("chineseZodiacAnimal")
    private String chineseZodiacAnimal;

    @JsonProperty("chineseZodiacAnimalSwe")
    private String chineseZodiacAnimalSwe;

    @JsonProperty("idType")
    private IDType idType;
}
