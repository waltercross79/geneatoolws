package com.waltercross.geneatoolmq;

import com.waltercross.geneatoolcore.RegistryRecord;
import com.waltercross.geneatoolcore.PersonRole;
import com.waltercross.geneatoolcore.PersonInRecord;
import com.waltercross.geneatoolcore.CustomDateSerializer;
import com.waltercross.geneatoolcore.ObjectToJsonSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.rabbitmq.client.AMQP;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MarriagePublisher extends PublisherBase {

    private String routingKey = "marriage";
    private String exchangeName = "personal_records";
    

    public MarriagePublisher() {
        super();
    }

    protected Log setUpLog() {
        return LogFactory.getLog(MarriagePublisher.class);
    }

    public void send(RegistryRecord record) {
        try {
            // Jsonify the message.
            // Send to the right exchange with "marriage" routing key.
            PersonInRecord groom = record.people.stream().filter(x -> x.personRole.equals(PersonRole.Groom)).findFirst().get();
            PersonInRecord bride = record.people.stream().filter(x -> x.personRole.equals(PersonRole.Bride)).findFirst().get();

            MarriageMessage message = new MarriageMessage();
            message.groom_id = groom.person.id;
            message.bride_id = bride.person.id;
            message.dateOfWedding = record.recordDate;

            getChannel().exchangeDeclare(exchangeName, "direct", true);
            String queueName = getChannel().queueDeclare().getQueue();
            getChannel().queueBind(queueName, exchangeName, routingKey);

            byte[] messageBodyBytes = new ObjectToJsonSerializer().serialize(message).getBytes();
            getChannel().basicPublish(exchangeName, routingKey,
                        new AMQP.BasicProperties.Builder()
                        .contentType("application/json")
                        .deliveryMode(2)
                        .priority(1)
                        .build(),
                        messageBodyBytes);
        } catch (Exception e) {
            // TODO: handle exception
            // Log it somehow...
            _log.error(e);
        }
    }

    class MarriageMessage {
        public String recordType = "marriage";
        public int groom_id;
        public int bride_id;
        @JsonSerialize(using = CustomDateSerializer.class)
        public Date dateOfWedding;
    }
}