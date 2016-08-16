package com.peachyy.springmvc.support;

import com.baidu.ueditor.ActionEnter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xsTao on 2016/8/16.
 */
@SuppressWarnings("Duplicates")
public class SpringMvcUeditorSupport {
    private String rootPath = null;
    @RequestMapping("/")
    public void index(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            String tmpRootPath = request.getServletContext().getRealPath("/");
//            String contextPath = request.getContextPath();
//            if(null==contextPath ||"".equals(contextPath)){
//                contextPath="";
//            }
            response.getWriter().write(new ActionEnter(request, (rootPath != null && !"".equals(rootPath)) ?
                    rootPath : tmpRootPath).exec());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getRootPath() {
        return this.rootPath;
    }
}
