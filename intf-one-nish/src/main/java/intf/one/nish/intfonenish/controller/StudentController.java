package intf.one.nish.intfonenish.controller;

import com.google.gson.Gson;
import intf.one.nish.intfonenish.IntfUtil.IntfUtil;
import intf.one.nish.intfonenish.pojo.CrudStudentPojo;
import intf.one.nish.intfonenish.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @GetMapping
    public List<Student> getAllStudents() {

        Map<String, Student> studentMap =
                IntfUtil.getStudentMap();

        List<Student> students = new ArrayList<>();

        for (Map.Entry<String, Student> entry : studentMap.entrySet()) {
            // Add each value to the list
            students.add(entry.getValue());
        }

        return students;
    }

    @GetMapping("{nric}")
    public Student getAllStudents(@PathVariable String nric) {

        Map<String, Student> studentMap =
                IntfUtil.getStudentMap();

        Student student = null;

        for (Map.Entry<String, Student> entry : studentMap.entrySet()) {
            // Add each value to the list
            if(entry.getValue().getNric().equals(nric)) {
                student = entry.getValue();
            }
        }

        return student;
    }

    @PostMapping
    public String insertStudent(@RequestBody Student student) {

        CrudStudentPojo crudStudentPojo = new CrudStudentPojo();
        crudStudentPojo.setStudent(student);
        crudStudentPojo.setOperationType("ADD");

        String addStudent = new Gson().toJson(crudStudentPojo);

        kafkaTemplate.send("crud-data", addStudent);

        return "success";
    }

    @PutMapping
    public String updateStudent(@RequestBody Student student) {

        CrudStudentPojo crudStudentPojo = new CrudStudentPojo();
        crudStudentPojo.setStudent(student);
        crudStudentPojo.setOperationType("UPDATE");

        String updateStudent = new Gson().toJson(crudStudentPojo);

        kafkaTemplate.send("crud-data",updateStudent);

        return "all good";
    }

    @DeleteMapping
    public String deleteStudent(@RequestBody Student student) {
        CrudStudentPojo crudStudentPojo = new CrudStudentPojo();
        crudStudentPojo.setStudent(student);
        crudStudentPojo.setOperationType("DELETE");

        String updateStudent = new Gson().toJson(crudStudentPojo);

        kafkaTemplate.send("crud-data",updateStudent);

        return "all power";
    }

    @PostMapping("/stressTest")
    public String addStudentWithTest(@RequestBody Student student) throws InterruptedException {

        CrudStudentPojo crudStudentPojo = new CrudStudentPojo();
        crudStudentPojo.setStudent(student);
        crudStudentPojo.setOperationType("ADD");

        String addStudent = new Gson().toJson(crudStudentPojo);

        kafkaTemplate.send("crud-data", addStudent);

        Thread.sleep(2000);
        Map<String, Student> studentMap = IntfUtil.getStudentMap();

        Student student1 = studentMap.get(student.getNric());

        if (student1 == null) {
            System.out.println("student has not been added in mem map hehe");
        }else {
            System.out.println("student has been added to mem map!");
        }

        return "success";
    }
}
