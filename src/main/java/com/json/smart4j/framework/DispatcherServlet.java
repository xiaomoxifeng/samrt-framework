package com.json.smart4j.framework;

import com.json.smart4j.framework.bean.Data;
import com.json.smart4j.framework.bean.Handle;
import com.json.smart4j.framework.bean.Param;
import com.json.smart4j.framework.bean.View;
import com.json.smart4j.framework.helper.*;
import com.json.smart4j.framework.util.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求转发器
 * Created by wuhao on 16/3/23.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关Helper
        HelperLoader.init();
        //获取ServletContext对象(用于注册Servlet)
        ServletContext servletContext = config.getServletContext();

        registerServlet(servletContext);

        UploadHelper.init(servletContext);
    }

    private void registerServlet(ServletContext servletContext) {
        //注册处理jsp饿得Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的默认servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        //defaultServlet.addMapping("/favicon.ico");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求方法与请求路径
        ServletHelper.init(req, resp);
        try {
            String requestMethod = req.getMethod().toLowerCase();
            String requestPath = req.getPathInfo();
            if (requestPath.equals("/favicon.ico")) {
                return;
            }
            Handle handle = ControllerHelper.getHandler(requestMethod, requestPath);
            if (handle != null) {
                Class<?> controllerClass = handle.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);

                Param param;
                if (UploadHelper.isMultipart(req)) {
                    param = UploadHelper.createParam(req);
                } else {
                    param = RequestHelper.createParam(req);
                }
                //调用Action方法
                Object result;
                Method actionMethod = handle.getActionMethod();
                if (param.isEmpty()) {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
                } else {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }
                //处理Action方法返回
                if (result instanceof View) {
                    handleViewResult(req, resp, (View) result);
                } else if (result instanceof Data) {
                    handleDataResult(resp, (Data) result);
                }

            }
        } finally {
            ServletHelper.destroy();
        }
    }

    private void handleDataResult(HttpServletResponse resp, Data result) throws IOException {
        //返回Json数据
        Data data = result;
        Object model = data.getModel();
        if (model != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

    private void handleViewResult(HttpServletRequest req, HttpServletResponse resp, View result) throws IOException, ServletException {
        //返回JSP页面
        View view = result;
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                resp.sendRedirect(req.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
            }
        }
    }
}
