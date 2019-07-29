/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.activemq.web.expand;

import com.alibaba.fastjson.JSON;
import org.apache.activemq.web.util.HttpUtils;
import org.apache.activemq.web.util.Result;
import org.apache.activemq.web.util.ResultFactory;
import org.apache.http.util.TextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.Text;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * 拦截/api/*
 */
public class TokenFilter implements Filter {
    public static final String TOKEN = "token";

    //临时缓存方案
    private HashMap<String, String> tokens = new HashMap<>();


    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getParameter(TOKEN);
        if (TextUtils.isEmpty(token)) {
            response(response, ResultFactory.obtainResultByFailure(0, "token cannot be empty!"));
            return;
        }
        //verifytoken
        if (tokens.get(token) == null) {
            String resultJson = HttpUtils.doGet("http://www.zhangchuany.com:8080/springboot/verifyToken?token=" + token);
            Result result = JSON.parseObject(resultJson, Result.class);
            if (result.getCode() == 0 && Boolean.parseBoolean(result.getData().toString())) {
                tokens.put(token, token);
            } else {
                response(response, ResultFactory.obtainResultByFailure(0, "Token validation failed, please retry the request with a valid token!"));
                return;
            }
        }
        chain.doFilter(request, response);

    }

    public void destroy() {
    }

    private void response(ServletResponse response, Result result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(JSON.toJSONString(result));
    }

}
