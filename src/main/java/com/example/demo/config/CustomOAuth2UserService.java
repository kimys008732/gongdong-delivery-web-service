package com.example.demo.config;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1) 우선 DefaultOAuth2UserService를 통해 카카오 UserInfo 엔드포인트 호출
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        // 2) 가져온 사용자 프로필 정보(Map)를 확인
        //    카카오의 경우 기본 스코프가 profile, account_email 등이므로, 
        //    getAttributes() 내부에 필요한 정보들이 Map 형태로 담깁니다.
        Map<String, Object> attributes = oauth2User.getAttributes();
        //    attributes 구조 예시 (카카오 반환 JSON):
        //    {
        //      "id": 123456789, 
        //      "kakao_account": {
        //        "email": "user@example.com",
        //        "profile": {
        //          "nickname": "홍길동",
        //          "profile_image_url": "https://xxx.kakaocdn.net/..."
        //        }
        //      }, ...
        //    }

        // 3) Map에서 oauthId, email, nickname 꺼내기 (카카오 레퍼런스에 따라 JSON 경로가 달라질 수 있음)
        Long kakaoid = ((Number) attributes.get("id")).longValue();
        String kakaoId = kakaoid.toString();

        // “kakao_account” 내부 꺼내기
        @SuppressWarnings("unchecked")
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        //String email = (String) kakaoAccount.get("email");

        @SuppressWarnings("unchecked")
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");
        // 필요시 profile_image_url 꺼내서 Member에 저장할 수도 있습니다.

        // 4) DB에 이미 같은 oauthId가 있는지 조회
        Member member = memberRepository.findBykakaoId(kakaoId)
                .orElseGet(() -> {
                    // 신규 회원이면 새 엔티티 생성 및 저장
                    //Member m = new Member(kakaoId, email, nickname);
                    Member m = new Member(kakaoId, nickname);
                    // 필요시 m.setProfileImageUrl( (String) profile.get("profile_image_url") );
                    return memberRepository.save(m);
                });

        // 5) (원한다면) 이미 있는 회원이지만 이메일이나 닉네임이 변경되었을 수 있으므로, 업데이트 로직을 추가
        boolean updated = false;
        //if (!member.getEmail().equals(email)) {
        //    member.setEmail(email);
        //    updated = true;
        //}
        if (!member.getNickname().equals(nickname)) {
            member.setNickname(nickname);
            updated = true;
        }
        if (updated) {
            memberRepository.save(member);
        }

        // 6) 최종적으로 스프링 시큐리티가 인식할 수 있도록
        //    DefaultOAuth2User(권한 목록, attributes Map, “userNameAttributeName”) 객체를 리턴
        //    여기서 userNameAttributeName은 카카오에서 고유 식별자로 쓰이는 “id” 필드 이름
        return oauth2User;
        // ※ 만약 CustomOAuth2User 라는 클래스를 만들어서 member.getId(), member.getNickname() 등을 직접 들고 다니고 싶으면, 
        //    OAuth2User를 wrapping 한 CustomOAuth2User를 리턴해도 됩니다.
    }
}
