package com.concrete.prj.login.pl.controller;

import com.concrete.prj.login.bl.service.CSiUserFacade;
import com.concrete.prj.login.exception.response.CSxRequestPageNotFoundException;
import com.concrete.prj.login.to.signup.CScLoginRequestTO;
import com.concrete.prj.login.to.signup.CScSignUpRequestTO;
import com.concrete.prj.login.to.signup.CScSignUpResponseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

/**
 * Description: API Authentication and Authorization controller
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@Controller
public class CScAuthController {

    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("myUserDetailsService")
    CSiUserFacade userService;

    /**
     * Entry point for signing up new users to the Concrete Challenge
     *
     * @param aSignUpRequestTO request body with a new user to be signed up to the system
     * @return response entity object
     */
    @PostMapping("/signup")
    public ResponseEntity<CScSignUpResponseTO> registerUser(@Valid @RequestBody CScSignUpRequestTO aSignUpRequestTO) {

        CScSignUpResponseTO signUpResponseTO = userService.registerUser(aSignUpRequestTO);
        return new ResponseEntity<>(signUpResponseTO, HttpStatus.OK);
    }

    /**
     * "Logins" to the system
     *
     * @param aLoginRequestTO user email and password to be logged in to the system
     * @return response entity object
     */
    @PostMapping("/login")
    public ResponseEntity<CScSignUpResponseTO> login(@Valid @RequestBody CScLoginRequestTO aLoginRequestTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        aLoginRequestTO.getEmail(),
                        aLoginRequestTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CScSignUpResponseTO signUpResponseTO = userService.registerLogin(aLoginRequestTO);

        return new ResponseEntity<>(signUpResponseTO, HttpStatus.OK);
    }

    /**
     * Entry point for a single user information retrieval
     *
     * @param aUserId             user id to be found
     * @param aHttpServletRequest http aHttpServletRequest
     * @return esponse entity object with the user's information regarding all the authentication process
     */
    @GetMapping("/users/{userid}")
    public ResponseEntity<CScSignUpResponseTO> getUserProfile(@PathVariable(value = "userid") long aUserId, HttpServletRequest aHttpServletRequest) {

        CScSignUpResponseTO signUpResponseTO = userService.getUserProfile(aUserId);
        if (signUpResponseTO == null) {
            throw new CSxRequestPageNotFoundException(String.format("User not found with the id: [%d].", aUserId));
        }

        return new ResponseEntity<CScSignUpResponseTO>(signUpResponseTO, HttpStatus.OK);
    }

    /**
     * That's not being used. Only for tests of myself.
     *
     * @param model     model map
     * @param principal user principal
     * @return nothing
     */
    @GetMapping("/users/{username}")
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<CScSignUpResponseTO> userProfile(ModelMap model, Principal principal) {
        model.addAttribute("greeting", principal.getName());
        //return new ModelAndView("hello");
        return null;
    }

    /**
     * Handles the 404 occurrences of the system.
     *
     * @param aModelMap aModelMap map object
     */
    @GetMapping("/404")
    public void getting404(ModelMap aModelMap) {
        throw new CSxRequestPageNotFoundException("Pagina nao encontrada! :(");
    }
}