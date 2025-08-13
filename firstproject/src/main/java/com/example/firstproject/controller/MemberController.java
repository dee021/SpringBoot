package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/signup")
    public String newMemberForm() {
        return "members/new";
    }

    @PostMapping("/join")
    public String createMember(MemberForm form) {
        log.info(form.toString());
//        System.out.println(form.toString());

        Member member = form.toEntity();
        log.info(member.toString());
//        System.out.println(member.toString());

        Member saved = memberRepository.save(member);
        log.info(saved.toString());
//        System.out.println(saved.toString());

        return "redirect:/members/" + saved.getId();
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model){
        Member memberEntity = memberRepository.findById(id).orElse(null);
        model.addAttribute(memberEntity);
        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model) {
        List<Member> memberList = (List<Member>)memberRepository.findAll();
        model.addAttribute(memberList);
        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Member memberEntity = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberEntity);
        return "members/edit";
    }

    @PostMapping("/members/update")
    public String update(MemberForm form, Model model) {
        Member memberEntity = form.toEntity();

        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        if (target != null)
            memberRepository.save(memberEntity);
        return "redirect:/members/" + memberEntity.getId();
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        Member target = memberRepository.findById(id).orElse(null);

        if (target != null) {
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }

        return "redirect:/members";
    }

}
