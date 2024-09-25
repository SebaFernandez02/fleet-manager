package ar.edu.ungs.fleet_manager.users.infrastructure.filters;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.UnauthorizedException;
import ar.edu.ungs.fleet_manager.users.domain.*;
import ar.edu.ungs.fleet_manager.users.domain.Module;
import ar.edu.ungs.fleet_manager.users.domain.services.PermissionsFinder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class AuthorizationsFilter implements Filter {
    private final PermissionsFinder permissionsFinder;
    private final ObjectMapper mapper;

    public AuthorizationsFilter(PermissionsFinder permissionsFinder, ObjectMapper mapper) {
        this.permissionsFinder = permissionsFinder;
        this.mapper = mapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        try {
            if (!req.getRequestURI().contains("/health-check") && !req.getRequestURI().contains("/auths")) {
                String callerId = req.getHeader("X-Caller-Id");

                if (callerId == null || callerId.isBlank()) {
                    throw new UnauthorizedException("caller id not found");
                }

                UserId userId = new UserId(callerId);
                Module module = Module.parse(req.getRequestURI().split("/")[2]);
                Operation operation = Operation.parse(req.getMethod());

                Permissions permissions = permissionsFinder.execute(userId);

                if (!permissions.contains(module, operation)) {
                    throw new UnauthorizedException("the user is not authorized for this operation");
                }
            }

            chain.doFilter(request, response);
        } catch (UnauthorizedException | NotFoundException e) {

            String responseBody = mapper.writeValueAsString(Map.of("error", e.code(), "message", e.getMessage()));

            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json");
            res.getWriter().write(responseBody);
        }
    }
}
