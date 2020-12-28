package com.oozeander.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.oozeander.service", "com.oozeander.resource"
        , "com.oozeander.repository"})
@Import({RestConfig.class, DataJpaConfig.class})
public class JavaConfig {}