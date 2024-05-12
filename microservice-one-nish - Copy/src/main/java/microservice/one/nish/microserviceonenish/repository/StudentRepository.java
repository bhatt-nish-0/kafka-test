package microservice.one.nish.microserviceonenish.repository;

import microservice.one.nish.microserviceonenish.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {

      public Student findByNric(String nric);
}
