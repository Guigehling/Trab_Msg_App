/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import bean.Mensagens;
import bean.Usuario;
import dao.MensagensDAO;
import dao.UsuarioDAO;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guilherme Gehling
 */
public class ControleRecebidas {

    private HttpServletRequest req;
    private HttpServletResponse resp;
    private Connection conn;

    public ControleRecebidas(HttpServletRequest req, HttpServletResponse resp, Connection conn) {
        this.req = req;
        this.resp = resp;
        this.conn = conn;
    }

    public void processo() throws ServletException, IOException, SQLException {
        String opc = req.getParameter("opc");

        if ("Listar".equals(opc)) {
            this.Listar();
        }
    }

    public void Listar() throws ServletException, IOException, SQLException {
        String login = req.getParameter("login");
        UsuarioDAO usrDao = new UsuarioDAO(conn);
        Usuario usuario = usrDao.achaLogin(login);
        MensagensDAO msgDAO = new MensagensDAO(conn);
        List<Mensagens> lista = msgDAO.listaRecebidas(usuario);
        OutputStream out = resp.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);
        for (int i = 0; i < lista.size(); i++) {
            dos.writeUTF(lista.get(i).toString());
        }
        dos.writeUTF("FIM");
        dos.close();
    }
}
