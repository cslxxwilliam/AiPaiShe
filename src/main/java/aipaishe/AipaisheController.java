package aipaishe;

import aipaishe.models.User;
import aipaishe.models.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hillmon on 12/4/2017.
 */
@RestController
public class AipaisheController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
