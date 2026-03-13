package org.example.projet.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "org.example.projet.repository.auth",
        entityManagerFactoryRef = "authEntityManagerFactory",
        transactionManagerRef = "authTransactionManager"
)
public class AuthJpaConfig {

    @Primary
    @Bean(name = "authDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.auth")
    public DataSource authDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "authEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean authEntityManagerFactory(
            @Qualifier("authDataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPackagesToScan("org.example.projet.model.auth");
        factory.setPersistenceUnitName("auth");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "update");
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        props.setProperty("hibernate.show_sql", "true");
        factory.setJpaProperties(props);

        return factory;
    }

    @Primary
    @Bean(name = "authTransactionManager")
    public PlatformTransactionManager authTransactionManager(
            @Qualifier("authEntityManagerFactory") LocalContainerEntityManagerFactoryBean factory) {
        return new JpaTransactionManager(factory.getObject());
    }
}