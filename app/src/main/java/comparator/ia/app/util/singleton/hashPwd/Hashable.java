package comparator.ia.app.util.singleton.hashPwd;

/**
 * 
 * All interface extends with Hashable would imply that class it will can encrypt.
 * 
 */
public interface Hashable {
	String createHash(String pwd);
}
