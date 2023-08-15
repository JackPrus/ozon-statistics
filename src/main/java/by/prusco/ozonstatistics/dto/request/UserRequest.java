package by.prusco.ozonstatistics.dto.request;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class UserRequest {
    private String login;
    private String password;
    private String passwordRepeat;
    private String email;
}
