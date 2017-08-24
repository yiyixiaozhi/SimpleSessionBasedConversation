package com.yyxz.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * Created by Czl on 2015/7/27.
 */
public class ExceptionAndLogInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation ai) {
        Controller controller = ai.getController();
        try {
            ai.invoke();
        } catch (Exception e) {
        }
    }
}
