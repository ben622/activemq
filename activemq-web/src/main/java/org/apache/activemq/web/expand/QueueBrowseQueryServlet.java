package org.apache.activemq.web.expand;


import com.alibaba.fastjson.JSON;
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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 服务端消息队列查询<br>
 * 可使用Token机制或现有用户密码形式进行安全验证
 */
public class QueueBrowseQueryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * 查询指定队列中的所有消息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queue = request.getParameter("queue");
        List<Message> messages = new ArrayList<>();
/*
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        QueueBrowseQuery queueBrowseQuery = (QueueBrowseQuery) context.getBean("queueBrowser");
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

        response.getWriter().print(JSON.toJSONString(ResultFactory.obtainResultByList(messages)));

    }

}
