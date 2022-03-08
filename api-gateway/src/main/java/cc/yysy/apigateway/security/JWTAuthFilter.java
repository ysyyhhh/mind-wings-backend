package cc.yysy.apigateway.security;


import cc.yysy.utilscommon.utils.JWTUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Component
@ConfigurationProperties("org.my.jwt")
public class JWTAuthFilter implements GlobalFilter, Ordered {
    static Logger logger = Logger.getLogger("JWTAuthFilter log");
    private String[] skipAuthUrls;
    @Autowired
    AuthService authService;


    /**
     * 解析后的用户唯一标识在header中的name
     */
    private static final String headerKeyOfUserPhone = "X-user-phone";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String reqPath = exchange.getRequest().getURI().getPath();
        logger.info(reqPath);
        for(int i = 0;i < skipAuthUrls.length;i++){
            logger.info(skipAuthUrls[i]);
        }
        //跳过不需要验证的路径
        if(null != skipAuthUrls&& Arrays.asList(skipAuthUrls).contains(reqPath)){
            logger.info("pass!!!");
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst("token");
        if(StringUtil.isNullOrEmpty(token)){
            throw new RuntimeException("无token");
        }
        logger.info("token = " + token);
        String userPhone = authService.verifyToken(reqPath, token);
        if (StringUtil.isNullOrEmpty(userPhone)) {
            logger.warning("没有授权的访问" + reqPath);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        //获取token中存储的用户唯一标识userPhone，并放入request header中，供后端业务服务使用

        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(headerKeyOfUserPhone, userPhone).build();

        return chain.filter(exchange.mutate().request(request).build());
    }

    /**
     * 过滤器的优先级，越低越高
     */
    @Override
    public int getOrder() {
        return -1;
    }
}