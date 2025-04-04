package com.metabirth;

import com.metabirth.config.JDBCConnection;
import com.metabirth.view.ClassesView;
import com.metabirth.view.InstructorView;
import com.metabirth.view.LinkView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== 메타벌스 강의 및 강사 배정 관리 시스템 =====");
            System.out.println("1. 강사(Instructor)관리");
            System.out.println("2. 강의(Class) 관리");
            System.out.println("3. 배정(Assign) 관리");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> startInstructorManagement(connection);
                case 2 -> startLessonManagement(connection);
                case 3 -> startAssignManagement(connection);
                case 0 -> {
                    connection.close();
                    System.out.println("🚀 프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("❌ 잘못된 입력입니다. 다시 선택하세요.");
            }
        }
    }

    static void startInstructorManagement(Connection connection) throws SQLException {
        InstructorView instructorView = new InstructorView(connection);
        instructorView.showInstructorMenu();
    }

    static void startLessonManagement(Connection connection) throws SQLException {
        ClassesView classesView = new ClassesView(connection);
        classesView.showClassMenu();
    }
    static void startAssignManagement(Connection connection) throws SQLException {
        LinkView linkView = new LinkView(connection);
        linkView.showLinkMenu();
    }
}
