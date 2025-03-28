package com.metabirth.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {

    // 데이터 타입의 커넥션 풀을 관리하는 라이브러리
    // 이걸 왜 관리해야하지?
    private static final HikariDataSource dataSource;

    // 메모리에 올라가기 전에 static 블록안에서 초기화한다.??/..
    static {
        try {
            /*
             * properites
             * 키 - 값 쌍으로 저장하는 방식이다.
             * 주로 설정 정부나 구성 데이터를 관리하는데 유용하게 사용된다.
             * */

            Properties props = new Properties();
            /*
             * properties.load 외부 파일을 읽어오는 역할을 수행한다.
             * JDBCConnection.class.GetClassLoader() : 클래스를 메모리에 로드하는 역할을 수행하며 이를 통해 설정 파일에 접근이 가능하다.
             * getResourceAsStream ("config") : 매개변수로 전달된 파일을 스트림으로 가져오는 역할을 수행한다.
             * */
            props.load(JDBCConnection.class.getClassLoader().getResourceAsStream("config.properties"));
            HikariConfig config = new HikariConfig();

            /* DB 접속을 위한 설정 정보 */
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));

            // 최대 커넥션
            config.setMaximumPoolSize(10);

            // 최소 커넥션
            config.setMinimumIdle(5);

            // 유휴 상태면 커넥션 닫기 즉, 30초 동안 아무런 요청이 없으면 커넥션을 닫는다.
            // 유휴 상태 = 아무런 요청이 없는 상태
            config.setIdleTimeout(30000);

            config.setMaxLifetime(1800000); // 30분 후 커넥션을 새로 생성한다.

            config.setConnectionTimeout(2000); // 최대 2초 대기 후 타임 아웃

            dataSource = new HikariDataSource(config);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 커넥션 풀에서 연결되어 있는 객체들을 꺼내오는 메서드
    public static Connection getConnection() throws SQLException {
        /*
         * DB에는 세션이라는 것이 존재한다.
         * 이러한 세션은 DB에 연결한 시점으로 생성되며
         * 이를 기점으로 트랜잭션 임시 데이터, 캐싱 등의 데이터를 관리하게 된다.
         *
         * 여기서 지그 ㅁ 우리가 사용하는 hikair의 경우 DB의 커넥션 객체를 몇개 생성하고
         * 다른 사용자에게 빌려주고 반환하고 다시 빌려주는 형식으로 동작된다.
         * 이러한 과정에서 세션이 중복되는 문제가 발생할 수 있다고 생각될 수 있으나.
         * 다시 빌려주는 과정에서 JDBC 내부에서 세션을 새롭게 생성하기 때문에 신경쓰지 않아도 된다.
         * */

        //connection pool 에서 connection 객체를 꺼내옴
        return dataSource.getConnection();
    }

    // hikaricp 전체 커넥션 풀을 종료하는 메서드
    // application -> 전체 종료 -> connection pool 더 이상 사용 불가.
    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public static void printConnectionPoolStatus(){
        HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();
        System.out.println("[HikariCP 커넥션 풀 상태 ]");
        System.out.println("총 커넥션 수 (total Connections : " + poolMXBean.getTotalConnections());
        System.out.println("활성 커넥션 수 (Active Connections : " + poolMXBean.getActiveConnections());
        System.out.println("유휴 커넥션 수 (IDLE Connections : " + poolMXBean.getIdleConnections());
        System.out.println("대기 중인 커넥션 요청 수 (Pending THreads) : " + poolMXBean.getThreadsAwaitingConnection());
    }
}
