package com.cos.blog.model;

/*import com.fasterxml.jackson.annotation.JsonIgnoreProperties;*/

import lombok.Data;

@Data
// @JsonIgnoreProperties 오류사항 (connected_at을 찾을 수 없는 데이터였는데 json 필드데이터로 존재하는 property로 착각하고 집어넣은 어노테이션  
public class KakaoProfile {

	public Long id;
	public String connected_at;
	public Properties properties;
	public KakaoAccount kakao_account;

	@Data
	static public class Properties {

		public String nickname;
		public String profileImage;
		public String thumbnailImage;
	}
	@Data
	static public class KakaoAccount {

		public Boolean profile_nickname_needs_agreement;
		public Profile profile;
		public Boolean has_email;
		public Boolean email_needs_agreement;
		public Boolean is_email_valid;
		public Boolean is_email_verified;
		public String email;

	}
	@Data
	static public class Profile {
		
		public String nickname;
		public String thumbnailImageUrl;
		public String profileImageUrl;
	}
}



