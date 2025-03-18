package telegram.bot.aa_run.telegram_bot.config.databases;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@EnableJpaRepositories(
        basePackages = PostgreSqlConfig.jpaRepositoryPackage,
        entityManagerFactoryRef = PostgreSqlConfig.entityManagerFactory,
        transactionManagerRef = PostgreSqlConfig.transactionManager)
@Configuration
@ConfigurationProperties(prefix = "primary.db")
@PropertySource("classpath:constantsDictionary.properties")
public class PostgreSqlConfig {
    public static final String jpaRepositoryPackage = "telegram.bot.aa_run.telegram_bot.repositories.postgre"; // Префикс для конфигурации в файле application.property
    public static final String entityManagerFactory = "primaryEntityManagerFactory"; //  Название бина EntityManager
    public static final String transactionManager = "primaryTransactionManager"; // Название бина транзакции
    public static final String datasource = "primaryDatasource"; // Название бина datasource
    public static final String databaseProperty = "primaryDatabaseProperty"; // Название бина свойств базы данных
    public static final String propertyPrefix="primary.db"; // Префикс для конфигурации в файле application.property
    public static final String entityPackage = "telegram.bot.aa_run.telegram_bot.models"; // Пакет сущностей для сканирования

    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String username = "postgres";
    private static String password="1331";
    private static String driverClassName="org.postgresql.Driver";

    @Bean(name = databaseProperty)
    @ConfigurationProperties(prefix = propertyPrefix)
    public DatabaseProperty appDatabaseProperty() {
        return new DatabaseProperty(url, username, password, driverClassName );
    }

    @Bean(name = datasource)
    public DataSource appDataSource(@Qualifier(databaseProperty) DatabaseProperty appDatabaseProperty) {

        return DataSourceBuilder
                .create()
                .username(DatabaseProperty.getUsername())
                .password(DatabaseProperty.getPassword())
                .url(DatabaseProperty.getUrl())
                .driverClassName(DatabaseProperty.getDriverClassName())
                .build();
    }

    @Bean(name = entityManagerFactory)
    public LocalContainerEntityManagerFactoryBean entityManager(@Qualifier("primaryDatasource") DataSource datasource) {

        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(datasource);
        em.setPersistenceUnitName(entityManagerFactory);
        em.setPackagesToScan(entityPackage);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        final HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("javax.persistence.validation.mode", "none");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean(name = transactionManager)
    public PlatformTransactionManager transactionManager(
            @Qualifier("primaryEntityManagerFactory")LocalContainerEntityManagerFactoryBean entityManagerFactory,
            @Qualifier("primaryDatasource") DataSource datasource) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        transactionManager.setDataSource(datasource);
        return transactionManager;
    }
}
