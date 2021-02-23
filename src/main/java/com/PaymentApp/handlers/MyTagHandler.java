package com.PaymentApp.handlers;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import javax.servlet.jsp.tagext.TagSupport;

import java.text.SimpleDateFormat;

import java.util.Date;


public class MyTagHandler extends TagSupport {


    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();//returns the instance of JspWriter
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            out.print(formatter.format(date));//printing date and time using JspWriter
        } catch (Exception e) {
            System.out.println(e);
        }
        return SKIP_BODY;//will not evaluate the body content of the tag
    }
}


