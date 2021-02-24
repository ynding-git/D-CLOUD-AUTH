package com.ynding.cloud.auth.authentication.config.security;

import com.alibaba.fastjson.JSONObject;
import com.ynding.cloud.common.model.bo.ResponseBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 身份验证访问被拒绝处理程序
 * @author ynding
 */
@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp, AccessDeniedException e) throws IOException, ServletException {

        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        ResponseBean error = ResponseBean.fail("权限不足，请联系管理员");
        out.write(JSONObject.toJSONString(error));
        out.flush();
        out.close();
    }
}
