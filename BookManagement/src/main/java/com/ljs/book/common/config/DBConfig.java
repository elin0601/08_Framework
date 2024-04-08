package com.ljs.book.common.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/* @Configuration
 * - 설정용 클래스임을 명시 
 * 	 + 객체로 생성해서 내부 코드를 서버 실행 시 모두 수행
 * 
 * @PropertySource("경로")
 * - 지정된 경로의 properties 파일 내용을 읽어와 사용
 * - 사용할 properties 파일이 다수일 경우
 *   해당 어노테이션을 연속해서 작성하면됨
 *   
 * @Bean
 * - 개발자가 수동으로 생성한 객체의 관리를
 * 	 스프링에게 넘기는 어노테이션
 * 	 (Bean 등록)
 * 
 * @ConfigurationProperties(prefix="spring.datasource.hikari")
 *  - @PropertySource 로 읽어온 properties 파일의 내용 중
 *    접두사(앞부분, prefix)가 일치하는 값만 읽어옴
 *    
 * - 읽어온 내용을 생성하려는 Bean에 자동으로 세팅
 * 
 * 
 * DataSource : Connection 생성 + Connection Pool 지원 하는 객체를
 * 				참조하기 위한 Java 인터페이스
 * 				(DriverManager 대안, Java JNDI 기술 이용)
 * 
 * @Autowired
 *  - 등록된 Bean 중에서 
 *    타입이 일치하거나, 상속관계에 있는 Bean을 
 *    지정된 필드에 주입
 *   	== 의존성 주입 (DI, Dependency Injection - IOC 관련 기술)
 * */

@Configuration
@PropertySource("classpath:/config.properties")
public class DBConfig {
	
	// 필드
	
	@Autowired // (DI, 의존성 주입)
	private ApplicationContext applicationContext; // 현재 프로젝트
	
	// -> ApplicationContext 타입릐 필드를 선언한 것으로 필드가 ApplicationContext를 참조한다는 것을 의미
	// @Autowired 어노테이션을 사용하면 Spring은 자동으로 ApplicationContext 인스턴스를 필드에 주입함
	// 필드를 초기화 하지 않아도 Spring이 적절한 객체(Bean)를 찾아서 주입함
	
	// 즉, 해당 코드를 작성하므로써 Spring이 ApplicationContext를 자동으로 주입하도록 지정함
	//  ApplicationContext에 정의된 Bean을 사용할 수 있게 됨
	
	
	///////////// HikariCP 설정 /////////////
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		
		// HikariConfig 설정 객체 생성
		// -> config.properties 파일에서 읽어온
		//	  spring.datasource.hikari로 시작하는 모든 값이
		//	  알맞은 필드에 세팅된 상태
		return new HikariConfig();
	}
	
	
	@Bean
	public DataSource dataSource(HikariConfig config) {
		// 매개 변수 HikariConfig config
		// -> 등록된 Bean 중 HikariConfig 타입의 Bean이 자동으로 주입
		
		DataSource dataSource = new HikariDataSource(config);	
		return dataSource;
		
		
		// ----------	
		// HikariDataSource은 HikariCP(High-performance JDBC connection pool)라는 
		// DB connection pool 라이브러리를 사용하여 DB와의 연결을 관리
		// 해당 메서드를 호출할 때 Spring은 등록된 HikariConfig 타입의 Bean을 찾아 메서드의 매개변수로 주입함
	}
	
	
	
	///////////// MyBatis 설정 /////////////
	
	@Bean
	public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception {
		
		SqlSessionFactoryBean sessionFatoryBean = new SqlSessionFactoryBean();
		
		sessionFatoryBean.setDataSource(dataSource);
		
		// mapper.xml(SQL) 파일이 모이는 경로 지정
		// -> MyBatis 코드 수행 시 mapper.xml 파일을 읽을 수 있음
		
		// sessionFactoryBean.setMapperLocations("현재프로젝트.자원.어떤파일");

		sessionFatoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**.xml"));
		
		
		// 해당 패키지 내 모든 클래스의 별칭을 등록
		// - MyBatis는 특정 클래스 지정 시 패키지명.클래스명을 모두 작성해야 함
		//  -> 긴 이름을 짧게 부를 별칭을 설정할 수 있음
		
		// - setTypeAliasesPackage("패키지") 이용 시
		//   클래스 파일명이 별칭으로 등록
		
		// ex) (원본) edu.kh.todo.model.dto.Todo --> Todo (별칭 등록)
		sessionFatoryBean.setTypeAliasesPackage("com.ljs.book");
		
		
		// MyBatis 설정 파일 경로 지정
		sessionFatoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		
		// 설정 내용이 모두 적용된 객체 반환
		return sessionFatoryBean.getObject();
		
		// ----------
		// SqlSessionFactory는 MyBatis의 핵심 인터페이스로, 데이터베이스와의 세션을 관리하고 SQL 쿼리를 실행할 수 있도록 해준다
		// SqlSessionFactory Bean는 Spring IOC Container 에 의해 관리되며 다른 Bean에서 주입하여 사용할 수 있다
	}
	
	
	// DBCP (DataBase Connection Pool)
	// SqlSessionTemplate : Connection + DBCP + MyBatis + 트랜잭션 제어 처리
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory factory) {
		
		return new SqlSessionTemplate(factory);
	}
	
	
	// DataSourceTransactionManager : 트랜잭션 매니저(제어 처리)
	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
		
		return new DataSourceTransactionManager(dataSource);
	}

}
