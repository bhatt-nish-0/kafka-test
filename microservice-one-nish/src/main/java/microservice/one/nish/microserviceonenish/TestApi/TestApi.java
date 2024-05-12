package microservice.one.nish.microserviceonenish.TestApi;

import microservice.one.nish.microserviceonenish.entity.Student;
import microservice.one.nish.microserviceonenish.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/retrieve-student")
public class TestApi {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public List<Student> getStudents() {
        List<Student> students=  this.studentRepository.findAll();

        System.out.println("printing out student's nrics");
        for (Student s: students) {

            System.out.println(s.getNric());
        }

        return students;
    }
}
