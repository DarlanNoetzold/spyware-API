package tech.noetzold.spyware.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.noetzold.spyware.message.RabbitmqConstantes;
import tech.noetzold.spyware.model.Alerta;
import tech.noetzold.spyware.service.AlertaService;

import javax.transaction.Transactional;

@Component
public class AlertaConsumer {

    @Autowired
    AlertaService alertaService;

    @Transactional
    @RabbitListener(queues = RabbitmqConstantes.FILA_ALERTA)
    private void cnsumerAlerta(String mensagem) throws JsonProcessingException {
        Alerta alerta = new ObjectMapper().readValue(mensagem, Alerta.class);
        alertaService.saveAlerta(alerta);
    }
}
