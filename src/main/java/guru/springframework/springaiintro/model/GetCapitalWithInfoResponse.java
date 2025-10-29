package guru.springframework.springaiintro.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Created by Pierrot on 29-10-2025.
 */
public record GetCapitalWithInfoResponse(@JsonPropertyDescription("The name of the city") String city,
                                         @JsonPropertyDescription("this is the population") Long population,
                                         @JsonPropertyDescription("this is the state or region") String region,
                                         @JsonPropertyDescription("this is the official language") String language,
                                         @JsonPropertyDescription("this is the currency") String currency) {
}
