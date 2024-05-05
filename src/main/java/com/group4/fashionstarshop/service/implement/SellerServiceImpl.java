package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.configuration.security.JwtTokenUtil;
import com.group4.fashionstarshop.enums.*;
import com.group4.fashionstarshop.exception.RedirectException;
import com.group4.fashionstarshop.dto.SellerDTO;
import com.group4.fashionstarshop.dto.SellerLoginDTO;
import com.group4.fashionstarshop.dto.SellerRegisterDTO;
import com.group4.fashionstarshop.dto.SellerResetDTO;
import com.group4.fashionstarshop.dto.UserResetDTO;
import com.group4.fashionstarshop.model.Address;
import com.group4.fashionstarshop.model.PasswordResetToken;
import com.group4.fashionstarshop.model.ResetSellerToken;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.model.VerificationToken;
import com.group4.fashionstarshop.model.VerifiyTokenSeller;
import com.group4.fashionstarshop.repository.AddressRepository;
import com.group4.fashionstarshop.repository.ResetSellerTokenRepository;
import com.group4.fashionstarshop.repository.SellerRepository;
import com.group4.fashionstarshop.repository.SellerVerifyRepository;
import com.group4.fashionstarshop.repository.StoreRepository;
import com.group4.fashionstarshop.request.UpdateSellerRequest;
import com.group4.fashionstarshop.configuration.security.JwtUserDetailsService;
import com.group4.fashionstarshop.converter.AddressConverter;
import com.group4.fashionstarshop.converter.SellerConverter;
import com.group4.fashionstarshop.service.ResetSellerTokenService;
import com.group4.fashionstarshop.service.SellerService;

import jakarta.servlet.http.HttpServletResponse;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerServiceImpl implements SellerService {
	@Autowired
	private ResetSellerTokenService resetSellerTokenService;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ResetSellerTokenRepository resetSellerTokenRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private SellerVerifyRepository tokeRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private SellerConverter sellerConverter;
	@Autowired
	private AddressConverter addressConverter;
	

	@Override
	public String login(SellerLoginDTO sellerLoginDto) {
		Seller seller = sellerRepository.findByEmail(sellerLoginDto.getEmail());

		if (seller == null) {
			throw new UsernameNotFoundException("User not found");
		}
		if (!passwordEncoder.matches(sellerLoginDto.getPassword(), seller.getPassword())) {
			throw new AuthenticationServiceException("Wrong password");
		}
		return jwtTokenUtil.generateSellerToken(seller);
	}


    @Override
    @Transactional
    public Seller register(SellerRegisterDTO sellerRegisterDto) {
        // Check if the email is already registered
        if (jwtUserDetailsService.loadSellerByEmail(sellerRegisterDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already registered");
        }
		String password = sellerRegisterDto.getPassword();
		String confirmPassword = sellerRegisterDto.getConfirmPassword();

		// Check if password and confirm password match
		if (!password.equals(confirmPassword)) {
			throw new IllegalArgumentException("Password and confirm password do not match");
		}
		Date birthDay;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			birthDay = sdf.parse(sellerRegisterDto.getBirthDay());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Invalid birthdate format");
		}

		// Create Seller entity
		Seller seller = new Seller();
		seller.setSellerName(sellerRegisterDto.getSellerName());
		seller.setEmail(sellerRegisterDto.getEmail());
		seller.setPhone(sellerRegisterDto.getPhone());
		seller.setPassword(passwordEncoder.encode(password));
		seller.setConfirmPassword(passwordEncoder.encode(password));
		seller.setCreatedAt(LocalDateTime.now());
		seller.setUpdatedAt(LocalDateTime.now());
		seller.setBalance((double) 100000);
		seller.setBirthDay(birthDay);
		seller.setRole("ROLE_".concat(Role.SELLER.toString()));

		// Save Seller entity
		seller = sellerRepository.save(seller);

		// Create Store entity
		Store store = new Store();
		store.setName(sellerRegisterDto.getSellerName()); // Set store name same as seller name
		store.setSeller(seller); // Set the seller for the store

		// Save Store entity
		storeRepository.save(store);

		return seller;
	}


    @Override
    public Seller findById(Long id) {
        Seller seller = sellerRepository.findById(id).orElse(null);
        return seller;
    }

	@Override
	public Seller findUserByJwt(String jwt) {
		String email = jwtTokenUtil.getEmailFromJwtToken(jwt);
		Seller seller = sellerRepository.findByEmail(email);
		
		return seller;
	}

	@Override
	public void changePassword(Seller seller, String newPassword) {
		seller.setPassword(passwordEncoder.encode(newPassword));
		sellerRepository.save(seller);
		
	}
    public Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
	@Override
	public String validatePasswordResetToken(String token) {
		// TODO Auto-generated method stub
		return resetSellerTokenService.validatePasswordResetToken(token);
	}

	@Override
	public void createPasswordResetTokenForSeller(Seller seller) {
		Date exDate = calculateExpiryDate(60*24);
		String resetToken = UUID.randomUUID().toString();
		ResetSellerToken passwordResetToken = new ResetSellerToken();
		passwordResetToken.setSeller(seller);
		passwordResetToken.setToken(resetToken);
		passwordResetToken.setExpiryDate(exDate);
		resetSellerTokenRepository.save(passwordResetToken);
		
	}
    @Override
    public void createVerificationToken(Seller seller ) {
        Date expireDate = calculateExpiryDate(60*24);
        String token = UUID.randomUUID().toString();
        VerifiyTokenSeller sellerToken = new VerifiyTokenSeller();
        sellerToken.setSeller(seller);
        sellerToken.setToken(token);
        sellerToken.setExpiryDate(expireDate);
        tokeRepository.save(sellerToken);
    }
    
    @Override
    public boolean checkExpiryDate(VerifiyTokenSeller verificationToken){
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void verifyToken(VerifiyTokenSeller verificationToken, HttpServletResponse response) throws Exception{

        if(verificationToken != null ){
            boolean isExpired = checkExpiryDate(verificationToken);
            if(!isExpired){
                Seller seller = verificationToken.getSeller();
                seller.setEnabled(true);
                sellerRepository.save(seller);
            } else {
                throw new RedirectException("Expired token", "http://localhost:3000/confirm?status=error");
            }
        } else {
            throw new RedirectException("Invalid token", "http://localhost:3000/confirm?status=error");
        }
    }
	@Override
	public boolean oldPasswordIsValid(Seller seller, String oldPassword) {
		// TODO Auto-generated method stub
		return passwordEncoder.matches(oldPassword, seller.getPassword());
	}

	@Override
	public Seller findByEmail(SellerResetDTO userResetDTO) {
		// TODO Auto-generated method stub
		return sellerRepository.findByEmail(userResetDTO.getEmail());
	}

	@Override
	public Optional<Seller> findSellerByPasswordToken(String token) {
		// TODO Auto-generated method stub
		return resetSellerTokenService.findSellerrByPasswordToken(token);
	}
		

	@Override
	public SellerDTO findBySellerId(Long id) {
		Seller seller = sellerRepository.findById(id).orElse(null);
		return sellerConverter.entityToDTO(seller);
	}

	@Override
	public SellerDTO updateSeller(Long sellerId, UpdateSellerRequest request) {
		Seller seller = sellerRepository.findById(sellerId).orElse(null);
		if (seller != null) {
			if (request.getSellerName() != null) {
				seller.setSellerName(request.getSellerName());
			}
			if (request.getPhone() != null) {
				seller.setPhone(request.getPhone());
			}
			if (request.getBirthDay() != null) {
				// Assuming request.getBirthDay() returns a Date object
				seller.setBirthDay(request.getBirthDay());
			}
			seller = sellerRepository.save(seller);
		}
		return sellerConverter.entityToDTO(seller);
	}

	@Override
	public SellerDTO findSeller(Long sellerId) {
		Seller seller = sellerRepository.findById(sellerId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		// Retrieve addresses for the seller using AddressRepository
		List<Address> addresses = addressRepository.findBySeller(seller);		
		seller.setAddresses(addresses);		
		SellerDTO sellerDTO = sellerConverter.convertEntityToDTO(seller);
		sellerDTO.setAddressDTOs(addressConverter.entitiesToDTOs(addresses));
		return sellerDTO;
	}


}
