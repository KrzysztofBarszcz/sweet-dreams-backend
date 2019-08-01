package pl.krzyb.sweetdreamsbackend.cakes;

import lombok.NonNull;
import lombok.Value;

@Value
class Cake {
    @NonNull
    private String name;
}
