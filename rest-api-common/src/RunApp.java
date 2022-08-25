import bo.UserBO;

public class RunApp {
	public static void main(String[] args) {
		UserBO.getInstance().getUsers();
//		System.out.println("res ==>"+UserBO.getInstance().getUsers().get(0).toString());
	}
}
