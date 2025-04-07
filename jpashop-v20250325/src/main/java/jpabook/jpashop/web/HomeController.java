package jpabook.jpashop.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.LoginService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static jpabook.jpashop.web.LoginController.LOGIN_MEMBER;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    MemberService memberService;

    @RequestMapping("/")
    public String homeLogin(
            @SessionAttribute(name = LOGIN_MEMBER, required = false)
            Member loginMember,
            Model model) {

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

}