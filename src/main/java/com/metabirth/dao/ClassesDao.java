package com.metabirth.dao;

import com.metabirth.model.Classes;
import com.metabirth.model.Instructors;
import com.metabirth.model.Link;
import com.metabirth.util.QueryUtil;
import com.mysql.cj.xdevapi.FindStatementImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassesDao {
    private final Connection connection;

    public ClassesDao(Connection connection) {
        this.connection = connection;
    }

    // 전체 수업 조회
    public List<Classes> getAllClasses() {
        List<Classes> classes = new ArrayList<>();

        String query = QueryUtil.getQuery("getAllClasses"); // XML에서 쿼리 로드

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                classes.add(new Classes(
                        rs.getInt("class_id"),
                        rs.getString("class_code"),
                        rs.getString("class_name"),
                        rs.getString("class_time"),
                        rs.getInt("capacity"),
                        rs.getBigDecimal("price"),
                        rs.getByte("status"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (Exception e) {
            System.out.println("getAllClasses() 사용 중 오류 발생!");
            throw new RuntimeException(e);
        }

        return classes;
    }


    // 특정 수업 조회
    public Classes getClasses(String classCode) {
        Classes classes = null;
        String query = QueryUtil.getQuery("getClasses"); // XML에서 쿼리 로드

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, classCode);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    classes = new Classes(
                            rs.getInt("class_id"),
                            rs.getString("class_code"),
                            rs.getString("class_name"),
                            rs.getString("class_time"),
                            rs.getInt("capacity"),
                            rs.getBigDecimal("price"),
                            rs.getByte("status"),
                            rs.getTimestamp("created_at").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            System.out.println("getClasses() 사용 중 오류 발생!");
            e.printStackTrace();
        }
        return classes;
    }

    // 특정 수업'들' 정보 조회
    public List<Classes> getClassesByCode(String classCode){
        List<Classes> classes = new ArrayList<>();
        String query = QueryUtil.getQuery("getClasses");

        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, classCode);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    classes.add(new Classes(
                            rs.getInt("class_id"),
                            rs.getString("class_code"),
                            rs.getString("class_name"),
                            rs.getString("class_time"),
                            rs.getInt("capacity"),
                            rs.getBigDecimal("price"),
                            rs.getByte("status"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("getClassesById() 사용 중 오류 발생!");
            e.printStackTrace();
        }
        return classes;
    }


    // 수업 생성
    public boolean addClasses(Classes classes) {
        String query = QueryUtil.getQuery("addClasses");

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, classes.getClassCode());
            ps.setString(2, classes.getClassName());
            ps.setString(3, classes.getClassTime());
            ps.setInt(4, classes.getCapacity());
            ps.setBigDecimal(5, classes.getPrice());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (Exception e) {
            System.out.println("addClasses() 사용 중 오류 발생!");
            e.printStackTrace();}

        return false;
    }

    // 수업 수정
    public boolean updateClasses(Classes classes) {
        String query = QueryUtil.getQuery("updateClasses");
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, classes.getClassName());
            ps.setString(2, classes.getClassTime());
            ps.setInt(3, classes.getCapacity());
            ps.setBigDecimal(4, classes.getPrice());
            ps.setString(5, classes.getClassCode());
            // SQL 실행 후 영향을 받은 행의 개수를 반환
            int affectedRows = ps.executeUpdate();
            // 행의 개수를 반환했을 때 0보다 크면 true를 반환
            return affectedRows > 0;

        } catch (Exception e) {
            System.out.println("updateClasses() 사용 중 오류 발생!");
            e.printStackTrace();
        }

        return false;
    }

    // 수업 삭제
    public boolean deleteClasses(String class_code) {
        String query = QueryUtil.getQuery("deleteClasses");

        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, class_code);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (Exception e) {
            System.out.println("deleteClasses() 사용 중 오류 발생!");
            e.printStackTrace();
        }

        return false;
    }

}
