package exercise.controller;

import exercise.model.Course;
import exercise.repository.CourseRepository;
import org.apache.el.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping(path = "")
    public Iterable<Course> getCorses() {
        return courseRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Course getCourse(@PathVariable long id) {
        return courseRepository.findById(id);
    }

    // BEGIN
    @GetMapping(path = "/{id}/previous")
    public Iterable<Course> getCoursePrevious(@PathVariable long id) {

        String path = courseRepository.findById(id).getPath();
        List<Long> ids = new ArrayList<>();
        if (path != null) {
            ids = Arrays.stream(path.split("\\."))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            System.out.println(ids);
        }

        return courseRepository.findAllById(ids);
    }
    // END

}
