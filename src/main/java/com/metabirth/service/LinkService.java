package com.metabirth.service;

import com.metabirth.dao.ClassesDao;
import com.metabirth.dao.InstructorsDao;
import com.metabirth.dao.LinkDao;
import com.metabirth.model.Classes;
import com.metabirth.model.Instructors;
import com.metabirth.model.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LinkService {
    private static Logger log = LoggerFactory.getLogger(LinkService.class);
    private final LinkDao linkDao;
    private final ClassesDao classesDao;
    private final InstructorsDao instructorsDao;
    private final Connection connection;

    public LinkService(Connection connection) {
        this.connection = connection;
        this.linkDao = new LinkDao(connection);
        this.classesDao = new ClassesDao(connection);
        this.instructorsDao = new InstructorsDao(connection);
    }

    /*
     * 배정 관리 메뉴 보여주기
     * 1. 강사를 수업에 배정하기
     * 2. 강사를 수업에서 해제하기
     * */

    // 1. 강사를 수업에 배정하기
    public boolean assignInstructor(Link link) {
        // 입력받을 때 하나의 link 객체(id - code)로 받을 거니까

        List<Link> linkList = linkDao.getAllList();
        for (Link l : linkList) {
            if (l.getInstructorId() == link.getInstructorId() &&
            l.getClassCode().equals(link.getClassCode())) {
                throw new RuntimeException("이미 해당 수업에 해당 강사가 배정되었습니다.");
            }
        }
        boolean result = linkDao.assignInstructor(link.getInstructorId(), link.getClassCode());
        if (!result) {
            throw new RuntimeException("배정하는 과정에서 오류가 발생했습니다");
        }
        return result;
    }


    // 2. 강사를 수업에서 해제하기
    public boolean deleteInstructor(Link link) {
        List<Link> linkList = linkDao.getAllList();
        boolean exists = false;

        for (Link l : linkList) {
            if (l.getInstructorId() == link.getInstructorId() &&
                    l.getClassCode().equals(link.getClassCode())) {
                exists = true;
                break; // 찾았으면 더 이상 순회할 필요 없음
            }
        }

        if (!exists) {
            throw new RuntimeException("해당 수업에 해당 강사 배정이 존재하지 않습니다.");
        }

        boolean result = linkDao.cancelInstructor(link.getInstructorId(), link.getClassCode());

        if (!result) {
            throw new RuntimeException("해제하는 과정에서 오류가 발생했습니다.");
        }

        return result;

    }




        // 3. 강사가 맡고 있는 수업들 정보 조회
    public List<Classes> getClasses(int instructorId){
        // 강사 ID에 해당하는 수업 목록을 가져온다.
        List<Link> classLinks = linkDao.getClasses(instructorId);
        List<Classes> classesInfo = new ArrayList<>();

        for (Link link : classLinks) {
            Classes classes = classesDao.getClasses(link.getClassCode());
            if (classes != null) {
                classesInfo.add(classes);
            }
        }
        if (classesInfo.isEmpty()) {
            log.error("해당 강사의 수업이 존재하지 않거나 오류가 발생했습니다.");
            return null;
        }
        return classesInfo;
    }


    // 4. 수업에 배정된 강사들 정보 조회
    public List<Instructors> getInstructors(String class_code) {
        // 강사 ID에 해당하는 수업 목록을 가져온다.
        List<Link> classLinks = linkDao.getInstructors(class_code);
        List<Instructors> instructorsInfo = new ArrayList<>();

        for (Link link : classLinks) {
            Instructors instructor = instructorsDao.getInstructors(link.getInstructorId());
            if (instructor != null) {
                instructorsInfo.add(instructor);
            }
        }
        if (instructorsInfo.isEmpty()) {
            log.error("해당 수업의 강사가 존재하지 않거나 오류가 발생했습니다.");
            return null;
        }
        return instructorsInfo;
    }
}
