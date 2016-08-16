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
    @RequestMapping("/")
    public void index(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            String rootPath = request.getServletContext().getRealPath("/");
            response.getWriter().write(new ActionEnter(request, rootPath).exec());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
