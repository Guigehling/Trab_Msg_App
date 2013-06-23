/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Mensagens;
import bean.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guilherme Gehling
 */
public class MensagensDAO {

    private Connection conexao;

    public MensagensDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void salvar(Mensagens msg) throws SQLException {
        if (this.valida(msg)) {
            String sql = "INSERT INTO MENSAGENS (COD_MSG,CONTEUDO,COD_EMITENTE,COD_DESTINATARIO) VALUES (NEXTVAL('seqmensagens'), ?, ?, ?)";
            PreparedStatement pst = this.conexao.prepareStatement(sql);
            pst.setString(1, msg.getConteudo());
            pst.setInt(2, msg.getEmitente().getCod_user());
            pst.setInt(3, msg.getDestinatario().getCod_user());
            pst.executeUpdate();
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT CURRVAL('seqmensagens')");
            if (rs.next()) {
                msg.setCod_msg(rs.getInt(1));
            }
            rs.close();
            st.close();
            pst.close();
        }
    }

    public List<Mensagens> listaRecebidas(Usuario user) throws SQLException {
        List<Mensagens> lista = new ArrayList<Mensagens>();
        String sql = "SELECT COD_MSG,CONTEUDO,COD_EMITENTE,COD_DESTINATARIO FROM MENSAGENS WHERE COD_DESTINATARIO=?";
        PreparedStatement pst = this.conexao.prepareStatement(sql);
        pst.setInt(1, user.getCod_user());
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Mensagens msg = new Mensagens();
            msg.setCod_msg(rs.getInt("COD_MSG"));
            msg.setConteudo(rs.getString("CONTEUDO"));
            UsuarioDAO userDAO = new UsuarioDAO(conexao);
            Usuario usuario = new Usuario();
            usuario.setCod_user(rs.getInt("COD_EMITENTE"));
            usuario = userDAO.retrieve(usuario);
            msg.setEmitente(usuario);
            usuario.setCod_user(rs.getInt("COD_DESTINATARIO"));
            usuario = userDAO.retrieve(usuario);
            msg.setDestinatario(usuario);
            lista.add(msg);
        }
        rs.close();
        pst.close();
        return lista;
    }

    public List<Mensagens> listaEnviadas(Usuario user) throws SQLException {
        List<Mensagens> lista = new ArrayList<Mensagens>();
        String sql = "SELECT COD_MSG,CONTEUDO,COD_EMITENTE,COD_DESTINATARIO FROM MENSAGENS WHERE COD_EMITENTE=?";
        PreparedStatement pst = this.conexao.prepareStatement(sql);
        pst.setInt(1, user.getCod_user());
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Mensagens msg = new Mensagens();
            msg.setCod_msg(rs.getInt("COD_MSG"));
            msg.setConteudo(rs.getString("CONTEUDO"));
            UsuarioDAO userDAO = new UsuarioDAO(conexao);
            Usuario usuario = new Usuario();
            usuario.setCod_user(rs.getInt("COD_EMITENTE"));
            usuario = userDAO.retrieve(usuario);
            msg.setEmitente(usuario);
            usuario.setCod_user(rs.getInt("COD_DESTINATARIO"));
            usuario = userDAO.retrieve(usuario);
            msg.setDestinatario(usuario);
            lista.add(msg);
        }
        rs.close();
        pst.close();
        return lista;
    }

    public boolean valida(Mensagens msg) {
        boolean ret = false;
        if (msg != null) {
            ret = true;
        }
        return ret;
    }
}
