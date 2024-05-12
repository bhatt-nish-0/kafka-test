package microservice.one.nish.microserviceonenish.kafkaListener;

import com.google.gson.Gson;
import microservice.one.nish.microserviceonenish.entity.Student;
import microservice.one.nish.microserviceonenish.pojo.CrudStudentPojo;
import microservice.one.nish.microserviceonenish.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerCrudData {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @KafkaListener(topics="crud-data",groupId = "m-s")
    public void crudData(String s) {

        System.out.println("only 1 ms needs to update data");
        CrudStudentPojo crudStudentPojo = new Gson().fromJson(s, CrudStudentPojo.class);

        if (crudStudentPojo.getOperationType().equals("ADD")) {
            String nricToRetrieve = crudStudentPojo.getStudent().getNric();

            Student byNric = studentRepository.findByNric(nricToRetrieve);

            if (byNric != null) {
                System.out.println("student already exists!");
                return;
            }

            Student save = studentRepository.save(crudStudentPojo.getStudent());

            crudStudentPojo.setStudent(save);
            String s1 = new Gson().toJson(crudStudentPojo);
            kafkaTemplate.send("crud-data-response", s1);
        }

        if (crudStudentPojo.getOperationType().equals("UPDATE")) {

            String nricToRetrieve = crudStudentPojo.getStudent().getNric();

            Student byNric = studentRepository.findByNric(nricToRetrieve);

            if (byNric == null) {
                System.out.println("cant find student in db");
                return;
            }

            Student save = studentRepository.save(crudStudentPojo.getStudent());

            crudStudentPojo.setStudent(save);
            String s1 = new Gson().toJson(crudStudentPojo);
            kafkaTemplate.send("crud-data-response", s1);
        }

        if (crudStudentPojo.getOperationType().equals("DELETE")) {

            String nricToRetrieve = crudStudentPojo.getStudent().getNric();

            Student byNric = studentRepository.findByNric(nricToRetrieve);

            if (byNric == null) {
                System.out.println("cant find student in db for deleting...");
                return;
            }

            studentRepository.delete(crudStudentPojo.getStudent());

            crudStudentPojo.setStudent(crudStudentPojo.getStudent());
            String s1 = new Gson().toJson(crudStudentPojo);
            kafkaTemplate.send("crud-data-response", s1);
        }
    }
}
