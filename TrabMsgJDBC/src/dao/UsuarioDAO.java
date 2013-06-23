/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Guilherme Gehling
 */
public class UsuarioDAO {

    private Connection conexao;

    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Usuario retrieve(Usuario user) throws SQLException {
        Usuario userRet = null;
        String sql = "SELECT cod_user, login FROM usuario WHERE cod_user=?";
        PreparedStatement pst = this.conexao.prepareStatement(sql);
        pst.setInt(1, user.getCod_user());
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            userRet = new Usuario();
            userRet.setCod_user(rs.getInt("cod_user"));
            userRet.setLogin(rs.getString("login"));
        }
        rs.close();
        pst.close();
        return userRet;
    }

    public Usuario achaLogin(String login) throws SQLException {
        Usuario usr = new Usuario();
        String sql = "SELECT COD_USER,LOGIN,SENHA FROM USUARIO WHERE LOGIN=?";
        PreparedStatement pst = this.conexao.prepareStatement(sql);
        pst.setString(1, login);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            usr.setCod_user(rs.getInt("COD_USER"));
            usr.setLogin(rs.getString("LOGIN"));
            usr.setSenha(rs.getString("SENHA"));
        }
        rs.close();
        pst.close();
        return usr;
    }
}
