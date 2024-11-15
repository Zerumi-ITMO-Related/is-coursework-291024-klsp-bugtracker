package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey


@Service
class JWTService {
    val secretKey: SecretKey = Jwts.SIG.HS256.key().build()

    fun generateToken(user: User): String {
        val claims = mapOf(
            "id" to user.id,
        )

        return generateToken(claims, user)
    }

    fun generateToken(claims: Map<String, Any?>, userDetails: User): String {
        return Jwts.builder().claims(claims).subject(userDetails.login)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 100000 * 60 * 24))
            .signWith(secretKey).compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val userName: String = extractUserName(token)
        return (userName == userDetails.username) && !isTokenExpired(token)
    }

    fun extractUserName(token: String): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    private fun extractAllClaims(token: String): Claims =
        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    private fun <T> extractClaim(token: String, claimsResolvers: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolvers(claims)
    }
}