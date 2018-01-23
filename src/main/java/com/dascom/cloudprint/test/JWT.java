package com.dascom.cloudprint.test;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import com.dascom.cloudprint.entity.device.CollectionPrinters;

import java.util.HashMap;
import java.util.Map;

public class JWT {
    private static final String SECRET = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545fdf>?N<:{LWPW";

    private static final String EXP = "exp";

    private static final String PAYLOAD = "payload";

    //加密，传入一个对象和有效期
    public static <T> String sign(T object, long maxAge) {
        try {
            final JWTSigner signer = new JWTSigner(SECRET);
            final Map<String, Object> claims = new HashMap<String, Object>();
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(object);
            claims.put(PAYLOAD, jsonString);
            claims.put(EXP, System.currentTimeMillis() + maxAge);
            return signer.sign(claims);
        } catch(Exception e) {
            return null;
        }
    }

    //解密，传入一个加密后的token字符串和解密后的类型
    public static<T> T unsign(String jwt, Class<T> classT) {
        final JWTVerifier verifier = new JWTVerifier(SECRET);
        try {
            final Map<String,Object> claims= verifier.verify(jwt);
            if (claims.containsKey(EXP) && claims.containsKey(PAYLOAD)) {
                long exp = (Long)claims.get(EXP);
                long currentTimeMillis = System.currentTimeMillis();
                if (exp > currentTimeMillis) {
                    String json = (String)claims.get(PAYLOAD);
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(json, classT);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
		a p=new a();
		p.setId("1");
		//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MTYzMjczMjk4OTAsInBheWxvYWQiOiJ7XCJfaWRcIjpcIjFcIixcImFsaXZlXCI6ZmFsc2UsXCJjbG91ZF9wcnRcIjpmYWxzZSxcInVzaW5nXCI6bnVsbCxcIm93bmVyXCI6bnVsbCxcInN0YXR1c1wiOm51bGwsXCJudW1iZXJcIjpudWxsLFwicmVnX2RhdGVcIjpudWxsLFwibG9naW5fZGF0ZVwiOm51bGwsXCJhbGVydFwiOm51bGwsXCJhbGlhc1wiOm51bGwsXCJpbmZvXCI6bnVsbCxcInN0YXRpc3RpY3NcIjpudWxsfSJ9.aN14QwwGe8Qrf6pLfauMnOE1nxoSYPx1Gahna8Z-PcY
		//String s=sign(p,1000);
		String a=sign(p,60L* 1000L);
		System.out.println(a);
		String s="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MTYzMjYxOTczNDksInBheWxvYWQiOiJ7XCJfaWRcIjpcIjFcIixcImFsaXZlXCI6ZmFsc2UsXCJjbG91ZF9wcnRcIjpmYWxzZSxcInVzaW5nXCI6bnVsbCxcIm93bmVyXCI6bnVsbCxcInN0YXR1c1wiOm51bGwsXCJudW1iZXJcIjpudWxsLFwicmVnX2RhdGVcIjpudWxsLFwibG9naW5fZGF0ZVwiOm51bGwsXCJhbGVydFwiOm51bGwsXCJhbGlhc1wiOm51bGwsXCJpbmZvXCI6bnVsbCxcInN0YXRpc3RpY3NcIjpudWxsfSJ9.MdrmV29WqOxedjKwV0956tGIP0F6E8jBfGbo46IIiZs";
		//System.out.println(s);
		System.out.println(unsign(s,CollectionPrinters.class));
		
	}
   
}
class a{
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
