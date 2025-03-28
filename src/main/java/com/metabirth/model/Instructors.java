package com.metabirth.model;

import java.time.LocalDateTime;

public class Instructors {
    private int instructor_id;
    private String instructor_name;
    private String phone;
    private Byte status;
    private LocalDateTime created_at;
    private String email;
    private String password;

    public Instructors(int instructor_id, String instructor_name, String phone, Byte status, LocalDateTime created_at, String email, String password) {
        this.instructor_id = instructor_id;
        this.instructor_name = instructor_name;
        this.phone = phone;
        this.status = status;
        this.created_at = created_at;
        this.email = email;
        this.password = password;
    }

    public int getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(int instructor_id) {
        this.instructor_id = instructor_id;
    }

    public String getInstructor_name() {
        return instructor_name;
    }

    public void setInstructor_name(String instructor_name) {
        this.instructor_name = instructor_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "classes{" +
                "instructor_id=" + instructor_id +
                ", instructor_name='" + instructor_name + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", created_at=" + created_at +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
