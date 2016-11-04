package pl.pacy.memorize.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by pacy on 2016-10-26.
 */
@RestController
public class UserController {
	@RequestMapping("/api/user")
	public Principal user(Principal user) {
		return user;
	}

	// TODO
//	@RequestMapping("/api/logout")
//	public Principal logout(Principal user) {
//		return null;
//	}

}
