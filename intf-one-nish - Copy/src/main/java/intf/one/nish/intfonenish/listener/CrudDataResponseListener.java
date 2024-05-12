package intf.one.nish.intfonenish.listener;

import com.google.gson.Gson;
import intf.one.nish.intfonenish.IntfUtil.IntfUtil;
import intf.one.nish.intfonenish.pojo.CrudStudentPojo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

    @Component
    public class CrudDataResponseListener {

        @KafkaListener(topics="crud-data-response", groupId="intf-2")
        public void receive(String s) {

            CrudStudentPojo crudStudentPojo = new Gson().fromJson(s, CrudStudentPojo.class);

            if (crudStudentPojo.getOperationType().equals("ADD")) {
                IntfUtil.getStudentMap().put(crudStudentPojo.getStudent().getNric(),crudStudentPojo.getStudent());
            }

            if (crudStudentPojo.getOperationType().equals("UPDATE")) {
                IntfUtil.getStudentMap().put(crudStudentPojo.getStudent().getNric(),crudStudentPojo.getStudent());
            }

            if (crudStudentPojo.getOperationType().equals("DELETE")) {
                IntfUtil.getStudentMap().remove(crudStudentPojo.getStudent().getNric());
            }
        }
}
