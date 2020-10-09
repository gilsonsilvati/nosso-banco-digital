package br.com.bancodigital.api.resource.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

@Getter
public class RecursoCriadoEvent extends ApplicationEvent {

    private HttpServletResponse response;
    private Long id;
    private String path;

    public RecursoCriadoEvent(Object source, HttpServletResponse response, Long id, String path) {
        super(source);

        this.response = response;
        this.id = id;
        this.path = path;
    }

}
