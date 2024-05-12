package intf.one.nish.intfonenish.runner;

import com.google.gson.Gson;
import intf.one.nish.intfonenish.pojo.InitPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RetrieveStudentInfoRunner implements ApplicationRunner {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        InitPojo initPojo = new InitPojo();
        initPojo.setGiveMeData("giveMeData");

        String initJson = new Gson().toJson(initPojo);

        this.kafkaTemplate.send("init-data",initJson);

    }
}
