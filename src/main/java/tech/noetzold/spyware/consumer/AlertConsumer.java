package tech.noetzold.spyware.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.noetzold.spyware.message.RabbitmqConstantes;
import tech.noetzold.spyware.model.Alert;
import tech.noetzold.spyware.service.AlertService;

import javax.transaction.Transactional;

@Component
public class AlertConsumer {

    @Autowired
    AlertService alertService;

    @Transactional
    @RabbitListener(queues = RabbitmqConstantes.FILA_ALERTA)
    private void consumerAlerta(String mensagem) throws JsonProcessingException {
        Alert alert = new ObjectMapper().readValue(mensagem, Alert.class);
        alertService.saveAlerta(alert);
    }
}
