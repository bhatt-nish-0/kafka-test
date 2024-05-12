package microservice.one.nish.microserviceonenish.pojo;

import microservice.one.nish.microserviceonenish.entity.Student;

public class CrudStudentPojo {

    private String operationType;

    private Student student;

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
