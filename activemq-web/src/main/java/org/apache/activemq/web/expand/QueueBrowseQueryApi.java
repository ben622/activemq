package org.apache.activemq.web.expand;


import org.apache.activemq.web.QueueBrowseQuery;
import org.apache.activemq.web.expand.bean.MessageWrapper;
import org.apache.activemq.web.util.Result;
import org.apache.activemq.web.util.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 服务端消息队列查询<br>
 * 可使用Token机制或现有用户密码形式进行安全验证
 */
@Controller
@RequestMapping("/testapi/v1")
public class QueueBrowseQueryApi {

    private QueueBrowseQuery queueBrowseQuery;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询指定队列中的所有消息
     * @param queue
     * @return
     */
    @ResponseBody
    @RequestMapping("/getQueueMessages")
    public Result<String> getQueueMessages(HttpServletRequest request,@RequestParam("queue") String queue){
        if (queue.isEmpty()){
            return ResultFactory.obtainResultByFailure(0, "queue cannot be empty!");
        }
        List<MessageWrapper> messages = new ArrayList<>();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        queueBrowseQuery = (QueueBrowseQuery) context.getBean("queueBrowser");
        queueBrowseQuery.setJMSDestination(queue);
        try {
            Enumeration iter = queueBrowseQuery.getBrowser().getEnumeration();
            while (iter.hasMoreElements()) {
                Message message = (Message) iter.nextElement();
                if (message != null) {
                    messages.add(MessageWrapper.convertJMSMessageToMessageWrapper(message,queue));
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return ResultFactory.obtainResultByList(messages);
    }


    /**
     * 查询指定队列中的所有消息
     * @param queue
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMessageById")
    public Result<String> getMessageById(HttpServletRequest request,@RequestParam("queue") String queue,@RequestParam("messageId") String messageId){
        if (queue.isEmpty()){
            return ResultFactory.obtainResultByFailure(0, "queue cannot be empty!");
        }
        if (messageId.isEmpty()){
            return ResultFactory.obtainResultByFailure(0, "messageId cannot be empty!");
        }
        MessageWrapper messages = null;
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        queueBrowseQuery = (QueueBrowseQuery) context.getBean("queueBrowser");
        queueBrowseQuery.setJMSDestination(queue);
        try {
            Enumeration iter = queueBrowseQuery.getBrowser().getEnumeration();
            while (iter.hasMoreElements()) {
                Message message = (Message) iter.nextElement();
                if (message != null&&message.getJMSMessageID().equals(messageId)) {
                    messages = MessageWrapper.convertJMSMessageToMessageWrapper(message, queue);
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return ResultFactory.obtainResultBySuccessful(1,messages);
    }

}
