package ar.edu.ungs.fleet_manager.users.infrastructure.filters;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.UnauthorizedException;
import ar.edu.ungs.fleet_manager.users.domain.*;
import ar.edu.ungs.fleet_manager.users.domain.Module;
import ar.edu.ungs.fleet_manager.users.domain.services.PermissionsFinder;
import ar.edu.ungs.fleet_manager.users.domain.services.UserFinder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component
public class AuthorizationsFilter implements Filter {
    private final PermissionsFinder permissionsFinder;

    public AuthorizationsFilter(PermissionsFinder permissionsFinder) {
        this.permissionsFinder = permissionsFinder;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (!req.getRequestURI().contains("/health-check")) {
            UserId userId = new UserId(req.getParameter("caller_id"));;
            Module module = Module.parse(req.getRequestURI().split("/")[3]);
            Operation operation = Operation.parse(req.getMethod());

            Permissions permissions = permissionsFinder.execute(userId);

            if (!permissions.contains(module, operation)) {
                throw new UnauthorizedException("the user not authorized for this operation");
            }
        }

        chain.doFilter(request, response);
    }
}
