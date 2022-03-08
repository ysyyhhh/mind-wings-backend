package cc.yysy.utilscommon.exception;



import cc.yysy.utilscommon.constant.MsgConstant;
import cc.yysy.utilscommon.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理的类
 *
 * @author yanglei
 * @date 2020/8/3
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     * @param req 无用
     * @param e 异常
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    public Result<String> bizExceptionHandler(HttpServletRequest req, BizException e){
        logger.error("发生业务异常！原因是：{}",e.getMessage());
        return Result.error(e.getCode(),e.getMessage());
    }

    /**
     * 处理空指针的异常
     * @param req 无用
     * @param e 异常
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    public Result<String> exceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生空指针异常！原因是:",e);
        return Result.error(-1,MsgConstant.MSG_NULL_POINTER);
    }


    /**
     * 处理其他异常
     * @param req 无用
     * @param e 异常
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest req, Exception e){
        logger.error("未知异常！原因是:",e);
        return Result.error(-2,MsgConstant.MSG_UNKNOWN_ERROR);
    }

}

