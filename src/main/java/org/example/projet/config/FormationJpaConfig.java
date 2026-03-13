package org.example.projet.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        basePackages = "org.example.projet.repository.formation",
        entityManagerFactoryRef = "formationEntityManagerFactory",
        transactionManagerRef = "formationTransactionManager"
)
public class FormationJpaConfig {

    @Bean(name = "formationDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.formation")
    public DataSource formationDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "formationEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean formationEntityManagerFactory(
            @Qualifier("formationDataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPackagesToScan("org.example.projet.model.formation");
        factory.setPersistenceUnitName("formation");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "update");
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        props.setProperty("hibernate.show_sql", "true");
        factory.setJpaProperties(props);

        return factory;
    }

    @Bean(name = "formationTransactionManager")
    public PlatformTransactionManager formationTransactionManager(
            @Qualifier("formationEntityManagerFactory") LocalContainerEntityManagerFactoryBean factory) {
        return new JpaTransactionManager(factory.getObject());
    }
}
