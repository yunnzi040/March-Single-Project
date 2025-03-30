package com.metabirth.view;

import com.metabirth.model.Classes;
import com.metabirth.model.Instructors;
import com.metabirth.model.Link;
import com.metabirth.service.ClassService;
import com.metabirth.service.InstructorService;
import com.metabirth.service.LinkService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class LinkView {
    private final LinkService linkService;
    private final InstructorService instructorService;
    private final ClassService classService;
    private final Scanner scanner;

    public LinkView(Connection connection) {
        this.linkService = new LinkService(connection);
        this.scanner = new Scanner(System.in);
        this.classService = new ClassService(connection);
        this.instructorService = new InstructorService(connection);
    }

    /*
     * 배정 관리 메뉴 보여주기
     * 1. 강사를 수업에 배정하기
     * 2. 강사를 특정 수업에서 해제하기
     * 3. 특정 강사가 밑고 있는 수업 조회
     * 4. 특정 수업에 배정된 강사 조회
     * */

    public void showLinkMenu() {
        while (true) {
            System.out.println("\n===== 배정 관리 시스템 =====");
            System.out.println("1. 강사 배정");
            System.out.println("2. 강사 배정 해제");
            System.out.println("3. 강사의 배정 조회");
            System.out.println("4. 수업의 배정 조회");
            System.out.println("0. 종료");
            System.out.print("선택하세요: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
                case 1 -> assignInstructor();
                case 2 -> cancelInstructor();
                case 3 -> getClasses();
                case 4 -> getInstructors();
                case 0 -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
            }
        }
    }

    // 강사 배정
    private void assignInstructor() {
        System.out.print("강의 코드를 입력해주세요. : ");
        String class_code = scanner.nextLine();
        System.out.print("강사 아이디를 입력해주세요.: ");
        int instructor_code = scanner.nextInt();

        boolean result = linkService.assignInstructor(new Link(
                class_code, instructor_code
        ));

        try {
            if (result) {
                System.out.println("해당 수업에 해당 강사가 배정 완료되었습니다.");
            } else {
                System.out.println("해당 수업에 해당 강사가 배정 실패되었습니다.");
            }
        } catch (Exception e) {
            System.out.println("배정 등록 중 오류가 발생되었습니다.");
            e.printStackTrace();
        }


    }

    // 강사 배정 해제
    private void cancelInstructor() {
        System.out.print("배정 해제하고 싶은 강의 코드를 입력해주세요. : ");
        String class_code = scanner.nextLine();
        System.out.print("배정 해제하고 싶은 강사 아이디를 입력해주세요. : ");
        int instructor_code = scanner.nextInt();

        boolean result = linkService.deleteInstructor(new Link(
                class_code, instructor_code
        ));

        try {
            if (result) {
                System.out.println("해당 수업과 강사의 배정이 해제되었습니다.");
            } else {
                System.out.println("해당 수업과 강사의 배정 해제가 실패되었습니다.");
            }
        } catch (Exception e) {
            System.out.println("배정 해제 중 오류가 발생되었습니다.");
            e.printStackTrace();
        }

    }

    //강사의 배정 조회
    private void getClasses() {
        // 지금은 아이디지만.. 이메일을 사용하고 싶어... ㅠㅠ
        System.out.print("조회하고 싶은 강사 아이디를 입력해주세요. : ");
        int instructorId = scanner.nextInt();

        try {
            List<Classes> classesInfo = linkService.getClasses(instructorId);

            if (classesInfo == null){
                System.out.println("해당 강사의 강의가 없습니다.");
            } else {
                for (Classes classes : classesInfo) {
                    System.out.println(classes);
                }
            }
        } catch (Exception e) {
            System.out.println("해당 강사의 강의 목록을 조회하는 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    // 수업의 배정 조회
    private void getInstructors() {
        System.out.print("조회하고 싶은 수업의 수업 코드를 입력해주세요. : ");
        String class_code = scanner.nextLine();

        try{
            List<Instructors> instructorsInfo = linkService.getInstructors(class_code);
            if (instructorsInfo == null){
                System.out.println("해당 강의의 담당 강사가 없습니다.");
            } else {
                for (Instructors instructors : instructorsInfo) {
                    System.out.println(instructors);
                }
            }
        } catch (Exception e) {
            System.out.println("해당 강의의 강사 목록을 조회하는 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
}
