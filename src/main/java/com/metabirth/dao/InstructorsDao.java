package com.metabirth.dao;

import com.metabirth.model.Instructors;
import com.metabirth.model.Link;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorsDao {

    private final Connection connection;

    public InstructorsDao(Connection connection) {
        this.connection = connection;
    }

    // 전체 강사 정보 조회
    public List<Instructors> getAllInstructor() {
        List<Instructors> instructors = new ArrayList<>();

        String sql = "SELECT * FROM instructors ";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                instructors.add(new Instructors(
                        rs.getInt("instructor_id"),
                        rs.getString("instructor_name"),
                        rs.getString("phone"),
                        rs.getByte("status"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getString("email"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            System.out.println("getAllInstructors() 사용 중 오류 발생!");
            e.printStackTrace();
        }
        return instructors;
    }

    // Email을 사용한 특정 강사 정보 조회
    public Instructors getInstructor(String Email) {
        String sql = "SELECT * FROM instructors WHERE email = ? AND status = 0";
        Instructors instructor = null;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, Email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    instructor = new Instructors(
                            rs.getInt("instructor_id"),
                            rs.getString("instructor_name"),
                            rs.getString("phone"),
                            rs.getByte("status"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getString("email"),
                            rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            System.out.println("getInstructor() 사용 중 오류 발생!");
            e.printStackTrace();
        }
        return instructor;
    }

    // instructor_id를 사용한 특정 강사 정보 조회 (LinkService에서 사용)
    public Instructors getInstructors(int instructorId) {
        String sql = "SELECT * FROM instructors WHERE instructor_id = ?";
        Instructors instructor = null;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, instructorId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    instructor = new Instructors(
                            rs.getInt("instructor_id"),
                            rs.getString("instructor_name"),
                            rs.getString("phone"),
                            rs.getByte("status"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getString("email"),
                            rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            System.out.println("getInstructors() 사용 중 오류 발생!");
            e.printStackTrace();
        }
        return instructor;
    }



    // 강사 추가
    public boolean addInstructor(Instructors instructors) {
        String sql = "INSERT INTO instructors (instructor_name, phone, status, created_at, email, password)\n" +
                "VALUES (?, ?, 0, now(), ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, instructors.getInstructor_name());
            ps.setString(2, instructors.getPhone());
            ps.setString(3, instructors.getEmail());
            ps.setString(4, instructors.getPassword());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("addInstructor() 사용 중 오류 발생");
            e.printStackTrace();
        }
        return false;
    }

    // 강사 수정
    public boolean updateInstuctor(Instructors instructor) {
        String sql = "UPDATE instructors SET instructor_name = ?, phone = ?, password = ?\n" +
                "WHERE email = ? AND status = 0";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, instructor.getInstructor_name());
            ps.setString(2, instructor.getPhone());
            ps.setString(3, instructor.getPassword());
            ps.setString(4, instructor.getEmail());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("updateUser() 사용 중 오류 발생");
            e.printStackTrace();
        }
        return true;
    }

    // 강사 삭제 (status = 1 상태로 변경)
    public boolean deleteInstructor(String email) {
        String sql = "UPDATE instructors\n" +
                "SET status = 1 WHERE email = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("deleteInstructor() 사용 중 오류 발생");
            e.printStackTrace();
        }
        return true;
    }
}
