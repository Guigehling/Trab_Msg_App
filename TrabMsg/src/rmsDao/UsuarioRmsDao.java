/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmsDao;

import java.io.IOException;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import rmsBean.UsuarioRmsBean;

/**
 *
 * @author Guilherme Gehling
 */
public class UsuarioRmsDao {

    private static final String RMSSTORE = "RMSUSUARIO";
    private RecordStore recordStore;

    public void open() throws RecordStoreException {
        this.recordStore = RecordStore.openRecordStore(RMSSTORE, true);
    }

    public void close() throws RecordStoreNotOpenException, RecordStoreException {
        recordStore.closeRecordStore();
    }

    public void clear() {
        try {
            RecordStore.deleteRecordStore(RMSSTORE);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    public void insert(UsuarioRmsBean usuario) throws IOException, RecordStoreNotOpenException, RecordStoreException {
        this.open();
        usuario.setCod_user(1);
        byte[] userBytes = usuario.getBytes();
        int cod = recordStore.addRecord(userBytes, 0, userBytes.length);
        usuario.setCod_user(cod);
        System.out.println("Salvo tamnho=" + recordStore.getNumRecords());
        this.close();
    }

    public void delete(UsuarioRmsBean usuario) throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException {
        this.open();
        recordStore.deleteRecord(usuario.getCod_user());
        this.close();
    }

    public void update(UsuarioRmsBean usuario) throws IOException, RecordStoreNotOpenException, RecordStoreException {
        this.open();
        byte[] registroBytes = usuario.getBytes();
        recordStore.setRecord(usuario.getCod_user(), registroBytes, 0, registroBytes.length);
        this.close();
    }

    public UsuarioRmsBean retrieve() {
        try {
            this.open();
            UsuarioRmsBean usuario = new UsuarioRmsBean();
            usuario.setBytes(recordStore.getRecord(1));
            this.close();
            return usuario;
        } catch (InvalidRecordIDException ex) {
            return null;
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}