package br.com.bancodigital.api.async.config;

import br.com.bancodigital.api.async.ContaAsync;
import br.com.bancodigital.api.async.ContaRunnable;
import br.com.bancodigital.api.async.impl.ContaAsyncImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { ContaRunnable.class })
public class ContaAsyncConfig {

    @Bean
    public ContaAsync contaAsync() {
        return new ContaAsyncImpl();
    }

}
