package com.xxkj.passenger.wechat.config;

import javax.sql.DataSource;

import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * 
 * @Title MyBatisConfig
 * @Description TODO 
 *
 * @Project ry-coach-data-access-service
 * @Date 2016年11月30日
 * @Author XZF
 * @Copyrigth  版权所有 (C) 2016 广州讯心信息科技有限公司. 
 *
 * @注意    本内容仅限于广州讯心内部传阅，禁止外泄以及用于其他的商业目的
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {

    private @Autowired DataSource dataSource;

    @Bean(name = "sqlSessionFactory")	
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setJdbcTypeForNull(JdbcType.OTHER);
        configuration.setCallSettersOnNulls(true);
		bean.setConfiguration(configuration);
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
            bean.setTypeAliasesPackage("com.xxkj.passenger.wechat.entity");
            bean.setTypeAliases(new Class[]{com.szyciov.param.QueryParam.class,
            		com.szyciov.entity.Dictionary.class,
            		com.szyciov.org.entity.OrgUser.class,
            		com.szyciov.op.entity.PeUser.class,
            		com.szyciov.passenger.entity.PassengerOrder.class,
            		com.szyciov.passenger.entity.AccountRules.class,
            		com.szyciov.passenger.entity.LeasesCompany.class,
            		com.szyciov.passenger.entity.Order4List.class,
            		com.szyciov.passenger.entity.DriverInfo.class,
            		com.szyciov.passenger.entity.MostAddress.class,
            		com.szyciov.passenger.entity.SysVersion.class,
            		com.szyciov.passenger.entity.VehicleModels.class,
            		com.szyciov.passenger.entity.AirportAddr.class,
            		com.szyciov.passenger.entity.MostContact.class,
            		com.szyciov.lease.entity.OrgOrgan.class,
            		com.szyciov.entity.PubMostcontact.class,
            		com.szyciov.entity.PubMostaddress.class,
            		com.szyciov.entity.PubDriver.class,
            		com.szyciov.lease.param.PubDriverInBoundParam.class});
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean({"transactionManager","platformTransactionManager"})
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
    
}
