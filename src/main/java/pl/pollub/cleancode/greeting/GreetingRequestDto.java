package pl.pollub.cleancode.greeting;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GreetingRequestDto {
    //[MagicNumber]
    private static final int MAX_NAME_LENGTH = 100;

    @NotBlank
    @Size(max = MAX_NAME_LENGTH)
    private String name;
}
