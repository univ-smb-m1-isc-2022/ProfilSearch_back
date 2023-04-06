package fr.louisetom.profilsearch.security.oauth2;

import fr.louisetom.profilsearch.exception.OAuth2AuthenticationProcessingException;
import fr.louisetom.profilsearch.model.AuthProvider;
import fr.louisetom.profilsearch.model.Invitation;
import fr.louisetom.profilsearch.model.User;
import fr.louisetom.profilsearch.repository.InvitationRepository;
import fr.louisetom.profilsearch.repository.UserRepository;
import fr.louisetom.profilsearch.security.UserPrincipal;
import fr.louisetom.profilsearch.security.oauth2.user.OAuth2UserInfo;
import fr.louisetom.profilsearch.security.oauth2.user.OAuth2UserInfoFactory;
import fr.louisetom.profilsearch.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        System.out.println(oAuth2UserRequest.getAccessToken().getTokenValue());
        System.out.println(oAuth2UserRequest.getAccessToken().getTokenType());
        System.out.println(oAuth2UserRequest.getAccessToken().getIssuedAt());

        System.out.println("additionalParameters" + oAuth2UserRequest.getAdditionalParameters() );

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        // get url request
        Map<String, Object> params = oAuth2UserRequest.getAdditionalParameters();
        System.out.println("params = " + params);
        String url = (String) params.get("id_token");


        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }



        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            //user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo, null);
            //user = null;

            System.out.println("invitationRepository.findByEmail(oAuth2UserInfo.getEmail()) = " + invitationRepository.findByEmail(oAuth2UserInfo.getEmail()));
            // service donc appel du repository

            Optional<Invitation> invitation = invitationRepository.findByEmail(oAuth2UserInfo.getEmail());
            
            if (invitation.isPresent()) {
                user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo, null);
            } else {
                throw new OAuth2AuthenticationProcessingException("User not admited");
            }
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo, String invitationLink) {
        User user = new User();

        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

}
