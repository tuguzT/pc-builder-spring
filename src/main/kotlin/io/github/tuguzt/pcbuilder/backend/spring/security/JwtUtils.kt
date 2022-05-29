package io.github.tuguzt.pcbuilder.backend.spring.security

import io.github.tuguzt.pcbuilder.backend.spring.ApplicationConfiguration
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import kotlin.time.Duration.Companion.days

private inline fun <reified T> Claims.getTyped(claimName: String): T = get(claimName, T::class.java)

@Service
class JwtUtils(private val applicationConfiguration: ApplicationConfiguration) {
    private val secretKey get() = applicationConfiguration.jwt.secretKey

    fun extractUsername(token: String): String = extractClaim(token, Claims::getSubject)

    fun extractExpiration(token: String): Date = extractClaim(token, Claims::getExpiration)

    fun extractRole(token: String): UserRole = extractClaim(token) {
        UserRole.valueOf(getTyped(User::role.name))
    }

    fun <T> extractClaim(token: String, claimsResolver: Claims.() -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims = mutableMapOf(User::role.name to userDetails.authorities.first().authority)
        return createToken(claims, userDetails.username)
    }

    fun validateToken(token: String, userDetails: UserDetails) =
        extractUsername(token) == userDetails.username && !isTokenExpired(token)

    private fun createToken(claims: MutableMap<String, out Any>, subject: String): String = Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(Date())
        .setExpiration(Date(System.currentTimeMillis() + 1.days.inWholeMilliseconds))
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact()

    private fun extractAllClaims(token: String): Claims =
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body

    private fun isTokenExpired(token: String) = extractExpiration(token).before(Date())
}
