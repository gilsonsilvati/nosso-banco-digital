package br.com.bancodigital.api.event.listener;

import br.com.bancodigital.api.event.RecursoCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

    @Override
    public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
        HttpServletResponse response = recursoCriadoEvent.getResponse();
        Long id = recursoCriadoEvent.getId();
        String path = recursoCriadoEvent.getPath();

        adicionarHeaderLocation(response, id, path);
    }

    private void adicionarHeaderLocation(HttpServletResponse response, Long id, String path) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .path(path)
                .buildAndExpand(id)
                .toUri();

        response.setHeader("Location", uri.toASCIIString());
    }

}
