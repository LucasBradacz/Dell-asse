package com.dellasse.backend.infrastructure;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Filtro de autenticação JWT que extrai o token de um cookie, se presente.
 * - Se o cabeçalho Authorization não estiver presente, verifica os cookies para um cookie chamado "token".
 * - Se o cookie for encontrado, adiciona o token ao cabeçalho Authorization da requisição.
 */
public class JwtCookieAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Entrou no JwtCookieAuthenticationFilter");

        String authHeader = request.getHeader("Authorization");
        HttpServletRequest requestWrapper = request;
        System.out.println("Header Authorization enviado: " + requestWrapper.getHeader("Authorization"));

        if (authHeader == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        String token = cookie.getValue();
                        System.out.println("Token do cookie: " + token);

                        requestWrapper = new HttpServletRequestWrapper(request) {
                            @Override
                            public String getHeader(String name) {
                                if ("Authorization".equals(name)) {
                                    return "Bearer " + token;
                                }
                                return super.getHeader(name);
                            }
                        };
                        break;
                    }
                }
            }
        } else {
            System.out.println("Token já presente no header Authorization: " + authHeader);
        }

        filterChain.doFilter(requestWrapper, response);
    }
}
