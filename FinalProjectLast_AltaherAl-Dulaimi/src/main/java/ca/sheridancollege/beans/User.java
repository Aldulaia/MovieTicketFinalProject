package ca.sheridancollege.beans;

import java.io.Serializable;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4820059646599452091L;
	// must match the field of the SQL table
	private Long userId;
	private String userName;
	private String encryptedPassword;

}
