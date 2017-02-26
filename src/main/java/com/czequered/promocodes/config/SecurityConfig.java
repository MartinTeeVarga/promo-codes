package com.czequered.promocodes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * @author Martin Varga
 */
@Configuration
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //TODO copy from the real example
//
//    @Autowired
//    OAuth2ClientContext oauth2ClientContext;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
//        http
//            .antMatcher("/**")
//            .authorizeRequests()
//                .antMatchers("/", "/login**", "/webjars/**")
//                .permitAll()
//            .anyRequest()
//                .authenticated()
//            .and().logout().logoutSuccessUrl("/").permitAll()
//            .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//            .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
//        // @formatter:on
//    }
//
//    private Filter ssoFilter() {
//
//        CompositeFilter filter = new CompositeFilter();
//        List<Filter> filters = new ArrayList<>();
//
//        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/facebook");
//        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
//        facebookFilter.setRestTemplate(facebookTemplate);
//        UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebook.getUserInfoUri(), facebook().getClientId());
//        tokenServices.setRestTemplate(oAuth2RestTemplate);
//        facebookFilter.setTokenServices(tokenServices);
//        filters.add(facebookFilter);
//
//        OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/github");
//        OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github(), oauth2ClientContext);
//        githubFilter.setRestTemplate(githubTemplate);
//        tokenServices = new UserInfoTokenServices(github().getUserInfoUri(), github().getClientId());
//        tokenServices.setRestTemplate(oAuth2RestTemplate);
//        githubFilter.setTokenServices(tokenServices);
//        filters.add(githubFilter);
//
//        filter.setFilters(filters);
//        return filter;
//
//    }
//
//    @Bean
//    @ConfigurationProperties("facebook.client")
//    public AuthorizationCodeResourceDetails facebook() {
//        return new AuthorizationCodeResourceDetails();
//    }
//
//    @Bean
//    @ConfigurationProperties("facebook.resource")
//    public ResourceServerProperties facebookResource() {
//        return new ResourceServerProperties();
//    }
//
//    @Bean
//    @ConfigurationProperties("github.client")
//    public AuthorizationCodeResourceDetails github() {
//        return new AuthorizationCodeResourceDetails();
//    }
//
//    @Bean
//    @ConfigurationProperties("github.resource")
//    public ResourceServerProperties githubResource() {
//        return new ResourceServerProperties();
//    }
}
