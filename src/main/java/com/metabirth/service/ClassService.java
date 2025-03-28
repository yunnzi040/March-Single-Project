package com.metabirth.service;

import com.metabirth.dao.ClassesDao;
import com.metabirth.model.Classes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClassService {
    private static final Logger log = LoggerFactory.getLogger(Classes.class);
    private final ClassesDao classesDao;
    private final Connection dbConnection;

    public ClassService(Connection dbConnection) {
        this.dbConnection = dbConnection;
        this.classesDao = new ClassesDao(dbConnection);
    }

    // 모든 수업 조회
    public List<Classes> getAllClasses() {
        List<Classes> classes = classesDao.getAllClasses();

        if (classes == null) {
            log.error("수업이 존재하지 않거나 오류가 발생했습니다.");
            return null;
        }
        return classes;
    }

    // 특정 수업 조회
    public Classes getClasses(String classCode){
        Classes classes = classesDao.getClasses(classCode);

        if (classes == null) {
            log.error("해당 수업이 존재하지 않거나 오류가 발생했습니다.");
            return null;
        }

        return classes;
    }

    // 수업 수정
    public boolean updateClasses(Classes classes) throws SQLException {
        Classes existing = classesDao.getClasses(classes.getClassCode());

        if (existing == null) {
            throw new IllegalArgumentException("수정할 수업을 찾을 수 없습니다!");
        }

        boolean result = classesDao.updateClasses(classes);
        if (!result) {
            throw new SQLException("수정하는 과정에서 오류가 발생되었습니다.");
        }
        return result;
    }



    // 수업 생성
    public boolean addClasses(Classes classes){
        // 수업 코드로 각 수업을 구분하니 중복되는 수업 코드가 있는지 확인
        // class 객체로 입력받으니까 객체 중 수업 코드랑 DB에 있는 수업 코드랑 같은지 확인
        List<Classes> classesList = classesDao.getAllClasses();
        for (Classes c : classesList) {
            if (c.getClassCode().equals(classes.getClassCode())) {
                throw new IllegalArgumentException("이미 존재하는 수업입니다.");
            }
        }

        return classesDao.addClasses(classes);
    }

    // 수업 삭제
    public boolean deleteClasses(String class_code) throws SQLException {
        Classes existing = getClasses(class_code);

        if (existing == null) {
            throw new IllegalArgumentException("삭제할 강의를 찾을 수 없습니다.");
        }

        boolean result = classesDao.deleteClasses(class_code);
        if (!result) {
            throw new SQLException("삭제하는 과정에서 오류가 발생되었습니다.");
        }
        return result;
    }

}
