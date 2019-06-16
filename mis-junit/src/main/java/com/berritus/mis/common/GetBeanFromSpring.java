package com.berritus.mis.common;

import com.berritus.mis.core.component.SpringContextSupport;
import com.berritus.mis.core.component.SpringContextUtil;
import com.berritus.mis.core.component.SpringContextWebSupport;
import com.berritus.mis.bean.mybatis.SysFiles;
import com.berritus.mis.controller.conf.MisApplication;
import com.berritus.mis.dubbo.api.QrySysService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @Description: 获取spring容器中bean的方法
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: 2019/6/15
 */
@RunWith(SpringRunner.class) // @RunWith(SpringRunner.class): 告诉Junit运行使用Spring 的单元测试支持,
// SpringRunner是SpringJunit4ClassRunner新的名称，只是视觉上看起来更简单了
@SpringBootTest(classes = MisApplication.class)//@SpringBootTest : 该注解可以在一个测试类指定运行Spring Boot为基础的测试
public class GetBeanFromSpring {

    /**
     * @Description: 在初始化时保存ApplicationContext对象
     * 说明：这种方式适用于采用Spring框架的独立应用程序，需要程序通过配置文件手工初始化Spring的情况。
     * @Date: Create in 2019/6/15
     * @Author: Qin Guihe
     *
     */
    @Test
    public void method1() {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("applicationContext.xml");
        applicationContext.getBean("beanName");
    }

    /**
     * @Description: 通过Spring提供的工具类获取ApplicationContext对象
     * 说明：这种方式适合于采用Spring框架的B/S系统，通过ServletContext对象获取ApplicationContext对象，
     * 然后在通过它获取需要的类实例。上面两个工具方式的区别是，前者在获取失败时抛出异常，后者返回null。
     * @Date: Create in 2019/6/15
     * @Author: Qin Guihe
     *
     */
    @Test
    public void method2() {
//        ApplicationContext applicationContext1 = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletContext sc);
//        ApplicationContext applicationContext2 = WebApplicationContextUtils.getWebApplicationContext(ServletContext sc);
//        applicationContext1.getBean("beanName");
//        applicationContext2.getBean("beanName2");
    }

    @Autowired
    private SpringContextUtil springContextUtil; // 可使用注入

    @Test
    public void method3() {
        QrySysService qrySysService = (QrySysService) SpringContextUtil.getBean(QrySysService.class);

        SysFiles bean = new SysFiles();
        bean.setFileId(1000004);
        SysFiles sysFiles = qrySysService.qrySysFiles2(bean);
        System.out.println(sysFiles.getMongoFileId());
    }

    @Autowired
    private SpringContextSupport springContextSupport;

    @Test
    public void method4() {
        QrySysService qrySysService = (QrySysService) springContextSupport.getBean(QrySysService.class);

        SysFiles bean = new SysFiles();
        bean.setFileId(1000004);
        SysFiles sysFiles = qrySysService.qrySysFiles2(bean);
        System.out.println(sysFiles.getMongoFileId());
    }

    @Autowired
    private SpringContextWebSupport springContextWebSupport;

    @Test
    public void method5() {
        QrySysService qrySysService = (QrySysService) springContextWebSupport.getBean(QrySysService.class);

        SysFiles bean = new SysFiles();
        bean.setFileId(1000004);
        SysFiles sysFiles = qrySysService.qrySysFiles2(bean);
        System.out.println(sysFiles.getMongoFileId());
    }

    /**
     * @Description: 通过Spring提供的ContextLoader
     * 提供一种不依赖于servlet,不需要注入的方式。但是需要注意一点，
     * 在服务器启动时，Spring容器初始化时，不能通过以下方法获取Spring 容器
     * @Date: Create in 2019/6/15
     * @Author: Qin Guihe
     *
     */
    @Test
    public void method6() {
        // 测试会报错，报webApplicationContext 为null，有待解决
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        QrySysService qrySysService = webApplicationContext.getBean(QrySysService.class);

        SysFiles bean = new SysFiles();
        bean.setFileId(1000004);
        SysFiles sysFiles = qrySysService.qrySysFiles2(bean);
        System.out.println(sysFiles.getMongoFileId());
    }
}
