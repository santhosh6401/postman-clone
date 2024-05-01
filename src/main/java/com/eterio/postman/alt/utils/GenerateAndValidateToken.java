package com.eterio.postman.alt.utils;

import com.eterio.postman.alt.model.entity.ProfileEntity;
import com.eterio.postman.alt.repository.ProfileRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenerateAndValidateToken {

    private final ProfileRepository profileRepository;


    private static final String SECRET_KEY = "U2FudGhvc2hLdW1hci1Hb3BhbGFrcmlzaG5hbi1EaGFtb2RoYXJhbi1TbmVrYS1TdWJhc2gtSm90aGVlc2gtWWFkdWtyaXNobmFu";

    public String generateToken(String firstName, String lastName, String email, String profileId) {

        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = currentTimeMillis + 5 * 3600000; // Token valid for 5 hours

        return Jwts.builder()
                .setSubject(email)
                .claim("firstName", firstName)
                .claim("lastName", lastName)
                .claim("profileId", profileId)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(expirationTimeMillis))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims decodeToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {

        try {
            Claims jwtClaims = decodeToken(token);


            if (System.currentTimeMillis() <= jwtClaims.getExpiration().getTime() && Objects.nonNull(jwtClaims.get("profileId"))) {

                Optional<ProfileEntity> entityOptional = profileRepository.findByProfileId(jwtClaims.get("profileId").toString());

                return entityOptional.isPresent();

            }

            return false;
        } catch (Exception ex) {
            throw new RuntimeException("Error through token  :: " + ex.getMessage());
        }

    }
}
