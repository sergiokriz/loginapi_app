package com.concrete.prj.login.bl.service;

import com.concrete.prj.login.bl.dao.CSiUserDao;
import com.concrete.prj.login.bl.model.CScUserEO;
import com.concrete.prj.login.bl.model.CScUserPhoneEO;
import com.concrete.prj.login.bl.model.CScUserRoleEO;
import com.concrete.prj.login.bl.model.CSnUserRole;
import com.concrete.prj.login.core.to.CScSignUpTOHelper;
import com.concrete.prj.login.exception.response.CSxConflictException;
import com.concrete.prj.login.exception.response.CSxRequestPageNotFoundException;
import com.concrete.prj.login.security.CScSpringUserPrincipalRepresentation;
import com.concrete.prj.login.security.CSiJwtTokenResolver;
import com.concrete.prj.login.to.signup.CScLoginRequestTO;
import com.concrete.prj.login.to.signup.CScPhoneTO;
import com.concrete.prj.login.to.signup.CScSignUpRequestTO;
import com.concrete.prj.login.to.signup.CScSignUpResponseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * Description: User facade
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@Service
public class CScUserFacade implements CSiUserFacade {

    @Autowired
    CSiUserDao userDao;

    @Autowired
    CSiJwtTokenResolver jwtTokenResolver;

    @Override
    @Transactional(readOnly = true)
    public CScUserEO findUserById(Long aId) {
        return userDao.findUserById(aId);
    }

    @Override
    @Transactional(readOnly = true)
    public CScUserEO findUserByName(String aName) {
        return userDao.findUserByName(aName);
    }

    @Override
    @Transactional(readOnly = true)
    public CScUserEO findUserByEmail(String aEmail) {
        return userDao.findUserByEmail(aEmail);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public CScSignUpResponseTO registerUser(CScSignUpRequestTO aSignUpRequestTO) {

        CScUserEO newUser = userDao.findUserByEmail(aSignUpRequestTO.getEmail());

        if (newUser != null) {
            throw new CSxConflictException("Email ja cadastrado!");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(aSignUpRequestTO.getPassword());

        newUser = new CScUserEO();
        newUser.setUserName(aSignUpRequestTO.getName());
        newUser.setEmail(aSignUpRequestTO.getEmail());
        newUser.setPassword(encryptedPassword);
        userDao.saveUser(newUser);

        String jwt = jwtTokenResolver.generateToken(newUser.getUserId());
        newUser.setJwt(jwt);

        for (CScPhoneTO phoneTO : aSignUpRequestTO.getPhones()) {
            CScUserPhoneEO newPhone = new CScUserPhoneEO();
            newPhone.setPhoneDDD(phoneTO.getDdd());
            newPhone.setPhoneNumber(phoneTO.getNumber());
            newPhone.setUser(newUser);
            userDao.savePhone(newPhone);
        }

        CScUserRoleEO newUserRole = new CScUserRoleEO();
        newUserRole.setUserRole(CSnUserRole.ROLE_USER);
        newUserRole.setUser(newUser);
        userDao.saveUserRole(newUserRole);

        Date currentDate = new Date();
        newUser.setCreatedAt(currentDate);
        newUser.setModifiedAt(currentDate);
        newUser.setLastLoginAt(currentDate);

        CScSignUpResponseTO signUpResponseTO = CScSignUpTOHelper.fillSignUpResponseTO(new CScSignUpResponseTO(),
                newUser);

        return signUpResponseTO;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public CScSignUpResponseTO registerLogin(CScLoginRequestTO aLoginRequestTO) {

        Optional<CScUserEO> user = Optional.ofNullable(userDao.findUserByEmail(aLoginRequestTO.getEmail()));

        user.filter(e -> {
            BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
            return bCrypt.matches(aLoginRequestTO.getPassword(), e.getPassword());
        }).orElseThrow(() -> new IllegalStateException("Usuário e/ou senha inválidos."));

        Date currentDate = new Date();
        user.get().setLastLoginAt(currentDate);

        CScSignUpResponseTO signUpResponseTO = CScSignUpTOHelper.fillSignUpResponseTO(new CScSignUpResponseTO(),
                user.get());

        return signUpResponseTO;
    }

    @Override
    @Transactional(readOnly = true)
    public CScSignUpResponseTO getUserProfile(Long aUserId) {

        CScUserEO user = userDao.findUserById(aUserId);

        if (user == null) {
            throw new IllegalArgumentException("test");
        }

        CScSignUpResponseTO signUpResponseTO = CScSignUpTOHelper.fillSignUpResponseTO(new CScSignUpResponseTO(),
                user);

        return signUpResponseTO;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long aUserId) {

        CScUserEO userEO = findUserById(aUserId);

        if (userEO == null) {
            throw new CSxRequestPageNotFoundException(String.format("User not found with the id: [%d].", aUserId));
        }

        CScSpringUserPrincipalRepresentation userPrincipal = CScSpringUserPrincipalRepresentation.resolveUserPrincipalFromEO(userEO);
        return userPrincipal;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CScUserEO userEO = findUserByEmail(username);
        if (userEO == null) {
            throw new UsernameNotFoundException("User not found with username or email : " + username);
        }

        CScSpringUserPrincipalRepresentation userPrincipal = CScSpringUserPrincipalRepresentation.resolveUserPrincipalFromEO(userEO);
        return userPrincipal;
    }
}
