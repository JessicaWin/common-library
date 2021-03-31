package com.jessica.jwt;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@Data
public class JwtKeyHelper {
	@Value("${jwt.privateKey}")
	private String privateKey;
	@Value("${jwt.publicKey}")
	private String publicKey;
}
