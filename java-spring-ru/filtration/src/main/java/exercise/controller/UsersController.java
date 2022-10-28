package exercise.controller;
import exercise.model.User;
import exercise.model.QUser;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

// Зависимости для самостоятельной работы
// import org.springframework.data.querydsl.binding.QuerydslPredicate;
// import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    // BEGIN
    @GetMapping()
    public Iterable<User> getUserConteinName(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName) {
        List<User> result = new ArrayList<>();
        if (firstName == null && lastName == null) {
            result = this.userRepository.findAll();
        }
        if (firstName != null && lastName == null) {
            result = (List<User>) this.userRepository.findAll(QUser.user.firstName.containsIgnoreCase(firstName));
        }
        if (firstName == null && lastName != null) {
            result = (List<User>) this.userRepository.findAll(QUser.user.lastName.containsIgnoreCase(lastName));
        }
        if (firstName != null && lastName != null) {
            result = (List<User>) this.userRepository.findAll(QUser.user.firstName.containsIgnoreCase(firstName)
                    .and(QUser.user.lastName.containsIgnoreCase(lastName)));
        }
        return result;
    }
    // END
}

