package cc.yysy.clientweb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 通用访问拦截匹配
 */
@Controller
public class IndexController {

    /**
     * 页面跳转

     * @return
     */
    @RequestMapping("{url}.shtml")
    public String page(@PathVariable("url") String url) {
        return  url;
    }
    /**
     * 页面跳转(一级目录)

     * @return
     */
    @RequestMapping("{module}/{url}.shtml")
    public String page(@PathVariable("module") String module,@PathVariable("url") String url) {
        return module + "/" + url;
    }
    /**
     * 页面跳转（二级目录)
     */
    @RequestMapping("{module}/{sub}/{url}.shtml")
    public String page(@PathVariable("module") String module,@PathVariable("sub") String sub,@PathVariable("url") String url) {
        return module + "/" + sub + "/" + url;
    }

}