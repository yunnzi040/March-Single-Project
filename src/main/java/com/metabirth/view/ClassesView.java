package com.metabirth.view;

import com.metabirth.model.Classes;
import com.metabirth.model.Instructors;
import com.metabirth.service.ClassService;
import com.metabirth.service.InstructorService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class ClassesView {
    /*
     * 수업 관리 목록 보여주기 (showInstructorMenu)
     *  1. 학원 전체 수업 조회
     *  2. 학원 특정 수업 정보 조회
     *  3. 수업 삭제
     *  4. 수업 수정 */

    private final ClassService classService;
    private final Scanner scanner;

    public ClassesView(Connection connection){
        this.classService = new ClassService(connection);
        this.scanner = new Scanner(System.in);
    }
    public void showClassMenu(){
        while (true) {
            System.out.println("\n===== 수업 관리 시스템 =====");
            System.out.println("1. 전체 수업 조회");
            System.out.println("2. 수업 등록");
            System.out.println("3. 수업 조회 (수업코드)");
            System.out.println("4. 수업 정보 수정");
            System.out.println("5. 수업 삭제");
            System.out.println("0. 종료");
            System.out.print("선택하세요: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
                case 1 -> getAllClasses();
                case 2 -> registerClasses();
                case 3 -> getClassesBycode();
                case 4 -> updateClasses();
                case 5 -> deleteClasses();
                case 0 -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
            }
        }
    }

    public void getAllClasses(){
        try {
            List<Classes> classes = classService.getAllClasses();

            if (classes == null){
                System.out.println("등록된 강의가 없습니다.");

            } else {
                for (Classes classes1 : classes) {
                    System.out.println(classes1);
                }

            }
        } catch (Exception e) {
            System.out.println("강의 목록을 조회하는 중 오류가 발생했습니다. ");
            e.printStackTrace();
        }
    }

    public void registerClasses(){
        // 수업 번호가 중복이면 안된다. 입력된 수업 번호가 기존 DB에 있는지 확인하기
        System.out.print("등록할 수업 번호를 입력해주세요: ");
        String classCode = scanner.nextLine();
        System.out.print("등록할 수업 제목을 입력해주세요. ex. 수학1  : ");
        String className = scanner.nextLine();
        System.out.print("등록할 수업 시간을 입력해주세요. ex. 월수금 10:00-12:00 : ");
        String classTime = scanner.nextLine();
        System.out.print("등록할 수업의 인원을 입력해주세요. ex. 10 : ");
        int capacity = Integer.parseInt(scanner.nextLine());
        System.out.print("등록할 수업 가격을 입력해주세요. ex. 35000 : ");
        BigDecimal price = new BigDecimal(scanner.nextLine());

        boolean result = classService.addClasses(new Classes(
                0, classCode, className, classTime, capacity, price, (byte) 0, null)
        );

        try {
            if(result){
                System.out.println("수업 등록이 완료되었습니다.");
            } else {
                System.out.println("수업 등록이 실패되었습니다.");
            }
        }
        catch (Exception e) {
            System.out.println("수업 등록 중 오류가 발생했습니다!");
        }
    }

    public void getClassesBycode(){
        try {
            System.out.print("조회할 수업의 수업 코드를 입력해주세요.: ");
            String class_code = scanner.nextLine();
            Classes classes = classService.getClasses(class_code);

            if (classes == null) {
                System.out.println("해당 수업이 존재하지 않습니다.");
            } else {
                System.out.println("\n======수업 정보========");
                System.out.println(classes);
            }
        } catch (Exception e) {
            System.out.println("수업 조회 중 오류가 발생했습니다!");
        }
    }

    public void updateClasses() {
        Classes classes = null;

        while (classes == null) {
            try {
                System.out.print("수정을 원하는 수업의 수업 코드를 입력해주세요: ");
                String class_code = scanner.nextLine();
                classes = classService.getClasses(class_code);

                if (classes == null) {
                    System.out.println("해당 수업이 존재하지 않습니다.");
                } else {
                    System.out.print("수업 제목: " + classes.getClassName() +
                            "\n수업 코드: " + classes.getClassCode() +
                            "\n수업 시간: " + classes.getClassTime() +
                            "\n수업 인원: " + classes.getCapacity() +
                            "\n수업 비용: " + classes.getPrice() +
                            "\n해당 수업 정보를 수정하시겠습니까? 1.수정 / 2.종료 : ");

                    switch (scanner.nextInt()) {
                        case 1:
                            scanner.nextLine();
                            System.out.print("수정할 수업 코드를 입력해주세요: ");
                            String class_code_up = scanner.nextLine();
                            System.out.print("수정할 수업 제목을 입력해주세요. ex. 수학1 : ");
                            String class_name = scanner.nextLine();
                            System.out.print("수정할 수업 시간을 입력해주세요. ex. 월수금 10:00-12:00 : ");
                            String class_time = scanner.nextLine();
                            System.out.print("수정할 수업의 인원을 입력해주세요. ex. 10 : ");
                            int capacity = Integer.parseInt(scanner.nextLine());
                            System.out.print("수정할 수업 비용을 입력해주세요. ex. 35000 : ");
                            BigDecimal price = new BigDecimal(scanner.nextLine());

                            Classes classes1 = new Classes(0, class_code_up, class_name, class_time, capacity, price, (byte) 0, null);

                            boolean result = classService.updateClasses(classes1);

                            if (result) {
                                System.out.println("수업 정보 수정이 완료되었습니다.");
                            } else {
                                System.out.println("수업 정보 수정에 실패하였습니다.");
                            }
                            break;

                        case 2:
                            System.out.println("수업 정보 수정을 종료합니다.");
                            break;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("수업 정보 수정 중 오류가 발생되었습니다.");
            }
        }
    }

    public void deleteClasses() {
        Classes classes = null;

        while (classes == null) {
            System.out.print("삭제할 수업의 수업 코드를 입력해주세요.: ");
            String class_code = scanner.nextLine();

            classes = classService.getClasses(class_code);

           try {
               if (classes == null) {
                   System.out.println("해당 수업이 존재하지 않습니다.");
               } else {
                   System.out.println("수업 코드: " + classes.getClassCode() +
                           "\n수업 제목: " + classes.getClassName() +
                           "\n수업 시간: " + classes.getClassTime() +
                           "\n수업 인원: " + classes.getCapacity() +
                           "\n수업 가격: " + classes.getPrice());
                   System.out.println("해당 수업을 삭제하시겠습니까? 1. 삭제 2. 종료");

                   switch (scanner.nextInt()) {
                       case 1:
                           scanner.nextLine();
                           boolean result = classService.deleteClasses(class_code);

                           if (result) {
                               System.out.println("수업 삭제가 성공되었습니다.");
                           } else {
                               System.out.println("수업 삭제가 실패되었습니다. ");
                           }
                           break;
                       case 2:
                           System.out.println("수업 삭제를 종료합니다.");
                           break;
                   }
               }
           } catch (Exception e) {
               throw new RuntimeException("수업 삭제 중 오류가 발생되었습니다.");
           }
        }
    }
}
