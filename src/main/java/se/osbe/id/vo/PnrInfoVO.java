package se.osbe.id.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import se.osbe.id.enums.GenderType;
import se.osbe.id.enums.IDType;

public record PnrInfoVO(
        @JsonProperty("personnummer10") String personnummer10,
        @JsonProperty("personnummer11") String personnummer11,
        @JsonProperty("personnummer12") String personnummer12,
        @JsonProperty("personnummer13") String personnummer13,
        @JsonProperty("lastFourDigits") String lastFourDigits,
        @JsonProperty("isForgiving") boolean isForgiving,
        @JsonProperty("correctChecksum") int correctChecksum,
        @JsonProperty("birthDate") String birthDate,
        @JsonProperty("age") int age,
        @JsonProperty("daysSinceBirth") int daysSinceBirth,
        @JsonProperty("gender") GenderType gender,
        @JsonProperty("zodiacSign") String zodiacSign,
        @JsonProperty("zodiacSignSwe") String zodiacSignSwe,
        @JsonProperty("chineseZodiacAnimal") String chineseZodiacAnimal,
        @JsonProperty("chineseZodiacAnimalSwe") String chineseZodiacAnimalSwe,
        @JsonProperty("idType") IDType idType,
        @JsonProperty("toString") String toZtring
) {
}
