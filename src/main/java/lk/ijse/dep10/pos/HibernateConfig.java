package lk.ijse.dep10.pos;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    private final Environment env;

    public HibernateConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource datasource) {
        LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
        lsfb.setDataSource(datasource);
        lsfb.setHibernateProperties(hibernateProps());
        lsfb.setPackagesToScan("lk.ijse.dep10.pos.entity");
        return lsfb;
    }

    @Bean
    public DataSource dataSource(){
        BasicDataSource dbcp = new BasicDataSource();
        dbcp.setUrl(env.getRequiredProperty("spring.datasource.url"));
        dbcp.setUsername(env.getRequiredProperty("spring.datasource.username"));
        dbcp.setPassword(env.getRequiredProperty("spring.datasource.password"));
        dbcp.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
        dbcp.setInitialSize(env.getRequiredProperty("spring.datasource.dbcp2.initial-size", Integer.class));
        dbcp.setMaxTotal(env.getRequiredProperty("spring.datasource.dbcp2.max-total", Integer.class));
        return dbcp;
    }

    private Properties hibernateProps() {
        Properties props = new Properties();
        props.put("hibernate.dialect", env.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
        props.put("hibernate.show_sql", env.getRequiredProperty("spring.jpa.properties.hibernate.show_sql", Boolean.class));
        props.put("hibernate.format_sql", env.getRequiredProperty("spring.jpa.properties.hibernate.format_sql", Boolean.class));
        props.put("hibernate.highlight_sql", env.getRequiredProperty("spring.jpa.properties.hibernate.highlight_sql", Boolean.class));
        props.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));
        return props;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory){
        return new HibernateTransactionManager(sessionFactory);
    }
}
