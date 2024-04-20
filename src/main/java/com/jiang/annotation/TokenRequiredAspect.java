package com.jiang.annotation;

import com.jiang.common.Result;
import com.jiang.exception.NoTokenException;
import com.jiang.service.UserService;
import com.jiang.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
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

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    protected HttpServletRequest httpServletRequest;

    /**
     * 判断是否存在token的具体方法
     */
    @Before("@annotation(TokenRequired)")
    public void beforeTokenRequired() {

        // 获取请求头中的token
        String token = httpServletRequest.getHeader("token");

        // 验证token
        Long id = JWTUtils.getIdByToken(token);

        try{
                boolean ifEquals = userService.getById(id).equals(null);
        }catch (Exception e){
            throw new NoTokenException(Result.error("缺少token，或token无效"));
        }
    }
}
