package xyz.keroro.hikvisionfacedata.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangpeng
 * @since 2025年06月23日 15:49
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/alive")
    public String alive() {
        return "yes";
    }

}
