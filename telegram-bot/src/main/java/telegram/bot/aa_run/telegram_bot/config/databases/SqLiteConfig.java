package telegram.bot.aa_run.telegram_bot.config.databases;

import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(
        basePackages = SqLiteConfig.jpaRepositoryPackage,
        entityManagerFactoryRef = SqLiteConfig.entityManagerFactory,
        transactionManagerRef = SqLiteConfig.transactionManager)
@Configuration
//@ConfigurationProperties(prefix = "primary.db")
@PropertySource("classpath:constantsDictionary.properties")
public class SqLiteConfig {

    public static final String jpaRepositoryPackage = "telegram.bot.aa_run.telegram_bot.repositories.sqlite"; // Путь до пакета, где лежат JPA репозитории для данной БД
    public static final String entityManagerFactory = "secondaryEntityManagerFactory"; // Название бина EntityManager
    public static final String transactionManager = "secondaryTransactionManager"; // Название бина TransactionManager
    public static final String datasource = "secondaryDatasource"; // Название бина DataSource
    public static final String databaseProperty = "secondaryDatabaseProperty"; // Название бина DatabaseProperty
    public static final String propertyPrefix="secondary.db"; // Префикс свойств для данного конфига БД
    public static final String entityPackage = "telegram.bot.aa_run.telegram_bot.models.sqlite"; // Путь до пакета, где лежат сущности для данной БД

    private static String url="jdbc:sqlite:database.db";
    private static String username = "";
    private static String password = "";
    private static String driverClassName="org.sqlite.JDBC";

    @Bean(name = databaseProperty)
    @ConfigurationProperties(prefix = propertyPrefix)
    public DatabaseProperty appDatabaseProperty() {
        return new DatabaseProperty(url, username, password, driverClassName);
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
    public LocalContainerEntityManagerFactoryBean entityManager(@Qualifier("secondaryDatasource") DataSource datasource) {

        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(datasource);
        em.setPersistenceUnitName(entityManagerFactory);
        em.setPackagesToScan(entityPackage);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        final HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
        properties.put("javax.persistence.validation.mode", "none");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean(name = transactionManager)
    public PlatformTransactionManager transactionManager(
            @Qualifier("secondaryEntityManagerFactory")LocalContainerEntityManagerFactoryBean entityManagerFactory,
            @Qualifier("secondaryDatasource") DataSource datasource) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        transactionManager.setDataSource(datasource);
        return transactionManager;
    }
}
