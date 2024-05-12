package intf.one.nish.intfonenish.IntfUtil;

import intf.one.nish.intfonenish.pojo.Student;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class IntfUtil {

    private static Map<String, Student> studentMap = new ConcurrentHashMap<>();

    public static Map<String, Student> getStudentMap() {
        return studentMap;
    }

    public static void setStudentMap(Map<String, Student> studentMap) {
        IntfUtil.studentMap = studentMap;
    }
}
