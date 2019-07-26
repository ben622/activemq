package org.apache.activemq.web.expand;


import org.apache.activemq.web.QueueBrowseQuery;
import org.apache.activemq.web.util.Result;
import org.apache.activemq.web.util.ResultFactory;
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
@RequestMapping("/v1")
public class QueueBrowseQueryApi {

    private QueueBrowseQuery queueBrowseQuery;


    /**
     * 查询指定队列中的所有消息
     * @param queue
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMessageByQueue")
    public Result<Message> getMessageByQueue(HttpServletRequest request,@RequestParam("queue") String queue){
        if (queue.isEmpty()){
            return ResultFactory.obtainResultByFailure(0, "Queue cannot be empty!");
        }
        List<Message> messages = new ArrayList<>();
        /*
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        queueBrowseQuery = (QueueBrowseQuery) context.getBean("queueBrowser");
        queueBrowseQuery.setJMSDestination(queue);

        try {
            Enumeration iter = queueBrowseQuery.getBrowser().getEnumeration();
            while (iter.hasMoreElements()) {
                Message message = (Message) iter.nextElement();
                if (message != null) {
                    messages.add(message);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }*/

        return ResultFactory.obtainResultByList(messages);
    }

}
