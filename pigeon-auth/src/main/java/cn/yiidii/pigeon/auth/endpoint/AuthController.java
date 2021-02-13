package cn.yiidii.pigeon.auth.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-13 11:36
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {
    /**
     * thymeleaf指定登录页面
     *
     * @param model
     * @return
     */
    @GetMapping("/loginPage")
    public String index(Model model) {
        return "login";
    }

}
