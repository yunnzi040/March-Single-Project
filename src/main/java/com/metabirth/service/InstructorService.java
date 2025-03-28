package com.metabirth.service;

import com.metabirth.dao.InstructorsDao;
import com.metabirth.model.Instructors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class InstructorService {
    private static final Logger log = LoggerFactory.getLogger(Instructors.class);
    private final InstructorsDao instructorsDao;
    private final Connection connection;


    public InstructorService(Connection connection) {
        this.connection = connection;
        this.instructorsDao = new InstructorsDao(connection);
    }


    // 모든 강사 조회 (SELECT)
    public List<Instructors> getAllInstructors() {
        List<Instructors> instructors = instructorsDao.getAllInstructor();

        if (instructors.isEmpty()) {
            log.error("강사가 존재하지 않거나 오류가 발생했습니다.");

        }
        return instructors;
    }

    // 특정 강사 조회 (SELECT)
    public Instructors getInstructors(String Email){
        Instructors instructor = instructorsDao.getInstructor(Email);

        if (instructor == null) {
            log.error("해당 강사가 존재하지 않거나 오류가 발생했습니다.");
            return null;
        }
        return instructor;
    }

    // 강사 등록 (CREATE)
    public boolean addInstructors(Instructors instructor) throws SQLException {
        // 이메일 중복이 안되도록 검사 후 등록 받기
        // 전체 강사 목록을 조회해서 Instructor 타입으로 이름이 existing인 리스트를 생성한다
        List<Instructors> existing = instructorsDao.getAllInstructor();
        // 해당 리스트에는 전체 강사 목록이 있고 목록 전체를 확인해야한다.
        for (Instructors I : existing) {
            // 입력 받은 이메일과 기존에 있던 이메일이 같은지 확인한 후
            if (I.getEmail().equals(instructor.getEmail())) {
                // IllegalArgumentException 예외를 던진다. ("이미 존재하는 이메일 입니다.")
                throw new IllegalArgumentException ("이미 존재하는 이메일 입니다.");
            }
        }
        // 이메일 검사를 통과한다면 instructor 객체를 등록할 수 있도록 한다.
        return instructorsDao.addInstructor(instructor);
    }

    // 강사 정보 수정 (UPDATE)
    public boolean updateInstructors(Instructors instructor) throws SQLException {
        // 수정하고 싶은 강사가 존재하는지 확인 (이메일로?)
        Instructors existing = getInstructors(instructor.getEmail());
        // 만약 해당 강사가 존재하지 않는다면 IllegalArgumentException 예외를 던진다. ("수정할 강사를 찾을 수 없습니다.")
        if (existing == null) {
            throw new IllegalArgumentException("수정할 강사를 찾을 수 없습니다.");
        }
        // 확인이 끝난 후에는 boolean 타입으로 user 객체로 수정할 수 있게 한다.
        boolean result = instructorsDao.updateInstuctor(instructor);

        // 만약 업데이트 결과가 false이라면 SQLException 예외를 던진다. ("수정하는 과정에서 오류가 발생되었습니다.")
        if (!result) {
            throw new SQLException("수정하는 과정에서 오류가 발생되었습니다.");
        }
        // 업데이트 수행 완료
        return result;
    }

    // 강사 삭제 (DELETE)
    public boolean deleteInstructors(String Email) throws SQLException {
        // 삭제하고 싶은 강사가 존재하는지 확인 (이메일로?)
        Instructors existing = getInstructors(Email);
        // 만약 해당 강사가 존재하지 않는다면 IllegalArgumentException 예외를 던진다. ("삭제할 강사를 찾을 수 없습니다."
        if (existing == null) {
            throw new IllegalArgumentException("삭제할 강사를 찾을 수 없습니다.");
        }
        // 확인이 끝난 후에는 boolean 타입으로 deleteInsructor(userId)를 실행시켜.
        boolean result = instructorsDao.deleteInstructor(Email);
        // 만약 삭제 결과가 false이라면 SQLException 예외를 던진다. ("삭제하는 과정에서 오류가 발생되었습니다.")
        if (result) {
            throw new SQLException("삭제하는 과정에서 오류가 발생되었습니다.");
        }
        // 삭제 수행 완료
        return result;
    }
}
