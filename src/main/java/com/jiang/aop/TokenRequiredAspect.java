package com.jiang.aop;

import com.jiang.common.Result;
import com.jiang.exception.NoTokenException;
import com.jiang.service.UserService;
import com.jiang.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Slf4j
@Component
public class TokenRequiredAspect {

    @Resource
    private UserService userService;

    /**
     * 判断是否存在token的具体方法
     */
    @Before("@annotation(TokenRequired)")
    public void beforeTokenRequired() {
        // 获取当前请求的 HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 从请求头中获取 token
        String token = request.getHeader("token");

        // 验证token
        Long id = JWTUtils.getIdByToken(token);

        try{
            boolean ifEquals = userService.getById(id).equals(null);
        }catch (Exception e){
            log.info("abcsavbasovuawegouag");
            throw new NoTokenException(Result.error("缺少token"));
        }
    }
}
