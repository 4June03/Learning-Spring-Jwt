package com.SpringDevteria.demo.JPA.service;

import com.SpringDevteria.demo.JPA.dto.request.AuthenticationRequest;
import com.SpringDevteria.demo.JPA.dto.request.IntrospectRequest;
import com.SpringDevteria.demo.JPA.dto.response.AuthenticationResponse;
import com.SpringDevteria.demo.JPA.dto.response.IntrospectResponse;
import com.SpringDevteria.demo.JPA.entity.User;
import com.SpringDevteria.demo.JPA.exception.AppException;
import com.SpringDevteria.demo.JPA.exception.ErrorCode;
import com.SpringDevteria.demo.JPA.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private UserRepository userRepository;

    @NonFinal //đánh dấu để k inject vào constructor
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;


    public IntrospectResponse introspectAuthenticate(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken(); //Lấy token từ request

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes()); //Truyền vào key mã hóa
        SignedJWT signedJWT = SignedJWT.parse(token); //Lấy token

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime(); //Lấy ra thời gian hết hạn

        var verified =  signedJWT.verify(verifier); //Verify

        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date())) //Trả về kết quả Token đã được xác thực và chưa hết hạn
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        //Lấy ra user
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword()); //kiểm tra pass có match hay không

        if (!authenticated) {
            //Nếu mật khẩu sai thì ném ra Exception
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(user);//Lấy token qua username


        return AuthenticationResponse.builder()
                .token(token)  //Trả về token
                .authenticated(true)
                .build();
    }


    //Method trả về Token
    private String generateToken(User user) { //đổi param thành user để lấy ttin user
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512); //Tham số đầu tiên của JWSObject

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder() //Tạo claimset cho Payload
                .subject(user.getUsername()) //Tên
                .issuer("HuuNghia.com") //xác định token issue từ ai
                .issueTime(new Date()) //Thời điểm issue token
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli())) //Thời gian token hết hạn (1hrs)
                .claim("scope",buildScope(user)) //Scope chứa roles của user
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject()); //cần convert claimset sang JSON Object

        JWSObject jwsObject = new JWSObject(header, payload);//Token

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes())); //cần generate 1 key 32 byte => lên gg generate 1 key random
            return jwsObject.serialize(); //Trả về token theo kiểu String
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user){ //Phương thức để build Roles scope vì role scope trong toke cách nhau 1 space
        StringJoiner stringJoiner = new StringJoiner(" "); //1 space mỗi Role
        //Cập nhật lại Role của User
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role->{
                stringJoiner.add(role.getName()); //Add list role vào scope
                if(!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions()
                            .forEach(permission -> stringJoiner.add(permission.getName())); //Add danh sách permission vào
            });
        }

        return stringJoiner.toString();
    }


}
