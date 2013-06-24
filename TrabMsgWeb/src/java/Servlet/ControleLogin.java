/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import bean.Usuario;
import dao.UsuarioDAO;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guilherme Gehling
 */
public class ControleLogin {

    private HttpServletRequest req;
    private HttpServletResponse resp;
    private Connection conn;

    public ControleLogin(HttpServletRequest req, HttpServletResponse resp, Connection conn) {
        this.req = req;
        this.resp = resp;
        this.conn = conn;
    }

    public void processo() throws ServletException, IOException, SQLException {
        String login = req.getParameter("login");
        String senha = req.getParameter("senha");
        UsuarioDAO usrDao = new UsuarioDAO(conn);
        Usuario usuario = usrDao.achaLogin(login);
        if (usuario == null || !usuario.validaSenha(senha)) {
            ServletMsg.dispatcherErro(req, resp, String.format("Usuário ou Senha Inválidos.[%s]", login));
//            return;
        } else {
            OutputStream out = resp.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeUTF("Ok");
            dos.close();
        }
    }
}
