package org.apache.activemq.web.expand.bean;

import javax.jms.*;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class MessageWrapper implements Serializable {
    private String messageId;
    private String destination;
    private String correlationId;
    private int priority;
    private boolean redelivered;
    private String replayTo;
    private long timestamp;
    private String type;

    private Object body;

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isRedelivered() {
        return redelivered;
    }

    public void setRedelivered(boolean redelivered) {
        this.redelivered = redelivered;
    }

    public String getReplayTo() {
        return replayTo;
    }

    public void setReplayTo(String replayTo) {
        this.replayTo = replayTo;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    /**
     * 将JMSMessage转换为MessageWrapper
     * @param message
     * @param queue
     * @return
     */
    public static MessageWrapper convertJMSMessageToMessageWrapper(Message message,String queue) {
        if (message==null) return null;
        MessageWrapper messageWrapper = new MessageWrapper();
        try {
            messageWrapper.setMessageId(message.getJMSMessageID());
            messageWrapper.setDestination(queue);
            messageWrapper.setCorrelationId(message.getJMSCorrelationID());
            messageWrapper.setPriority(message.getJMSPriority());
            messageWrapper.setRedelivered(message.getJMSRedelivered());
            //messageWrapper.setReplayTo();
            messageWrapper.setTimestamp(message.getJMSTimestamp());
            messageWrapper.setType(message.getJMSType());
            messageWrapper.setBody(getMessageBody(message));
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }
        return messageWrapper;
    }

    /**
     * 获取消息内容
     * @param message
     * @return
     * @throws JMSException
     */
    public static Object getMessageBody(Message message) throws JMSException {
        if (message instanceof TextMessage) {
            return ((TextMessage) message).getText();
        }
        if (message instanceof ObjectMessage) {
            try {
                return ((ObjectMessage) message).getObject();
            } catch (Exception e) {
                //message could not be parsed, make the reason available
                return new String("Cannot display ObjectMessage body. Reason: " + e.getMessage());
            }
        }
        if (message instanceof MapMessage) {
            MapMessage mapMessage = (MapMessage) message;
            Map<String, Object> answer = new HashMap<String, Object>();
            Enumeration iter = mapMessage.getMapNames();
            while (iter.hasMoreElements()) {
                String name = (String) iter.nextElement();
                Object value = mapMessage.getObject(name);
                if (value != null) {
                    answer.put(name, value);
                }
            }
            return answer;

        }
        if (message instanceof BytesMessage) {
            BytesMessage msg = (BytesMessage) message;
            int len = (int) msg.getBodyLength();
            if (len > -1) {
                byte[] data = new byte[len];
                msg.readBytes(data);
                return new String(data);
            } else {
                return "";
            }
        }
        if (message instanceof StreamMessage) {
            return "StreamMessage is not viewable";
        }

        // unknown message type
        if (message != null) {
            return "Unknown message type [" + message.getClass().getName() + "] " + message;
        }
        return null;
    }
}
