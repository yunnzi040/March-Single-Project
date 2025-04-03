# Java 기반 CRUD 프로젝트

## 1. 프로젝트 개요

### 프로젝트 이름
- **메타버스 아카데미 관리 프로그램**

### 기능 개요
- 강사, 수업, 강사-수업 배정을 관리하는 CRUD 기반 시스템
- 강사 및 수업 정보를 등록, 수정, 삭제, 조회하는 기능 제공
- 강사와 수업 간 배정 및 해제 기능을 통해 유연한 관리 지원

---

## 2. 기술 스택

- **언어:** Java
- **데이터베이스:** MySQL
- **프레임워크:** JDBC
- **빌드 도구:** Gradle


---

## 3. ERD 및 테이블 설명

### 데이터베이스 설계

#### 강사와 수업의 다대다(M:N) 관계
강사(Instructors)와 수업(Classes)은 **다대다(M:N) 관계**를 가지므로, 이를 해결하기 위해 **"강사-수업 배정 테이블 (Instructor_Class)"**을 설계하였습니다.

- 하나의 강사는 여러 개의 수업을 맡을 수 있음
- 하나의 수업도 여러 명의 강사가 담당할 수 있음
- 이를 효과적으로 관리하기 위해 **중간 테이블(`instructor_class`)을 활용**하여 **강사와 수업 간의 연결을 관리**

### ERD (Entity-Relationship Diagram)

전체 ERD
![Image](https://github.com/user-attachments/assets/f7cc3a4e-98c6-4e35-8521-f50c87ecfcd7)

담당 ERD
![Image](https://github.com/user-attachments/assets/9771e17d-a8d3-4f50-8f21-e611132913ea)

#### 테이블 설명

**강사 테이블 (`instructors`)**
- 강사 정보를 저장하는 테이블
- `instructor_id` (PK), `instructor_name`, `email`, `phone` 등의 컬럼 포함

**수업 테이블 (`classes`)**
- 수업 정보를 저장하는 테이블
- `class_id` (PK), `class_code` (Unique), `class_name`, `capacity`, `price` 등의 컬럼 포함

**강사-수업 배정 테이블 (`instructor_class`)**
- **강사와 수업 간의 다대다 관계를 해결하기 위한 링크 테이블**
- **복합 기본키**(`instructor_id`, `class_code`)를 사용하여 한 강사가 여러 수업을 맡을 수 있도록 설계
- 두 개의 **외래 키(FK)** (`instructor_id`, `class_code`)를 통해 `instructors`, `classes` 테이블을 참조
- **`ON DELETE CASCADE` 설정으로, 강사 또는 수업이 삭제될 경우 자동으로 관련 배정 정보도 삭제됨**

---

## 4. 주요 기능 및 시퀀스 다이어그램

### 강사 관리
- 강사 등록
  <img width="2272" alt="Image" src="https://github.com/user-attachments/assets/3b698145-21c3-49cb-87dd-c9dbd9175777" />
  
- 강사 삭제
  <img width="2272" alt="Image" src="https://github.com/user-attachments/assets/b1175e54-7c8d-4994-8aa6-b9491f149f4f" />
  
- 전체 강사 조회
  <img width="2272" alt="Image" src="https://github.com/user-attachments/assets/06739a33-f393-42f5-b143-bc91106a3c45" />
- 특정 강사 조회
  <img width="2272" alt="Image" src="https://github.com/user-attachments/assets/0a475db0-1c45-4c43-a75f-4efeec1050c6" />
- 특정 강사 수정
  <img width="2272" alt="Image" src="https://github.com/user-attachments/assets/71baa480-c4ca-44aa-8f77-d24aa738ce0c" />


### 수업 관리
- 수업 등록
  <img width="2272" alt="Image" src="https://github.com/user-attachments/assets/7578bf88-5df9-4aff-b5c8-780f7ed29e6f" />
- 수업 삭제
  <img width="2272" alt="Image" src="https://github.com/user-attachments/assets/3d58909f-e930-462f-8e5a-013f9f9b3fa1" />
- 전체 수업 조회
  <img width="2272" alt="Image" src="https://github.com/user-attachments/assets/95a7e076-7495-45ec-bef7-e403e4c63d11" />
- 특정 수업 조회
  <img width="2272" alt="Image" src="https://github.com/user-attachments/assets/536cf6c4-fc92-405c-958d-1e5886dfc680" />
- 특정 수업 수정
  <img width="2272" alt="Image" src="https://github.com/user-attachments/assets/a5777b61-5fb7-4887-bf95-88ee28c54fca" />

### 배정 관리
- 강사를 특정 수업에 배정
  <img width="2272" alt="Image" src="https://github.com/user-attachments/assets/90253641-e5e7-4768-b31e-23832898fce1" />
- 강사를 특정 수업에서 해제
  <img width="2272" alt="Image" src="https://github.com/user-attachments/assets/fde2ca9e-250d-4ab6-b4fd-853bc757b893" />
- 특정 강사가 맡고 있는 수업 조회
  <img width="2272" alt="Image" src="https://github.com/user-attachments/assets/558c18aa-2acd-4bb0-bb53-c2de54c9bd52" />
- 특정 수업에 배정된 강사 조회
  <img width="2272" alt="Image" src="https://github.com/user-attachments/assets/4bd2b32a-7bcf-4040-8a3c-444dc85a2218" />
