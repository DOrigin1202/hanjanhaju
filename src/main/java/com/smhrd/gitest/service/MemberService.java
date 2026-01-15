package com.smhrd.gitest.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.smhrd.gitest.entity.MemberEntity;
import com.smhrd.gitest.repository.MemberRepository;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    

    public String register(MemberEntity entity) {
        if (memberRepository.existsByEmail(entity.getEmail())) {
            return "duplicate"; // 이메일 중복일 때 바로 반환
        }
        MemberEntity e = memberRepository.save(entity);
        if (e != null) {
            return "success"; // 저장 성공
        }
        return "fail"; // 저장 실패 등 모든 나머지 경우의 디폴트 반환
        
    }
    	
    public Optional<String> findEmailByNicknameAndBirthdate(String nickname, LocalDate birthdate) {
    	return memberRepository.findByNicknameAndBirthdate(nickname, birthdate)
    			.map(MemberEntity::getEmail); // 찾은 MemberEntity에서 email만 추출
    }
       
    

    // 아이디(이메일) 중복 체크
    public boolean check(String email) {
        return memberRepository.existsByEmail(email);
    }

    
    public MemberEntity login(String email, String pw) {
        MemberEntity member = memberRepository.findByEmail(email).orElse(null);
        if (member != null && member.getPw().equals(pw)) { // 암호화 안 쓸 때
            return member;
        }
        return null;
    }
    
    public void updatePassword(String email, String newPassword) {
        // 1. 이메일로 회원 정보 조회
        Optional<MemberEntity> memberOpt = memberRepository.findByEmail(email);
        
        // 2. 회원이 존재하면 비밀번호 업데이트
        if (memberOpt.isPresent()) {
            MemberEntity member = memberOpt.get();
            // ★★★ 현재는 평문 저장. 만약 암호화를 쓴다면 아래 코드로 변경 ★★★
            // member.setPw(passwordEncoder.encode(newPassword));
            member.setPw(newPassword);
            memberRepository.save(member);
        }
        // 회원이 존재하지 않는 경우는 컨트롤러에서 이미 처리했으므로, 별도 처리는 생략 가능
        
        /**
         * 이메일과 생년월일이 모두 일치하는 회원이 존재하는지 확인합니다.
         */
    }
    public boolean checkAccountExists(String email, LocalDate birthdate) {
    	return memberRepository.existsByEmailAndBirthdate(email, birthdate);
    }
    
}
