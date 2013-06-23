/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Guilherme Gehling
 */
@WebServlet(name = "ServletMsg", urlPatterns = {"/ServletMsg"})
public class ServletMsg extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource dataSource = null;
        Connection conn = null;
        System.out.println("Conexão invocada");
        resp.setContentType("text/plain;charset=UTF-8");
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("jdbc/TrabMsg");
            conn = dataSource.getConnection();
            String acao = req.getParameter("acao");
            if ("Logar".equals(acao)) {
                ControleLogin ctrl = new ControleLogin(req, resp, conn);
                ctrl.processo();
            }
            if ("Enviadas".equals(acao)) {
                ControleEnviadas crtl = new ControleEnviadas(req, resp, conn);
                crtl.processo();
            }
            if ("Recebidas".equals(acao)) {
                ControleRecebidas crtl = new ControleRecebidas(req, resp, conn);
                crtl.processo();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ServletMsg.dispatcherErro(req, resp, ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
            }

        }
    }

    public static void dispatcherErro(HttpServletRequest req, HttpServletResponse resp, String msg) throws ServletException, IOException {
        req.setAttribute("mensagem", msg);
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }
}
