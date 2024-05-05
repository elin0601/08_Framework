	package edu.kh.music.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.music.member.model.dto.Member;
import edu.kh.music.member.model.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@SessionAttributes({ "loginMember" })
@RequestMapping("member")
@Slf4j
public class MemberController {

	private final MemberService service;

	// 로그인
	@PostMapping("login")
	public String login(Member member, @RequestParam(value = "saveId", required = false) String saveId, Model model,
			RedirectAttributes ra, HttpServletResponse resp) {

		Member loginMember = service.login(member);

		if(loginMember == null) 
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다");
		
		
		if (loginMember != null) {
			model.addAttribute("loginMember", loginMember);

		Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());
		cookie.setPath("/");

		if (saveId != null)
			cookie.setMaxAge(24 * 60 * 60); // 세션 만료기간 하루

		else
			cookie.setMaxAge(0); // 클라이언트 쿠기 삭제

		resp.addCookie(cookie);
		}

		return "redirect:/";
	}

	// 로그아웃
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/";
	}

	// 회원가입
	@GetMapping("signup")
	public String signup() {
		return "member/signup";
	}

	@PostMapping("signup")
	public String signup(Member member, RedirectAttributes ra) {

		int result = service.signup(member);

		String message = null;
		String path = null;

		if (result > 0) {
			message = "가입이 완료 되었습니다. 로그인 후 홈페이지를 이용해 주세요.";
			path = "/";

		} else {
			message = "회원 가입 실패";
			path = "signup";
		}

		ra.addFlashAttribute("message", message);

		return "redirect:" + path;
	}

	// 이메일 확인
	@ResponseBody
	@PostMapping("checkEmail")
	public int checkEmail(@RequestBody String memberEmail) {
		return service.checkEmail(memberEmail);
	}

	// 닉네임 확인
	@ResponseBody
	@PostMapping("checkNickname")
	public int checkNickname(@RequestBody String memberNickname) {
		return service.checkNickname(memberNickname);
	}

	// id/pw 찾기 화면 전환
	@GetMapping("idpw")
	public String idpw() {
		return "member/idpw";
	}

	// id 찾기
	@PostMapping("findId")
	public String findId(
			Member member, 
			Model model, 
			RedirectAttributes ra) {
		
		String id = service.findId(member);
		
		if (id == null) {
			ra.addFlashAttribute("message", "조회된 아이디(이메일)이 없습니다." + "\n" + "정확한 이메일 또는 전화번호를 입력해 주세요.");
			return "redirect:/member/idpw";

		} else {
			model.addAttribute("id", id);
			model.addAttribute("memberNickname", member.getMemberNickname());
			return "member/selectId"; // forward
		}

	}

	// pw 찾기
	@PostMapping("findPw")
	public String findPw(
			Member member, 
			RedirectAttributes ra, HttpSession session) {

		Integer id = service.findPw(member);

		if (id == null) {
			ra.addFlashAttribute("message", "조회된 결과가 없습니다. 정확한 정보를 입력 해주세요.");
			return "redirect:/member/idpw";

		} else {
			session.setAttribute("id", id);
			return "member/updatePw";
		}
	
	}
	
	
	
	// 비밀번호 변경
	@PostMapping("updatePw")
	public String updatePw(
			Member member,
			@RequestParam("newPw") String newPw,
			RedirectAttributes ra,  HttpSession session) {
		
		member.setMemberNo((int)session.getAttribute("id"));
		
		int result = service.updatePw(newPw, member);
		
		String path;
		String message;
		
		// 변경 성공
		if(result > 0) {
			session.removeAttribute("id");
			message = "새 비밀번호로 변경 되었습니다." + "\n" + "로그인 후 이용해 주세요.";
			path = "/";
			
		} else {
			message = "새 비밀번호를 알맞게 입력했는지 확인 해주세요.";
			path = "updatePw";
		}
		
		ra.addFlashAttribute("message", message);
		return "redirect:" + path;
	}
	
	@GetMapping("updatePw")
	public String updatePw() {
		return "member/updatePw";
	}
	
	
	// 로그인 페이지로 전환
	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	
	
	// 빠른 로그인
	@GetMapping("quickLogin")
	public String quickLogin(
			@RequestParam("memberEmail") String memberEmail,
			Model model, 
			RedirectAttributes ra) {
		
		Member loginMember = service.quickLogin(memberEmail);
		
		if(loginMember == null) {
			ra.addAttribute("message", "해당 이메일 회원이 존재하지 않습니다.");
		} else {
			model.addAttribute("loginMember", loginMember);
		}
		
		return "redirect:/";
	}
	
}
