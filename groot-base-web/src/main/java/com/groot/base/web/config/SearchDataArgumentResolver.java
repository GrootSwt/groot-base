package com.groot.base.web.config;

import com.groot.base.common.SearchData;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 处理器方法入参解析器
 */
public class SearchDataArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * 形参类型是否与自己想要对特殊参数进行封装的类型一致
     *
     * @param methodParameter handler method中的形参
     * @return 是否是自己想要处理的形参
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // 形参类型是否与自己想要对特殊参数进行封装的类型一致
        return SearchData.class.equals(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        SearchData searchData = new SearchData();
        // 获取所有参数名列表，过滤出自己想要的参数，并放到SearchData中
        nativeWebRequest.getParameterNames().forEachRemaining((paramName) -> {
            if (paramName.startsWith("s_")) {
                // 由于根据参数名获取到的参数都为数组，简单的对单个参数进行处理
                String[] parameterValues = nativeWebRequest.getParameterValues(paramName);
                if (parameterValues != null && parameterValues.length == 1) {
                    searchData.put(paramName.substring(2), parameterValues[0]);
                } else {
                    searchData.put(paramName.substring(2), parameterValues);
                }
            }
        });
        return searchData;
    }
}
