package microservice.one.nish.microserviceonenish.kafkaListener;

import com.google.gson.Gson;
import microservice.one.nish.microserviceonenish.entity.Student;
import microservice.one.nish.microserviceonenish.pojo.InitPojo;
import microservice.one.nish.microserviceonenish.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaListenerInitData {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @KafkaListener(topics="init-data", groupId = "m-s")
    public void initData(String s) {

        System.out.println("only 1 ms needs to initialize");

        InitPojo initPojo = new Gson().fromJson(s, InitPojo.class);

        if (initPojo.getGiveMeData().equals("giveMeData")) {

            List<Student> students = studentRepository.findAll();

            String studentReturnData = new Gson().toJson(students);

            this.kafkaTemplate.send("init-data-response",studentReturnData);
        }
    }
}
