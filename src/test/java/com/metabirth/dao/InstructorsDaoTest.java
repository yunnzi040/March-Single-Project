package com.metabirth.dao;

import com.metabirth.config.JDBCConnection;
import com.metabirth.model.Instructors;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InstructorsDaoTest {
    private InstructorsDao instructorsDao;
    private Connection connection;
    private static final String TEST_INSTRUCTOR = "정윤지";
    private static final String TEST_EMAIL = "test@metabirth.com";


    // 데이터베이스 연결 확인
    @BeforeEach
    void setUp() {
        try {
            connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            instructorsDao = new InstructorsDao(connection);

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO instructors (instructor_name, phone, status, created_at, email, password)\n" +
                            "VALUES (?, 'Test', 0, now(), ?, 'Test')"
            );
            ps.setString(1, TEST_INSTRUCTOR);
            ps.setString(2, TEST_EMAIL);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결 실패 : " + e.getMessage());
        }
    }

    @Test
    @DisplayName("전체 강사 정보 조회 테스트")
    void getAllInstructors() {
        List<Instructors> instructors = instructorsDao.getAllInstructor();
        Assertions.assertFalse(instructors.isEmpty(), "강사 목록이 비어있으면 안됨.");
    }

    @Test
    @DisplayName("특정 강사 정보 조회")
    void getInstructor(){
        Instructors instructor = instructorsDao.getInstructor(TEST_EMAIL);

        Assertions.assertNotNull(instructor, "강사가 존재해야함.");
        Assertions.assertEquals(TEST_INSTRUCTOR, instructor.getInstructor_name(), "조회한 강사가 일치해야 함.");
    }
}