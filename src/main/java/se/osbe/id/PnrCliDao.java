package se.osbe.id;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.osbe.id.enums.GenderType;

@Data
@NoArgsConstructor
public class PnrCliDao {

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

    @JsonProperty("gender")
    private GenderType gender;

    @JsonProperty("zodiacSign")
    private String zodiacSign;

    @JsonProperty("zodiacSignSwe")
    private String zodiacSignSwe;
}
