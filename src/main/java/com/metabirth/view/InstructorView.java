package com.metabirth.view;

import com.metabirth.model.Instructors;
import com.metabirth.service.InstructorService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class InstructorView {

    private final InstructorService instructorService;
    private final Scanner scanner;

    public InstructorView(Connection connection){
        this.instructorService = new InstructorService(connection);
        this.scanner = new Scanner(System.in);
    }

    /*
    * 강사 관리 목록 보여주기 (showInstructorMenu)
    *  1. 학원 전체 강사 조회
    *  2. 학원 특정 강사 정보 조회
    *  3. 강사 퇴사 처리
    *  4. 강사 정보 수정 */

    public void showInstructorMenu(){
        while (true) {
            System.out.println("\n===== 강사 관리 시스템 =====");
            System.out.println("1. 전체 강사 조회");
            System.out.println("2. 강사 등록");
            System.out.println("3. 강사 조회 (Email)");
            System.out.println("4. 강사 정보 수정");
            System.out.println("5. 강사 삭제");
            System.out.println("0. 종료");
            System.out.print("선택하세요: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
                case 1 -> getAllInstructors();
                case 2 -> registerInstructor();
                case 3 -> getInstructorByEmail();
                case 4 -> updateInstructor();
                case 5 -> deleteInstructor();
                case 0 -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
            }
        }
    }


    private void getAllInstructors() {
        //userService의 'getAllUsers()' 메서드를 호출하면 sql 결과를 담을 리스트가 있어야 하는데
        // User 타입의 users 이름인 리스트를 만들어 여기에 넣어준다.
        List<Instructors> instructors = instructorService.getAllInstructors();
        // try-catch 문을 사용해서 만약 리스트의 값이 비어있다면
        try {
            if (instructors.isEmpty()) {
                // 등록된 사용자가 없다고 알리기
                System.out.println("등록된 사용자가 없습니다.");
            } else {
                // 만약 있다면 전체 사용자의 목록을 보여주기
                for (Instructors instructor : instructors) {
                    System.out.println(instructor);
                }
            }
            // 그리고 중간에 Exeption e 오류가 생긴다면
        } catch (Exception e) {
            // "사용자 목록을 조회하는 중 오류가 발생했습니다. 출력
            System.out.println("사용자 목록을 조회하는 중 오류가 발생했습니다.");
        }
    }

    public void registerInstructor() {
        System.out.print("등록할 강사 이름을 입력해주세요. : ");
        String instructor_name = scanner.nextLine();
        System.out.print("등록할 강사 번호를 입력해주세요. (ex. 010-1234-1234) : ");
        String phone = scanner.nextLine();
        System.out.print("등록할 강사 이메일을 입력해주세요. (ex. abcd@example.com) : ");
        String email = scanner.nextLine();
        System.out.print("등록할 강사 비밀번호를 입력해주세요. : ");
        String password = scanner.nextLine();

        Instructors instructors = new Instructors(0, instructor_name, phone, (byte) 0, null, email, password);

        try {
            boolean result = instructorService.addInstructors(instructors);
            if (result){ // 등록 성공했을 뗴
                System.out.println("강사 등록을 성공했습니다.");
            } else {
                System.out.println("강사 등록에 실패했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("강사 등록 중 오류가 발생했습니다. ");
        }




    }

    private void getInstructorByEmail() {
        System.out.print("조회할 강사 이메일을 입력해주세요: ");
        String email = scanner.nextLine();
        Instructors instructors = instructorService.getInstructors(email);

        try {
            if (instructors == null) {
                System.out.println("해당 강사가 존재하지 않습니다.");
            } else {
                System.out.println("\n====해당 강사 정보====");
                System.out.println(instructors);
            }
        } catch (Exception e) {
            System.out.println("강사 조회 중 오류가 발생했습니다.");
        }


    }

    // 강사 정보 수정
    public void updateInstructor() {
        Instructors instructors = null;

        while (instructors == null) {
            try {
                System.out.print("수정을 원하는 강사의 이메일을 입력해주세요: ");
                String email = scanner.nextLine();
                instructors = instructorService.getInstructors(email);

                if (instructors == null) {
                    System.out.println("해당 강사가 존재하지 않습니다. 다시 입력해주세요.");
                    continue;
                }

                // 강사가 존재하면 정보 출력
                System.out.println("강사 이름: " + instructors.getInstructor_name() +
                        "\n강사 핸드폰 번호: " + instructors.getPhone() +
                        "\n강사 이메일: " + instructors.getEmail() +
                        "\n강사 비밀번호: " + instructors.getPassword() +
                        "\n해당 강사 정보를 수정하시겠습니까? 1.수정 / 2.종료");

                switch (scanner.nextInt()) {
                    case 1:
                        scanner.nextLine();
                        System.out.print("수정할 강사 이름을 입력해주세요. : ");
                        String instructor_name = scanner.nextLine();
                        System.out.print("수정할 강사 번호를 입력해주세요. (ex. 010-1234-1234) : ");
                        String phone = scanner.nextLine();
                        System.out.print("수정할 강사 비밀번호를 입력해주세요. : ");
                        String password = scanner.nextLine();

                        Instructors instructors1 = new Instructors(0, instructor_name,
                                phone, (byte) 0, null, email, password);

                        boolean result = instructorService.updateInstructors(instructors1);

                        if (result){
                            System.out.println("강사 정보 수정이 완료되었습니다.");
                        } else {
                            System.out.println("강사 정보 수정에 실패하였습니다.");
                        }
                        break;
                    case 2:
                        System.out.println("강사 정보 수정을 종료합니다.");
                        break;
                }

            } catch (Exception e) {
                //throw new RuntimeException("강사 정보 수정 중 오류가 발생하였습니다.");
                e.printStackTrace();
            }
        }
    }

    // 강사 정보 삭제 (status 정보 수정)
    public void deleteInstructor() {
        Instructors instructors = null;

        while (instructors == null) {
            System.out.print("삭제를 원하는 강사의 이메일을 입력해주세요: ");
            String email = scanner.nextLine();
            instructors = instructorService.getInstructors(email);

            try {
                if (instructors == null) {
                    System.out.print("해당 강사가 존재하지 않습니다. 다시 입력해주세요.");
                } else {
                    System.out.println("강사 이름: " + instructors.getInstructor_name() +
                            "\n강사 핸드폰 번호: " + instructors.getPhone() +
                            "\n강사 이메일: " + instructors.getEmail() +
                            "\n강사 비밀번호: " + instructors.getPassword() +
                            "\n해당 강사 정보를 삭제하시겠습니까? 1.삭제 / 2.종료");

                    switch (scanner.nextInt()) {
                        case 1:
                            boolean result = instructorService.deleteInstructors(email);
                            if (result){
                                System.out.println("강사 정보 삭제가 성공되었습니다.");
                            } else {
                                System.out.println("강사 정보 삭제가 실패되었습니다.");
                            }
                            break;
                        case 2:
                            System.out.println("강사 정보 삭제를 종료합니다.");
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("강사 정보 삭제 중 오류가 발생하였습니다.");
            }

        }
    }

}
