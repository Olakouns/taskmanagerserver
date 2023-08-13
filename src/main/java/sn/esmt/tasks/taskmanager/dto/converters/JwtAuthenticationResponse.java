package sn.esmt.tasks.taskmanager.dto.converters;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@JsonAutoDetect
public class JwtAuthenticationResponse {

	private String token;
	private String accessToken;
    private String tokenType = "Bearer";
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date expiryToken;
	private String tokenPermission;

    public JwtAuthenticationResponse(String token, String accessToken, Date expiryToken, String tokenPermission) {
		super();
		this.token = token;
		this.accessToken = accessToken;
		this.expiryToken = expiryToken;
		this.tokenPermission = tokenPermission;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

	/**
	 * @return the expiryToken
	 */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	public Date getExpiryToken() {
		return expiryToken;
	}

	/**
	 * @param expiryToken the expiryToken to set
	 */
	public void setExpiryToken(Date expiryToken) {
		this.expiryToken = expiryToken;
	}

	public String getTokenPermission() {
		return tokenPermission;
	}

	public void setTokenPermission(String tokenPermission) {
		this.tokenPermission = tokenPermission;
	}

	@Override
	public String toString() {
		return "JwtAuthenticationResponse{" +
				"token='" + token + '\'' +
				", accessToken='" + accessToken + '\'' +
				", tokenType='" + tokenType + '\'' +
				", expiryToken=" + expiryToken +
				'}';
	}
}
