package ltbank;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ltbank.control.managedBeans.PersonageBean;
import ltbank.control.managedBeans.TransactionBean;
import ltbank.model.personage.PersonageDTO;
import ltbank.model.transaction.Transaction;

@WebServlet("/Main")
public class Main extends HttpServlet {
	@Inject
	PersonageBean bean;
	@Inject
	TransactionBean tBean;
	private static final long serialVersionUID = 1L;
    public Main() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		bean.sendPersona(new Personage("Patolino", "123456789101112131415", ZonedDateTime.now(),
//				ZonedDateTime.now(), new BigDecimal(0)));
//		bean.sendPersona(new Personage("Pernalonga", "123456789101112131415", ZonedDateTime.now(),
//				ZonedDateTime.now(), new BigDecimal(0)));
//		bean.sendPersona(new Personage("Papaleguas", "123456789101112131415", ZonedDateTime.now(),
//				ZonedDateTime.now(), new BigDecimal(0)));
//		bean.sendPersona(new Personage("Marciono", "123456789101112131415", ZonedDateTime.now(),
//				ZonedDateTime.now(), new BigDecimal(0)));
//		bean.sendPersona(new Personage("Gaguinho", "123456789101112131415", ZonedDateTime.now(),
//				ZonedDateTime.now(), new BigDecimal(0)));
//		tBean.addMoney(new PersonageDTO(1, "Patolino"), new BigDecimal(500));
//		tBean.sendMoney(new PersonageDTO(1, "Patolino"), 5, new BigDecimal(500));
//		tBean.sendMoney(new PersonageDTO(5, "Gaguinho"), 1, new BigDecimal(500));
//		tBean.sendMoney(new PersonageDTO(1, "Patolino"), 5, new BigDecimal(250));
		//tBean.sendMoney(new PersonageDTO(5, "Gaguinho"), 3, new BigDecimal(50));
		List<Transaction> list = tBean.getListTransaction(new PersonageDTO(5, "Gaguinho"));
		for (Transaction trans : list) {
			System.out.println(trans.getReceiver().getName() + " - " + trans.getReceiver().getName() + " - " + trans.getSender().getName());
		}
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		bean.deleteMySelf(1, "123456789101112131415");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		bean.updateName("Luna", 2, ZonedDateTime.now(), "123456789101112131415");
	}
}
