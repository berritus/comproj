package com.berritus.mis.security;


import com.berritus.mis.security.handler.MyAccessDecisionManager;
import com.berritus.mis.security.handler.MyAccessDeniedHandle;
import com.berritus.mis.security.handler.MyFilterInvocationSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity//开启WebSecurity功能
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启方法级别的保护
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    @Qualifier("myUserDetailsService")
    private UserDetailsService userDetailsService;
    //403页面
    @Autowired
    private MyAccessDeniedHandle myAccessDeniedHandle;
    //根据一个url请求，获得访问它所需要的roles权限
    @Autowired
    private MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;
    //接收一个用户的信息和访问一个url所需要的权限，判断该用户是否可以访问
    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public PersistentTokenRepository persistentTokenRepository(){
//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        //jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
//        return jdbcTokenRepository;
//    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //内存中创建了一个认证用户信息
//        auth.inMemoryAuthentication()
//                .withUser("berritus")
//                .password(passwordEncoder().encode("q123456")).roles("USER");

        //从数据库获取用户认证信息
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.antMatchers("/bootstrap/**").permitAll()
                //.antMatchers("/user/**").hasRole("ADMIN")
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>(){
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//                        o.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);
//                        o.setAccessDecisionManager(myAccessDecisionManager);
//                        return o;
//                    }
//                })
                .antMatchers("/login","/tip/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")///main/login.html
                .failureUrl("/login-error")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/user/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
//                .and()
//                .exceptionHandling().accessDeniedPage("/401")
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandle)
//                .and()
//                .rememberMe().tokenValiditySeconds(84600).tokenRepository(persistentTokenRepository())
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        //Spring boot的默认静态资源放置位置是在resource/static下
        web.ignoring().antMatchers("/bootstrap/**", "/favicon.ico", "/js/**", "js/**");
    }
}
