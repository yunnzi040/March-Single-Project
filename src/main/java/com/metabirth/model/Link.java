package com.metabirth.model;

public class Link {
    private String classCode;
    private int instructorId;

    public Link(String classCode, int instructorId) {
        this.classCode = classCode;
        this.instructorId = instructorId;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    @Override
    public String toString() {
        return "Link{" +
                "classCode='" + classCode + '\'' +
                ", instructorId=" + instructorId +
                '}';
    }
}
