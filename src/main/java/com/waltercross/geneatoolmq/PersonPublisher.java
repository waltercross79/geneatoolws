package com.waltercross.geneatoolmq;

import com.waltercross.geneatoolcore.RegistryRecord;
import com.waltercross.geneatoolcore.PersonRole;
import com.waltercross.geneatoolcore.PersonInRecord;
import com.waltercross.geneatoolcore.RecordType;
import com.waltercross.geneatoolcore.CustomDateSerializer;
import com.waltercross.geneatoolcore.ObjectToJsonSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.rabbitmq.client.AMQP;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PersonPublisher extends PublisherBase {

    private String routingKey = "personal";
    private String exchangeName = "personal_records";

    public PersonPublisher() {
        super();
    }

    protected Log setUpLog() {
        return LogFactory.getLog(PersonPublisher.class);
    }

    public void send(RegistryRecord record) {
        try {
            // Jsonify the message.
            // Send to the right exchange with "marriage" routing key.
            PersonChangeMessage message;

            if(record.recordType == RecordType.Birth) {
                PersonInRecord newborn = record.people.stream().filter(x -> x.personRole.equals(PersonRole.Newborn)).findAny().orElse(null);
                PersonInRecord father = record.people.stream().filter(x -> x.personRole.equals(PersonRole.Father)).findAny().orElse(null);
                PersonInRecord mother = record.people.stream().filter(x -> x.personRole.equals(PersonRole.Mother)).findAny().orElse(null);

                message = new PersonChangeMessage("birth", newborn.person.id, record.recordDate);
                if(mother != null && mother.person.id > 0)
                    message.mother = mother.person.id;
                if(father != null && father.person.id > 0)
                    message.father = father.person.id;
            } else if (record.recordType == RecordType.Death) {
                PersonInRecord deceqased = record.people.stream().filter(x -> x.personRole.equals(PersonRole.Deceased)).findAny().orElse(null);

                message = new PersonChangeMessage("death", deceqased.person.id, record.recordDate);
            } else {
                return;
            }

            getChannel().exchangeDeclare(exchangeName, "direct", true);

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

    class PersonChangeMessage {
        public String recordType;
        public int personId;
        public int father;
        public int mother;
        @JsonSerialize(using = CustomDateSerializer.class)
        public Date dateOfRecord;

        public PersonChangeMessage(String recordType, int personId, Date recordDate) {
            this.recordType = recordType;
            this.personId = personId;
            this.dateOfRecord = recordDate;
        }
    }
}