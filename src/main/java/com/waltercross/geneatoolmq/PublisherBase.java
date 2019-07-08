package com.waltercross.geneatoolmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import com.waltercross.geneatoolcore.RegistryRecord;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class PublisherBase {
    private Connection _conn;
    private Channel _channel;

    protected Log _log;

    protected Channel getChannel() {
        return _channel;
    }

    protected abstract Log setUpLog();

    public PublisherBase() {
        super();

        _log = setUpLog();

        try {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://admin:admin2017@172.30.5.1:5672");

        _log.info("Creating new connection to rabbit...");
        _conn = factory.newConnection();
        _log.info("Created new connection to rabbit...");

        _log.info("Creating new channel...");
        _channel = _conn.createChannel();
        _log.info("Created new channel...");
        } catch (Exception e) {
            _log.info("Exception thrown - disconnecting from rabbit...");
            disconnect();
            _log.info("Exception thrown - disconnected from rabbit...");
            // Log this...
            _log.error(e);
        }
    }

    public abstract void send(RegistryRecord record);

    public void disconnect() {
        try {
            if(_channel != null) {
                _channel.close();
            }
            if(_conn != null) {
                _conn.close();
            }
        } catch (Exception e) {
            // Log the exception...
            _log.info("Error disconnecting...");
            _log.error(e);
        }
    }
}