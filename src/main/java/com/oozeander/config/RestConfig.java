/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oozeander.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * @author b.ketrouci
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.oozeander.resource"})
public class RestConfig {
    @Bean
    CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        multipartResolver.setMaxUploadSize(20971520); // 20 Mo
        multipartResolver.setMaxInMemorySize(4194304); // 4 Mo
        multipartResolver.setMaxUploadSizePerFile(8388608); // 8 Mo
        return multipartResolver;
    }
}
