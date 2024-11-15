package upe.LMPP.ArtHub.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import upe.LMPP.ArtHub.entities.Usuario;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.token.secret}")
    String oSegredo;

    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(oSegredo);
            String token = JWT.create()
                    .withIssuer("arthub")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(
                            LocalDateTime.now()
                                    .plusHours(5)
                                    .toInstant(
                                            ZoneOffset.of("-03:00")
                                    )
                    )
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Ocorreu um erro ao tentar gerar o token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(oSegredo);
            return JWT.require(algorithm)
                    .withIssuer("arthub")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception) {
            return "Erro ao validar o token";
        }
    }
}
