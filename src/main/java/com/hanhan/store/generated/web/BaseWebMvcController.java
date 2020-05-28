/**
 * 
 */
package com.hanhan.store.generated.web;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.hanhan.store.generated.security.IdUser;
import com.hanhan.store.generated.util.SyntacticSugarMap;

/**
 * @author JerryXia
 *
 */
public class BaseWebMvcController {

    protected ModelAndView getMvNotFound(String msg) {
        ModelAndView source = new ModelAndView("error");
        source.setStatus(HttpStatus.NOT_FOUND);
        // source.addObject("code", 0);
        // source.addObject("msg", msg);
        source.addObject("exception", msg);
        return source;
    }

    protected ModelAndView getMvOk(String viewName) {
        ModelAndView source = new ModelAndView(viewName);
        // source.addObject("code", 1);
        // source.addObject("msg", msg);
        return source;
    }

    protected ModelAndView getMvRedirect(String jumpurl, String msg) {
        ModelAndView mv = new ModelAndView("redirect");
        mv.addObject("jumpurl", jumpurl);
        mv.addObject("msg", msg);
        return mv;
    }

    protected HashMap<String, Object> tkd(ModelAndView mv, String title, String keywords, String description) {
        HashMap<String, Object> viewData = SyntacticSugarMap.init().instance();
        mv.addObject("viewData", viewData);
        viewData.put("title", title);
        viewData.put("keywords", keywords);
        viewData.put("description", description);
        return viewData;
    }

    protected IdUser authencationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return IdUser.ANONYMOUS_USER;
        } else if (authentication instanceof RememberMeAuthenticationToken) {
            IdUser currentUser = (IdUser) authentication.getPrincipal();
            return currentUser;
        } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
            IdUser currentUser = (IdUser) authentication.getPrincipal();
            return currentUser;
        } else {
            // ignore
            throw new IllegalStateException("unknown authentication type.");
        }
    }

}
