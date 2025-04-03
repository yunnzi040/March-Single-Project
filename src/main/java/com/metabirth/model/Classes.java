package com.metabirth.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Classes {
    private int classId;
    private String classCode;
    private String className;
    private String classTime;
    private int capacity;
    private BigDecimal price;
    private Byte status;
    private LocalDateTime createdAt;


    public Classes(int classId, String classCode, String className, String classTime, int capacity, BigDecimal price, Byte status, LocalDateTime createdAt) {
        this.classId = classId;
        this.classCode = classCode;
        this.className = className;
        this.classTime = classTime;
        this.capacity = capacity;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "[" +
                "수업 번호= " + classId +
                ", 수업 코드= '" + classCode + '\'' +
                ", 수업 이름= '" + className + '\'' +
                ", 수업 시간= '" + classTime + '\'' +
                ", 수업 인원= " + capacity +
                ", 수업 가격= " + price +
                ", 수업 여부= " + status +
                ", 수업 생성일= " + createdAt +
                ']';
    }
}