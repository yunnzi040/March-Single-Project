package com.metabirth.dao;

import com.metabirth.model.Link;
import com.metabirth.util.QueryUtil;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LinkDao {
    private final Connection connection;

    public LinkDao(Connection connection) {
        this.connection = connection;
    }

    /*
     * 배정 관리 메뉴 보여주기
     * 1. 강사를 수업에 배정하기
     * 2. 강사를 수업에서 해제하기
     * 3. 강사가 밑고 있는 수업 조회
     * 4. 수업에 배정된 강사 조회
     * */

    // 강사를 수업에 배정하기(CREATE)
    public boolean assignInstructor(int instructorId, String classCode){
        String query = QueryUtil.getQuery("assignInstructor");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, instructorId);
            ps.setString(2, classCode);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;


        } catch (Exception e) {
            System.out.println("assignInstructor() 사용 중 오류 발생!");
            e.printStackTrace();
        }
        return true;
    }

    //2. 강사를 특정 수업에서 해제하기 (물리적 삭제 DELETE)
    public boolean cancelInstructor(int instructorId, String classCode){
        String query = QueryUtil.getQuery("cancelInstructor");

        try (PreparedStatement ps = connection.prepareStatement(query)){
           ps.setInt(1, instructorId);
           ps.setString(2, classCode);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (Exception e) {
            System.out.println("cancelInstructor() 사용 중 오류 발생!");
            e.printStackTrace();
        }

        return true;
    }

    // 3. 강사 아이디로 강사가 맡고 있는 수업들 조회
    public List<Link> getClasses(int instructor_id){
        List<Link> links = new ArrayList<>();
        String query = QueryUtil.getQuery("getAssignedClasses");

        try (PreparedStatement ps = connection.prepareStatement(query)){
             ps.setInt(1, instructor_id);

             try (ResultSet rs = ps.executeQuery()) {
                 while (rs.next()) {
                     links.add(new Link(
                             rs.getString("class_code"),
                             rs.getInt("instructor_id")
                     ));
                 }
             }
        } catch (Exception e) {
            System.out.println("getClasses() 사용 중 오류 발생!");
            e.printStackTrace();
        }
        return links;
    }

    // 4. 수업 코드로 강사 아이디들 조회
    public List<Link> getInstructors(String class_code){
        List<Link> links = new ArrayList<>();
        String query = QueryUtil.getQuery("getAssignedInstructors");

        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, class_code);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    links.add(new Link(
                            rs.getString("class_code"),
                            rs.getInt("instructor_id")
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("getInstructors() 사용 중 오류 발생!");
            e.printStackTrace();
        }
        return links;
    }

    // 5. 전체 조회
    public List<Link> getAllList(){
        List<Link> links = new ArrayList<>();
        String query = QueryUtil.getQuery("getAllList");

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                links.add(new Link(
                        rs.getString("class_code"),
                        rs.getInt("instructor_id")
                ));
            }
        } catch (Exception e){
            System.out.println("getAllList() 사용 중 오류 발생");
            throw new RuntimeException(e);
        }

        return links;
    }
}
