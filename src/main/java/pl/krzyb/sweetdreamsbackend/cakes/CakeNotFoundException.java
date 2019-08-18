package pl.krzyb.sweetdreamsbackend.cakes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CakeNotFoundException extends RuntimeException {
    public CakeNotFoundException() {
        super("Cake with the given name does not exist");
    }
}
