package intf.one.nish.intfonenish.listener;

import com.google.gson.Gson;
import intf.one.nish.intfonenish.IntfUtil.IntfUtil;
import intf.one.nish.intfonenish.pojo.Student;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class InitDataResponseListener {

    @KafkaListener(topics="init-data-response", groupId="intf-2")
    public void receive(String s) {


        Student[] students = new Gson().fromJson(s, Student[].class);

        Map<String,Student> studentMap = new ConcurrentHashMap<String, Student>();
        System.out.println("received....");
        for (Student student : students) {
            System.out.println(student.getNric());
            studentMap.put(student.getNric(),student);
        }

        System.out.println("even if service is not restarted, this part will still be updated :(");
        //if (IntfUtil.getStudentMap().size() == 0) {
        //    System.out.println("only udating for the freshly started interface");
            IntfUtil.setStudentMap(studentMap);
        //}
    }

}
